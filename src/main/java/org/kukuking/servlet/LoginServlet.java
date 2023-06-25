package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.entity.User;

import java.io.IOException;

@WebServlet("/Filter/Login")
public class LoginServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userID = req.getParameter("userID");
        String password = req.getParameter("password");
        if (MyDatabaseUtil.login(userID, password)) {
            User user = MyDatabaseUtil.buildUserFromDB(userID);
            req.getSession().setAttribute("user",user);
            if (user.getGroupID() != 2) {
                req.getRequestDispatcher(BASE + "/Teacher.jsp").forward(req, resp);
            }else {
                req.getRequestDispatcher(BASE + "/Student.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("tips","login failed");
            req.getRequestDispatcher(BASE + "/Login.jsp").forward(req, resp);
        }
    }
}
