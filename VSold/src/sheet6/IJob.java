package sheet6;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public interface IJob<T extends Serializable> extends Remote, Serializable {

	public boolean isDone() throws RemoteException;

	public T getResult() throws RemoteException, InterruptedException,
			ExecutionException;
	
	public void setDone(boolean isDone) throws RemoteException;
	
	public void setResult(T result) throws RemoteException;
	
	public boolean isRefused() throws RemoteException;
	
	public void setRefused(boolean isRefused) throws RemoteException;
}
