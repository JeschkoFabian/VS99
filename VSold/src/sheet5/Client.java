package sheet5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client implements Runnable {

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
						
			IRemoteAnswer remoteAnswer = (IRemoteAnswer) UnicastRemoteObject.exportObject(new RemoteAnswer(),0);
			registry.rebind("Answer" + clientId, remoteAnswer);
			
			String question = "Answer to the Ultimate Question of Life, The Universe, and Everything";
			
			System.out.println("Asking server the following question: " + question);
			comp.deepThought(question, remoteAnswer);
			
			
			System.out.println("Client " + clientId + " request");
//			int p = comp.fibonacci(40);
//			System.out.println("Client " + clientId + " result: " + p);
			
			while (!remoteAnswer.isAnswerSet()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("The almighty server answered to the question " + question + " with:");
			System.out.println(remoteAnswer.getAnswer());
			
			registry.unbind("Answer" + clientId);
			UnicastRemoteObject.unexportObject(remoteAnswer, true);
			
		} catch (Exception e) {
			System.err.println("ComputePi exception:");
			e.printStackTrace();
		}

	}

	public static final int MAX = 10;

	public static void main(String[] args) {

		for (int i = 0; i < MAX; i++) {
			new Thread(new Client()).start();
		}
	}
}
