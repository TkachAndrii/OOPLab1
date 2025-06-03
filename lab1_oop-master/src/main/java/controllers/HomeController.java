package controllers;

import dao.impl.HotelDAO;
import dao.impl.impl.HotelDAOImpl;
import jakarta.servlet.http.*;
import model.Hotel;

import java.util.List;

public class HomeController implements Controller {


    protected HotelDAO hotelDAO = new HotelDAOImpl();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Hotel> hotels = hotelDAO.findAll();
        request.setAttribute("Hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
