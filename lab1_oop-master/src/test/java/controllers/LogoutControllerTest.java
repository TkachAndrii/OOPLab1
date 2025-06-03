package controllers;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class LogoutControllerTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock HttpSession session;

    private LogoutController logoutController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logoutController = new LogoutController();
        when(request.getContextPath()).thenReturn("/controller");
    }

    @Test
    public void testHandle_WithSession() throws Exception {
        when(request.getSession(false)).thenReturn(session);

        logoutController.handle(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/controller/login");
    }

    @Test
    public void testHandle_WithoutSession() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        logoutController.handle(request, response);

        verify(response).sendRedirect("/controller/login");
    }
}
