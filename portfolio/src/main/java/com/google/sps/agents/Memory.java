package com.google.sps.agents;

// Imports the Google Cloud client library
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.UserService;
import com.google.protobuf.Value;
import com.google.sps.data.ConversationOutput;
import com.google.sps.data.Pair;
import com.google.sps.utils.UserUtils;
import java.util.List;
import java.util.Map;

/** Memory Agent */
public class Memory implements Agent {
  private final String intentName;
  private String userID;
  private String fulfillment;
  private String display;
  private DatastoreService datastore;
  private UserService userService;

  public Memory(
      String intentName,
      Map<String, Value> parameters,
      UserService userService,
      DatastoreService datastore) {
    this.intentName = intentName;
<<<<<<< HEAD
    this.userService = userService;
    this.datastore = datastore;
=======
>>>>>>> Added memory agent
    setParameters(parameters);
  }

  @Override
  public void setParameters(Map<String, Value> parameters) {
    if (!userService.isUserLoggedIn()) {
      fulfillment = "Please login to access conversation history.";
      return;
    }
    userID = userService.getCurrentUser().getUserId();
    if (intentName.contains("keyword")) {
      findKeyword(parameters);
    }
  }

  private void findKeyword(Map<String, Value> parameters) {
    String word = parameters.get("keyword").getStringValue();
    List<Pair<Entity, List<Entity>>> conversationList =
<<<<<<< HEAD
        UserUtils.getKeywordCommentEntities(datastore, userID, word.toLowerCase());
=======
        UserUtils.getKeywordCommentEntities(datastore, userID, word);
>>>>>>> Added memory agent
    if (conversationList.isEmpty()) {
      fulfillment = "Sorry, unable to find any results including the keyword \"" + word + ".\"";
    } else {
      fulfillment = "Here are all the results including the keyword \"" + word + ".\"";
      ConversationOutput convoOutput = new ConversationOutput(word, conversationList);
      display = convoOutput.toString();
    }
  }

  @Override
  public String getOutput() {
    return fulfillment;
  }

  @Override
  public String getDisplay() {
<<<<<<< HEAD
    System.out.println(display);
=======
>>>>>>> Added memory agent
    return display;
  }

  @Override
  public String getRedirect() {
    return null;
  }
}
