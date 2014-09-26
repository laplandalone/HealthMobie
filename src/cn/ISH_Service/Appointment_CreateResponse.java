/**
 * Appointment_CreateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class Appointment_CreateResponse  implements java.io.Serializable {
    private java.lang.String appointment_CreateResult;

    public Appointment_CreateResponse() {
    }

    public Appointment_CreateResponse(
           java.lang.String appointment_CreateResult) {
           this.appointment_CreateResult = appointment_CreateResult;
    }


    /**
     * Gets the appointment_CreateResult value for this Appointment_CreateResponse.
     * 
     * @return appointment_CreateResult
     */
    public java.lang.String getAppointment_CreateResult() {
        return appointment_CreateResult;
    }


    /**
     * Sets the appointment_CreateResult value for this Appointment_CreateResponse.
     * 
     * @param appointment_CreateResult
     */
    public void setAppointment_CreateResult(java.lang.String appointment_CreateResult) {
        this.appointment_CreateResult = appointment_CreateResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Appointment_CreateResponse)) return false;
        Appointment_CreateResponse other = (Appointment_CreateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appointment_CreateResult==null && other.getAppointment_CreateResult()==null) || 
             (this.appointment_CreateResult!=null &&
              this.appointment_CreateResult.equals(other.getAppointment_CreateResult())));
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
        if (getAppointment_CreateResult() != null) {
            _hashCode += getAppointment_CreateResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Appointment_CreateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">Appointment_CreateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appointment_CreateResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "Appointment_CreateResult"));
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
