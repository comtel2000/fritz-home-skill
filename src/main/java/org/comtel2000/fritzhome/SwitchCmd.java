package org.comtel2000.fritzhome;

/**
 * switchcmd tag
 * <p>
 * 
 * @author comtel
 * 
 * @see <a href=
 *      "https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AHA-HTTP-Interface.pdf">AHA-HTTP-Interface.pdf</a>
 */
public enum SwitchCmd {

  /**
   * Liefert die kommaseparierte AIN/MAC Liste aller bekannten Steckdosen<br>
   * kommaseparierte AIN/MAC-Liste, leer wenn keine Steckdose bekannt
   */
  getswitchlist,

  /**
   * Schaltet Steckdose ein "1"
   */
  setswitchon,

  /**
   * Schaltet Steckdose aus "0"
   */
  setswitchoff,
  /**
   * Toggeln der Steckdose ein/aus<br>
   * "0" oder "1" (Steckdose aus oder an)
   */
  setswitchtoggle,

  /**
   * Ermittelt Schaltzustand der Steckdose<br>
   * "0" oder "1" (Steckdose aus oder an), "inval" wenn unbekannt
   */
  getswitchstate,
  /**
   * Ermittelt Verbindungsstatus des Aktors<br>
   * "0" oder "1" für Gerät nicht verbunden bzw. verbunden. Bei Verbindungsverlust wechselt der
   * Zustand erst mit einigen Minuten Verzögerung zu "0".
   */
  getswitchpresent,

  /**
   * Ermittelt aktuell über die Steckdose entnommene Leistung<br>
   * Leistung in mW, "inval" wenn unbekannt
   */
  getswitchpower,
  /**
   * Liefert die über die Steckdose entnommene Ernergiemenge seit Erstinbetriebnahme oder
   * Zurücksetzen der Energiestatistik<br>
   * Energie in Wh, "inval" wenn unbekannt
   */
  getswitchenergy,
  /**
   * Liefert Bezeichner des Aktors (Name)
   */
  getswitchname,

  /**
   * Liefert die grundlegenden Informationen aller SmartHome-Geräte<br>
   * XML-Format mit grundlegenden und funktionsspezifischen Informationen
   */
  getdevicelistinfos,

  /**
   * Letzte Temperaturinformation des Aktors<br>
   * Temperatur-Wert in 0,1 °C, negative und positive Werte möglich<br>
   * Bsp. "200" bedeutet 20°C
   */
  gettemperature,

  /**
   * Für HKR aktuell eingestellte Solltemperatur<br>
   * Temperatur-Wert in 0,5 °C, Wertebereich: 16 – 56 8 bis 28°C, 16 <= 8°C, 17 = 8,5°C...... 56 >=
   * 28°C 254 = ON , 253 = OFF
   */
  gethkrtsoll,

  /**
   * Für HKR-Zeitschaltung eingestellte Komforttemperatur<br>
   * Temperatur-Wert in 0,5 °C, Wertebereich: 16 – 56 8 bis 28°C, 16 <= 8°C, 17 = 8,5°C...... 56 >=
   * 28°C 254 = ON , 253 = OFF
   */
  gethkrkomfort,

  /**
   * Für HKR-Zeitschaltung eingestellte Spartemperatur<br>
   * Temperatur-Wert in 0,5 °C, Wertebereich: 16 – 56 8 bis 28°C, 16 <= 8°C, 17 = 8,5°C...... 56 >=
   * 28°C 254 = ON , 253 = OFF
   */
  gethkrabsenk,

  /**
   * HKR Solltemperatur einstellen. Mit dem "param" Get-Parameter wird die Solltemperatur
   * übergeben.<br>
   * Temperatur-Wert in 0,5 °C, Wertebereich: 16 – 56 8 bis 28°C, 16 <= 8°C, 17 = 8,5°C...... 56 >=
   * 28°C 254 = ON , 253 = OFF
   */
  sethkrtsoll;
}
