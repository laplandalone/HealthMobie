/**
 * SQLExecA.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class SQLExecA  implements java.io.Serializable {
    private java.lang.String SQLFEP;

    public SQLExecA() {
    }

    public SQLExecA(
           java.lang.String SQLFEP) {
           this.SQLFEP = SQLFEP;
    }


    /**
     * Gets the SQLFEP value for this SQLExecA.
     * 
     * @return SQLFEP
     */
    public java.lang.String getSQLFEP() {
        return SQLFEP;
    }


    /**
     * Sets the SQLFEP value for this SQLExecA.
     * 
     * @param SQLFEP
     */
    public void setSQLFEP(java.lang.String SQLFEP) {
        this.SQLFEP = SQLFEP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SQLExecA)) return false;
        SQLExecA other = (SQLExecA) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SQLFEP==null && other.getSQLFEP()==null) || 
             (this.SQLFEP!=null &&
              this.SQLFEP.equals(other.getSQLFEP())));
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
        if (getSQLFEP() != null) {
            _hashCode += getSQLFEP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SQLExecA.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">SQLExecA"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQLFEP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "SQLFEP"));
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
