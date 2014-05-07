package sheet6;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;

public class JobProcessingClientA implements Runnable, Remote, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3473799680434074696L;
	private static int clients = 0;
	private static final int RMI_PORT = JobProcessingService.RMI_PORT;
	private static final String RMI_INTERFACE_NAME = JobProcessingService.RMI_INTERFACE_NAME;
	private int clientId = clients++;

	@Override
	public void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry registry = LocateRegistry.getRegistry(RMI_PORT);
			IJobProcessingService comp = (IJobProcessingService) registry
					.lookup(RMI_INTERFACE_NAME);
			System.out.println("Client " + clientId + " request");
			Job<Integer> p = comp.submit(new CehTask());
			while (!p.isDone()) {
				System.out.println(p.isDone());
				System.out.println("Callable not yet done with execution");
				Thread.sleep(100);
			}
			System.out.println("Client " + clientId + " result: "
					+ p.getResult());
		} catch (Exception e) {
			System.err.println("ComputePi exception:");
			e.printStackTrace();
		}

	}

	static class CehTask implements Callable<Integer>, Serializable {

		private static final long serialVersionUID = -6113384650361683638L;

		@Override
		public Integer call() throws Exception {
			Thread.sleep(10);
			System.out.println("done");
			return Integer.valueOf(77);
		};

	}

	public static final int MAX = 1;

	public static void main(String[] args) {

		for (int i = 0; i < MAX; i++) {
			new Thread(new JobProcessingClientA()).start();
		}
	}

}
