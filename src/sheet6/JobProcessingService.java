package sheet6;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobProcessingService implements IJobProcessingService, Remote {

	private static final int MAX_THREADS = 10;
	public static final int RMI_PORT = 50001;
	public static final String RMI_INTERFACE_NAME = IJobProcessingService.class
			.getSimpleName();

	private static Registry registry;

	ExecutorService exec = Executors.newFixedThreadPool(MAX_THREADS);

	@Override
	public <T extends Serializable> Job<T> submit(Callable<T> job)
			throws RemoteException {
		JobImpl<T> ret = new JobImpl<T>();
		exec.submit(new CallableWrapper<>(job, ret));
		return ret;
	}

	static class CallableWrapper<T extends Serializable> implements Callable<T> {
		Callable<T> callable;
		JobImpl<T> result;

		public CallableWrapper(Callable<T> callable, JobImpl<T> result) {
			super();
			this.callable = callable;
			this.result = result;
		}

		@Override
		public T call() throws Exception {
			System.out.println("WRAPPER STARTING EXECUTION");
			T res = callable.call();
			System.out.println("WRAPPER DONE WITH EXECUTION");
			result.setDone(true);
			result.setResult(res);
			System.out.println("WRAPPER SET RESULT TO JOB");
			return res;
		}

	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			JobProcessingService engine = new JobProcessingService();
			IJobProcessingService stub = (IJobProcessingService) UnicastRemoteObject
					.exportObject(engine, 0);

			LocateRegistry.createRegistry(RMI_PORT);
			registry = LocateRegistry.getRegistry(RMI_PORT);
			registry.rebind(RMI_INTERFACE_NAME, stub);

			System.out.println("ComputeEngine bound");
		} catch (Exception e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}

	}

}
