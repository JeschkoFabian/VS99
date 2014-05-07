package sheet6;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public interface Job<T extends Serializable> extends Remote, Serializable {

	public boolean isDone() throws RemoteException;

	public T getResult() throws RemoteException, InterruptedException,
			ExecutionException;
}
