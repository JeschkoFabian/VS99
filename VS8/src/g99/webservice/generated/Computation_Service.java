
package g99.webservice.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Computation", targetNamespace = "http://vs8.g99/Computation/", wsdlLocation = "file:/home/ms/workspace/VS8/Computation.wsdl")
public class Computation_Service
    extends Service
{

    private final static URL COMPUTATION_WSDL_LOCATION;
    private final static WebServiceException COMPUTATION_EXCEPTION;
    private final static QName COMPUTATION_QNAME = new QName("http://vs8.g99/Computation/", "Computation");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/ms/workspace/VS8/Computation.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        COMPUTATION_WSDL_LOCATION = url;
        COMPUTATION_EXCEPTION = e;
    }

    public Computation_Service() {
        super(__getWsdlLocation(), COMPUTATION_QNAME);
    }

    public Computation_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), COMPUTATION_QNAME, features);
    }

    public Computation_Service(URL wsdlLocation) {
        super(wsdlLocation, COMPUTATION_QNAME);
    }

    public Computation_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, COMPUTATION_QNAME, features);
    }

    public Computation_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Computation_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Computation
     */
    @WebEndpoint(name = "ComputationSOAP")
    public Computation getComputationSOAP() {
        return super.getPort(new QName("http://vs8.g99/Computation/", "ComputationSOAP"), Computation.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Computation
     */
    @WebEndpoint(name = "ComputationSOAP")
    public Computation getComputationSOAP(WebServiceFeature... features) {
        return super.getPort(new QName("http://vs8.g99/Computation/", "ComputationSOAP"), Computation.class, features);
    }

    private static URL __getWsdlLocation() {
        if (COMPUTATION_EXCEPTION!= null) {
            throw COMPUTATION_EXCEPTION;
        }
        return COMPUTATION_WSDL_LOCATION;
    }

}
