package com.example.adeso1.huyenthoai.PlayerOnline;

import com.example.adeso1.huyenthoai.Player.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private MainActivity main;
    private InetAddress host;
    private int port;
    boolean line=true;
    public  Client(InetAddress host,int port)
    {
        this.host=host;
        this.port=port;
    }
    private  void excute() throws IOException{
        Socket client=new Socket(host,port);
        DataInputStream dis=new DataInputStream(client.getInputStream());
        DataOutputStream dos=new DataOutputStream(client.getOutputStream());
        while (!line)
        {
           line= dis.readBoolean();
           dos.writeBoolean(line);
        }
        dis.close();
        dos.close();
        client.close();
    }
}
