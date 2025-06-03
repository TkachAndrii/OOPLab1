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

public class BookingsControllerTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock HttpSession session;
    @Mock RequestDispatcher dispatcher;
    @Mock HotelDAO hotelDAOMock;

    private BookingsController bookingsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingsController = new BookingsController() {{
            this.hotelDAO = hotelDAOMock;
            when(request.getContextPath()).thenReturn("/controller");
        }};
    }

    @Test
    public void testHandle_WhenUserLoggedIn() throws Exception {
        List<Hotel> hotels = List.of(new Hotel());
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(new Object());
        when(hotelDAOMock.findAll()).thenReturn(hotels);
        when(request.getRequestDispatcher("/WEB-INF/views/booking.jsp")).thenReturn(dispatcher);

        bookingsController.handle(request, response);

        verify(request).setAttribute("Hotels", hotels);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testHandle_WhenUserNotLoggedIn() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        bookingsController.handle(request, response);

        verify(response).sendRedirect("/controller/login");
    }
}
