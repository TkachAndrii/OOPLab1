package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(
        name = "FrontController",
        urlPatterns = {
                "/home",
                "/detail",
                "/hello",
                "/login",
                "/logout",
                "/bookings"
        }
)
public class FrontControllerServlet extends HttpServlet {

    private final Map<String, Controller> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {

        controllerMap.put("/home", new HomeController());
        controllerMap.put("/detail", new DetailController());
        controllerMap.put("/hello", new HelloController());
        controllerMap.put("/login", new LoginController());
        controllerMap.put("/logout", new LogoutController());
        controllerMap.put("/bookings", new BookingsController());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        Controller controller = controllerMap.get(path);
        if (controller != null) {
            try {
                controller.handle(request, response);
            } catch (Exception e) {
                throw new ServletException("Exception in controller for path " + path, e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No controller mapped for " + path);
        }
    }
}
