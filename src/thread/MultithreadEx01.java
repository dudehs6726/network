package thread;

public class MultithreadEx01 {

	public static void main(String[] args) {
		
		Thread digitThread = new DigitThread();
		
		digitThread.start();
		
		for(int i= 0; i<= 9; i++)
		{
			System.out.print(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
