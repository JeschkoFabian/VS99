package sheet6.server;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sheet6.IJob;
import sheet6.JobUtility;
import sheet6.dispatcher.IDispatcherService;

public class JobProcessingService implements IJobProcessingService {
	private int currentThreads = 0;
	private int MAX_THREADS = JobUtility.getThreadsPerServer();

	private Registry registry;

	private String serverName;

	private ExecutorService exec = Executors.newFixedThreadPool(MAX_THREADS);

	public JobProcessingService(String Name) {
		serverName = JobUtility.getDefaultServerName() + Name;
	}

	public boolean start() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			IJobProcessingService stub = (IJobProcessingService) UnicastRemoteObject
					.exportObject(this, 0);

//			LocateRegistry.getRegistry(JobUtility.getPort());
			registry = LocateRegistry.getRegistry(JobUtility.getPort());
			registry.rebind(serverName, stub);

			System.out.println(serverName + " bound");

			IDispatcherService dispatcher = (IDispatcherService) registry
					.lookup(JobUtility.getDispatcherExecutionName());

			dispatcher.register(serverName);

			return true;
		} catch (RemoteException e) {
			System.err.println("Failed to start service: " + e.getMessage());
			try {
				shutDown();
			} catch (RemoteException e1) {
				// bad luck
			}
			return false;
		} catch (NotBoundException e) {
			System.err.println("Failed to connect to dispatcher: "
					+ e.getMessage());
			try {
				shutDown();
			} catch (RemoteException e1) {
				// bad luck
			}
			return false;
		}
	}

	@Override
	public <T extends Serializable> void submit(Callable<T> job, IJob<T> callBack)
			throws RemoteException {
		if (currentThreads <= MAX_THREADS) {
			exec.submit(new CallableWrapper<>(job, callBack));
			currentThreads++;
			callBack.setRefused(false);
		} else {
			callBack.setRefused(true);
		}
	}

	@Override
	public int getFreeThreadCount() {
		return MAX_THREADS - currentThreads;
	}

	class CallableWrapper<T extends Serializable> implements Callable<T> {
		Callable<T> callable;
		IJob<T> result;

		public CallableWrapper(Callable<T> callable, IJob<T> callBack) {
			super();
			this.callable = callable;
			this.result = callBack;
		}

		@Override
		public T call() throws Exception {
//			System.out.println("WRAPPER STARTING EXECUTION");
			T res = callable.call();
//			System.out.println("WRAPPER DONE WITH EXECUTION");
			result.setResult(res);
			result.setDone(true);
//			System.out.println("WRAPPER SENT RESULT TO JOB");
			currentThreads--;
			System.out.println(serverName + ": Request done.");
			return res;
		}
	}

	@Override
	public void shutDown() throws RemoteException {
		try {
			if (registry != null) {
				registry.unbind(serverName);
//				UnicastRemoteObject.unexportObject(registry, true);
			}
		} catch (NotBoundException e) {
			System.err.println("Failed to unbind Server!");
			e.printStackTrace();
		}

	}

	// public static void main(String[] args) {
	// if (System.getSecurityManager() == null) {
	// System.setSecurityManager(new SecurityManager());
	// }
	// try {
	// JobProcessingService engine = new JobProcessingService();
	// IJobProcessingService stub = (IJobProcessingService) UnicastRemoteObject
	// .exportObject(engine, 0);
	//
	// LocateRegistry.createRegistry(JobUtility.getPort());
	// registry = LocateRegistry.getRegistry(JobUtility.getPort());
	// registry.rebind(JobUtility.getDefaultServerName(), stub);
	//
	// System.out.println("ComputeEngine bound");
	// } catch (Exception e) {
	// System.err.println("ComputeEngine exception:");
	// e.printStackTrace();
	// }
	//
	// }

}
