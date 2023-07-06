package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.kukuking.entity.Introductions;

import java.io.IOException;
import java.util.List;


@WebServlet("/Filter/Introductions")
public class IntrosServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Introductions> introductionsList = Introductions.buildIntroductionsFromDB();
        session.setAttribute("introductionsList", introductionsList);
        if (req.getParameter("introductionID") == null && !introductionsList.isEmpty()) {
            session.setAttribute("mainIntroduction", introductionsList.get(0));
        } else {
            for (Introductions introduction : introductionsList) {
                if (Integer.parseInt(req.getParameter("introductionID")) == introduction.getIntroductionID()) {
                    session.setAttribute("mainIntroduction", introduction);
                    break;
                }
            }
        }
        req.getRequestDispatcher(BASE + "/Introductions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ifDelete = Integer.parseInt(req.getParameter("ifDelete"));
        int id = Integer.parseInt(req.getParameter("mainID"));
        if(ifDelete == 0){
            Introductions introduction = new Introductions(id, req.getParameter("mainName"), req.getParameter("mainText"));
            Introductions.saveIntroductionToDB(introduction);
        }
        if (ifDelete == 1){
            Introductions.deleteIntroductionFromDB(id);
        }
        resp.sendRedirect(req.getContextPath() + "/Filter/Introductions");
    }
}
