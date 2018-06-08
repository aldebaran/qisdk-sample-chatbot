# ChatbotSample

ChatbotSample is an Android application for **Pepper the robot** that
demonstrates how to implement a chatbot thanks to the **Android QiSDK**.
(http://android.ald.softbankrobotics.com)


This sample relies on an online **Dialogflow** agent and a local **qichatbot**.
The chat engine utilizes both chatbots to provide a dialog whose
progress is structured according to a functional scenario while providing
casual discussion.

* The local qichatbot is in charge of a shopping-oriented conversation whose
  description in qiChat language is provided in a .top resource file.
* The Dialogflow chatbot deals with the small talk plus several simple
  rules about wine.


## Minimum configuration

* Pepper 1.9
* API Level 3


## Getting started ##

For the sake of simplicity, this sample uses a **Dialogflow v1 agent**.
The class DialogflowChatbot.java shows how to interact with such an agent.

### Creating a Dialogflow agent ###

In order to run the application, you need to create first your own agent
via the Dialogflow console (https://console.dialogflow.com).

Ensure you select *V1 API* in the *General* tab of the settings page.

### Client access token ###
Once your agent is created, you should make the provided **client access token**
available to the Android project. This token is needed to connect the
application to the agent.

In the **global gradle.properties** file located in your *~/.gradle* folder,
add the following line:

```
CHATBOT_SAMPLE_DIALOGFLOW_TOKEN=your_client_access_token
```
This avoids keeping the token under version control.

### Populating the agent ###

Your Dialogflow agent may be populated either by hand (via the console)
or by importing an already made agent. Learning how to create Dialogflow
intents goes beyond the scope of this sample. Therefore we provide you
with a sample agent description you just have to import in your own agent.

In the Dialogflow console:
* Go to the *Settings* page of the agent
* Choose the *Export and Import* option
* Click on *Restore from zip*
* Then follow instructions to upload the [DialogflowChatbotAgent.zip] file.

This sample agent provides basic interaction about french wine and
and some customized small talk.


## Additional resources ##

#### Overview class diagram ####
A PlantUML [class diagram](classDiagram.plantuml) is available at the root of this project.
The *PlantUML integration* plugin is needed to display it in AndroidStudio.
Alternatively you can visit http://www.plantuml.com/plantuml.

## Licence ##

See the [COPYING](COPYING.md) file for the license.