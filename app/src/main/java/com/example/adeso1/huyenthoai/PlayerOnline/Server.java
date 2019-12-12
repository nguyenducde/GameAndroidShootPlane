package com.example.adeso1.huyenthoai.PlayerOnline;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private  int port;
    public  Server(int port)
    {
        this.port=port;
    }
    private void execute() throws IOException{
        ServerSocket server=new ServerSocket(port);
       Socket socket= server.accept();
       DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }
}
