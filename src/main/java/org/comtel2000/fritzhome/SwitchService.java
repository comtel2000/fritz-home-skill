package org.comtel2000.fritzhome;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.comtel2000.fritzhome.xml.Group;
import org.comtel2000.fritzhome.xml.Device;
import org.comtel2000.fritzhome.xml.Devicelist;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SwitchService {

  private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SwitchService.class);

  public final static String ENV_USER = "FRITZHOME_USER";
  public final static String ENV_PWD = "FRITZHOME_PWD";
  public final static String ENV_URL = "FRITZHOME_URL";

  private final long SID_CACHE_TIMEOUT = TimeUnit.MINUTES.toMillis(10);

  private String sidCache;
  private long sidCacheTime = -1;

  private final static String EMPTY_SID = "0000000000000000";

  private final static String SID_REQUEST_URL = "/login_sid.lua";
  private final static String SID_CREDENTIAL_URL = "/login_sid.lua?username=%s&response=%s";

  private final static String SID_SWITCHCMD_URL = "/webservices/homeautoswitch.lua?switchcmd=%s&sid=%s";
  private final static String SID_AIN_SWITCHCMD_URL = "/webservices/homeautoswitch.lua?ain=%s&switchcmd=%s&sid=%s";
  private final static String SID_PARAM_SWITCHCMD_URL = "/webservices/homeautoswitch.lua?switchcmd=%s&param=%s&sid=%s";
  private final static String SID_AIN_PARAM_SWITCHCMD_URL = "/webservices/homeautoswitch.lua?ain=%s&switchcmd=%s&param=%s&sid=%s";

  private final DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();

  /**
   * FRITZ!BOX firmware >= FRITZ!OS 05.55 (homeautoswitch.lua)
   * <p>
   * FRITZ!OS 05.58-26414 BETA<br>
   * FRITZ!OS 05.59-26514 BETA<br>
   * 
   * @see <a href=
   *      "https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AHA-HTTP-Interface.pdf">AHA-HTTP-Interface.pdf</a>
   */
  public SwitchService() {

    try {
      trustAllSslCertificate();
    } catch (KeyManagementException | NoSuchAlgorithmException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void trustAllSslCertificate() throws NoSuchAlgorithmException, KeyManagementException {
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkServerTrusted(X509Certificate[] c, String s) throws CertificateException {
        logger.debug("ServerTrusted authType: {}", s);
      }

      @Override
      public void checkClientTrusted(X509Certificate[] c, String s) throws CertificateException {
        logger.debug("ClientTrusted authType: {}", s);
      }
    } };

    final SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());

    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    HostnameVerifier allHostsValid = (host, session) -> {
      logger.warn("auto verify host: {}", host);
      return true;
    };
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
  }

  private void invalidateSidCache() {
    sidCache = null;
    sidCacheTime = -1;
  }


  public String getCachedSessionId() throws IOException, ParserConfigurationException, SAXException, NoSuchAlgorithmException {
    if (sidCache == null || System.currentTimeMillis() - sidCacheTime > SID_CACHE_TIMEOUT) {
      sidCache = getSessionId();
      sidCacheTime = System.currentTimeMillis();
      logger.info("update session id: {}", sidCache);
    }
    return sidCache;
  }

  private String getSessionId() throws IOException, ParserConfigurationException, SAXException, NoSuchAlgorithmException {

    HttpURLConnection con = createConnection(SID_REQUEST_URL);

    handleResponseCode(con.getResponseCode());

    Document doc = xmlFactory.newDocumentBuilder().parse(con.getInputStream());
    logger.trace("response:\n{}", XmlUtils.docToString(doc, true));
    if (doc == null) {
      throw new IOException("SessionInfo element not available");
    }
    String sid = XmlUtils.getValue(doc.getDocumentElement(), "SID");

    if (EMPTY_SID.equals(sid)) {
      String challenge = XmlUtils.getValue(doc.getDocumentElement(), "Challenge");
      sid = getSessionId(challenge);
    }

    if (sid == null || EMPTY_SID.equals(sid)) {
      throw new IOException("sid request failed: " + sid);
    }
    return sid;

  }

  private void handleResponseCode(int response) throws IOException {
    switch (response) {
      case HttpURLConnection.HTTP_OK:
        logger.debug("positive response: {}", response);
        break;
      case HttpURLConnection.HTTP_FORBIDDEN:
        throw new IOException("access denied");
      default:
        throw new IOException("response code: " + response);
    }
  }

  private String getSessionId(String challenge) throws IOException, ParserConfigurationException, SAXException, NoSuchAlgorithmException {
    HttpURLConnection con =
        createConnection(String.format(SID_CREDENTIAL_URL, System.getenv(ENV_USER), FritzUtils.getChallengeResponse(challenge, System.getenv(ENV_PWD))));

    handleResponseCode(con.getResponseCode());

    Document doc = xmlFactory.newDocumentBuilder().parse(con.getInputStream());
    logger.trace("response:\n{}", XmlUtils.docToString(doc, true));
    String sid = XmlUtils.getValue(doc.getDocumentElement(), "SID");
    return sid;
  }

  private HttpURLConnection createConnection(String path) throws MalformedURLException, IOException {
    URL url = new URL(getHost(System.getenv(ENV_URL), path));
    logger.debug("Sending request to URL: {}", url);
    return (HttpURLConnection) url.openConnection();
  }

  private String getHost(String host, String path) throws IOException {
    if (host == null || host.isEmpty()) {
      throw new IOException("host must not be empty");
    }
    return host + path;
  }

  public void validateConnection() throws Exception {
    String sid = getCachedSessionId();
    if (sid == null || EMPTY_SID.equals(sid) || sid.length() != EMPTY_SID.length()) {
      throw new IOException("invalid SID received: " + sid);
    }
  }

  public List<SwitchDevice> getSwitchDevices() throws Exception {
    return getSwitchDevices(getDevicelist());
  }

  public List<SwitchDevice> getSwitchDevices(Devicelist devList) throws Exception {
    if (devList == null) {
      return Collections.emptyList();
    }
    List<SwitchDevice> list = devList.getDevice().stream().map(d -> {
      FritzUtils.isSwitch(d.getFunctionbitmask());
      SwitchDevice dev = new SwitchDevice(d.getIdentifier(), d.getFunctionbitmask());
      dev.setName(d.getName());
      dev.setPresent(d.isPresent());
	  dev.setId(String.valueOf(d.getId()));
      Optional.ofNullable(d.getSwitch()).ifPresent(v -> {
        dev.setState(v.getState());
        dev.setMode(v.getMode());
        dev.setLock(v.getLock());
      });
      Optional.ofNullable(d.getPowermeter()).ifPresent(v -> {
        dev.setPower(v.getPower());
        dev.setEnergy(v.getEnergy());
      });
      Optional.ofNullable(d.getTemperature()).ifPresent(v -> dev.setTemperature(v.getCelsius() + v.getOffset()));
      return dev;
    }).collect(Collectors.toList());

    if (devList.getGroup().isEmpty()) {
      return list;
    }
    List<SwitchDevice> groups = devList.getGroup().stream().map(d -> {
      SwitchDevice dev = new SwitchDevice(d.getIdentifier(), d.getFunctionbitmask());
      dev.setName(d.getName());
      dev.setGroup(true);
	  	dev.setId(String.valueOf(d.getId()));
      dev.setPresent(d.isPresent());
	  	Group.Groupinfo info = d.getGroupinfo();
	  	if (info != null) {
	  		dev.setGroupmembers(info.getMembers());
	  		dev.setMasterdevice(String.valueOf(info.getMasterdeviceid()));
	  	}
      Optional.ofNullable(d.getSwitch()).ifPresent(v -> {
        dev.setState(v.getState());
        dev.setMode(v.getMode());
        dev.setLock(v.getLock());
      });
		  Optional.ofNullable(d.getTemperature()).ifPresent(v -> dev.setTemperature(v.getCelsius() + v.getOffset()));
      return dev;
    }).collect(Collectors.toList());
    list.addAll(groups);
    return list;
  }

  	public int getDeviceTemp(final SwitchDevice dev, SwitchCmd cmd){
		try {
			String sid = getCachedSessionId();
			String ain = dev.getAin();
			
			String path = (ain == null) ? String.format(SID_SWITCHCMD_URL, cmd, sid) : String.format(SID_AIN_SWITCHCMD_URL, ain, cmd, sid);
			
			HttpURLConnection con = createConnection(path);

			handleResponseCode(con.getResponseCode());
			
			Scanner scanner = new Scanner(con.getInputStream(), StandardCharsets.UTF_8.name());
			String rtrn = scanner.useDelimiter("\\A").next().trim();
			return Integer.parseInt(FritzUtils.readTemperature(rtrn));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
			
		}
		
	}
 
  
  
  public void setTemp(final SwitchDevice dev, int temp) throws Exception {
    logger.debug("try to change Temp: {}°", temp);
    String sid = getCachedSessionId();
	
	String rtrn = FritzUtils.writeTemperature(temp);
	
	String temperature;
	switch (rtrn){
		case "ON": temperature = String.valueOf(FritzUtils.writeTemperature(getDeviceTemp(dev, SwitchCmd.gethkrkomfort)));
		break;
		case "OFF": temperature = String.valueOf(FritzUtils.writeTemperature(getDeviceTemp(dev, SwitchCmd.gethkrabsenk)));
		break;
		default: temperature = rtrn;
		break;
	}
	
    dev.setTemperature(sendTempCmd(dev.getAin(), SwitchCmd.sethkrtsoll, sid, String.valueOf(temperature)));
	
	return;
  }
  
  

  public void setDeviceState(final SwitchDevice dev, boolean on) throws Exception {
    logger.debug("try to change device state: {} to {}", dev, on);
    String sid = getCachedSessionId();
    dev.setState(sendSwitchCmd(dev.getAin(), on ? SwitchCmd.setswitchon : SwitchCmd.setswitchoff, sid));
  }

  public void setToggleDeviceState(final SwitchDevice dev) throws Exception {
    logger.debug("try to toggle device state: {}", dev);
    String sid = getCachedSessionId();
    dev.setState(sendSwitchCmd(dev.getAin(), SwitchCmd.setswitchtoggle, sid));
  }

  private String sendSwitchCmd(String ain, SwitchCmd cmd, String sid) throws MalformedURLException, IOException {

    String path = (ain == null) ? String.format(SID_SWITCHCMD_URL, cmd, sid) : String.format(SID_AIN_SWITCHCMD_URL, ain, cmd, sid);

    logger.debug("send: {}", path);
    HttpURLConnection con = createConnection(path);

    try {
      handleResponseCode(con.getResponseCode());
    } catch (IOException e) {
      invalidateSidCache();
      throw e;
    }

    sidCacheTime = System.currentTimeMillis();

    StringBuilder response = new StringBuilder();
    try (Scanner scanner = new Scanner(con.getInputStream())) {
      while (scanner.hasNextLine()) {
        response.append(scanner.nextLine());
      }
    }
    if (response.indexOf("filename=/webservices/homeautoswitch.lua") > 0) {
      throw new IOException(response.toString());
    }
    logger.debug("get: {}", response);
    return response.toString();
  }
  
  
  private int sendTempCmd(String ain, SwitchCmd cmd, String sid, String temp) throws MalformedURLException, IOException {
  
    String path = (ain == null) ? String.format(SID_PARAM_SWITCHCMD_URL, cmd, temp, sid) : String.format(SID_AIN_PARAM_SWITCHCMD_URL, ain, cmd, temp, sid);

    logger.debug("send: {}", path);
    HttpURLConnection con = createConnection(path);

    try {
      handleResponseCode(con.getResponseCode());
    } catch (IOException e) {
      invalidateSidCache();
      throw e;
    }
	
	return Integer.parseInt(temp)/2;
	
  }
  

  public void refreshSwitchDevice(final SwitchDevice dev) throws Exception {

    Devicelist devList = getDevicelist();
    if (devList == null) {
      return;
    }

    Optional<Device> device = devList.getDevice().stream().filter(d -> dev.getAin().equals(d.getIdentifier())).findAny();
    device.ifPresent(d -> {
      dev.setName(d.getName());
      dev.setPresent(d.isPresent());
      Optional.ofNullable(d.getSwitch()).ifPresent(v -> {
        dev.setState(v.getState());
        dev.setMode(v.getMode());
        dev.setLock(v.getLock());
      });
      Optional.ofNullable(d.getPowermeter()).ifPresent(v -> {
        dev.setPower(v.getPower());
        dev.setEnergy(v.getEnergy());
      });
      Optional.ofNullable(d.getTemperature()).ifPresent(v -> dev.setTemperature(v.getCelsius() + v.getOffset()));
      logger.debug("updated: {}", dev);
    });
  }


  void readDevicelist() throws IOException, ParserConfigurationException, SAXException, NoSuchAlgorithmException {
    String sid = getCachedSessionId();
    String path = String.format(SID_SWITCHCMD_URL, SwitchCmd.getdevicelistinfos, sid);
    logger.debug("send: {}", path);
    HttpURLConnection con = createConnection(path);
	
    try (Scanner scanner = new Scanner(con.getInputStream(), StandardCharsets.UTF_8.name())) {
      logger.debug("response:\n{}", scanner.useDelimiter("\\A").next());
    }
  }

  public Devicelist getDevicelist() throws Exception {
    String sid = getCachedSessionId();

    String path = String.format(SID_SWITCHCMD_URL, SwitchCmd.getdevicelistinfos, sid);

    logger.debug("send: {}", path);
    HttpURLConnection con = createConnection(path);

    try {
      handleResponseCode(con.getResponseCode());
    } catch (IOException e) {
      invalidateSidCache();
      throw e;
    }

    sidCacheTime = System.currentTimeMillis();

    Unmarshaller unmarshaller = JAXBContext.newInstance(Devicelist.class).createUnmarshaller();

    Devicelist devList = null;

    try (BufferedInputStream is = new BufferedInputStream(con.getInputStream())) {
		
      devList = (Devicelist) unmarshaller.unmarshal(is);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    if (devList == null) {
      throw new IOException(String.format("read device list failed: %s", SwitchCmd.getdevicelistinfos));
    }
    return devList;
  
  }

}
