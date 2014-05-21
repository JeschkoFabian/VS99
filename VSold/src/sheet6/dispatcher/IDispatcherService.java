package sheet6.dispatcher;

import java.rmi.RemoteException;

import sheet6.server.IJobProcessingService;

public interface IDispatcherService extends IJobProcessingService {

	public void register (String serviceName) throws RemoteException;
	public void unregister (String serviceName) throws RemoteException;

}
