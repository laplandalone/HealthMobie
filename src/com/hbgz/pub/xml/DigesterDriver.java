package com.hbgz.pub.xml;

import java.io.*;
import java.net.*;
import org.apache.commons.digester.*;
import org.apache.commons.digester.xmlrules.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

public class DigesterDriver {
    private static Logger logger = Logger.getLogger(DigesterDriver.class);

    public DigesterDriver() {
    }

    public Object parse(InputStream in, URL rolesURL) {
        Digester digester = null;
        Object obj = null;
        try {
            digester = DigesterLoader.createDigester(rolesURL);
            obj = digester.parse(in);
        }
        catch (Exception ex) {
           logger.error(ex);;
        }

        return obj;
    }

    public Object parse(String xmlStr, URL rolesURL) {
        Digester digester = null;
        Object obj = null;
        try {
            digester = DigesterLoader.createDigester(rolesURL);
            obj = digester.parse(xmlStr);
        }
        catch (Exception ex) {
           logger.error(ex);;
        }

        return obj;

    }

   
    
}
