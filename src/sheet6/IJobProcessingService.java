package sheet6;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

public interface IJobProcessingService extends Remote {

	public <T extends Serializable> Job<T> submit(Callable<T> job)
			throws RemoteException;

}
