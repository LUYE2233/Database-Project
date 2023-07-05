package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDatabaseUtil;

import java.io.IOException;


@WebServlet("/Reset")
public class ResetServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/Reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userID = req.getParameter("userID");
        String password = req.getParameter("password");
        String oldPassword = req.getParameter("oldPassword");
        if (MyDatabaseUtil.login(userID, oldPassword)) {
            MyDatabaseUtil.reset(userID, password);
            req.getRequestDispatcher(BASE + "/Login.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher(BASE + "/Reset.jsp").forward(req, resp);
        }
    }
}
