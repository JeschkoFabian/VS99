package sheet6;


public class JobUtility {
	private static final int RMI_PORT = 50001;	
	private static final String RMI_SERVER_NAME = "JobProcessingService";
	private static final String RMI_DISPATCHER_NAME = "JobDispatcherService";
	private static final int THREADS_PER_SERVER = 10;
	
	public static int getPort(){
		return RMI_PORT;
	}
	
	public static String getDefaultServerName(){
		return RMI_SERVER_NAME;
	}
	
	public static String getDispatcherName(){
		return RMI_DISPATCHER_NAME;
	}
	
	public static int getThreadsPerServer(){
		return THREADS_PER_SERVER;
	}
	
	public static String getDispatcherRegistrationName(){
		return RMI_DISPATCHER_NAME + "Registration";
	}
	
	public static String getDispatcherExecutionName(){
		return RMI_DISPATCHER_NAME + "Execution";
	}
}
