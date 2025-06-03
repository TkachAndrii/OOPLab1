package controllers;

import dao.impl.HotelDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import model.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class DetailControllerTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock HttpSession session;
    @Mock RequestDispatcher dispatcher;
    @Mock HotelDAO hotelDAOMock;

    private DetailController detailController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        detailController = new DetailController() {{
            this.hotelDAO = hotelDAOMock;
        }};
        when(request.getContextPath()).thenReturn("/controller");
    }

    @Test
    public void testHandle_ValidHotelId() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(new Object());
        when(request.getParameter("id")).thenReturn("1");

        Hotel hotel = new Hotel();
        hotel.setId(1);
        when(hotelDAOMock.findById(1)).thenReturn(hotel);
        when(request.getRequestDispatcher("/WEB-INF/views/booking.jsp")).thenReturn(dispatcher);

        detailController.handle(request, response);

        verify(request).setAttribute("hotel", hotel);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testHandle_MissingHotelId() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(new Object());
        when(request.getParameter("id")).thenReturn(null);

        detailController.handle(request, response);

        verify(response).sendRedirect("/controller/home");
    }

    @Test
    public void testHandle_Unauthenticated() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        detailController.handle(request, response);

        verify(response).sendRedirect("/controller/login");
    }
}
