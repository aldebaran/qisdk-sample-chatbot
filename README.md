# ChatbotSample

**ChatbotSample** is an Android robotic application that demonstrates how
 to implement a chatbot thanks to the **QiSDK** API.

This sample relies on an online **Dialogflow** agent and a local qichatbot.
The chat engine utilizes both chatbots to provide a dialog whose
progress is structured according to a functional scenario while providing
casual discussion.

* The local qichatbot is in charge of a shopping-oriented conversation whose
  description in qiChat language is provided in a .top resource file.
* The Dialogflow chatbot deals with the small talk.

## Dialogflow ##

For the sake of simplicity, this sample uses a v1 Dialogflow agent.


### Overview class diagram ###

A PlantUML class diagram is available at the root of this project.
The "PlantUML integration" plugin is needed to display it in AndroidStudio,
or you can visit http://www.plantuml.com/plantuml too.