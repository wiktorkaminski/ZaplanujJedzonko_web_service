package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PlanRecipeDel")
public class PlanRecipeDel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        int planId = Integer.parseInt(request.getParameter("planId"));
        RecipeDao recipeDao = new RecipeDao();

        recipeDao.deleteRecipeFromPlan(recipeId,planId);

        response.sendRedirect("/app/schedule-details.jsp?planId="+planId);
    }
}
