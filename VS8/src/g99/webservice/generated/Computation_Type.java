
package g99.webservice.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="op" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operand1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="operand2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "op",
    "operand1",
    "operand2"
})
@XmlRootElement(name = "Computation")
public class Computation_Type {

    @XmlElement(required = true)
    protected String op;
    protected double operand1;
    protected double operand2;

    /**
     * Gets the value of the op property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOp() {
        return op;
    }

    /**
     * Sets the value of the op property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOp(String value) {
        this.op = value;
    }

    /**
     * Gets the value of the operand1 property.
     * 
     */
    public double getOperand1() {
        return operand1;
    }

    /**
     * Sets the value of the operand1 property.
     * 
     */
    public void setOperand1(double value) {
        this.operand1 = value;
    }

    /**
     * Gets the value of the operand2 property.
     * 
     */
    public double getOperand2() {
        return operand2;
    }

    /**
     * Sets the value of the operand2 property.
     * 
     */
    public void setOperand2(double value) {
        this.operand2 = value;
    }

}
