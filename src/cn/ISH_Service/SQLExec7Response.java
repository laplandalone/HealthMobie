/**
 * SQLExec7Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class SQLExec7Response  implements java.io.Serializable {
    private cn.ISH_Service._return SQLExec7Result;

    private cn.ISH_Service.SQLExec7ResponseDs ds;

    public SQLExec7Response() {
    }

    public SQLExec7Response(
           cn.ISH_Service._return SQLExec7Result,
           cn.ISH_Service.SQLExec7ResponseDs ds) {
           this.SQLExec7Result = SQLExec7Result;
           this.ds = ds;
    }


    /**
     * Gets the SQLExec7Result value for this SQLExec7Response.
     * 
     * @return SQLExec7Result
     */
    public cn.ISH_Service._return getSQLExec7Result() {
        return SQLExec7Result;
    }


    /**
     * Sets the SQLExec7Result value for this SQLExec7Response.
     * 
     * @param SQLExec7Result
     */
    public void setSQLExec7Result(cn.ISH_Service._return SQLExec7Result) {
        this.SQLExec7Result = SQLExec7Result;
    }


    /**
     * Gets the ds value for this SQLExec7Response.
     * 
     * @return ds
     */
    public cn.ISH_Service.SQLExec7ResponseDs getDs() {
        return ds;
    }


    /**
     * Sets the ds value for this SQLExec7Response.
     * 
     * @param ds
     */
    public void setDs(cn.ISH_Service.SQLExec7ResponseDs ds) {
        this.ds = ds;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SQLExec7Response)) return false;
        SQLExec7Response other = (SQLExec7Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SQLExec7Result==null && other.getSQLExec7Result()==null) || 
             (this.SQLExec7Result!=null &&
              this.SQLExec7Result.equals(other.getSQLExec7Result()))) &&
            ((this.ds==null && other.getDs()==null) || 
             (this.ds!=null &&
              this.ds.equals(other.getDs())));
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
        if (getSQLExec7Result() != null) {
            _hashCode += getSQLExec7Result().hashCode();
        }
        if (getDs() != null) {
            _hashCode += getDs().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SQLExec7Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">SQLExec7Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQLExec7Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "SQLExec7Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", "Return"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "ds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">>SQLExec7Response>ds"));
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
