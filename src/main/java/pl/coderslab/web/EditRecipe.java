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

@WebServlet(name = "EditRecipe", value = "/app/recipe/edit")
public class EditRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        int id = Integer.parseInt(request.getParameter("id"));
        Recipe recipeToEdit = recipeDao.read(id);

        String name = request.getParameter("name");
        String ingredients = request.getParameter("ingredients");
        String description = request.getParameter("description");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String preparation = request.getParameter("preparation");

        recipeToEdit.setName(name);
        recipeToEdit.setIngredients(ingredients);
        recipeToEdit.setDescription(description);
        recipeToEdit.setPreparationTime(preparationTime);
        recipeToEdit.setPreparation(preparation);

        recipeDao.update(recipeToEdit);

        response.sendRedirect("/app/recipe/list/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(recipeId);
        session.setAttribute("recipe", recipe);

        getServletContext().getRequestDispatcher("/app/app-edit-recipe.jsp").forward(request, response);
    }
}
