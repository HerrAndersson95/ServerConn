import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
			try {
				ServerSocket ss = new ServerSocket(30000);
				System.out.println("Server is up and listening");
					while(true){
					Socket conn = ss.accept();
					HTTPRequest client = new HTTPRequest(conn);
					client.start();
					System.out.println("New Connection");
				}	
			
			} catch (IOException e) {	e.printStackTrace();	}
	}

}
