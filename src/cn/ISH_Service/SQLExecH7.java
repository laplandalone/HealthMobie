/**
 * SQLExecH7.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class SQLExecH7  implements java.io.Serializable {
    private java.lang.String SQLListHIS;

    public SQLExecH7() {
    }

    public SQLExecH7(
           java.lang.String SQLListHIS) {
           this.SQLListHIS = SQLListHIS;
    }


    /**
     * Gets the SQLListHIS value for this SQLExecH7.
     * 
     * @return SQLListHIS
     */
    public java.lang.String getSQLListHIS() {
        return SQLListHIS;
    }


    /**
     * Sets the SQLListHIS value for this SQLExecH7.
     * 
     * @param SQLListHIS
     */
    public void setSQLListHIS(java.lang.String SQLListHIS) {
        this.SQLListHIS = SQLListHIS;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SQLExecH7)) return false;
        SQLExecH7 other = (SQLExecH7) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SQLListHIS==null && other.getSQLListHIS()==null) || 
             (this.SQLListHIS!=null &&
              this.SQLListHIS.equals(other.getSQLListHIS())));
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
        if (getSQLListHIS() != null) {
            _hashCode += getSQLListHIS().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SQLExecH7.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">SQLExecH7"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQLListHIS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "SQLListHIS"));
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
