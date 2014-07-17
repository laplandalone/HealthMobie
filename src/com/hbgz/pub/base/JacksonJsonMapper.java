package com.hbgz.pub.base;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonJsonMapper 
{
    private static volatile ObjectMapper objectMapper = null;

    private JacksonJsonMapper(){}

    public static ObjectMapper getInstance()
    {
	if (objectMapper == null)
	{
	    synchronized (ObjectMapper.class) 
	    {
		if (objectMapper==null)
		{
		    objectMapper = new ObjectMapper();
		}
	    }
	}
	return objectMapper;
    }
}
