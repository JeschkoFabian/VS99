package g99.server;

import g99.webservice.Computation;

import javax.swing.JOptionPane;
import javax.xml.ws.Endpoint;

public class Server {

	public final static String endpointURL = "http://localhost:8081/services";

	public static void main(final String[] args) {
		String url = endpointURL;

		System.out.println("Starting webservice at " + url);
		Endpoint endpoint = Endpoint.publish(url, new Computation());

		JOptionPane.showMessageDialog(null, url + "\n\nKill Server?");

		endpoint.stop();
	}

}
