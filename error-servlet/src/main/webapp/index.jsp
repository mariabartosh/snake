<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Errors</title>
  </head>
  <body>
  <table class="data">
    <tr>
        <th>Message</th>
        <th>Stacktrace</th>
        <th>Date</th>
    </tr>
    <c:forEach items="${errors}" var="error">
        <tr>
        <td>${error.message}</td>
        <td>${error.stacktrace}</td>
        <td>${error.date}</td>
        </tr>
    </c:forEach>
  </table>
  </body>
</html>