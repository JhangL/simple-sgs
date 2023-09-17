package com.jh.sgs.uiServer;

import com.jh.sgs.ui.TcpObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UiServer {

    Socket[] sockets;
    ServerSocket ss;

    public void accept() throws IOException {
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = ss.accept();
            System.out.println("玩家"+i+"已连接："+sockets[i]);
            TcpObject tcpObject = new TcpObject();
            tcpObject.setIndex(i);
            tcpObject.setType(TcpObject.T_I);
            request(i, tcpObject);
        }
    }

    public UiServer(int size){
        sockets = new Socket[size];
        try {
            ss = new ServerSocket(6666); // 监听指定端口
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("等待玩家进入，"+size+"人");
    }
    public void request(TcpObject tcpObject) {
        for (int i = 0; i < sockets.length; i++) {
            request(i,tcpObject);
        }
    }
    public void request(int player, TcpObject tcpObject) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sockets[player].getOutputStream());
            objectOutputStream.writeObject(tcpObject);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.err.println("与客户端断开连接"+player);
            System.exit(0);
        }
    }

    public TcpObject requestWaitHandle(int player, TcpObject tcpObject) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(sockets[player].getOutputStream());
            objectOutputStream.writeObject(tcpObject);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(sockets[player].getInputStream());
            TcpObject o = (TcpObject) objectInputStream.readObject();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("与客户端断开连接"+player);
            System.exit(0);
        }
        return null;
    }
}
