package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AdminDao adminDao = new AdminDao();
        if (!adminDao.checkEmailAndPassword(email, password)){
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }else {
            //getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
            HttpSession session = request.getSession();

            session.setAttribute("adminId", adminDao.returnId(email, password));
            response.sendRedirect("/dashboard.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
