package sheet5;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements ICompute {

	private static Registry registry;

	@Override
	public int plus(int a, int b) throws RemoteException {
		return a + b;
	}

	@Override
	public int minus(int a, int b) throws RemoteException {
		return a - b;
	}

	@Override
	public int mult(int a, int b) throws RemoteException {
		return a * b;
	}

	@Override
	public int fibonacci(int n) throws RemoteException {
		System.out.println("Fib(" + n + ") called");
		int res = fib(n);
		System.out.println("Computation done: Fib(" + n + ") is " + res);
		return res;

	}

	private int fib(int n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else
			return fib(n - 1) + fib(n - 2);
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "ICompute";
			ICompute engine = new ComputeEngine();
			ICompute stub = (ICompute) UnicastRemoteObject.exportObject(engine,
					0);

			LocateRegistry.createRegistry(50000);
			registry = LocateRegistry.getRegistry(50000);
			registry.rebind(name, stub);

			System.out.println("ComputeEngine bound");
		} catch (Exception e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}

	}

	@Override
	public <T> T executeTask(ITask<T> t) throws RemoteException {
		return t.execute();
	}

	@Override
	public void deepThought(String question, IRemoteAnswer remoteAnswer)
			throws RemoteException {

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String answer = "The answer to your question " + question
				+ " is probably 42";

		remoteAnswer.setAnswer(answer);
	}

}
