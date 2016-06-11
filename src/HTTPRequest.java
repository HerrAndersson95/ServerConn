import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class HTTPRequest extends Thread{
	
	private Socket client;
	
	public HTTPRequest(Socket s){
		this.client = s;
	}
	public void run(){
		try{
			System.out.println("--Begin read requestLine--");
			System.out.println(client.getInetAddress().getHostAddress().toString());
			InputStream is = client.getInputStream();
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String webLine = br.readLine();
			System.out.println(webLine);
			
			StringTokenizer subs = new StringTokenizer(webLine);
			subs.nextToken(); //GET commands often
			String command = subs.nextToken().substring(1); //What is written after 192.168.1.74:30000/
			
			System.out.println("Command was: " + command);
			System.out.println("--END--");
			
			System.out.println("HE GETS THE FUCKING INDEX");
			
			FileInputStream fis = null;
			boolean fileFound = true;
			
			try{
				fis = new FileInputStream("index.html"); //fil.txt
				
			} catch (FileNotFoundException e1){
				fileFound = false;
				System.out.println("Could not locate index.html");
				e1.printStackTrace();
			}
			
			if(fileFound){
				System.out.println("File was found, sending");
				sendSite(fis, dos);
			}
			dos.close();
			fis.close();
			is.close();
			client.close();
			return;
			
		} catch (Exception e){
			//e.printStackTrace();
		}
	}
	
	private void sendSite(FileInputStream fis, DataOutputStream dos) throws IOException{
		byte[] buff = new byte[1024];
		int bytes = 0;
		
		while((bytes = fis.read(buff)) != -1){
			dos.write(buff, 0 ,bytes);
		}
	}
}
