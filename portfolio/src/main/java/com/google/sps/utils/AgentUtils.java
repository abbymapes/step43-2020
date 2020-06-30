package com.google.sps.utils;

// Imports the Google Cloud client library
import com.google.protobuf.ByteString;
import com.google.protobuf.Value;
import com.google.sps.agents.*;
import com.google.sps.data.DialogFlowClient;
import com.google.sps.data.Output;
import java.util.Map;

/** Identifies agent from Dialogflow API Query result and creates Output object */
public class AgentUtils {

  public static Output getOutput(DialogFlowClient queryResult, String languageCode) {
    String fulfillment = null;
    String display = null;
    String redirect = null;
    byte[] byteStringToByteArray = null;
    Agent object = null;

    String detectedIntent = queryResult.getIntentName();
    String agentName = getAgentName(detectedIntent);
    String intentName = getIntentName(detectedIntent);

    // Retrieve detected input from DialogFlow result.
    String inputDetected = queryResult.getQueryText();
    inputDetected = inputDetected.equals("") ? " (null) " : inputDetected;
    Map<String, Value> parameterMap = queryResult.getParameters();

    object = getAgent(agentName, intentName, parameterMap);
    if (object != null) {
      fulfillment = object.getOutput();
      fulfillment = fulfillment == null ? queryResult.getFulfillmentText() : fulfillment;
      display = object.getDisplay();
      redirect = object.getRedirect();
    } else {
      fulfillment = queryResult.getFulfillmentText();
    }
    fulfillment = fulfillment.equals("") ? "Can you repeat that?" : fulfillment;

    byteStringToByteArray = getByteStringToByteArray(fulfillment, languageCode);
    Output output =
        new Output(inputDetected, fulfillment, byteStringToByteArray, display, redirect);
    return output;
  }

  private static Agent getAgent(
      String agentName, String intentName, Map<String, Value> parameterMap) {
    switch (agentName) {
      case "calculator":
        return new Tip(intentName, parameterMap);
      case "currency":
        return new Currency(intentName, parameterMap);
      case "date":
        return new Date(intentName, parameterMap);
      case "language":
        return new Language(intentName, parameterMap);
      case "name":
        return new Name(intentName, parameterMap);
      case "reminders":
        return new Reminders(intentName, parameterMap);
      case "time":
        return new Time(intentName, parameterMap);
      case "translate":
        return new TranslateAgent(intentName, parameterMap);
      case "units":
        return new UnitConverter(intentName, parameterMap);
      case "weather":
        return new Weather(intentName, parameterMap);
      case "web":
        return new WebSearch(intentName, parameterMap);
      default:
        return null;
    }
  }

  private static String getAgentName(String detectedIntent) {
    String[] intentList = detectedIntent.split("\\.", 2);
    return intentList[0];
  }

  private static String getIntentName(String detectedIntent) {
    String[] intentList = detectedIntent.split("\\.", 2);
    String intentName = detectedIntent;
    if (intentList.length > 1) {
      intentName = intentList[1];
    }
    return intentName;
  }

  public static byte[] getByteStringToByteArray(String fulfillment, String languageCode) {
    byte[] byteArray = null;
    try {
      ByteString audioResponse = SpeechUtils.synthesizeText(fulfillment, languageCode);
      byteArray = audioResponse.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return byteArray;
  }

  public static String getLanguageCode(String language) {
    if (language == null) {
      return "en-US";
    }
    switch (language) {
      case "Chinese":
        return "zh-CN";
      case "English":
        return "en-US";
      case "French":
        return "fr";
      case "German":
        return "de";
      case "Hindi":
        return "hi";
      case "Italian":
        return "it";
      case "Japanese":
        return "ja";
      case "Korean":
        return "ko";
      case "Portuguese":
        return "pt";
      case "Russian":
        return "ru";
      case "Spanish":
        return "es";
      case "Swedish":
        return "sv";
      default:
        return null;
    }
  }
}
