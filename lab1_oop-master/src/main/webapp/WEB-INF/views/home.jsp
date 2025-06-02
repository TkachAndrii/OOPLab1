<%--
  Created by IntelliJ IDEA.
  User: TkachAndrii
  Date: 6/1/2025
  Time: 11:00 AM
  Purpose: Home Page for Hotel Booking System
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../fragments/header.jsp"/>
<div class="container">
  <h2>Welcome to the Hotel Booking System</h2>
  <p>Find the perfect room for your stay!</p>

  <c:forEach var="room" items="${roomList}">
    <div style="border:1px solid #ccc; padding: 1rem; margin-bottom: 1rem;">
      <h3>Room ${room.number} - ${room.type}</h3>
      <p>Capacity: ${room.capacity}</p>
      <p>Price: $${room.price} per night</p>
      <a href="detail?roomId=${room.id}">View Details</a>
    </div>
  </c:forEach>
</div>
<jsp:include page="../fragments/footer.jsp"/>
