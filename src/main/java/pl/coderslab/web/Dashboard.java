package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.PlanDetail;
import pl.coderslab.model.RecentPlanDetail;

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

@WebServlet(name = "Dashboard", value = "/app/dashboard")
public class Dashboard extends HttpServlet {
    private final String READ_ADMIN_DATA = "SELECT * FROM admins WHERE id = ?";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        RecipeDao recipeDao = new RecipeDao();
        AdminDao adminDao = new AdminDao();


//        IMPORTANT: getting logged admin_id
        HttpSession session = request.getSession();
        int adminId = (int) session.getAttribute("adminId"); // !! UWAGA: założenie, że w sesji będzie atrybut adminID

//        passing counters and current admin name
        session.setAttribute("plansNumber", planDao.countPlansByAdminId(adminId));
        session.setAttribute("recipeNumber", recipeDao.countRecipesByAdminId(adminId));
        session.setAttribute("firstName", adminDao.read(adminId).getFirstName());

//        passing recent plan name
        session.setAttribute("recentPlanName",  planDao.finRecentPlanName(adminId));

//        passing which weekdays are in recent plan
        List<PlanDetail> recentPlan = planDao.findRecentPlan(adminId);
        Set<String> weekdaysInPlan = new LinkedHashSet<>();
        for (PlanDetail recentPlanDetail : recentPlan) {
            weekdaysInPlan.add(recentPlanDetail.getDayName());
        }
        session.setAttribute("weekdaysInPlan", weekdaysInPlan);

//        passing recent plan details
        session.setAttribute("recentPlan", recentPlan);

        getServletContext().getRequestDispatcher("/app/dashboard.jsp").forward(request, response);
    }
}
