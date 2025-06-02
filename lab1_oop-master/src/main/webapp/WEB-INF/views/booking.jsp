<%--
  Created by IntelliJ IDEA.
  User: TkachAndrii
  Date: 6/1/2025
  Time: 11:00 AM
  Purpose: Booking Page – відображає всі готелі у форматі інтернет-магазину
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../fragments/header.jsp"/>

<style>
  /* Контейнер для сітки готелів */
  .hotel-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 20px;
    padding: 1rem;
  }
  /* Окремий «card» для кожного готелю */
  .hotel-card {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    overflow: hidden;
    display: flex;
    flex-direction: column;
    transition: transform 0.2s;
  }
  .hotel-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 10px rgba(0,0,0,0.15);
  }
  /* Місце для зображення готелю */
  .hotel-image {
    width: 100%;
    height: 160px;
    background-color: #f0f0f0; /* якщо зображення відсутнє */
    background-size: cover;
    background-position: center;
  }
  /* Контент готелю (назва, адреса тощо) */
  .hotel-info {
    flex: 1;
    padding: 1rem;
    display: flex;
    flex-direction: column;
  }
  .hotel-name {
    font-size: 1.2rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
    color: #333;
  }
  .hotel-city {
    font-size: 0.95rem;
    color: #666;
    margin-bottom: 0.75rem;
  }
  .hotel-address {
    font-size: 0.9rem;
    color: #555;
    margin-bottom: 0.75rem;
  }
  .hotel-rating {
    margin-top: auto;
    font-size: 0.9rem;
    color: #ff9800;
    margin-bottom: 1rem;
  }
  /* Кнопка «Book Now» */
  .book-btn {
    background-color: #007BFF;
    color: #fff;
    text-decoration: none;
    text-align: center;
    padding: 0.6rem;
    border-radius: 4px;
    font-weight: 500;
    transition: background-color 0.2s;
  }
  .book-btn:hover {
    background-color: #0056b3;
  }
</style>

<div class="container">
  <h2 style="margin-top: 1rem; color: #444;">Available Hotels</h2>
  <c:if test="${empty Hotels}">
    <p style="padding: 1rem; font-size: 1rem; color: #777;">
      Currently, no hotels are available.
    </p>
  </c:if>

  <div class="hotel-grid">
    <c:forEach var="hotel" items="${Hotels}">
      <div class="hotel-card">
          <%-- Якщо у вашій моделі є поле imageUrl, можна використати:
               <div class="hotel-image" style="background-image: url('${hotel.imageUrl}');"></div>
               Інакше залишимо просте фонове поле.
          --%>
        <div class="hotel-image"></div>

        <div class="hotel-info">
          <div class="hotel-name">${hotel.name}</div>
          <div class="hotel-city">${hotel.city}</div>
          <div class="hotel-address">${hotel.address}</div>
          <div class="hotel-rating">Rating: ${hotel.rating}</div>
          <a class="book-btn" href="${pageContext.request.contextPath}/detail?id=${hotel.id}">
            Book Now
          </a>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<jsp:include page="../fragments/footer.jsp"/>


