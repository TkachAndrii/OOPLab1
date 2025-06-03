package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class HelloControllerTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher dispatcher;

    private HelloController helloController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        helloController = new HelloController();
        when(request.getContextPath()).thenReturn("/controller");
    }

    @Test
    public void testHandle() throws Exception {
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);

        helloController.handle(request, response);

        verify(request).setAttribute("message", "Hello from Front Controller!");
        verify(dispatcher).forward(request, response);
    }
}
