package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.BookDao;
<<<<<<< HEAD
import pl.coderslab.model.Admin;
=======
import pl.coderslab.dao.PlanDao;
>>>>>>> 5_strona_główna_aplikacji
import pl.coderslab.model.Book;
import pl.coderslab.model.RecentPlanDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Do not change servlet address !!!
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.findAll();
        System.out.println(books);

        HttpSession session = request.getSession();
        session.setAttribute("adminId", 1);
        
        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
