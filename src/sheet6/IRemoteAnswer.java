package sheet6;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteAnswer extends Remote{
	
	public void setAnswer(String answer) throws RemoteException;
	
	public String getAnswer() throws RemoteException;
	
	public boolean isAnswerSet() throws RemoteException;
}
