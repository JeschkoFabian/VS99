package sheet5;

import java.rmi.RemoteException;

public class RemoteAnswer implements IRemoteAnswer {
	private String answer = null;
	
	@Override
	public void setAnswer(String answer) throws RemoteException {
		this.answer = answer;
	}

	@Override
	public String getAnswer() throws RemoteException  {
		return answer;
	}

	@Override
	public boolean isAnswerSet() throws RemoteException  {
		return (answer == null) ? false : true;
	}

}
