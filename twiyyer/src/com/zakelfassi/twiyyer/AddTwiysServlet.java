package com.zakelfassi.twiyyer;


import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AddTwiysServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AddTwiysServlet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String content = req.getParameter("twiycontent");
        if (content == null) {
            content = "(Empty)";
        }
        if (user != null) {
            log.info("Twiy by " + user.getNickname() + ": " + content);
        } else {
            log.info("Anonymous Twiy : " + content);
        }
        resp.sendRedirect("/twiyyer.jsp");
    }
}