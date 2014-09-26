/**
 * Appointment_Cancel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class Appointment_Cancel  implements java.io.Serializable {
    private java.lang.String appointmentid;

    private java.lang.String cancelreason;

    public Appointment_Cancel() {
    }

    public Appointment_Cancel(
           java.lang.String appointmentid,
           java.lang.String cancelreason) {
           this.appointmentid = appointmentid;
           this.cancelreason = cancelreason;
    }


    /**
     * Gets the appointmentid value for this Appointment_Cancel.
     * 
     * @return appointmentid
     */
    public java.lang.String getAppointmentid() {
        return appointmentid;
    }


    /**
     * Sets the appointmentid value for this Appointment_Cancel.
     * 
     * @param appointmentid
     */
    public void setAppointmentid(java.lang.String appointmentid) {
        this.appointmentid = appointmentid;
    }


    /**
     * Gets the cancelreason value for this Appointment_Cancel.
     * 
     * @return cancelreason
     */
    public java.lang.String getCancelreason() {
        return cancelreason;
    }


    /**
     * Sets the cancelreason value for this Appointment_Cancel.
     * 
     * @param cancelreason
     */
    public void setCancelreason(java.lang.String cancelreason) {
        this.cancelreason = cancelreason;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Appointment_Cancel)) return false;
        Appointment_Cancel other = (Appointment_Cancel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appointmentid==null && other.getAppointmentid()==null) || 
             (this.appointmentid!=null &&
              this.appointmentid.equals(other.getAppointmentid()))) &&
            ((this.cancelreason==null && other.getCancelreason()==null) || 
             (this.cancelreason!=null &&
              this.cancelreason.equals(other.getCancelreason())));
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
        if (getAppointmentid() != null) {
            _hashCode += getAppointmentid().hashCode();
        }
        if (getCancelreason() != null) {
            _hashCode += getCancelreason().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Appointment_Cancel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">Appointment_Cancel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appointmentid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "appointmentid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancelreason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "cancelreason"));
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
