package com.mariabartosh;

import org.sqlite.JDBC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorServlet extends HttpServlet
{
    private final static String DB_FILE = "errorsdb.s3db";
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String message = request.getParameter("message");
        String stacktrace = request.getParameter("stacktrace");
        if (message == null || stacktrace == null)
        {
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO errors (message, stacktrace, 'date') VALUES(?, ?, datetime('now'))"))
        {
            if (message.length() > 65535)
            {
                message = message.substring(0, 65535);
            }
            if (stacktrace.length() > 65535)
            {
                stacktrace = stacktrace.substring(0, 65535);
            }
            statement.setObject(1, message);
            statement.setObject(2, stacktrace);
            statement.execute();
        }
        catch (SQLException e)
        {
            System.err.println(message + "\n" + stacktrace + "\n" + e.getMessage());
        }
    }

    @Override
    public void init() throws ServletException
    {
        super.init();
        try
        {
            if (!Files.exists(Paths.get(DB_FILE)))
            {
                Files.createFile(Paths.get(DB_FILE));
            }
            System.out.println("DB path " + Paths.get(DB_FILE).toAbsolutePath());
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS errors (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, stacktrace TEXT, 'date' DATETIME);");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ArrayList<ErrorData> errors = new ArrayList<>();
        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM errors");
            while (resultSet.next())
            {
                ErrorData errorData = new ErrorData();
                errorData.setId(resultSet.getInt("id"));
                errorData.setMessage(resultSet.getString("message"));
                errorData.setStacktrace(resultSet.getString("stacktrace"));
                errorData.setDate(resultSet.getDate("date"));
                errors.add(errorData);
            }
        }
        catch (SQLException ignored)
        {
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
