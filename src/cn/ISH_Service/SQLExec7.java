/**
 * SQLExec7.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class SQLExec7  implements java.io.Serializable {
    private java.lang.Object[] SQLListHIS;

    private java.lang.Object[] SQLListFEP;

    private cn.ISH_Service.SQLExec7Ds ds;

    public SQLExec7() {
    }

    public SQLExec7(
           java.lang.Object[] SQLListHIS,
           java.lang.Object[] SQLListFEP,
           cn.ISH_Service.SQLExec7Ds ds) {
           this.SQLListHIS = SQLListHIS;
           this.SQLListFEP = SQLListFEP;
           this.ds = ds;
    }


    /**
     * Gets the SQLListHIS value for this SQLExec7.
     * 
     * @return SQLListHIS
     */
    public java.lang.Object[] getSQLListHIS() {
        return SQLListHIS;
    }


    /**
     * Sets the SQLListHIS value for this SQLExec7.
     * 
     * @param SQLListHIS
     */
    public void setSQLListHIS(java.lang.Object[] SQLListHIS) {
        this.SQLListHIS = SQLListHIS;
    }


    /**
     * Gets the SQLListFEP value for this SQLExec7.
     * 
     * @return SQLListFEP
     */
    public java.lang.Object[] getSQLListFEP() {
        return SQLListFEP;
    }


    /**
     * Sets the SQLListFEP value for this SQLExec7.
     * 
     * @param SQLListFEP
     */
    public void setSQLListFEP(java.lang.Object[] SQLListFEP) {
        this.SQLListFEP = SQLListFEP;
    }


    /**
     * Gets the ds value for this SQLExec7.
     * 
     * @return ds
     */
    public cn.ISH_Service.SQLExec7Ds getDs() {
        return ds;
    }


    /**
     * Sets the ds value for this SQLExec7.
     * 
     * @param ds
     */
    public void setDs(cn.ISH_Service.SQLExec7Ds ds) {
        this.ds = ds;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SQLExec7)) return false;
        SQLExec7 other = (SQLExec7) obj;
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
              java.util.Arrays.equals(this.SQLListHIS, other.getSQLListHIS()))) &&
            ((this.SQLListFEP==null && other.getSQLListFEP()==null) || 
             (this.SQLListFEP!=null &&
              java.util.Arrays.equals(this.SQLListFEP, other.getSQLListFEP()))) &&
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
        if (getSQLListHIS() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSQLListHIS());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSQLListHIS(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSQLListFEP() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSQLListFEP());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSQLListFEP(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDs() != null) {
            _hashCode += getDs().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SQLExec7.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">SQLExec7"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQLListHIS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "SQLListHIS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "anyType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQLListFEP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "SQLListFEP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "anyType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "ds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">>SQLExec7>ds"));
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
