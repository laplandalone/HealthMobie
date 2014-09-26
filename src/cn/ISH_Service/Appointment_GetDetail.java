/**
 * Appointment_GetDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class Appointment_GetDetail  implements java.io.Serializable {
    private java.lang.String begin_date;

    private java.lang.String end_date;

    private java.lang.String pernrs;

    private java.lang.String instid;

    private java.lang.String OU;

    public Appointment_GetDetail() {
    }

    public Appointment_GetDetail(
           java.lang.String begin_date,
           java.lang.String end_date,
           java.lang.String pernrs,
           java.lang.String instid,
           java.lang.String OU) {
           this.begin_date = begin_date;
           this.end_date = end_date;
           this.pernrs = pernrs;
           this.instid = instid;
           this.OU = OU;
    }


    /**
     * Gets the begin_date value for this Appointment_GetDetail.
     * 
     * @return begin_date
     */
    public java.lang.String getBegin_date() {
        return begin_date;
    }


    /**
     * Sets the begin_date value for this Appointment_GetDetail.
     * 
     * @param begin_date
     */
    public void setBegin_date(java.lang.String begin_date) {
        this.begin_date = begin_date;
    }


    /**
     * Gets the end_date value for this Appointment_GetDetail.
     * 
     * @return end_date
     */
    public java.lang.String getEnd_date() {
        return end_date;
    }


    /**
     * Sets the end_date value for this Appointment_GetDetail.
     * 
     * @param end_date
     */
    public void setEnd_date(java.lang.String end_date) {
        this.end_date = end_date;
    }


    /**
     * Gets the pernrs value for this Appointment_GetDetail.
     * 
     * @return pernrs
     */
    public java.lang.String getPernrs() {
        return pernrs;
    }


    /**
     * Sets the pernrs value for this Appointment_GetDetail.
     * 
     * @param pernrs
     */
    public void setPernrs(java.lang.String pernrs) {
        this.pernrs = pernrs;
    }


    /**
     * Gets the instid value for this Appointment_GetDetail.
     * 
     * @return instid
     */
    public java.lang.String getInstid() {
        return instid;
    }


    /**
     * Sets the instid value for this Appointment_GetDetail.
     * 
     * @param instid
     */
    public void setInstid(java.lang.String instid) {
        this.instid = instid;
    }


    /**
     * Gets the OU value for this Appointment_GetDetail.
     * 
     * @return OU
     */
    public java.lang.String getOU() {
        return OU;
    }


    /**
     * Sets the OU value for this Appointment_GetDetail.
     * 
     * @param OU
     */
    public void setOU(java.lang.String OU) {
        this.OU = OU;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Appointment_GetDetail)) return false;
        Appointment_GetDetail other = (Appointment_GetDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.begin_date==null && other.getBegin_date()==null) || 
             (this.begin_date!=null &&
              this.begin_date.equals(other.getBegin_date()))) &&
            ((this.end_date==null && other.getEnd_date()==null) || 
             (this.end_date!=null &&
              this.end_date.equals(other.getEnd_date()))) &&
            ((this.pernrs==null && other.getPernrs()==null) || 
             (this.pernrs!=null &&
              this.pernrs.equals(other.getPernrs()))) &&
            ((this.instid==null && other.getInstid()==null) || 
             (this.instid!=null &&
              this.instid.equals(other.getInstid()))) &&
            ((this.OU==null && other.getOU()==null) || 
             (this.OU!=null &&
              this.OU.equals(other.getOU())));
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
        if (getBegin_date() != null) {
            _hashCode += getBegin_date().hashCode();
        }
        if (getEnd_date() != null) {
            _hashCode += getEnd_date().hashCode();
        }
        if (getPernrs() != null) {
            _hashCode += getPernrs().hashCode();
        }
        if (getInstid() != null) {
            _hashCode += getInstid().hashCode();
        }
        if (getOU() != null) {
            _hashCode += getOU().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Appointment_GetDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">Appointment_GetDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("begin_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "begin_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "end_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pernrs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "pernrs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "instid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "OU"));
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
