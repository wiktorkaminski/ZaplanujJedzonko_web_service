package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Dashboard", value = "/dashboard")
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
        int adminId = (int) session.getAttribute("adminId"); // !! UWAGA: założenie, że w sesji będzie strybut adminID

        session.setAttribute("plansNumber", planDao.countPlansByAdminId(adminId));
        session.setAttribute("recipeNumber", recipeDao.countRecipesByAdminId(adminId));
        session.setAttribute("firstName", adminDao.read(adminId).getFirstName());
        System.out.println(adminDao.read(adminId).getFirstName());

        getServletContext().getRequestDispatcher("/app/dashboard.jsp").forward(request, response);
    }
}
