package sheet6.dispatcher;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import sheet6.IJob;
import sheet6.JobUtility;
import sheet6.server.IJobProcessingService;

public class JobDispatcher implements IDispatcherService {
	private Registry registry;
	private List<IJobProcessingService> services = new ArrayList<IJobProcessingService>();

	private int nextServiceNumber = 0;

	public JobDispatcher() {

	}

	public void start() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			IDispatcherService stub = (IDispatcherService) UnicastRemoteObject
					.exportObject(this, 0);

			LocateRegistry.createRegistry(JobUtility.getPort());
			registry = LocateRegistry.getRegistry(JobUtility.getPort());

			registry.rebind(JobUtility.getDispatcherRegistrationName(), stub);
			System.out.println("Dispatcher registration bound");

			registry.rebind(JobUtility.getDispatcherExecutionName(), stub);
			System.out.println("Dispatcher execution bound");
		} catch (RemoteException e) {
			System.err.println("Failed to bind Dispatcher.");
			e.printStackTrace();

			try {
				shutDown();
			} catch (RemoteException e1) {
				// bad luck..
			}
		}
	}

	@Override
	public <T extends Serializable> void submit(Callable<T> job, IJob<T> callBack)
			throws RemoteException {
		// if no services are registered nothing can be computed
		if (services.size() == 0)
			callBack.setRefused(true);

		// iterate over services and search for a free one
		for (int i = nextServiceNumber; i < services.size() + nextServiceNumber; i++) {
			// grab service
			IJobProcessingService currentService = services.get(i);
			// if service has free threads
			if (currentService.getFreeThreadCount() > 0) {
				nextServiceNumber = i + 1;				
				if (nextServiceNumber == services.size())
					nextServiceNumber = 0;
				
				// submit the job
				currentService.submit(job, callBack);

				// if job was accepted and returned a result, return to client,
				// else continue
				if (callBack.isRefused() == false)
					return;
			}
		}

		// no free services
		callBack.setRefused(true);
	}

	@Override
	public void shutDown() throws RemoteException {
		for (IJobProcessingService service : services) {
			service.shutDown();
		}

		try {
			if (registry != null) {
				registry.unbind(JobUtility.getDispatcherRegistrationName());
				registry.unbind(JobUtility.getDispatcherExecutionName());
				try {
					UnicastRemoteObject.unexportObject(registry, true);
				} catch (Exception e){
					// prob already unexported
				}
			}
		} catch (NotBoundException e) {
			System.err.println("Failed to unbind Dispatcher!");
			e.printStackTrace();
		}
	}

	@Override
	public void register(String serviceName) throws RemoteException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			IJobProcessingService service = (IJobProcessingService) registry
					.lookup(serviceName);
			services.add(service);
		} catch (Exception e) {
			System.err.println("Failed to bind service with name: "
					+ serviceName);
			e.printStackTrace();
		}

	}

	@Override
	public void unregister(String serviceName) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFreeThreadCount() throws RemoteException {
		int freeThreads = 0;

		for (IJobProcessingService service : services) {
			freeThreads += service.getFreeThreadCount();
		}

		return freeThreads;
	}
}
