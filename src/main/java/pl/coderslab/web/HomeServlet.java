package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Do not change servlet address !!!
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        BookDao bookDao = new BookDao();
//        List<Book> books = bookDao.findAll();
//        System.out.println(books);

        PlanDao planDao = new PlanDao();
        System.out.println("number of plans " + planDao.countPlansByAdminId(1));
        System.out.println("number of plans " + planDao.countPlansByAdminId(2));
        System.out.println("number of plans " + planDao.countPlansByAdminId(3));

        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
