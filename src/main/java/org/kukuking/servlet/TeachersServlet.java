package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.kukuking.entity.Teachers;
import java.io.IOException;
import java.util.List;


@WebServlet("/Filter/Teachers")
public class TeachersServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Teachers> teachersList = Teachers.buildTeachersFromDB();
        session.setAttribute("teachersList", teachersList);
        if (req.getParameter("teacherID") == null && !teachersList.isEmpty()) {
            session.setAttribute("mainTeacher", teachersList.get(0));
        } else {
            for (Teachers teachers : teachersList) {
                if (Integer.parseInt(req.getParameter("teacherID")) == teachers.getTeacherID()) {
                    session.setAttribute("mainTeacher", teachers);
                    break;
                }
            }
        }
        req.getRequestDispatcher(BASE + "/Teachers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ifDelete = Integer.parseInt(req.getParameter("ifDelete"));
        int id = Integer.parseInt(req.getParameter("mainID"));
        if(ifDelete == 0){
            Teachers teachers = new Teachers(id, req.getParameter("mainName"), req.getParameter("mainText"));
            Teachers.saveTeacherToDB(teachers);
        }
        if (ifDelete == 1){
            Teachers.deleteTeacherFromDB(id);
        }
        resp.sendRedirect(req.getContextPath() + "/Filter/Teachers");
    }
}
