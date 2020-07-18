package com.google.sps.servlets;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.utils.OAuthHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/oauth2")
public class OAuthServlet extends AbstractAuthorizationCodeServlet {

  UserService userService = UserServiceFactory.getUserService();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // System.out.println("Got to do get");
    // OAuthHelper helper = new OAuthHelper();
    // System.out.println(helper.loadUserCredential(userService.getCurrentUser().getUserId()).getAccessToken());
    response.sendRedirect("/");
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return OAuthHelper.createRedirectUri();
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return OAuthHelper.createFlow();
  }

  @Override
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    return userService.getCurrentUser().getUserId();
  }
}
