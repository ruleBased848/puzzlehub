package servlet;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/main")
public class Main extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        var out = response.getWriter();
        out.println("abc");
    }

    private boolean isValidBox(String[][] board, int box)
    {
        var al = new ArrayList<String>();
        for (int i = 0; i < 9; ++i)
        {
            var str = board[box][i];
            if (!str.equals(""))
            {
                al.add(str);
            }
        }
        return areUnique(al);
    }

    private boolean areUnique(ArrayList<String> al)
    {
        Collections.sort(al);
        var size = al.size();
        for (int i = 0; i < size - 1; ++i)
        {
            if (al.get(i).equals(al.get(i + 1)))
            {
                return false;
            }
        }
        return true;
    }
}