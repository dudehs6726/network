package chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread {

	private BufferedReader bufferedReader;
	
	public ChatClientThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}
	
	@Override
	public void run() {
		//reader를 통해 읽은 데이터 콘솔에 출력하기(message 처리)
		try {
			while(true) {

				String request = bufferedReader.readLine();
				System.out.println(request);
			}
		}catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
