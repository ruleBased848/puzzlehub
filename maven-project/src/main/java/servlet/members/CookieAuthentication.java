package servlet.members;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/members/cookieauthentication")
public class CookieAuthentication extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var out = response.getWriter();
        out.println("{\"ok\":" + (boolean)request.getAttribute("authenticated") + "}");
        out.close();
    }
}