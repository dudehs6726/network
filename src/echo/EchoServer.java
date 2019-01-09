package echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 6000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. Binding(Socket에 SocketAddres(IPAddress + port) 바인딩한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhostAddress, PORT));
			System.out.println("[server] binding " + localhostAddress + ":" + PORT);
			
			while(true) {
			
				//3.accept(클라이언트로 부터 연결요청을 기다린다.)
				Socket socket = serverSocket.accept(); //Blocking
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() + "]");
	}

}
