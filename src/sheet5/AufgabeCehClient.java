package sheet5;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AufgabeCehClient implements Runnable {

	private static int clients = 0;
	private int clientId = clients++;

	@Override
	public void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "ICompute";
			Registry registry = LocateRegistry.getRegistry(50000);
			ICompute comp = (ICompute) registry.lookup(name);
			System.out.println("Client " + clientId + " request");
			String p = comp.executeTask(new CehTaskZwo());
			System.out.println("Client " + clientId + " result: " + p);
		} catch (Exception e) {
			System.err.println("ComputePi exception:");
			e.printStackTrace();
		}

	}

	static class CehTask implements ITask<String>, Serializable {

		private static final long serialVersionUID = -6113384650361683638L;

		@Override
		public String execute() {
			return "it" + " wörks";
		};

	}

	static class CehTaskZwo implements ITask<String>, Serializable {

		private static final long serialVersionUID = -6113384651361683638L;

		@Override
		public String execute() {
			return "it" + " wukrks";
		};

	}

	public static final int MAX = 1;

	public static void main(String[] args) {

		for (int i = 0; i < MAX; i++) {
			new Thread(new AufgabeCehClient()).start();
		}
	}
}
