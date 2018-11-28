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
1. Go to the AWS Console and click on the Lambda link. Note: ensure you are in 'us-east' / 'irland' or you wont be able to use Alexa with Lambda.
2. Click on the Create a Lambda Function or Get Started Now button.
3. Skip the blueprint
4. Name your Lambda Function - sample: "Fritz-Home-Skill".
5. Select the runtime as Java 8
6. Download the latest release jar package from (https://github.com/comtel2000/fritz-home-skill/releases).
7. Select Code entry type as "Upload a .ZIP file" and then upload the 'fritz-home-skill-[VERSION]-jar-with-dependencies.jar' file from the build directory to Lambda
8. Set the Handler as 'org.comtel2000.fritzhome.skill.FritzHomeSpeechletRequestStreamHandler' (this refers to the Lambda RequestStreamHandler file in the zip).
9. Create a basic execution role and click create.
10. Leave the Advanced settings as the defaults. Click Save (not Save and Test).
11. Click "Next" and review the settings then click "Create Function"
12. Click the "Event Sources" tab and select "Add event source"
13. Set the Event Source type as Alexa Skills kit and Enable it now. Click Submit.
14. Copy the *ARN* from the top right to be used later in the Alexa Skill Setup.
15. Fill the environment variables with your application id (amzn1.sdk...) dyndns provider or myfritz.net url and create a [Fritzbox](http://fritz.box) user with the permission home automation.

![env variables](https://github.com/comtel2000/fritz-home-skill/blob/master/doc/aws_lambda.png "ENV settings")

![aws handler](https://github.com/comtel2000/fritz-home-skill/blob/master/doc/aws_handler.png "Handler settings")

### Alexa Skill Setup
1. Go to the [Alexa Console](https://developer.amazon.com/edw/home.html) and click Add a New Skill.
2. Create your language by select Add new Language (the current version only supports german)
3. Set "FritzHome" as the skill name and "fritzbox" as the invocation name, this is what is used to activate your skill. For example you would say: "Alexa, ask fritzbox for device list."
4. Select the Lambda ARN for the skill Endpoint and paste the ARN copied from above. Click Next.
5. Copy the Intent Schema from the included IntentSchema.json.
6. Add Custom Slot Type with name: 'DEVICE_NAMES' and copy the names from the included device_names.txt. Click Add.
7. Copy the Utterances from the included Utterances.txt. Click Next.
8. Go back to the skill Information tab and copy the appId. Paste the appId into the environment variable 'FRITZHOME_APPID', this step makes sure the lambda function only serves request from your private and authorized source.
9. Enable your private test account and skip Publish stuff -> This skill is enabled for testing on your account
10. The skill is now available as your own private skill, done.

![alexa app id](https://github.com/comtel2000/fritz-home-skill/blob/master/doc/app_id.png "Application Id")

![alexa intent](https://github.com/comtel2000/fritz-home-skill/blob/master/doc/intent_schema.png "Intents + Utterances")

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

    User: "Alexa, frage Fritzbox nach dem Energieverbrauch von Flurlicht."
    Alexa: "Der aktuelle Energieverbrauch von Flurlicht liegt bei 10 Watt."
## License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

Trademarks:

All trademarks and logos are the property of their respective owners.

This project is an unofficial pice of software and has nothing in common with the AVM FRITZ!Box (AVM Computersysteme Vertriebs GmbH).

THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.
