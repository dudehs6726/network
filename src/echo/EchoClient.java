package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "218.39.221.75";
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) {
		
		Scanner scanner = null;
		Socket socket = null;
		
		try {
			
			scanner = new Scanner(System.in);
			//1. 소켓 생성
			socket = new Socket();
			
			//2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] connected");
			
			//3. 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			while(true) {
				System.out.print(">>");
				String scan = scanner.nextLine();
				
				if("quit".contentEquals(scan)) {
					break;
				}
				
				pw.println(scan);
				
				String line = br.readLine();
				
				if(line == null) {
					System.out.println("[Client] closed by server");
					break;
				}
				
				System.out.println(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(scanner != null) {
					scanner.close();
				}
				
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
