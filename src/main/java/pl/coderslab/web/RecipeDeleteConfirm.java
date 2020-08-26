package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RecipeDeleteConfirm", value = "/app/recipe/delete/confirm")
public class RecipeDeleteConfirm extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int idToDelete = (Integer)session.getAttribute("idToDelete");
        //int idToDelete = Integer.parseInt(request.getParameter("idToDelete"));
        RecipeDao recipeDao = new RecipeDao();
        if(recipeDao.recipeIsNotPartOfPlan(idToDelete)){
            recipeDao.delete(idToDelete);
        }else{
            getServletContext().getRequestDispatcher("/app/deletenotpossible.jsp").forward(request,response);
        }
        response.sendRedirect("/app/recipe/list/");
    }
}