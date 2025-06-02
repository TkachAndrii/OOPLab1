package controllers;

import dao.HotelDAO;
import dao.impl.HotelDAOImpl;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Hotel;
import model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "MainServlet", urlPatterns = {"/home", "/detail", "/hello", "/login", "/logout", "/bookings"})
public class MainServlet extends HttpServlet {

    private HotelDAO hotelDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        hotelDAO = new HotelDAOImpl();
        userDAO  = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/home":
                try {
                    handleHome(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/detail":
                handleDetail(request, response);
                break;

            case "/hello":
                handleHello(request, response);
                break;

            case "/login":
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("loggedUser") != null) {
                    response.sendRedirect(request.getContextPath() + "/home");
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                }
                break;

            case "/logout":
                handleLogout(request, response);
                break;

            case "/bookings":
                try {
                    handleBookings(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        if ("/login".equals(action)) {
            try {
                handleLoginPost(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }


    private void handleHome(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Hotel> hotels = hotelDAO.findAll();
        request.setAttribute("Hotels", hotels);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }

    private void handleDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            Hotel hotelOpt = hotelDAO.findById(hotelId);

            request.setAttribute("hotel", hotelOpt);
            request.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleHello(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "Hello from Front Controller!");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void handleLoginPost(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedUser", user);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");

    }
    private void handleBookings(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Hotel> hotels = hotelDAO.findAll();
        request.setAttribute("Hotels", hotels);

        request.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(request, response);
    }
}
