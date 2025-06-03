package controllers;

import dao.impl.HotelDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import model.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock HttpSession session;
    @Mock RequestDispatcher dispatcher;
    @Mock HotelDAO hotelDAOMock;

    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        homeController = new HomeController() {{
            this.hotelDAO = hotelDAOMock;
            when(request.getContextPath()).thenReturn("/controller");
        }};
    }

    @Test
    public void testHandle_WhenUserIsLoggedIn() throws Exception {
        List<Hotel> hotels = List.of(new Hotel());
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(new Object());
        when(hotelDAOMock.findAll()).thenReturn(hotels);
        when(request.getRequestDispatcher("/WEB-INF/views/home.jsp")).thenReturn(dispatcher);

        homeController.handle(request, response);

        verify(request).setAttribute("Hotels", hotels);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testHandle_WhenUserIsNotLoggedIn() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        homeController.handle(request, response);

        verify(response).sendRedirect("/controller/login");
    }
}
