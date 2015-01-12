package com.hbgz.pub.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.tools.pub.utils.StringUtil;

/**
 * @modify  2005-08-12 ׷�Ӽ����滻�Ľڵ�ĺ���
 * jdom ��xmlһЩͨ�ú���
 *
 */
public class XMLComm
{
	
	private static Log log = LogFactory.getLog(XMLComm.class);
	
	/**
     * ȡ��һ���ڵ��������ӽ������(attrName)
     * @param Element ele �ڵ�
     */
    public static void removeAllAttrByName( Element ele,String attrName)
    {
        if ( ele != null )
        {   
        	ele.removeAttribute(attrName);
            List lst = ele.getChildren();
            if ( lst.size() == 0 ) 
            {
            	
            	return;
            }
            
            for ( int i = 0; i < lst.size(); i++ )
            {
                Element one_ele = ( Element )lst.get( i );
                if ( one_ele.getChildren().size() > 0 )
                {
                	removeAllAttrByName( one_ele,attrName);
                }else{
                	one_ele.removeAttribute(attrName);
                }
            }
        }
    }
    
	/**
     * ����ָ�����ļ�������·��,����xml�ļ�
     * @param fileName �ļ���
     * @return һ��document
     **/
    public static Document loadXML( String fileName )
    {
        //��һ��xml�ļ��õ�һ��document/
        try
        {
            SAXBuilder sb = new SAXBuilder();
            Document myDoc = sb.build( new FileInputStream( fileName ) );
            return myDoc;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ����ָ�����ļ�������·��,����xml�ļ�
     * @param fileName �ļ���
     * @return һ��document
     **/
    public static Document loadXMLStr( String str )
    {
        //��һ��xml�ļ��õ�һ��document/
        try
        {
            SAXBuilder sb = new SAXBuilder();
            Document myDoc = sb.build( new FileInputStream( str ) );
            return myDoc;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ���ݼ���xml��ʽ String ���� add by xcx
     * @param str �ַ�����
     * @return һ��document
     **/
    public static Document loadXMLString( String str )
    {
        //��һ��xml�ļ��õ�һ��document/
        try
        {
            SAXBuilder sb = new SAXBuilder();
            Document myDoc = sb.build( new StringReader(str) );
            return myDoc;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ���ݽڵ�key���ؽڵ�List (ѡ������·����KeyԪ��)
     * @param String Key //�ڵ�����
     * @return Data
     **/
    public static List findEleByKey( Document doc, String key )
    {
        try
        {
            XPath prodAttrPath = XPath.newInstance( "//" + key ); //,ѡ������·����servletԪ��
            List lst = prodAttrPath.selectNodes( doc ); //�������е�servletԪ�ء�
            return lst;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ����һ���ڵ㸴��һ���ڵ�
     * @param Element eleSource //Դ�ڵ�
     * @return elsDes ����һ���ڵ�
     **/
    public static Element copyElement( Element eleSource )
    {
        Element eleDes = ( Element )eleSource.clone();
        return eleDes;
    }

    /**
     * ����document:(�κ�һ���ڵ㲻����ͬ�����ֵܽڵ�),�������ֵܽڵ�ֻ����һ��;
     * �ֵܽڵ�:������ͬ�ĸ��ڵ�Ľڵ�ͳ��Ϊ�ֵܽڵ�
     * @param Element eleSource //Ҫ�滻�Ľڵ�
     * @param List eleDesList Ŀ��ڵ��б�
     * @return ��
     **/
    public static void addJustDoc( Document doc, List lstKey )
    {
        //�����Ϸ����ж�
        if ( lstKey == null || lstKey.size() == 0 )
        {
            return;
        }
        for ( int i = 0; i < lstKey.size(); i++ )
        {
            String key = ( String )lstKey.get( i );
            //����key�õ�һ���ڵ�
            List lstElement = findEleByKey( doc, key );

            //ѭ���ȽϽڵ�

            for ( int j = 0; j < lstElement.size(); j++ )
            {
                if ( lstElement == null || lstElement.size() == 0 )
                {
                    continue;
                }
                Element eleOld = ( Element )lstElement.get( j );
                if ( eleOld == null )
                {
                    continue;
                }
                Element eleParent = eleOld.getParentElement();
                if ( eleParent == null )
                {
                    continue;
                }
                Element eleCopy = copyElement( eleOld );
                //�õ�Ҫ�ڵ����parent��������
                int npos = eleParent.indexOf( eleOld );
                eleParent.removeChildren( key );
                if ( eleParent.getChildren().size() > 0 )
                {
                    eleParent.setContent( npos, eleCopy );
                }
                else
                {
                    eleParent.addContent( eleCopy );
                }
                eleCopy = null;
            }
        }
    }

    /**
     * �滻һ���ڵ�: ��һ��Ŀ��ڵ���list �滻һ��ָ���ڵ�(��ͬ����λ�õ��滻)
     * @param Element eleSource //Ҫ�滻�Ľڵ�
     * @param List eleDesList Ŀ��ڵ��б�
     * @return ��
     **/
    public static void replaceEle( Element eleSource, List eleDesList )
    {
        if ( !( eleSource == null ) )
        {
            Element parent = eleSource.getParentElement();
            if ( parent == null )
            {
                return;
            }
            int npos = parent.indexOf( eleSource );
            if ( npos < 0 )
            {
                return;
            }
            if ( eleDesList == null || eleDesList.size() == 0 )
            {
                parent.removeContent( npos ); //2005-08-23ojw ����ڵ��б�Ϊ��,����ոýڵ�
                return;
            }
            Element eleFirst = ( Element )eleDesList.get( 0 );

            parent.setContent( npos, eleFirst );
            for ( int i = 1; i < eleDesList.size(); i++ )
            {
                parent.addContent( npos + i, ( Element )eleDesList.get( i ) );
            }

        }

    }

    /**
     * �滻һ���ڵ�ĺ��ӽڵ�: ����key��eleSource�ҵ�����,Ȼ����һ��Ŀ��ڵ����滻
     * @param eleParent  ���ڵ�
     * @param childKeys  ���ӽڵ��key(������д: ���� //T_ServT//M_Product)
     * @param eleDesList
     */
    public static void replaceChild( Element eleParent, String childKey, List eleDesList )
    {
        List keyList = StringUtil.stringMid( childKey, "/" );

        if ( eleParent == null )
        {
            return;
        }
        if ( keyList == null || keyList.size() == 0 )
        {
            return;
        }
        Element ele = eleParent.getChild( ( String )keyList.get( 0 ) );
        if ( ele == null )
        {
            return;
        }

        for ( int i = 1; i < keyList.size(); i++ )
        {
            String key = ( String )keyList.get( i );
            if ( key == null || key.equals( "" ) )
            {
                continue;
            }
            ele = ele.getChild( key );
            if ( ele == null )
            {
                return;
            }
        }
        replaceEle( ele, eleDesList );
    }

    /**
     * ��һ��ָ���Ľڵ㰴��ָ����·�����ҽڵ�
     * @param eleSource :ԭ�ڵ�
     * @param path      :·��
     * @return          :�ڵ�list
     */
    public static List selectNodes( Element eleSource, String path )
    {
        try
        {
            return XPath.selectNodes( eleSource, path );
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ��һ��ָ���Ľڵ㰴��ָ����·�����ҽڵ�
     * @param eleSource :ԭ�ڵ�
     * @param path      :·��
     * @return          :�ڵ�list
     */
    public static Element selectSingleNode( Element eleSource, String path )
    {
        try
        {
            return ( Element )XPath.selectSingleNode( eleSource, path );
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ����һ����¼(map)�滻һ���ڵ��ӦKey�ļ�ֵ:
     * @param Element eleSource //Ҫ�滻�Ľڵ�
     * @param Map mapRec һ����¼
     * @param String key ���key="" ����keyΪ��,���滻��һ�����ӵ�key��Ӧ��ֵ
     *                   ���key��Ϊ��,���滻ָ�����ӵ�key��Ӧ��ֵ
     * @return ��
     **/
    public static void replactKeyValByMap( Map mapRec, Element eleSource, String key )
    {
        if ( eleSource == null )
        {
            return;
        }
        if ( mapRec == null )
        {
            return;
        }

        //�ж���Ҫ�滻�Ķ���
        Element eleDes = null; //Ҫ�滻�Ķ���
        if ( key == null || key.equals( "" ) )
        {
            eleDes = eleSource;
        }
        else
        {
            eleDes = eleSource.getChild( key );
            if ( eleDes == null )
            {
                return;
            }
        }

        //��ʼ�滻ָ���ڵ��key��ӦVal
        List lstChildEle = eleDes.getChildren();
        if ( lstChildEle == null || lstChildEle.size() == 0 )
        {
            return;
        }
        for ( int i = 0; i < lstChildEle.size(); i++ )
        {
            Element child = ( Element )lstChildEle.get( i );
            if ( child.getChildren().size() == 0 )
            { //ֻ�滻û�к��ӵĽڵ�
                //���ݽڵ�key�ҵ���Ӧ���ݵ��ֶ���
                String dbfldName = StringUtil.getRstFieldName_ByVoFldName( child.getName().trim() );
                dbfldName = dbfldName.toLowerCase();
                Object val = mapRec.get( dbfldName );
                String keyVal = "";
                if ( val != null )
                {
                    keyVal = val.toString();
                }
                //�滻�ýڵ�key��Ӧ��ֵ
                child.setText( keyVal );
            }
        }

    }

    /**
     * ��n����¼(map):����һ���ڵ��� ,��Ӧÿһ���ڵ㶼�ü�¼����
     *                            �滻һ���ڵ��ӦKey�ļ�ֵ:
     * @param Element eleSource //Ҫ�滻�Ľڵ�
     * @param Map mapRec һ����¼
     * @param String key ���key="" ����keyΪ��,���滻��һ�����ӵ�key��Ӧ��ֵ
     *                   ���key��Ϊ��,���滻ָ�����ӵ�key��Ӧ��ֵ
     * @return ��
     **/
    public static List genEleLstByRst( List rst, Element eleSource, String key )
    {
        List lstResult = new ArrayList();
        if ( rst == null || rst.size() == 0 )
        {
            lstResult.add( copyElement( eleSource ) );
            return lstResult;
        }

        for ( int i = 0; i < rst.size(); i++ )
        {
            Element newEle = copyElement( eleSource );
            Map mapRec = ( Map )rst.get( i );
            replactKeyValByMap( mapRec, newEle, key );
            lstResult.add( newEle );
        }
        return lstResult;
    }

    /**
     * ƴ��һ���ڵ����������
     * @param Element ele //�ڵ�
     * @return �����ַ���
     **/
    public static String getOneEleAttr( Element ele )
    {
        List lstAttr = ele.getAttributes();
        String sResult = "";
        for ( int i = 0; i < lstAttr.size(); i++ )
        {
            Attribute attr = ( Attribute )lstAttr.get( i );
            if ( i < lstAttr.size() - 1 )
            {
                sResult = sResult + attr.getName() + "=\"" + attr.getValue() + "\"" + " ";
            }
            else
            {
                sResult = sResult + attr.getName() + "=\"" + attr.getValue() + "\"";
            }

        }
        if ( sResult.equals( "" ) )
        {
            return "";
        }
        else
        {
            return ( " " + sResult );
        }
    }

    /**
     * �ڿ���̨��ʾһ���ڵ�����ӽڵ�,(���ڵ����õ�)
     * �ú������õݹ�,
     * @param Element ele //�ڵ�,split�����ַ�: һ��Ϊ��
     * @return ��
     * @exam  showOneEle(myEle,"");
     **/
    public static void showOneEle( Element ele, String split )
    {
        String initsplit = split;
        log.debug( initsplit + "<" + ele.getName() + getOneEleAttr( ele ) + ">" + ele.getText() );
        split = split + "  ";
        List children = ele.getChildren();
        for ( int i = 0; i < children.size(); i++ )
        {
            Element aEle = ( Element )children.get( i );
            if ( aEle.getChildren().size() == 0 )
            {
                log.debug( split + "<" + aEle.getName() + getOneEleAttr( aEle ) + ">" + aEle.getText() + "</" + aEle.getName() + ">" );
            }
            else
            {
                String oldsplit = split;
                showOneEle( aEle, split );
            }
        }
        log.debug( initsplit + "</" + ele.getName() + ">" );
    }

    /**
     * ��docת����XML�ַ���
     * @param doc
     * @return String
     */
    public static String docAsXML( Document doc )
    {
        XMLOutputter outputter = new XMLOutputter();
        Format format = outputter.getFormat();
        format.setEncoding( "GBK" );
        outputter.setFormat( format );
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        try
        {
            outputter.output( doc, writer );
            //log.debug(writer.toString());
            return writer.toString();
        } catch ( Exception e )
        {
            e.printStackTrace();
            return "";
        }
    }

    /** ����һ���ڵ�����xml�ַ���
     * @param ele   Ҫ��ʾ�Ľڵ�
     * @param split �ڵ�ͽڵ�֮��ļ����
     * @param ret   ����ֵ
     * @return ��
     **/
    public static void eleAsXML( Element ele, String split, StringBuffer ret )
    {
        String initsplit = split;
        ret.append( initsplit + "<" + ele.getName() + getOneEleAttr( ele ) + ">" + ele.getText() );
        split = split + "  ";
        List children = ele.getChildren();
        for ( int i = 0; i < children.size(); i++ )
        {
            Element aEle = ( Element )children.get( i );
            if ( aEle.getChildren().size() == 0 )
            {
                ret.append( split + "<" + aEle.getName() + getOneEleAttr( aEle ) + ">" + aEle.getText() + "</" + aEle.getName() + ">" );
            }
            else
            {
                String oldsplit = split;
                eleAsXML( aEle, split, ret );
            }
        }
        ret.append( initsplit + "</" + ele.getName() + ">" );
    }

    /**
     * ����xml�ĸ�ʽ���ַ���
     * @param charset�ַ���: ����gb2312,GBK
     * @return ����һ���ַ���
     **/
    public static String getXMLHead( String charset )
    {
        return ( "<?xml version=\"1.0\" encoding=\"" + charset + "\"?>" );
    }

    /**
     * ����һ��documnet,����һ��XML�ļ�
     * @param Document doc
     * @param fileName �ļ�·��
     * @return �ɹ���
     */
    public static boolean BulidXML( Document doc, String fileName )
    {
        try
        {
            //��������XML�ĸ�ʽ
            String cset = "gb2312"; //���룬��ʾ����
            XMLOutputter outer = new XMLOutputter();
            Format format = outer.getFormat();
            format.setIndent("");
            format.setEncoding( cset );
            //format.setExpandEmptyElements(true);
            outer.setFormat( format );
            //����ļ�
            outer.output( doc, new FileOutputStream( fileName ) );
            return true;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ����һ��Model��ָ������������µ�Element
     * @param Element eleTab ָ���Ľڵ�
     * @param Class   type   ����(ģ����)
     * @param String  pre    Ҫ���ɵĽڵ����Ƶ�ǰ׺: һ��Ϊ"T_"
     * @param String  cninfo Ҫ���ɽڵ������˵��
     * @exma  aModelToXml_InEle(V_ele,PCustT.class,"T_","�ͻ��ʻ�");
     */
    public static Element aModelToXML_InEle( Element eleTab, Class type, String pre, String cninfo )
    {
        try
        {
            //ѭ������Ԫ��
            Object vo = type.newInstance(); //����һ��ֵ����
            Field[] fields = type.getSuperclass().getDeclaredFields(); //�õ�ֵ�����������ֶ�
            for ( int i = 0; i < fields.length; i++ )
            {
                //������Ԫ��
                String fldname = fields[i].getName();
                if ( fldname.startsWith( "PROP_" ) == false )
                {
                    Element ele = new Element( fields[i].getName() );
                    ele.setText( "1" );
                    eleTab.addContent( ele );
                }
            }
            return eleTab;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ����һ��Model����һ���ڵ�
     * @param Class   type   ����(ģ����)
     * @param String  pre    Ҫ���ɵĽڵ����Ƶ�ǰ׺: һ��Ϊ"T_"
     * @param String  cninfo Ҫ���ɽڵ������˵��
     * @return ����һ��Element
     */
    public static Element aModelToXML( Class type, String pre, String cninfo )
    {
        //�õ�ȫ·����������
        String className = type.getName();
        List lstClassName = StringUtil.stringMid( className, "." );
        if ( lstClassName == null || lstClassName.size() == 0 )
        {
            return null;
        }
        //�õ�����·����������
        className = ( String )lstClassName.get( lstClassName.size() - 1 );
        Element eleTab = new Element( pre + className );
        if ( !( cninfo.equals( "" ) ) )
        {
            eleTab.setAttribute( "cninfo", cninfo );
        }
        return aModelToXML_InEle( eleTab, type, pre, cninfo );
    }

    /**
     * ��ʾһ���ڵ������н������(·��ֻ��ʾ��ǰpath)
     * @param Element ele Ҫ��ʾ�Ľڵ�
     * @param split   ���ӽڵ�������ַ�: ���ڸú����õĵݹ�,����һ��Ϊ""
     */
    public static void showAllElement( Element ele, String split )
    {
        if ( ele != null )
        {
            List lst = ele.getChildren();
            if ( lst.size() == 0 )
            {
                return;
            }
            split = split + "  ";
            if ( ele.isRootElement() )
            {
                log.debug( split + ele.getName() );
            }
            for ( int i = 0; i < lst.size(); i++ )
            {
                Element one_ele = ( Element )lst.get( i );
                if ( one_ele.getChildren().size() > 0 )
                {
                    log.debug( split + one_ele.getName() );
                    showAllElement( one_ele, split );
                }
                else
                {
                    log.debug( split + one_ele.getName() + ":=" + one_ele.getTextTrim() );
                }

            }
        }
    }

    /**
     * ��ʾһ���ڵ������н������(·����ʾȫ·��)
     * @param Element ele Ҫ��ʾ�Ľڵ�
     * @param split   ���ӽڵ�������ַ�: ���ڸú����õĵݹ�,����һ��Ϊ""
     */
    public static void showAllElement_FullPath( Element ele, String split, String parentName )
    {
        if ( ele != null )
        {
            List lst = ele.getChildren();
            if ( lst.size() == 0 )
            {
                return;
            }
            if ( ele.isRootElement() )
            {
                log.debug( ele.getName() );
                parentName = ele.getName();
            }
            for ( int i = 0; i < lst.size(); i++ )
            {
                Element one_ele = ( Element )lst.get( i );
                if ( one_ele.getChildren().size() > 0 )
                {
                    log.debug( parentName + "." + one_ele.getName() );
                    showAllElement_FullPath( one_ele, split, parentName + "." + one_ele.getName() );
                }
                else
                {
                    log.debug( parentName + "." + one_ele.getName() + ":=" + one_ele.getTextTrim() );
                }

            }
        }
    }

    /**
     * �Ƚ������ڵ�(�������ԵĽڵ�,�ݹ麯��)
     * @param Element ele1
     * @param Element ele1
     * @param parentName    out���� ���ڷ���·��(����ʱΪ��,�ݹ�ʱ�õ�)
     * @param List lstComp  out���� ���ڷ��رȽϽ��(����ʱΪ��,�ݹ�ʱ�õ�)
     */
    public static boolean compAllElement( Element ele1, Element ele2, String parentName, List lstComp )
    {
        if ( ( ele1 == null ) && ( ele2 == null ) )
        {
            return false;
        }
        //�õ��ӽ��ĸ���
        List lstOne = ele1.getChildren();
        List lstTwo = ele2.getChildren();

        if ( ( lstOne.size() == 0 ) || ( lstTwo.size() == 0 ) )
        {
            return ( true );
        }

        //�������Ľ��ĸ�����ͬ,����ǰ�˳�
        if ( lstOne.size() != lstTwo.size() )
        {
            XmlCompResult comp = new XmlCompResult();
            comp.setPath( parentName + "." + ele1.getName() );
            comp.setOldNodes( lstOne.size() );
            comp.setNewNodes( lstTwo.size() );
            lstComp.add( comp );
            return ( false );
        }

        //����Ǹ����
        if ( ele1.isRootElement() )
        {
            log.debug( ele1.getName() );
            parentName = ele1.getName();
        }

        for ( int i = 0; i < lstOne.size(); i++ )
        {
            Element one_ele = ( Element )lstOne.get( i );
            Element two_ele = ( Element )lstTwo.get( i );
            if ( one_ele.getChildren().size() > 0 )
            {
                log.debug( parentName + "." + one_ele.getName() );
                compAllElement( one_ele, two_ele, parentName + "." + one_ele.getName(), lstComp );
            }
            else
            {
                if ( !( ( one_ele.getText().equals( two_ele.getText() ) ) ) )
                {
                    log.debug( parentName + "." + one_ele.getName() + ": " + one_ele.getTextTrim() + "!=" + two_ele.getTextTrim() );
                    XmlCompResult comp = new XmlCompResult();
                    comp.setPath( parentName + "." + one_ele.getName() );
                    comp.setOldValue( one_ele.getText() );
                    comp.setNewValue( two_ele.getText() );
                    lstComp.add( comp );
                }
                else
                {
                    log.debug( parentName + "." + one_ele.getName() + ":=" + one_ele.getTextTrim() );
                }
            }

        }
        return false;
    }

    /**
     * ����һ���ڵ�,������������xml
     * @param Element ele
     * @param int split   ��ǰ���Ľڵ�id
     * @param int parent  ��ǰ���ĸ��ڵ�id
     */
    public static void AllElement_ToTree( Element ele, int split, int parent )
    {
        if ( ele != null )
        {
            List lst = ele.getChildren();
            if ( lst.size() == 0 )
            {
                return;
            }
            split = split + 1;
            if ( ele.isRootElement() )
            {
                split = 0;
                log.debug( "d = new dTree('d');" );
                log.debug( "d.add(0,-1,'My example tree');" );
                split = 0;
                parent = 0;
                //log.debug(split+ele.getName());
                //log.debug("d.add("+i+",)"
            }

            for ( int i = 0; i < lst.size(); i++ )
            {
                Element one_ele = ( Element )lst.get( i );
                split = split + 1;
                if ( one_ele.getChildren().size() > 0 )
                {
                    log.debug( "d.add(" + ( split ) + "," + ( parent ) + ",'" + one_ele.getName().trim() + "','xxx.htm');" );
                    //log.debug(split+one_ele.getName());
                    //split=split+1;
                    AllElement_ToTree( one_ele, split, split );
                }
                else
                {
                    log.debug( "d.add(" + ( split ) + "," + ( parent ) + ",'" + one_ele.getText().trim() + "','xxx.htm');" );
                    //log.debug(split+one_ele.getName()+":="+one_ele.getTextTrim());
                }

            }
        }
    }

    /**��ʾXML�еĲ�ι�ϵ
     *
     * @param ele
     * @param split
     */
    public void visitElement_OnlyParent( Element ele, String split )
    {
        if ( ele != null )
        {
            List lst = ele.getChildren();
            if ( lst.size() == 0 )
            {
                return;
            }
            split = split + "  ";
            if ( ele.isRootElement() )
            {
                log.debug( split + ele.getName() );
            }
            for ( int i = 0; i < lst.size(); i++ )
            {
                Element one_ele = ( Element )lst.get( i );
                //log.debug(split+one_ele.getName());
                if ( one_ele.getChildren().size() > 0 )
                {
                    log.debug( split + one_ele.getName() );
                    visitElement_OnlyParent( one_ele, split );
                }
            }
        }
    }

    public static void main( String[] args )
    {
        try
        {
            XMLComm s1 = new XMLComm();

            ///////////���Է���һ��xml�����н��///////////////
            /*
                //��һ��xml�ļ��õ�һ��document/
                SAXBuilder sb=new SAXBuilder();
                Document myDoc=sb.build(new FileInputStream("allobj.xml"));
                //������Ԫ��
                Element root=myDoc.getRootElement(); //�ȵõ���Ԫ��
                log.debug("��ʾ���н���Name");
                s1.showAllElement(root,"");
                log.debug("��ʾ����(������: Ŀ¼)��Name");
                s1.visitElement_OnlyParent(root,"");
                log.debug("��ʾ����(������: Ŀ¼)��Name");
                s1.showAllElement_FullPath(root,"","");
             */

            ///////////////����2��xml�ļ��ıȽ�////////////////////////////
            /*
                SAXBuilder sb=new SAXBuilder();
                Document myDoc1=sb.build(new FileInputStream("allobj_1.xml"));
                Document myDoc2=sb.build(new FileInputStream("allobj_2.xml"));
                //������Ԫ��
                Element root1=myDoc1.getRootElement(); //�ȵõ���Ԫ��
                Element root2=myDoc2.getRootElement(); //�ȵõ���Ԫ��
                ArrayList lstComp=new ArrayList();
                s1.compAllElement(root1,root2,"",lstComp);

                log.debug("��ʾ�ȽϽṹ: ");
                for (int i=0;i<lstComp.size();i++){
             XmlCompResult comp=(XmlCompResult)lstComp.get(i);
             log.debug(comp.getPath()+": "+comp.getOldValue()+"!="+comp.getNewValue());
                }
             */

            //������
            /*
                SAXBuilder sb=new SAXBuilder();
                Document myDoc=sb.build(new FileInputStream("tree.xml"));
                //������Ԫ��
                Element root=myDoc.getRootElement(); //�ȵõ���Ԫ��
                log.debug("XML���ͱ�ʾ");
                s1.AllElement_ToTree(root,0,-1);
             */


        } catch ( Exception e )
        {
            log.error("Method main caught an error:", e);
        }
    }

    /*���ݼ�¼list�õ�XML
     *
     *
     */
    public List rstToXML_ForOacle( List rst, Class type, String pre, String cninfo )
    {
        List lstEle = new ArrayList();
        XMLComm xmlcomm = new XMLComm();

        //�����¼��Ϊ�ջ���Ϊ0,�򷵻�xml���
        if ( rst == null || rst.size() == 0 )
        {
            Element eleTab = xmlcomm.aRowToXML_ForOacle( new HashMap(), type, pre, cninfo );
            lstEle.add( eleTab );
            return lstEle;
        }

        //ѭ������Ԫ��
        for ( int i = 0; i < rst.size(); i++ )
        {
            Element eleTab = xmlcomm.aRowToXML_ForOacle( ( Map )rst.get( i ), type, pre, cninfo );
            lstEle.add( eleTab );
        }
        return lstEle;

    }

    /*�ж�һ��Key��Map.key���Ƿ����
     *
     */
    public boolean keyInMap( String key, Map map )
    {
        boolean bResult = false;
        if ( map == null )
        {
            return false;
        }
        Iterator ite = map.keySet().iterator();
        while ( ite.hasNext() )
        {
            String tmpKey = ( String )ite.next();
            if ( tmpKey.equals( key ) )
            {
                return true;
            }
        }
        return bResult;
    }

    //���ݸ���һ����¼��дEle
    public Element fillData_InEle( Element eleTab, Map rstMap )
    {
        //�޸Ľ������Ӧ��ļ�ֵ
        List lstchild = eleTab.getChildren();
        for ( int i = 0; i < lstchild.size(); i++ )
        {
            Element ele = ( Element )lstchild.get( i );
            if ( ele.getChildren().size() == 0 )
            {
                String name = ele.getName();

                //����model��fieldName�õ���¼�����ֶ���(ȫ����Сд)
                name = StringUtil.getRstFieldName_ByVoFldName( name );
                //********��һ��ֻ���oracle*****************//
                name = name.toLowerCase();
                //*****************************************//

                Object val = rstMap.get( name );
                if ( val != null )
                {
                    ele.setText( rstMap.get( name ).toString() );
                }
            }
        }
        return eleTab;
    }

    //����module ����xml_Element(�������)
    public Element aRowToXML_ForOacle( Map rstMap, Class type, String pre, String cninfo )
    {
        Element eleTab = aModelToXML( type, pre, cninfo );
        fillData_InEle( eleTab, rstMap );
        return eleTab;
    }

    /**
     * ���ݽڵ�key���ؽڵ�List
     * @param String Key //�ڵ�����
     * @return Data
     **/
    public List findEleByName( Document doc, String key )
    {
        try
        {
            XPath prodAttrPath = XPath.newInstance( "//" + key ); //,ѡ������·����servletԪ��
            List lst = prodAttrPath.selectNodes( doc ); //�������е�servletԪ�ء�
            return lst;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * �������errMessag����һ�������xmlDoc
     * @param errMessage
     * @return
     */
    public static String createErrDoc( String errMessage, Exception e )
    {
        Element root = new Element( "Page" );
        Document document = new Document( root );
        Element error = new Element( "Error" ).setText( errMessage + ":" + e.getMessage() );
        Element detail = new Element( "Detail" );


        root.addContent( error );
        StringBuffer sbf = new StringBuffer();
        XMLComm.eleAsXML( root, "", sbf );
        return XMLComm.getXMLHead( "GBK" ) + sbf.toString();
    }

    /**
     * �������successMsg����һ����ʾ�ɹ���xmlDoc
     * @param errMessage
     * @return
     */
    public static String createSuccessDoc( String successMsg )
    {
        Element root = new Element( "Page" );
        Document document = new Document( root );
        root.addContent( new Element( "Success" ).setText( successMsg ) );
        StringBuffer sbf = new StringBuffer();
        XMLComm.eleAsXML( root, "", sbf );
        return XMLComm.getXMLHead( "GBK" ) + sbf.toString();
    }


}
