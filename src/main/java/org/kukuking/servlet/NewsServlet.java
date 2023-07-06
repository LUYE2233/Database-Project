package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.kukuking.entity.News;

import java.io.IOException;
import java.util.List;


@WebServlet("/Filter/News")
public class NewsServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<org.kukuking.entity.News> newsList = org.kukuking.entity.News.buildNewsFromDB();
        session.setAttribute("newsList", newsList);
        if (req.getParameter("newsID") == null && !newsList.isEmpty()) {
            session.setAttribute("mainNews", newsList.get(0));
        } else {
            for (org.kukuking.entity.News news : newsList) {
                if (Integer.parseInt(req.getParameter("newsID")) == news.getNewsID()) {
                    session.setAttribute("mainNews", news);
                    break;
                }
            }
        }
        req.getRequestDispatcher(BASE + "/News.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ifDelete = Integer.parseInt(req.getParameter("ifDelete"));
        int id = Integer.parseInt(req.getParameter("mainID"));
        if(ifDelete == 0){
            org.kukuking.entity.News news = new org.kukuking.entity.News(id, req.getParameter("mainTitle"), req.getParameter("mainArt"));
            org.kukuking.entity.News.saveNewsToDB(news);
        }
        if (ifDelete == 1){
            News.deleteNewsFromDB(id);
        }
        resp.sendRedirect(req.getContextPath() + "/Filter/News");
    }
}
