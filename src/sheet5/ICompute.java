package sheet5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICompute extends Remote {

	int plus(int a, int b) throws RemoteException;

	int minus(int a, int b) throws RemoteException;

	int mult(int a, int b) throws RemoteException;

	int fibonacci(int a) throws RemoteException;

	<T> T executeTask(ITask<T> t) throws RemoteException;

	void deepThought(String question, IRemoteAnswer remoteAnswer) throws RemoteException;

}
