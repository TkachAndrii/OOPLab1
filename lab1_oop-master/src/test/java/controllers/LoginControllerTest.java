package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getContextPath()).thenReturn("/controller");
        when(request.getSession(false)).thenReturn(session);

        when(session.getAttribute("loggedUser")).thenReturn(new Object());

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getContextPath()).thenReturn("/controller");
    }

    @Test
    void testHandle_GET_UserIsLoggedIn() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession(false)).thenReturn(session); // use getSession(false)
        /*when(session.getAttribute("user")).thenReturn(new Object());*/
        when(request.getContextPath()).thenReturn("/controller");
        controller.handle(request, response);

        verify(response).sendRedirect("/controller/home");

    }

}
