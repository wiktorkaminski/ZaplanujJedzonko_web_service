package pl.coderslab.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RecipeDelete", value = "/app/recipe/delete")
public class RecipeDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idToDelete = request.getParameter("id");
        HttpSession session = request.getSession();
        session.setAttribute("idToDelete", Integer.parseInt(idToDelete));
        getServletContext().getRequestDispatcher("/app/delete-recipe.jsp").forward(request, response);
    }
}
