
package controllers;

import jakarta.servlet.http.*;

public interface Controller {
    void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

