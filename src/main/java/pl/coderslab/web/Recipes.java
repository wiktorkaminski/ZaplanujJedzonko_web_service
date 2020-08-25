package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "Recipes", value = "/app/recipe/list/")
public class Recipes extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        HttpSession session = request.getSession();

        int adminId = (int) session.getAttribute("adminId");
        List<Recipe> recipes = recipeDao.findRecipesByAdminId(adminId);
        Collections.reverse(recipes);

        session.setAttribute("recipes", recipes);

        getServletContext().getRequestDispatcher("/app/recipes.jsp").forward(request, response);
    }
}
