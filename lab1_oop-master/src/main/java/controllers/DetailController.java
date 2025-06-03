package controllers;

import dao.impl.HotelDAO;
import dao.impl.impl.HotelDAOImpl;
import jakarta.servlet.http.*;
import model.Hotel;

public class DetailController implements Controller {

    protected HotelDAO hotelDAO = new HotelDAOImpl();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String hotelIdParam = request.getParameter("id");
        if (hotelIdParam == null || hotelIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            int hotelId = Integer.parseInt(hotelIdParam);
            Hotel hotel = hotelDAO.findById(hotelId);
            request.setAttribute("hotel", hotel);
            request.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}
