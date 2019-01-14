package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import chat.client.win.ChatWindow;
import echo.EchoServer;

public class ChatClinet {
	private static final String SERVER_IP = "218.39.221.75";
	private static final int SERVER_PORT = 7777;
	
	public static void main(String[] args) {
		
		Scanner scanner = null;
		Socket socket = null;
		
		try {
			
			//1. 키보드 연결
			scanner = new Scanner(System.in);
			//2. socket 생성
			socket = new Socket();
			//3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			//4. reader / writer 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			
			//5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			pw.println("join:" + nickname);
			pw.flush();
			
			/* UI                                                                                */
			ChatWindow cw = new ChatWindow(nickname, pw);
			new ChatClientThread(cw, br).start();
			/*  */
			
			//6.ChatClientReceiveThread 시작
			//new ChatClientThread(br).start();
			
			//7.키보드 입력 처리
			while(true) {
				//System.out.print(">>");
				String input = scanner.nextLine();
				
				if("quit".equals(input) == true) {
					//8.quit 프로토콜 처리
					break;
				}else {
					//9. 메시지 처리
					pw.println("message:" + input);
					pw.flush();
				}
			}
		}catch(IOException ex) {
			EchoServer.log("error:" + ex);
		}finally {
			//IO 자원정리
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
