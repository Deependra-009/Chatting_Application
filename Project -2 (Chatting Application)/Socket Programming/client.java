import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class client {

	Socket socket;
	BufferedReader br;
	PrintWriter out;

	public client() {

		try {
			System.out.println("Sending Request");
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("connection done");
			if(!socket.isClosed()) {
				System.out.println("true");
			}

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			startReading();
			startWriting();
			
			
			if(socket.isClosed()) {
				System.out.println("flase");
			}

		} catch (Exception e) {

		}

	}

	public void startReading() {

		Runnable r1 = () -> {

			System.out.println("reader started: ");
			while (true) {

				try {
					String msg = br.readLine();
					System.out.println("Client: "+msg);
					if(msg.equals("exit")){
						System.out.println("Client Terminate");
						if(socket.isClosed()) {
							System.out.println();
						}
						socket.close();
						break;
					}
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		};
		new Thread(r1).start();
	}

	public void startWriting() {

		Runnable r2 = () -> {

			System.out.println("Writer started");

			while (true) {

				try {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();
					out.println(content);
					out.flush();
					if(content.equals("exit")){
						System.out.println("Client Terminate");
						socket.close();
						break;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		};

		new Thread(r2).start();

	}

	public static void main(String[] args) {
		
		new client();

		System.out.println("Client");
	}

}
