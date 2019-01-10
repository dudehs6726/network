package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickname;
	private Socket socket;
	private List<Writer> listWriters;
	
	public ChatServerThread(Socket socket,List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		//1. Remote Host Information
		InetSocketAddress inetRemoteSocketAddress =  (InetSocketAddress)socket.getRemoteSocketAddress();
		
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();		
		ChatServer.log("[server] connected by client[" + remoteHostAddress + ":" + remotePort );//찌른놈 포트, 주소
		
		
		try {
			//2. IO스트림 얻기 
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			
			//3. 요청 처리
			while(true) {
				String request = br.readLine();
				
				if(request == null) {
					ChatServer.log("클라이언트로 부터 연결 끊김");
					doQuit(pw);
					break;
				}
				
				//4. 프로젝트 분석(파라미터1:파라미터2:...\r\n)
				String[] tokens = request.split(":");
				
				if("join".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				}else if("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				}else if("guit".equals(tokens[0])) {
					doQuit(pw);
				}else {
					ChatServer.log("에러:알수 없는 요청(" + tokens[0] + ")");
				}
			}
		
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
		
		String data = nickName + "님이 참여하였습니다.";
		broadcast(data);
		
		// writer pool에 저장
		addWriter(writer);
		
		//ask -- 처리 확인
		PrintWriter pw = (PrintWriter)writer;
		pw.println("join:ok");
		pw.flush();
		
	}
	
	// writer pool에 저장
	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}
	
	//서버에 견결된 모든 클라이언트에 메시지를 보낸다.
	private void broadcast(String data) {
		synchronized (listWriters) {
			for(Writer wirter : listWriters) {
				PrintWriter pw = (PrintWriter)wirter;
				pw.println(data);
				pw.flush();
			}
		}
	}
	
	private void doMessage(String message) {
		//구현 해보기(15)
		String data = nickname + ">>" + message;
		broadcast(data);
	}
	
	private void doQuit(Writer writer) throws IOException {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}
	
	private void removeWriter(Writer writer) throws IOException {
		//구현 해보기(16) writerpool에서 제거
		writer.close();
	}

}
