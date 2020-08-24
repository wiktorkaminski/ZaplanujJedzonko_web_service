package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipePlanAdd", value = "/app/recipe/plan/add")
public class RecipePlanAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/app/recipe/plan/add").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        RecipeDao recipeDao = new RecipeDao();
        DayNameDao dayNameDao = new DayNameDao();
        HttpSession session = request.getSession();

        int adminId = (int) session.getAttribute("adminId");

        List<Plan> plansByAdminId = planDao.findPlansByAdminId(adminId);
        session.setAttribute("plansByAdminId", plansByAdminId);

        List<Recipe> recipes = recipeDao.findAll();
        session.setAttribute("recipes", recipes);

        List<DayName> dayNames = dayNameDao.findAll();
        session.setAttribute("dayNames", dayNames);


        getServletContext().getRequestDispatcher("/app/app-schedules-meal-recipe.jsp").forward(request, response);
    }
}