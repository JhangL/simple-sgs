package com.jh.sgs.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UiClient {
    Socket sock;

    public UiClient(String add) throws IOException {
        sock = new Socket(add, 6666); // 连接指定服务器和端口
    }

    public TcpObject handle() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
        TcpObject o = (TcpObject) objectInputStream.readObject();
        return o;

    }
    public void request(TcpObject tcpObject) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
        objectOutputStream.writeObject(tcpObject);
        objectOutputStream.flush();
//        TcpObject o = (TcpObject) objectInputStream.readObject();
    }
}
