package sheet6;

import java.rmi.RemoteException;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import sheet6.client.JobProcessingClient;
import sheet6.dispatcher.JobDispatcher;
import sheet6.server.JobProcessingService;

public class StartEverything {

	private static int CLIENTS = 5;
	private static int EXECUTIONS = 5;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<Thread> threads = new ArrayList<Thread> (CLIENTS);
			JobDispatcher dispatcher = new JobDispatcher();

			dispatcher.start();

			JobProcessingService service1 = new JobProcessingService("1");
			JobProcessingService service2 = new JobProcessingService("2");
			JobProcessingService service3 = new JobProcessingService("3");

			service1.start();
			service2.start();
			service3.start();

			for (int i = 0; i < CLIENTS; i++) {
				Thread thread = new Thread(new JobProcessingClient(EXECUTIONS)); 
				threads.add(thread);
				thread.start();
			}
			
			for (int i = 0; i < CLIENTS; i++){
				try {
					threads.get(i).join();
				} catch (InterruptedException e) {
					System.err.println("Interrupted!");
				}
			}
			

			
			dispatcher.shutDown();
		} catch (RemoteException e) {
			System.err.println("Failed to shutdown dispatcher/servers.");
			e.printStackTrace();
		} catch (AccessControlException e) {
			System.err
					.println("Permissions not correct, try to add this to your VM Args:\n");
			System.err
					.println("-Djava.rmi.server.codebase=file:$pathToBin\n"
							+ "-Djava.rmi.server.hostname=localhost\n"
							+ "-Djava.security.policy=file:$pathToBin/server.policy");
		}

	}

}
