package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.model.DayName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PlanDelConfirmation", value = "/app/plan/del/recipe-del-confirmation")
public class PlanDelConfirmation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dayName = request.getParameter("dayName");
        DayNameDao dayNameDao = new DayNameDao();
        dayNameDao.findDayIdByName(dayName);

        request.setAttribute("dayNameId",  dayNameDao.findDayIdByName(dayName));

        getServletContext().getRequestDispatcher("/app/recipe-del-confirmation.jsp").forward(request, response);
    }
}
