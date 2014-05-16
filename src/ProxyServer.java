import java.io.*;
import java.net.*;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xc.http.Request;


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
            
                System.out.println("========== begin ==========");
                Request request = getRequest(in);
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

	private static Request getRequest(BufferedReader in) throws IOException {
		String inputLine;
		int i = 0;
		Request request = null;
		while ((inputLine = in.readLine()) != null) {
			System.out.println("request: "+inputLine);
			if	(i == 0) {
				request = extractUriMethodProtocal(inputLine);
			} else if (inputLine.length() == 0) {
				break;
			} else {
				SimpleEntry<String,String> kv = extractKeyAndValue(inputLine);
				request.addHeader(kv);
			}
			i++;
		}
		return request;
	}

	private static Request extractUriMethodProtocal(String inputLine) {
		String[] strings = inputLine.split(" ");
		System.out.println("first line strings length "+strings.length);
		String method = strings[0];
		String uri = strings[1];
		String protocol = strings[2];
		System.out.println("method "+method);
		System.out.println("uri "+uri);
		System.out.println("protocol "+protocol);
		Request request = new Request(uri, method, protocol);
		return request;
	}

	private static SimpleEntry<String, String> extractKeyAndValue(String inputLine) {
		Pattern pattern = 
		        Pattern.compile("^([\\w-]+):\\s*(.+)$", Pattern.CASE_INSENSITIVE);

		        Matcher matcher = pattern.matcher(inputLine);

		        if (matcher.find()) {
		        	System.out.println("key: "+matcher.group(1));
		            System.out.println("value: "+matcher.group(2));
		            SimpleEntry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(matcher.group(1), matcher.group(2));
		            return entry;
		        } else {
		        	System.out.println("we can not find");
		        	return null;
		        }
		        
	}

}
