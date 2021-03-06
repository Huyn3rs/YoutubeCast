// livestreamer --player=omxplayer --fifo https://youtube.com best =ringbuffer-size="4M"
import java.net.*;
import java.io.*;

public class server {
	public static Process p;
	
	public static void execute_stream(String url) throws IOException, InterruptedException
	{
		Runtime r = Runtime.getRuntime();
		String i = String.format(
				"livestreamer --player=omxplayer --fifo %s best -ringbuffer-size=\"4M\"", url);
		System.out.println(i);
		p = r.exec("livestreamer --player=omxplayer --fifo " + url + " best -ringbuffer-size=\"4M\"");
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

		System.out.println("done");	
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		if (args.length != 1) {
            		System.err.println("Usage: java KnockKnockServer <port number>");
            		System.exit(1);
        	}

        int portNumber = Integer.parseInt(args[0]);
        InetAddress ip = InetAddress.getLocalHost();
  		System.out.println("Current IP address : " + ip.getHostAddress() + 
  				"\nCurrent Port Number : " + Integer.toString(portNumber));
  	  	

        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        		)
		{
        		System.out.println("Ready.");
        	
        		String inputLine;
        		String outputLine = "Ready.";
        		out.println(outputLine);
        		while ((inputLine = in.readLine()) != null) {
                		if (inputLine.equals("stop")){
					p.destroy();
                			continue;
				}
                		else if (inputLine.equals("quit"))
                			break;
                		System.out.println(inputLine);
                		outputLine = "Now Playing: " + inputLine;
                		out.println(outputLine);
                		try{
                			execute_stream(inputLine);
                		} catch (InterruptedException e) {
                			System.out.println("Failed to execute command.");
                		}
        	
        		}
		} catch(IOException e) {
        
       	  	}
	}

}
