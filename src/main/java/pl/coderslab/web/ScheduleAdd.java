package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "PlanAdd", value = "/app/plan/add")
public class ScheduleAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int adminId = (int) session.getAttribute("adminId");

        Plan newPlan = new Plan();
        newPlan.setName(name);
        newPlan.setDescription(description);
        newPlan.setAdminId(adminId);

        planDao.create(newPlan);

        //getServletContext().getRequestDispatcher("/app/plan/list").forward(request, response);
        response.sendRedirect("/app/plan/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app/schedule-add.jsp").forward(request, response);

    }
}
