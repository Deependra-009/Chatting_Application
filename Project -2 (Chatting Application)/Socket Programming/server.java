import java.net.*;
import java.io.*;

public class server {
	
	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	public server() {
		
		
		try {
			
			server=new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting...");
			socket=server.accept();
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			
			
			startReading();
			startWriting();
			
			
			
		}
		catch(Exception e) {
			
		}

	}
	public void startReading()  {
		
		Runnable r1=()->{
			
			System.out.println("reader started: ");
			while(true) {
				
				try {
					String msg=br.readLine();
					System.out.println("Client: "+msg);
					if(msg.equals("exit")){
						System.out.println("Client Terminate");
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
		
		Runnable r2=()->{
			
			System.out.println("Writer started");
			
			
			while(true) {
				
				try {
					BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
					String content=br1.readLine();
					out.println(content);
					out.flush();
					
					if(content.equals("exit")){
						System.out.println("Client Terminate");
						socket.close();
						break;
					}
					
					
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
			
			
			
		};
		
		new Thread(r2).start();
		
		
	}
	
	
	public static void main(String[] args) {
		System.out.println("Server Start....");
		new server();
	}

}
