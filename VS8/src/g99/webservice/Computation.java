package g99.webservice;

import java.math.BigInteger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "Computation")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Computation {


	@WebMethod
	public BigInteger add(BigInteger number1, BigInteger number2) {
		return number1.add(number2);
	}

	@WebMethod
	public BigInteger sub(BigInteger number1, BigInteger number2) {
		return number1.subtract(number2);
	}

	@WebMethod
	public BigInteger mul(BigInteger number1, BigInteger number2) {
		return number1.multiply(number2);
	}

	@WebMethod
	public BigInteger fac(BigInteger number) {
		BigInteger result = BigInteger.ONE;

		while (number.compareTo(BigInteger.ONE) == 1) {
			result = result.multiply(number);
			number = number.subtract(BigInteger.ONE);
		}
		return result;
	}

}
