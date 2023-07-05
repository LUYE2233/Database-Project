package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDruid;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    Logger LOGGER = MyDruid.getLogger();
    private static final String BASE = "/WEB-INF";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/test.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info((String) req.getParameter("mainArt"));
    }
}
