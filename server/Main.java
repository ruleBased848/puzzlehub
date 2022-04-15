package server;

import java.net.*;
import java.io.*;

class Main
{
    public static void main(String[] args) throws Exception
    {
        var ss = new ServerSocket(8080);

        String str = "abc\n";
        var buf = str.getBytes();

        while (true)
        {
            var socket = ss.accept();
            var out = socket.getOutputStream();
            out.write(buf);
            socket.close();
        }
    }
}