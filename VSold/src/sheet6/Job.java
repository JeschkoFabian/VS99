package sheet6;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public class Job<T extends Serializable> implements IJob<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 427567083290889547L;
	private boolean isDone;
	private boolean isRefused;
	private T result;

	@Override
	public boolean isDone() {
		return isDone;
	}

	@Override
	public T getResult() throws InterruptedException, ExecutionException {
		if (!isDone())
			throw new IllegalStateException("Computation not done");
		return result;
	}

	@Override
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	@Override
	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public boolean isRefused() throws RemoteException {
		return isRefused;
	}

	@Override
	public void setRefused(boolean isRefused) throws RemoteException {
		this.isRefused = isRefused;
	}

}
