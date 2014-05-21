package g99.client;

import g99.webservice.generated.Computation;
import g99.webservice.generated.ComputationService;

import java.math.BigInteger;
import java.util.Random;

import javax.xml.ws.WebServiceException;

public class Client {

	/** Number of threads running on this client */
	private static final int threadCount = 3;

	@SuppressWarnings("unused")
	public static void main(final String[] args) {

		final Computation service;
		try {
			service = new ComputationService().getComputationPort();
		} catch (WebServiceException e) {
			System.err.println("Error accessing WSDL: " + e.getMessage());
			return;
		}
		
		final Random rnd = new Random(System.currentTimeMillis());

		// Runnable for different threads
		final Runnable doSomeRandomTasks = new Runnable() {

			// Fine-Tuning. Number of random task to perform
			final int count = 100;

			@Override
			public void run() {
				for (int i = 0; i < count; i++)
					doRandomTask();
			}

			private final BigInteger[] getNumbers(int count) {
				final BigInteger[] result = new BigInteger[count];
				for (int i = 0; i < count; i++)
					result[i] = BigInteger.valueOf(rnd.nextInt(1000));
				return result;
			}

			private void doRandomTask() {
				int action = rnd.nextInt(4);
				final BigInteger[] numbers = getNumbers(2);
				StringBuilder line = new StringBuilder();

				switch (action) {
				case 1:
					line.append(numbers[0] + " + " + numbers[1] + " = ");
					line.append(service.add(numbers[0], numbers[1]));
					break;
				case 2:
					line.append(numbers[0] + " - " + numbers[1] + " = ");
					line.append(service.sub(numbers[0], numbers[1]));

					break;
				case 3:
					line.append(numbers[0] + " * " + numbers[1] + " = ");
					line.append(service.mul(numbers[0], numbers[1]));

					break;
				default:
					// NOT too big faculty calculations
					final BigInteger number = BigInteger.valueOf(Math.abs(rnd
							.nextLong() % 30));

					line.append("fac(" + number + ") = ");
					line.append(service.fac(number));

					break;
				}

				System.out.println(line.toString());
			}
		};

		long computationTime = -System.currentTimeMillis();
		if (threadCount < 2) {
			// Single threaded variant. Just run
			doSomeRandomTasks.run();
		} else {
			System.out.println("Setting up " + threadCount + " threads ... ");
			final Thread[] threads = new Thread[threadCount];
			for (int i = 0; i < threadCount; i++) {
				threads[i] = new Thread(doSomeRandomTasks);
				threads[i].setDaemon(true);
			}
			System.out.println("Launching ...");
			for (int i = 0; i < threadCount; i++) {
				threads[i].start();
			}
			try {
				for (int i = 0; i < threadCount; i++)
					threads[i].join();
			} catch (InterruptedException ex) {
				System.err.println("Interrupted");
				System.exit(-1);
				return;
			}
		}

	}
}
