package org.thefouthgroup.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thefouthgroup.database.MyDatabaseUtil;

import java.io.IOException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/Login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        if (MyDatabaseUtil.login(userName, password)) {
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("userName", userName);
            String userID = MyDatabaseUtil.getUserID(userName);
            httpSession.setAttribute("userID", userID);
            if (MyDatabaseUtil.getUserGroup(userID) != 2) {
                req.getRequestDispatcher(BASE + "/Teacher.jsp").forward(req, resp);
            }else {
                req.getRequestDispatcher(BASE + "/Student.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher(BASE + "/Login.html").forward(req, resp);
        }
    }
}
