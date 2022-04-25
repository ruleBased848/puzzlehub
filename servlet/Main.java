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

    private int getCaseNum(String[][] board)
    {
        if (!isValid(board))
        {
            return 0;
        }

        int box = -1;
        int cell = -1;
        outer:
        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                if (board[i][j].equals(""))
                {
                    box = i;
                    cell = j;
                    break outer;
                }
            }
        }
        if (box == -1)
        {
            return 1;
        }

        int acc = 0;
        final int limit = 1000;
        for (int i = 1; i <= 9; ++i)
        {
            if (acc >= limit)
            {
                break;
            }
            board[box][cell] = "" + i;
            acc += getCaseNum(board);
            board[box][cell] = "";
        }

        return Math.min(acc, limit);
    }

    private boolean isValid(String[][] board)
    {
        for (int i = 0; i < 9; ++i)
        {
            if (!isValidBox(board, i) || !isValidRow(board, i) || !isValidColumn(board, i))
            {
                return false;
            }
        }
        return true;
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

    private boolean isValidRow(String[][] board, int row)
    {
        int box = row / 3 * 3;
        int cell = row % 3 * 3;
        var al = new ArrayList<String>();
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                var str = board[box + i][cell + j];
                if (!str.equals(""))
                {
                    al.add(str);
                }
            }
        }
        return areUnique(al);
    }

    private boolean isValidColumn(String[][] board, int col)
    {
        int box = col / 3;
        int cell = col % 3;
        var al = new ArrayList<String>();
        for (int i = 0; i < 9; i += 3)
        {
            for (int j = 0; j < 9; j += 3)
            {
                var str = board[box + i][cell + j];
                if (!str.equals(""))
                {
                    al.add(str);
                }
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