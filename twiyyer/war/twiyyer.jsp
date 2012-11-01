<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>

<%
    String hashtag = request.getParameter("hashtag");
    if (hashtag == null) {
        hashtag = "NaN";
    }
    pageContext.setAttribute("hashtag", hashtag);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>

<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name with Twiys you post.</p>
<%
    }
%>

<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key twiyKey = KeyFactory.createKey("TwiysHashtag", hashtag);

    Query query = new Query("Twiy", twiyKey).addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> twiys = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));

    if (twiys.isEmpty()) {
        %>
        <p>Twiys-Hashtag '${fn:escapeXml(hashtag)}' has no messages.</p>
        <%
    } else {
        %>
        <p>Messages in Twiys-Hashtag '${fn:escapeXml(hashtag)}'.</p>
        <%
        for (Entity twiy : twiys) {
            pageContext.setAttribute("twiys_content",
                                     twiy.getProperty("twiyscontent"));
            if (twiy.getProperty("user") == null) {
                %>
                <p>Anonymous wrote:</p>
                <%
            } else {
                pageContext.setAttribute("twiy_user",
                                         twiy.getProperty("user"));
                %>
                <p><b>${fn:escapeXml(twiy_user.nickname)}</b> wrote:</p>
                <%
            }
            %>
            <blockquote>${fn:escapeXml(twiys_content)}</blockquote>
            <%
        }
    }
%>


  <form action="/addtwiys" method="post">
    <div><textarea name="twiyscontent" rows="3" cols="60"></textarea></div>
    <div><input type="submit" value="Post Twiy" /></div>
    <input type="hidden" name="hashtag" value="${fn:escapeXml(hashtag)}"/>
  </form>

  </body>
</html>