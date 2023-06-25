package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDatabaseUtil;

import java.io.IOException;

@WebServlet("/Filter/SignUp")

public class SignUpServlet extends HttpServlet {

    private static final String BASE = "/WEB-INF";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String userID = req.getParameter("userID");
        MyDatabaseUtil.signUp(userName,password,userID);
        req.getRequestDispatcher(BASE + "/Login.jsp").forward(req, resp);
    }
}
