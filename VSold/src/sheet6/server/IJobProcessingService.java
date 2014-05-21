package sheet6.server;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

import sheet6.IJob;

public interface IJobProcessingService extends Remote {

	public <T extends Serializable> void submit(Callable<T> job, IJob<T> callBack)
			throws RemoteException;

	public void shutDown() throws RemoteException;
	
	public int getFreeThreadCount() throws RemoteException;

}
