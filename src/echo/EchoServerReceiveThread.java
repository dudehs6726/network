package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress =  (InetSocketAddress)socket.getRemoteSocketAddress();
		
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();		
		System.out.println("[server] connected by client[" + remoteHostAddress + ":" + remotePort );//찌른놈 포트, 주소
		
		
		try {
			//4. IOStream 받아오기		 
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			while(true) {
				
				String line = br.readLine();
				
				if(line == null) {
					System.out.println("[server] closed by client");
					break;
				}
				
				pw.println("<<" + line);
				
			}
		} catch(SocketException e) {
			System.out.println("[server] abnormal closed ");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. 자원정리(소켓 닫기)
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
