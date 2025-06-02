<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Hotel Booking System</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f0f8ff;
      font-family: 'Segoe UI', sans-serif;
    }
    .navbar-custom {
      background-color: #4b0082;
    }
    .navbar-custom .nav-link,
    .navbar-custom .navbar-brand {
      color: #fff;
    }
    .navbar-custom .nav-link:hover {
      color: #ffdead;
    }
    .container {
      padding: 2rem;
    }
  </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-custom">
  <div class="container-fluid">
    <a class="navbar-brand" href="main">Hotel Booking</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="main">Main</a></li>
        <li class="nav-item"><a class="nav-link" href="bookings">Bookings</a></li>
        <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
        <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
      </ul>
    </div>
  </div>
</nav>
