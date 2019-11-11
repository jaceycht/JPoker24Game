import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;

public class Counter extends UnicastRemoteObject implements Count {

	public static void main(String[] args) {
		try {
			Counter app = new Counter();
			System.setSecurityManager(new SecurityManager());
			Naming.rebind("Counter", app);
			System.out.println("Service registered");
			
			BufferedWriter writer_online = new BufferedWriter(new FileWriter("OnlineUser.txt", false));
			writer_online.write("");
			writer_online.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter("UserInfo.txt", true));
			writer.write("");
			writer.close();
			
		} catch(Exception e) {
			System.err.println("Exception thrown: "+e);
		}
	}
	
	public Counter() throws RemoteException {}

	/* for logout */
	public String count(String name) throws RemoteException {
		try {
			File inputFile = new File("OnlineUser.txt");
			File tempFile = new File("temp.txt");
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(name)) continue;
			    writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close(); 
			boolean successful = tempFile.renameTo(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ok";
	}

	/* for login */
	public String count(String name, String pw) throws RemoteException {
		try {
			String line, password = null;
	    	Boolean found=false;
	    	BufferedReader reader = new BufferedReader(new FileReader("UserInfo.txt"));
	        while ((line=reader.readLine()) != null) {     
	        	String[] data = line.split(",");
	        	if (name.equals(data[0])) {
	        		found = true;
					password = data[1];
	        	}
	        }	
	        if (found.equals(false)) return "invalid name";
	        BufferedReader reader_online = new BufferedReader(new FileReader("OnlineUser.txt"));
	        while ((line=reader_online.readLine()) != null) {     
	        	if (name.equals(line)) {
					return "multiple";
	        	}
	        }	
	        
	        if (pw.equals(password)) {
	        	BufferedWriter writer_online = new BufferedWriter(new FileWriter("OnlineUser.txt", true));
				writer_online.write(name);
				writer_online.newLine();
				writer_online.close();
	        } else {
	        	return "invalid pw";
	        }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	/* for register */
	public String count(String name, String pw, String confirm_pw) throws RemoteException {
		try {
			String line;
	    	BufferedReader reader = new BufferedReader(new FileReader("UserInfo.txt"));
	        while ((line=reader.readLine()) != null) {     
	        	String[] data = line.split(",");
	        	if (name.equals(data[0])) {
					return "duplicated";
	        	}
	        }					    	
	        
	    	BufferedWriter writer = new BufferedWriter(new FileWriter("UserInfo.txt", true));
	    	writer.write(name);
			writer.write(",");
			writer.write(pw);
			writer.newLine();
			writer.close();
			
			BufferedWriter writer_online = new BufferedWriter(new FileWriter("OnlineUser.txt", true));
			writer_online.write(name);
			writer_online.newLine();
			writer_online.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ok";
	}

}
