package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Registration", value = "/register")
public class Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminDao adminDao = new AdminDao();
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");

        if (!password.equals(repassword)) {
            getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
            return;
        }

//        --- checking if email is unique ---
        if (!adminDao.checkIfEmailIsUnique(email)) {
            getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
            return;
        }


        Admin newAdmin = new Admin();
        newAdmin.setFirstName(request.getParameter("firstName"));
        newAdmin.setLastName(request.getParameter("lastName"));
        newAdmin.setEmail(email);
        newAdmin.setPassword(password);
        newAdmin.setEnable(1);

        adminDao.create(newAdmin);

        //getServletContext().getRequestDispatcher("/login").forward(request, response);
        response.sendRedirect("/login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
    }
}
