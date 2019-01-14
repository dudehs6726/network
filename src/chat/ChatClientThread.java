package chat;

import java.io.BufferedReader;
import java.io.IOException;

import chat.client.win.ChatWindow;

public class ChatClientThread extends Thread {

	private BufferedReader bufferedReader;
	private ChatWindow chatWindow;
	
	public ChatClientThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}
	
	public ChatClientThread(ChatWindow chatWindow) {
		
		//UI
		this.chatWindow = chatWindow;
	}
	
	public ChatClientThread(ChatWindow chatWindow, BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
		
		//UI
		this.chatWindow = chatWindow;
		this.chatWindow.show();
	}
	
	@Override
	public void run() {
		//reader를 통해 읽은 데이터 콘솔에 출력하기(message 처리)
		try {
			while(true) {
				
				String request = bufferedReader.readLine();
				System.out.println(request);
				
				chatWindow.textArea(request);
			
			}
		}catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
