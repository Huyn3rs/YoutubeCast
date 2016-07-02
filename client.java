import java.net.*;
import java.io.*;

public class client {
	

	
	public static void main(String[] args) throws IOException
	{
		String hostName;
		int portNumber = 22;
		BufferedReader input =
                new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter an IP address : ");
        hostName = input.readLine();
        System.out.println("Enter a port number : ");
        try{
        	portNumber = Integer.parseInt(input.readLine());
        }
        catch (NumberFormatException e)
        {
        	System.err.println("Port is not a number.");
        	System.exit(1);
        }
        try 
        (
            Socket SSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(SSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(SSocket.getInputStream()));
	        ) 
	        {
	            
	            String fromServer;
	            String fromUser;

	            while ((fromServer = in.readLine()) != null) {
	            	System.out.println("Enter a URL to watch: ");
	                fromUser = input.readLine();
	                
	                if (fromUser != null) {
	                    out.println(fromUser);
	                    String usrcmd;
	                    while ((usrcmd = input.readLine()) != null)
	                    {
	                    	if (usrcmd.equals("quit") || usrcmd.equals("stop"))
	                    		out.println(usrcmd);
	                    	System.out.println("[" + usrcmd + "]");
	                    	
	                    }
	                }
	            }
	            
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                hostName);
	            System.exit(1);
	        }
	}
}
