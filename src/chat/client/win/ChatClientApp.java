package chat.client.win;
import java.util.Scanner;

import chat.ChatClientThread;

public class ChatClientApp {

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while( true ) {
			
			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();
			
			if (name.isEmpty() == false ) {
				break;
			}
			
			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}
		
		scanner.close();
		
		//JOIN 처리
		//Response가 "JOIN:OK" 이면
		//

		ChatWindow cw = new ChatWindow(name);
		
		new ChatClientThread(cw).start();
		cw.show();
	}

}
