<%--
  Created by IntelliJ IDEA.
  User: TkachAndrii
  Date: 6/1/2025
  Time: 11:00 AM
  Purpose: Login Page for Hotel Booking System
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <style>
    body {
      font-family: sans-serif;
      background-color: #f0f2f5;
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100vh;
    }
    form {
      background: white;
      padding: 2rem;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
      width: 300px;
    }
    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 0.5rem;
      margin-top: 0.5rem;
      margin-bottom: 1rem;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      width: 100%;
      padding: 0.75rem;
      background-color: #007BFF;
      color: white;
      border: none;
      border-radius: 4px;
    }
    .error {
      color: red;
      margin-bottom: 1rem;
    }
  </style>
</head>
<body>
<form method="post" action="login">
  <h2>Login</h2>
  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>
  <input type="text" name="username" placeholder="Username" required />
  <input type="password" name="password" placeholder="Password" required />
  <button type="submit">Log In</button>
</form>
</body>
</html>
