package com.mariabartosh;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String message = request.getParameter("message");
        String stacktrace = request.getParameter("stacktrace");
        System.out.println(message + "\n" + stacktrace);
    }
}
