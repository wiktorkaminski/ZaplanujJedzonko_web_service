package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.PlanDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "ScheduleDetails", value = "/app/plan/details")
public class ScheduleDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        HttpSession session = request.getSession();

//        IMPORTANT: assumed that plan_id will be passed as "planId" parameter
        int planId = Integer.parseInt(request.getParameter("planId"));
        session.setAttribute("plan", planDao.read(planId));

//        passing which weekdays are in plan
        List<PlanDetail> planDetails = planDao.findMealsInPlan(planId);
        Set<String> weekdaysInPlan = new LinkedHashSet<>();
        for (PlanDetail PlanDetail : planDetails) {
            weekdaysInPlan.add(PlanDetail.getDayName());
        }
        session.setAttribute("weekdaysInPlan", weekdaysInPlan);

//        passing meals in plan
        session.setAttribute("planDetails", planDetails);

        getServletContext().getRequestDispatcher("/app/schedule-details.jsp").forward(request, response);
    }
}
