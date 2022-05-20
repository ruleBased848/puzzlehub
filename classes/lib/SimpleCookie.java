package lib;

import jakarta.servlet.http.*;

public class SimpleCookie
{
    public void setCookies(Cookie[] cookies)
    {
        this.cookies = cookies;
    }

    public String getValue(String name)
    {
        if (cookies == null)
        {
            return null;
        }

        String value = null;
        for (var cookie : cookies)
        {
            if (cookie.getName().equals(name))
            {
                value = cookie.getValue();
                break;
            }
        }

        return value;
    }

    private Cookie[] cookies = null;
}