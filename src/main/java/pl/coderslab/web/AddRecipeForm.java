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

@WebServlet(name = "AddRecipeForm", value = "/app/recipe/add")
public class AddRecipeForm extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = new Recipe();
        HttpSession session = request.getSession();
        recipe.setAdminId((Integer)session.getAttribute("adminId"));
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setPreparationTime(preparationTime);
        recipe.setPreparation(preparation);
        recipe.setIngredients(ingredients);
        recipeDao.create(recipe);
        response.sendRedirect("/app/recipe/list/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app/app-add-recipe.jsp").forward(request, response);
    }
}
