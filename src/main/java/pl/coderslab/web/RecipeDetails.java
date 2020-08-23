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

@WebServlet(name = "RecipeDetails", value = "/app/recipe/details")
public class RecipeDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RecipeDao recipeDao = new RecipeDao();

        int recipeId = Integer.parseInt(request.getParameter("id"));
        Recipe recipe = recipeDao.read(recipeId);

//        --- passing recipe ---
        session.setAttribute("recipe_details", recipe);

//        --- extracting ingredients and passing as table ---
        String ingredients = recipe.getIngredients();
        String[] ingredientsTab = ingredients.split(",");// IMPORTANT NOTE:  assumed, that in database ingredients are ALWAYS separated with comma ","
        session.setAttribute("ingredients", ingredientsTab);

        getServletContext().getRequestDispatcher("/app/recipe-details.jsp").forward(request, response);
    }
}
