# Alexa FritzBox Home Skill
Control your AVM FritzBox Home devices over [AWS Lambda](http://aws.amazon.com/lambda) by your own private developer skill.

### Why not as Alexa Home Skill Kit?
The current version has a lot of restrictions and missing functionality, no Java SDK, etc.

### Why not as public skill?
There is no fritzbox internal OAuth and your connection data must be managed by a cloud OAuth service or additional hardware in your home network.
Alternatives can be found here:
[https://www.eclipse.org/smarthome/](https://www.eclipse.org/smarthome/)

## Setup
To run this fritzbox home skill you need to do two things. The first is to deploy the fritz-home-skill code in lambda, and the second is to configure your private Alexa skill to use Lambda.

### AWS Lambda Setup
1. Go to the AWS Console and click on the Lambda link. Note: ensure you are in us-east/irland or you wont be able to use Alexa with Lambda.
2. Click on the Create a Lambda Function or Get Started Now button.
3. Skip the blueprint
4. Name the Lambda Function "Fritz-Home-Skill".
5. Select the runtime as Java 8
6. Go to the project root directory containing pom.xml, change the fritzhome.user + password properties (remove the url to skip connection tests) and run 'mvn clean package -P jar'. This will generate a zip file named "fritz-home-skill-0.0.1-SNAPSHOT-jar-with-dependencies.jar" in the target directory.
7. Select Code entry type as "Upload a .ZIP file" and then upload the "fritz-home-skill-0.0.1-SNAPSHOT-jar-with-dependencies.jar" file from the build directory to Lambda
8. Set the Handler as "org.comtel2000.fritzhome.skill.FritzHomeSpeechletRequestStreamHandler" (this refers to the Lambda RequestStreamHandler file in the zip).
9. Create a basic execution role and click create.
10. Leave the Advanced settings as the defaults.
11. Click "Next" and review the settings then click "Create Function"
12. Click the "Event Sources" tab and select "Add event source"
13. Set the Event Source type as Alexa Skills kit and Enable it now. Click Submit.
14. Copy the ARN from the top right to be used later in the Alexa Skill Setup.
15. Fill the environment variables with your dyndns provider or myfritz.net url and create a [Fritzbox](http://fritz.box) user with the permission home automation.

![env variables] (doc/fritz_url.png)

![aws handler] (doc/aws_handler.png)

### Alexa Skill Setup
1. Go to the [Alexa Console](https://developer.amazon.com/edw/home.html) and click Add a New Skill.
2. Create your language by select Add new Language (the current version only supports german)
3. Set "FritzHome" as the skill name and "fritzbox" as the invocation name, this is what is used to activate your skill. For example you would say: "Alexa, ask fritzbox for device list."
4. Select the Lambda ARN for the skill Endpoint and paste the ARN copied from above. Click Next.
5. Copy the Intent Schema from the included IntentSchema.json.
6. Copy the Utterances from the included SampleUtterances.txt. Click Next.
7. Go back to the skill Information tab and copy the appId. Paste the appId into the FritzHomeSpeechletRequestStreamHandler.java file for the variable supportedApplicationIds,
   then update the lambda source zip file with this change and upload to lambda again, this step makes sure the lambda function only serves request from authorized source.
8. Enable your private test account and skip Publish stuff -> This skill is enabled for testing on your account
9. The skill is now available as your own private skill, done.

## Howto use
### dialogs:
    User: "Alexa, frage Fritzbox nach der Temperatur im Büro."
    Alexa: "Die Temperatur beträgt 22,3 Grad Celsius."

    User: "Alexa, frage Fritzbox nach der Geräteliste."
    Alexa: "Das Geräte xy ist eingeschaltet, die Temp..."

    User: "Alexa, öffne Fritzbox und schalte Gerät Büro an."
    Alexa: "Ok."

    User: "Alexa, öffne Fritzbox und schalte Gruppe Außenbeleuchtung aus."
    Alexa: "Ok."
## License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
