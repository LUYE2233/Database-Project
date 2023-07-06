package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.kukuking.entity.Labs;

import java.io.IOException;
import java.util.List;


@WebServlet("/Filter/Labs")
public class LabsServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Labs> labsList = Labs.buildLabsFromDB();
        session.setAttribute("labsList", labsList);
        if (req.getParameter("labID") == null && !labsList.isEmpty()) {
            session.setAttribute("mainLab", labsList.get(0));
        } else {
            for (Labs lab : labsList) {
                if (Integer.parseInt(req.getParameter("labID")) == lab.getLabID()) {
                    session.setAttribute("mainLab", lab);
                    break;
                }
            }
        }
        req.getRequestDispatcher(BASE + "/Labs.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ifDelete = Integer.parseInt(req.getParameter("ifDelete"));
        int id = Integer.parseInt(req.getParameter("mainID"));
        if(ifDelete == 0){
            Labs lab = new Labs(id, req.getParameter("mainName"), req.getParameter("mainText"));
            Labs.saveLabToDB(lab);
        }
        if (ifDelete == 1){
            Labs.deleteLabFromDB(id);
        }
        resp.sendRedirect(req.getContextPath() + "/Filter/Labs");
    }
}
