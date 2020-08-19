package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.BookDao;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Book;
import pl.coderslab.model.DayName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Do not change servlet address !!!
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //AdminDao adminDao = new AdminDao();
        /*Admin admin = new Admin();
        admin.setFirstName("Mar");
        admin.setLastName("KKK");
        admin.setEnable(0);
        admin.setSuperAdmin(0);
        admin.setPassword("mnmn");
        admin.setEmail("mmm@gmail.com");
        adminDao.create(admin);*/
        //List<Admin> adminList = adminDao.findAll();
        //System.out.println(adminDao.read(1));
         BookDao bookDao = new BookDao();
        List<Book> books = bookDao.findAll();
        System.out.println(books);



        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
