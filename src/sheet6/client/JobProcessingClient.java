package sheet6.client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;

import sheet6.IJob;
import sheet6.Job;
import sheet6.JobUtility;
import sheet6.server.IJobProcessingService;

public class JobProcessingClient implements Runnable, Remote, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3473799680434074696L;
	private static int clients = 0;
	private int clientId = clients++;
	private int requests;

	public JobProcessingClient(int requests) {
		this.requests = requests;
	}

	@Override
	public void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry registry = LocateRegistry
					.getRegistry(JobUtility.getPort());
			IJobProcessingService comp = (IJobProcessingService) registry
					.lookup(JobUtility.getDispatcherExecutionName());

			@SuppressWarnings("unchecked")
			IJob<Integer> job = (IJob<Integer>) UnicastRemoteObject
					.exportObject(new Job<Integer>(), 0);

			registry.rebind("Job" + clientId, job);

			for (int i = 0; i < requests; i++) {
				System.out.println("Client " + clientId + " request");
				comp.submit(new CallImplementation(), job);

				while (job != null && !job.isDone()) {
					System.out.println("Client " + clientId + " waiting...");
					Thread.sleep(100);
				}
				System.out.println("Client " + clientId + " result: "
						+ job.getResult());
			}

			registry.unbind("Job" + clientId);
			try {
				UnicastRemoteObject.unexportObject(job, true);
			} catch (Exception e) {
				// welp
			}

//			try {
//				UnicastRemoteObject.unexportObject(registry, true);
//			} catch (Exception e) {
//				// welp
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class CallImplementation implements Callable<Integer>, Serializable {

		private static final long serialVersionUID = -6113384650361683638L;

		@Override
		public Integer call() throws Exception {
			Thread.sleep(10);
			return Integer.valueOf(clientId);
		};

	}

	public static final int MAX = 1;

	public static void main(String[] args) {

		for (int i = 0; i < MAX; i++) {
			new Thread(new JobProcessingClient(1)).start();
		}
	}

}
