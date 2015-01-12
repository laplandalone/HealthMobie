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
 * @modify  2005-08-12 追加几个替换的节点的函数
 * jdom 对xml一些通用函数
 *
 */
public class XMLComm
{
	
	private static Log log = LogFactory.getLog(XMLComm.class);
	
	/**
     * 取掉一个节点下所有子结点属性(attrName)
     * @param Element ele 节点
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
     * 根据指定的文件名包含路径,加载xml文件
     * @param fileName 文件名
     * @return 一个document
     **/
    public static Document loadXML( String fileName )
    {
        //从一个xml文件得到一个document/
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
     * 根据指定的文件名包含路径,加载xml文件
     * @param fileName 文件名
     * @return 一个document
     **/
    public static Document loadXMLStr( String str )
    {
        //从一个xml文件得到一个document/
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
     * 根据加载xml格式 String 对象 add by xcx
     * @param str 字符串名
     * @return 一个document
     **/
    public static Document loadXMLString( String str )
    {
        //从一个xml文件得到一个document/
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
     * 根据节点key返回节点List (选择任意路径下Key元素)
     * @param String Key //节点名称
     * @return Data
     **/
    public static List findEleByKey( Document doc, String key )
    {
        try
        {
            XPath prodAttrPath = XPath.newInstance( "//" + key ); //,选择任意路径下servlet元素
            List lst = prodAttrPath.selectNodes( doc ); //返回所有的servlet元素。
            return lst;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据一个节点复制一个节点
     * @param Element eleSource //源节点
     * @return elsDes 返回一个节点
     **/
    public static Element copyElement( Element eleSource )
    {
        Element eleDes = ( Element )eleSource.clone();
        return eleDes;
    }

    /**
     * 调整document:(任何一个节点不能有同名的兄弟节点),重名的兄弟节点只保留一个;
     * 兄弟节点:具有相同的父节点的节点统称为兄弟节点
     * @param Element eleSource //要替换的节点
     * @param List eleDesList 目标节点列表
     * @return 无
     **/
    public static void addJustDoc( Document doc, List lstKey )
    {
        //参数合法性判断
        if ( lstKey == null || lstKey.size() == 0 )
        {
            return;
        }
        for ( int i = 0; i < lstKey.size(); i++ )
        {
            String key = ( String )lstKey.get( i );
            //根据key得到一串节点
            List lstElement = findEleByKey( doc, key );

            //循环比较节点

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
                //得到要节点的在parent的索引号
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
     * 替换一个节点: 用一个目标节点列list 替换一个指定节点(在同样的位置的替换)
     * @param Element eleSource //要替换的节点
     * @param List eleDesList 目标节点列表
     * @return 无
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
                parent.removeContent( npos ); //2005-08-23ojw 如果节点列表为空,则清空该节点
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
     * 替换一个节点的孩子节点: 根据key在eleSource找到孩子,然后用一个目标节点列替换
     * @param eleParent  父节点
     * @param childKeys  孩子节点的key(可以连写: 例如 //T_ServT//M_Product)
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
     * 在一个指定的节点按照指定的路径查找节点
     * @param eleSource :原节点
     * @param path      :路径
     * @return          :节点list
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
     * 在一个指定的节点按照指定的路径查找节点
     * @param eleSource :原节点
     * @param path      :路径
     * @return          :节点list
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
     * 根据一条记录(map)替换一个节点对应Key的价值:
     * @param Element eleSource //要替换的节点
     * @param Map mapRec 一条记录
     * @param String key 如果key="" 或者key为空,则替换第一曾孩子的key对应的值
     *                   如果key不为空,则替换指定孩子的key对应的值
     * @return 无
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

        //判断是要替换的对象
        Element eleDes = null; //要替换的对象
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

        //开始替换指定节点的key对应Val
        List lstChildEle = eleDes.getChildren();
        if ( lstChildEle == null || lstChildEle.size() == 0 )
        {
            return;
        }
        for ( int i = 0; i < lstChildEle.size(); i++ )
        {
            Element child = ( Element )lstChildEle.get( i );
            if ( child.getChildren().size() == 0 )
            { //只替换没有孩子的节点
                //根据节点key找到对应数据的字段名
                String dbfldName = StringUtil.getRstFieldName_ByVoFldName( child.getName().trim() );
                dbfldName = dbfldName.toLowerCase();
                Object val = mapRec.get( dbfldName );
                String keyVal = "";
                if ( val != null )
                {
                    keyVal = val.toString();
                }
                //替换该节点key对应的值
                child.setText( keyVal );
            }
        }

    }

    /**
     * 根n条记录(map):生成一个节点列 ,对应每一个节点都用记录数据
     *                            替换一个节点对应Key的价值:
     * @param Element eleSource //要替换的节点
     * @param Map mapRec 一条记录
     * @param String key 如果key="" 或者key为空,则替换第一曾孩子的key对应的值
     *                   如果key不为空,则替换指定孩子的key对应的值
     * @return 无
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
     * 拼凑一个节点的所有属性
     * @param Element ele //节点
     * @return 属性字符串
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
     * 在控制台显示一个节点包括子节点,(用于调试用的)
     * 该函数采用递归,
     * @param Element ele //节点,split缩进字符: 一遍为空
     * @return 无
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
     * 将doc转换成XML字符串
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

    /** 根据一个节点生成xml字符串
     * @param ele   要显示的节点
     * @param split 节点和节点之间的间隔符
     * @param ret   返回值
     * @return 无
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
     * 生成xml的格式的字符串
     * @param charset字符集: 例如gb2312,GBK
     * @return 返回一个字符串
     **/
    public static String getXMLHead( String charset )
    {
        return ( "<?xml version=\"1.0\" encoding=\"" + charset + "\"?>" );
    }

    /**
     * 根据一个documnet,生成一个XML文件
     * @param Document doc
     * @param fileName 文件路径
     * @return 成功否
     */
    public static boolean BulidXML( Document doc, String fileName )
    {
        try
        {
            //设置生成XML的格式
            String cset = "gb2312"; //编码，显示中文
            XMLOutputter outer = new XMLOutputter();
            Format format = outer.getFormat();
            format.setIndent("");
            format.setEncoding( cset );
            //format.setExpandEmptyElements(true);
            outer.setFormat( format );
            //输出文件
            outer.output( doc, new FileOutputStream( fileName ) );
            return true;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据一个Model在指定结点下生成新的Element
     * @param Element eleTab 指定的节点
     * @param Class   type   类名(模型名)
     * @param String  pre    要生成的节点名称的前缀: 一般为"T_"
     * @param String  cninfo 要生成节点的中文说明
     * @exma  aModelToXml_InEle(V_ele,PCustT.class,"T_","客户帐户");
     */
    public static Element aModelToXML_InEle( Element eleTab, Class type, String pre, String cninfo )
    {
        try
        {
            //循环加入元素
            Object vo = type.newInstance(); //创建一个值对象
            Field[] fields = type.getSuperclass().getDeclaredFields(); //得到值对象中所有字段
            for ( int i = 0; i < fields.length; i++ )
            {
                //加入子元素
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
     * 根据一个Model生成一个节点
     * @param Class   type   类名(模型名)
     * @param String  pre    要生成的节点名称的前缀: 一般为"T_"
     * @param String  cninfo 要生成节点的中文说明
     * @return 返回一个Element
     */
    public static Element aModelToXML( Class type, String pre, String cninfo )
    {
        //得到全路径的类名称
        String className = type.getName();
        List lstClassName = StringUtil.stringMid( className, "." );
        if ( lstClassName == null || lstClassName.size() == 0 )
        {
            return null;
        }
        //得到不带路径的类名称
        className = ( String )lstClassName.get( lstClassName.size() - 1 );
        Element eleTab = new Element( pre + className );
        if ( !( cninfo.equals( "" ) ) )
        {
            eleTab.setAttribute( "cninfo", cninfo );
        }
        return aModelToXML_InEle( eleTab, type, pre, cninfo );
    }

    /**
     * 显示一个节点下所有结点数据(路径只显示当前path)
     * @param Element ele 要显示的节点
     * @param split   父子节点的缩进字符: 由于该函数用的递归,所以一般为""
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
     * 显示一个节点下所有结点数据(路径显示全路径)
     * @param Element ele 要显示的节点
     * @param split   父子节点的缩进字符: 由于该函数用的递归,所以一般为""
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
     * 比较两个节点(包括所以的节点,递归函数)
     * @param Element ele1
     * @param Element ele1
     * @param parentName    out参数 用于返回路径(调用时为空,递归时用到)
     * @param List lstComp  out参数 由于返回比较结果(调用时为空,递归时用到)
     */
    public static boolean compAllElement( Element ele1, Element ele2, String parentName, List lstComp )
    {
        if ( ( ele1 == null ) && ( ele2 == null ) )
        {
            return false;
        }
        //得到子结点的个数
        List lstOne = ele1.getChildren();
        List lstTwo = ele2.getChildren();

        if ( ( lstOne.size() == 0 ) || ( lstTwo.size() == 0 ) )
        {
            return ( true );
        }

        //如果本层的结点的个数不同,则提前退出
        if ( lstOne.size() != lstTwo.size() )
        {
            XmlCompResult comp = new XmlCompResult();
            comp.setPath( parentName + "." + ele1.getName() );
            comp.setOldNodes( lstOne.size() );
            comp.setNewNodes( lstTwo.size() );
            lstComp.add( comp );
            return ( false );
        }

        //如果是根结点
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
     * 根据一个节点,生成用于树的xml
     * @param Element ele
     * @param int split   当前树的节点id
     * @param int parent  当前树的父节点id
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

    /**显示XML中的层次关系
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

            ///////////测试访问一个xml的所有结点///////////////
            /*
                //从一个xml文件得到一个document/
                SAXBuilder sb=new SAXBuilder();
                Document myDoc=sb.build(new FileInputStream("allobj.xml"));
                //访问子元素
                Element root=myDoc.getRootElement(); //先得到根元素
                log.debug("显示所有结点的Name");
                s1.showAllElement(root,"");
                log.debug("显示所有(父类结点: 目录)的Name");
                s1.visitElement_OnlyParent(root,"");
                log.debug("显示所有(父类结点: 目录)的Name");
                s1.showAllElement_FullPath(root,"","");
             */

            ///////////////测试2个xml文件的比较////////////////////////////
            /*
                SAXBuilder sb=new SAXBuilder();
                Document myDoc1=sb.build(new FileInputStream("allobj_1.xml"));
                Document myDoc2=sb.build(new FileInputStream("allobj_2.xml"));
                //访问子元素
                Element root1=myDoc1.getRootElement(); //先得到根元素
                Element root2=myDoc2.getRootElement(); //先得到根元素
                ArrayList lstComp=new ArrayList();
                s1.compAllElement(root1,root2,"",lstComp);

                log.debug("显示比较结构: ");
                for (int i=0;i<lstComp.size();i++){
             XmlCompResult comp=(XmlCompResult)lstComp.get(i);
             log.debug(comp.getPath()+": "+comp.getOldValue()+"!="+comp.getNewValue());
                }
             */

            //测试树
            /*
                SAXBuilder sb=new SAXBuilder();
                Document myDoc=sb.build(new FileInputStream("tree.xml"));
                //访问子元素
                Element root=myDoc.getRootElement(); //先得到根元素
                log.debug("XML树型表示");
                s1.AllElement_ToTree(root,0,-1);
             */


        } catch ( Exception e )
        {
            log.error("Method main caught an error:", e);
        }
    }

    /*根据记录list得到XML
     *
     *
     */
    public List rstToXML_ForOacle( List rst, Class type, String pre, String cninfo )
    {
        List lstEle = new ArrayList();
        XMLComm xmlcomm = new XMLComm();

        //如果记录数为空或者为0,则返回xml框架
        if ( rst == null || rst.size() == 0 )
        {
            Element eleTab = xmlcomm.aRowToXML_ForOacle( new HashMap(), type, pre, cninfo );
            lstEle.add( eleTab );
            return lstEle;
        }

        //循环构造元素
        for ( int i = 0; i < rst.size(); i++ )
        {
            Element eleTab = xmlcomm.aRowToXML_ForOacle( ( Map )rst.get( i ), type, pre, cninfo );
            lstEle.add( eleTab );
        }
        return lstEle;

    }

    /*判断一个Key在Map.key中是否存在
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

    //根据根据一条记录填写Ele
    public Element fillData_InEle( Element eleTab, Map rstMap )
    {
        //修改结点中相应域的价值
        List lstchild = eleTab.getChildren();
        for ( int i = 0; i < lstchild.size(); i++ )
        {
            Element ele = ( Element )lstchild.get( i );
            if ( ele.getChildren().size() == 0 )
            {
                String name = ele.getName();

                //根据model的fieldName得到记录集中字段名(全部是小写)
                name = StringUtil.getRstFieldName_ByVoFldName( name );
                //********这一句只针对oracle*****************//
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

    //根据module 生成xml_Element(结点数据)
    public Element aRowToXML_ForOacle( Map rstMap, Class type, String pre, String cninfo )
    {
        Element eleTab = aModelToXML( type, pre, cninfo );
        fillData_InEle( eleTab, rstMap );
        return eleTab;
    }

    /**
     * 根据节点key返回节点List
     * @param String Key //节点名称
     * @return Data
     **/
    public List findEleByName( Document doc, String key )
    {
        try
        {
            XPath prodAttrPath = XPath.newInstance( "//" + key ); //,选择任意路径下servlet元素
            List lst = prodAttrPath.selectNodes( doc ); //返回所有的servlet元素。
            return lst;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据入参errMessag生成一个报错的xmlDoc
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
     * 根据入参successMsg生成一个提示成功的xmlDoc
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
