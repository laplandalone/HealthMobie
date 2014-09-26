/**
 * SQLExecResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class SQLExecResponse  implements java.io.Serializable {
    private cn.ISH_Service._return SQLExecResult;

    private cn.ISH_Service.SQLExecResponseDs ds;

    public SQLExecResponse() {
    }

    public SQLExecResponse(
           cn.ISH_Service._return SQLExecResult,
           cn.ISH_Service.SQLExecResponseDs ds) {
           this.SQLExecResult = SQLExecResult;
           this.ds = ds;
    }


    /**
     * Gets the SQLExecResult value for this SQLExecResponse.
     * 
     * @return SQLExecResult
     */
    public cn.ISH_Service._return getSQLExecResult() {
        return SQLExecResult;
    }


    /**
     * Sets the SQLExecResult value for this SQLExecResponse.
     * 
     * @param SQLExecResult
     */
    public void setSQLExecResult(cn.ISH_Service._return SQLExecResult) {
        this.SQLExecResult = SQLExecResult;
    }


    /**
     * Gets the ds value for this SQLExecResponse.
     * 
     * @return ds
     */
    public cn.ISH_Service.SQLExecResponseDs getDs() {
        return ds;
    }


    /**
     * Sets the ds value for this SQLExecResponse.
     * 
     * @param ds
     */
    public void setDs(cn.ISH_Service.SQLExecResponseDs ds) {
        this.ds = ds;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SQLExecResponse)) return false;
        SQLExecResponse other = (SQLExecResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SQLExecResult==null && other.getSQLExecResult()==null) || 
             (this.SQLExecResult!=null &&
              this.SQLExecResult.equals(other.getSQLExecResult()))) &&
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
        if (getSQLExecResult() != null) {
            _hashCode += getSQLExecResult().hashCode();
        }
        if (getDs() != null) {
            _hashCode += getDs().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SQLExecResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">SQLExecResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQLExecResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "SQLExecResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", "Return"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "ds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">>SQLExecResponse>ds"));
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
