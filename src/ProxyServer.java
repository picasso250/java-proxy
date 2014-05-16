import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProxyServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            
        ) {
        	while (true) {
        		Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            
                String inputLine, outputLine;

                System.out.println("========== begin ==========");

                int i = 0;
                while ((inputLine = in.readLine()) != null) {
                	System.out.println("request: "+inputLine);
                	if	(i == 0) {
                		String[] strings = inputLine.split(" ");
                		System.out.println("first line strings length "+strings.length);
                		String method = strings[0];
                		String uri = strings[1];
                		String protocol = strings[2];
                		System.out.println("method "+method);
                		System.out.println("uri "+uri);
                		System.out.println("protocol "+protocol);
                	} else if (inputLine.length() == 0) {
                		break;
                	} else {
                		Pattern pattern = 
                	            Pattern.compile("^([\\w-]+):\\s*(.+)$", Pattern.CASE_INSENSITIVE);

                	            Matcher matcher = pattern.matcher(inputLine);

                	            if (matcher.find()) {
                	            	System.out.println("key: "+matcher.group(1));
                    	            System.out.println("value: "+matcher.group(2));
                	            } else {
                	            	System.out.println("we can not find");
                	            }
                	            
                	}
                	i++;
                	
                }
                System.out.println("close connection.");
                out.println("hello--");
                clientSocket.close();
                System.out.println("========== end ==========");
        	}
        	
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

	}

}
