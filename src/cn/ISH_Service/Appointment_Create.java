/**
 * Appointment_Create.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public class Appointment_Create  implements java.io.Serializable {
    private java.lang.String name;

    private java.lang.String sex;

    private java.lang.String birthday;

    private java.lang.String IDNo;

    private java.lang.String instid;

    private java.lang.String OU;

    private java.lang.String doctor;

    private java.lang.String appdate;

    private java.lang.String AMPM;

    private java.lang.String appway;

    public Appointment_Create() {
    }

    public Appointment_Create(
           java.lang.String name,
           java.lang.String sex,
           java.lang.String birthday,
           java.lang.String IDNo,
           java.lang.String instid,
           java.lang.String OU,
           java.lang.String doctor,
           java.lang.String appdate,
           java.lang.String AMPM,
           java.lang.String appway) {
           this.name = name;
           this.sex = sex;
           this.birthday = birthday;
           this.IDNo = IDNo;
           this.instid = instid;
           this.OU = OU;
           this.doctor = doctor;
           this.appdate = appdate;
           this.AMPM = AMPM;
           this.appway = appway;
    }


    /**
     * Gets the name value for this Appointment_Create.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Appointment_Create.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the sex value for this Appointment_Create.
     * 
     * @return sex
     */
    public java.lang.String getSex() {
        return sex;
    }


    /**
     * Sets the sex value for this Appointment_Create.
     * 
     * @param sex
     */
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }


    /**
     * Gets the birthday value for this Appointment_Create.
     * 
     * @return birthday
     */
    public java.lang.String getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this Appointment_Create.
     * 
     * @param birthday
     */
    public void setBirthday(java.lang.String birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the IDNo value for this Appointment_Create.
     * 
     * @return IDNo
     */
    public java.lang.String getIDNo() {
        return IDNo;
    }


    /**
     * Sets the IDNo value for this Appointment_Create.
     * 
     * @param IDNo
     */
    public void setIDNo(java.lang.String IDNo) {
        this.IDNo = IDNo;
    }


    /**
     * Gets the instid value for this Appointment_Create.
     * 
     * @return instid
     */
    public java.lang.String getInstid() {
        return instid;
    }


    /**
     * Sets the instid value for this Appointment_Create.
     * 
     * @param instid
     */
    public void setInstid(java.lang.String instid) {
        this.instid = instid;
    }


    /**
     * Gets the OU value for this Appointment_Create.
     * 
     * @return OU
     */
    public java.lang.String getOU() {
        return OU;
    }


    /**
     * Sets the OU value for this Appointment_Create.
     * 
     * @param OU
     */
    public void setOU(java.lang.String OU) {
        this.OU = OU;
    }


    /**
     * Gets the doctor value for this Appointment_Create.
     * 
     * @return doctor
     */
    public java.lang.String getDoctor() {
        return doctor;
    }


    /**
     * Sets the doctor value for this Appointment_Create.
     * 
     * @param doctor
     */
    public void setDoctor(java.lang.String doctor) {
        this.doctor = doctor;
    }


    /**
     * Gets the appdate value for this Appointment_Create.
     * 
     * @return appdate
     */
    public java.lang.String getAppdate() {
        return appdate;
    }


    /**
     * Sets the appdate value for this Appointment_Create.
     * 
     * @param appdate
     */
    public void setAppdate(java.lang.String appdate) {
        this.appdate = appdate;
    }


    /**
     * Gets the AMPM value for this Appointment_Create.
     * 
     * @return AMPM
     */
    public java.lang.String getAMPM() {
        return AMPM;
    }


    /**
     * Sets the AMPM value for this Appointment_Create.
     * 
     * @param AMPM
     */
    public void setAMPM(java.lang.String AMPM) {
        this.AMPM = AMPM;
    }


    /**
     * Gets the appway value for this Appointment_Create.
     * 
     * @return appway
     */
    public java.lang.String getAppway() {
        return appway;
    }


    /**
     * Sets the appway value for this Appointment_Create.
     * 
     * @param appway
     */
    public void setAppway(java.lang.String appway) {
        this.appway = appway;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Appointment_Create)) return false;
        Appointment_Create other = (Appointment_Create) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.sex==null && other.getSex()==null) || 
             (this.sex!=null &&
              this.sex.equals(other.getSex()))) &&
            ((this.birthday==null && other.getBirthday()==null) || 
             (this.birthday!=null &&
              this.birthday.equals(other.getBirthday()))) &&
            ((this.IDNo==null && other.getIDNo()==null) || 
             (this.IDNo!=null &&
              this.IDNo.equals(other.getIDNo()))) &&
            ((this.instid==null && other.getInstid()==null) || 
             (this.instid!=null &&
              this.instid.equals(other.getInstid()))) &&
            ((this.OU==null && other.getOU()==null) || 
             (this.OU!=null &&
              this.OU.equals(other.getOU()))) &&
            ((this.doctor==null && other.getDoctor()==null) || 
             (this.doctor!=null &&
              this.doctor.equals(other.getDoctor()))) &&
            ((this.appdate==null && other.getAppdate()==null) || 
             (this.appdate!=null &&
              this.appdate.equals(other.getAppdate()))) &&
            ((this.AMPM==null && other.getAMPM()==null) || 
             (this.AMPM!=null &&
              this.AMPM.equals(other.getAMPM()))) &&
            ((this.appway==null && other.getAppway()==null) || 
             (this.appway!=null &&
              this.appway.equals(other.getAppway())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSex() != null) {
            _hashCode += getSex().hashCode();
        }
        if (getBirthday() != null) {
            _hashCode += getBirthday().hashCode();
        }
        if (getIDNo() != null) {
            _hashCode += getIDNo().hashCode();
        }
        if (getInstid() != null) {
            _hashCode += getInstid().hashCode();
        }
        if (getOU() != null) {
            _hashCode += getOU().hashCode();
        }
        if (getDoctor() != null) {
            _hashCode += getDoctor().hashCode();
        }
        if (getAppdate() != null) {
            _hashCode += getAppdate().hashCode();
        }
        if (getAMPM() != null) {
            _hashCode += getAMPM().hashCode();
        }
        if (getAppway() != null) {
            _hashCode += getAppway().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Appointment_Create.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ISH_Service.cn/", ">Appointment_Create"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "sex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "IDNo"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doctor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "doctor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appdate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "appdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AMPM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "AMPM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appway");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ISH_Service.cn/", "appway"));
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
