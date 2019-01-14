package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketTimeoutException;

public class TCPClient {
	private static final String SERVER_IP = "218.39.221.75";
	private static final int SERVER_PORT = 5000;
	
	public static void main(String[] args) {	
		
		Socket socket = null;
		try {
			//1. 소켓 생성
			socket = new Socket();
			
			//1-1 소켓 버퍼 용량 확인
			int receiveBufferSize = socket.getReceiveBufferSize();
			int sendBufferSize = socket.getSendBufferSize();
			
			System.out.println(receiveBufferSize + " : " + sendBufferSize);
			
			//-2 소켓 버퍼 용량 변경
			socket.setReceiveBufferSize(1024 * 10);
			socket.setSendBufferSize(1024 * 10);
			System.out.println(receiveBufferSize + " : " + sendBufferSize);
			
			//1-3. so_nodelay
			socket.setTcpNoDelay(true);
			
			//1-4 so_timeout
			socket.setSoTimeout(1);
			
			//2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, 0));
			System.out.println("[client] connected");
			
			//3. 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			//4.쓰기
			String data = "Hello World\n";
			os.write(data.getBytes("UTF-8"));
			
			//5.읽기
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer);
			if(readByteCount == -1){
				System.out.println("[client] closed by server");
				return;
			}
			
			data = new String(buffer, 0, readByteCount, "UTF-8");
			System.out.println("[client] received:" + data);
		}catch (SocketTimeoutException e) {
			System.out.println("[client] Time Out");
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null && socket.isClosed() == false) {
				//socket.close();
			}
		}

	}

}
