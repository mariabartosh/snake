<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Errors</title>
    <style>

        table {
            border-spacing: 0;
        }
        td {
            vertical-align: top;
        }
    </style>
  </head>
  <body>
  <table border="1" cellpadding="5">
    <tr>
        <th>Message</th>
        <th>Stacktrace</th>
        <th>Date</th>
    </tr>
    <c:forEach items="${errors}" var="error">
        <tr>
        <td>${error.message}</td>
        <td>${fn:replace(error.stacktrace, ",", "<br>")}</td>
        <td>${error.date}</td>
        </tr>
    </c:forEach>
  </table>
  </body>
</html>
