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
import java.util.Collections;
import java.util.List;

@WebServlet(name = "Schedules", value = "/app/plan/list")
public class Schedules extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        HttpSession session = request.getSession();

        int adminId = (Integer) session.getAttribute("adminId");
        List<Plan> allPlans = planDao.findPlansByAdminId(adminId);
        Collections.reverse(allPlans);

        session.setAttribute("allPlans", allPlans);

        getServletContext().getRequestDispatcher("/app/schedules.jsp").forward(request, response);
    }
}
