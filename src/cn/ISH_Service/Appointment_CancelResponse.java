/**
 * Appointment_CancelResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class Appointment_CancelResponse  implements java.io.Serializable {
    private java.lang.String appointment_CancelResult;

    public Appointment_CancelResponse() {
    }

    public Appointment_CancelResponse(
           java.lang.String appointment_CancelResult) {
           this.appointment_CancelResult = appointment_CancelResult;
    }


    /**
     * Gets the appointment_CancelResult value for this Appointment_CancelResponse.
     * 
     * @return appointment_CancelResult
     */
    public java.lang.String getAppointment_CancelResult() {
        return appointment_CancelResult;
    }


    /**
     * Sets the appointment_CancelResult value for this Appointment_CancelResponse.
     * 
     * @param appointment_CancelResult
     */
    public void setAppointment_CancelResult(java.lang.String appointment_CancelResult) {
        this.appointment_CancelResult = appointment_CancelResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Appointment_CancelResponse)) return false;
        Appointment_CancelResponse other = (Appointment_CancelResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appointment_CancelResult==null && other.getAppointment_CancelResult()==null) || 
             (this.appointment_CancelResult!=null &&
              this.appointment_CancelResult.equals(other.getAppointment_CancelResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAppointment_CancelResult() != null) {
            _hashCode += getAppointment_CancelResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Appointment_CancelResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">Appointment_CancelResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appointment_CancelResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "Appointment_CancelResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
