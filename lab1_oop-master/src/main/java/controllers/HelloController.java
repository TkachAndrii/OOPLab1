package controllers;

import jakarta.servlet.http.*;

public class HelloController implements Controller {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("message", "Hello from Front Controller!");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
