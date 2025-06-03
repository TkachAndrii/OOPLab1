package controllers;

import dao.impl.UserDAO;
import dao.impl.impl.UserDAOImpl;
import jakarta.servlet.http.*;
import model.User;

import java.util.Optional;

public class LoginController implements Controller {

    protected UserDAO userDAO = new UserDAOImpl();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("loggedUser") != null) {
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
            return;
        }

        if ("POST".equalsIgnoreCase(request.getMethod())) {
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
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
