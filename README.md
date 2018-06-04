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
* The Dialogflow chatbot deals with the small talk.

## Prerequisites

* NAOqi version: 2.9.0 or greater.

## Getting started ##

### Creating a Dialogflow agent ###

For the sake of simplicity, this sample uses a **v1** Dialogflow agent.
In order to run the application, you need to create first your own agent
via the Dialogflow console (https://console.dialogflow.com).

Ensure you select *V1 API* in the *General* tab of the settings page.

### Client access token ###
Once your agent is created, you should make the provided **client access token**
you are provided available to the project.
In the **global gradle.properties** file located in your *~/.gradle* folder,
add the following line:

```
CHATBOT_SAMPLE_DIALOGFLOW_TOKEN=your_client_access_token
```
This avoids keeping the token under version control.

### Enabling and customizing small talk ###

In the Dialogflow console, go the the "Small talk" page and ensure this
feature is **enabled**. This will allow your agent to answer to popular
requests without extra efforts.

However you may optionally customize several reponses to adapt them to
Pepper or to your application.

For example, in the topic "About agent", a possible answer to the question
*Who are you?* is *I'm Pepper the robot*. As an answer to the question *How
old are you?*, you may enter *I was born in 2014*.

Of course, only enabling the "small talk" option of your Dialogflow agent
is the minimal approach. You may also add Intents to your agent, but this
goes beyond the scope of this sample. See Dialogflow documentation.


## Additional resources ##

#### Overview class diagram ####
A PlantUML [class diagram](classDiagram.plantuml) is available at the root of this project.
The "PlantUML integration" plugin is needed to display it in AndroidStudio,
or you can visit http://www.plantuml.com/plantuml too.

## Licence ##

See the [COPYING](COPYING.md) file for the license.