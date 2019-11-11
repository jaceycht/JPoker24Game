import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Count extends Remote {
	String count(String name) throws RemoteException;
	String count(String name, String pw) throws RemoteException;
	String count(String name, String pw, String confirm_pw) throws RemoteException;
}
