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

@WebServlet(name = "EditPlan", value = "/app/plan/edit")
public class EditPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        int id = Integer.parseInt(request.getParameter("id"));
        Plan planToEdit = planDao.read(id);

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        planToEdit.setName(name);
        planToEdit.setDescription(description);

        planDao.update(planToEdit);

        response.sendRedirect("/app/plan/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(planId);
        session.setAttribute("plan", plan);

        getServletContext().getRequestDispatcher("/app/app-edit-schedules.jsp").forward(request, response);
    }
}
