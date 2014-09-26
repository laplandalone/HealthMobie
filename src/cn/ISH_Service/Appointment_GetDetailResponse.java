/**
 * Appointment_GetDetailResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class Appointment_GetDetailResponse  implements java.io.Serializable {
    private java.lang.String appointment_GetDetailResult;

    public Appointment_GetDetailResponse() {
    }

    public Appointment_GetDetailResponse(
           java.lang.String appointment_GetDetailResult) {
           this.appointment_GetDetailResult = appointment_GetDetailResult;
    }


    /**
     * Gets the appointment_GetDetailResult value for this Appointment_GetDetailResponse.
     * 
     * @return appointment_GetDetailResult
     */
    public java.lang.String getAppointment_GetDetailResult() {
        return appointment_GetDetailResult;
    }


    /**
     * Sets the appointment_GetDetailResult value for this Appointment_GetDetailResponse.
     * 
     * @param appointment_GetDetailResult
     */
    public void setAppointment_GetDetailResult(java.lang.String appointment_GetDetailResult) {
        this.appointment_GetDetailResult = appointment_GetDetailResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Appointment_GetDetailResponse)) return false;
        Appointment_GetDetailResponse other = (Appointment_GetDetailResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appointment_GetDetailResult==null && other.getAppointment_GetDetailResult()==null) || 
             (this.appointment_GetDetailResult!=null &&
              this.appointment_GetDetailResult.equals(other.getAppointment_GetDetailResult())));
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
        if (getAppointment_GetDetailResult() != null) {
            _hashCode += getAppointment_GetDetailResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Appointment_GetDetailResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">Appointment_GetDetailResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appointment_GetDetailResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "Appointment_GetDetailResult"));
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
