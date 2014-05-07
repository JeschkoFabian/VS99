package sheet6;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class JobImpl<T extends Serializable> implements Job<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 427567083290889547L;
	private boolean isDone;
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

	public void setDone(boolean isDone) {
		System.out.println("set done");
		this.isDone = isDone;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
