/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.hbgz.pub.exception;

/**≤È—Ø“Ï≥£¿‡
 * @author hbgz
 */
public class QryException extends Exception {
    private String sql;

    /**
     * Constructor for QryException.
     */
    public QryException(  )
    {
        super();
    }

    /**
     * Constructor for QryException.
     * @param message
     */
    public QryException( String message )
    {
        super( message );
    }

    /**
     * Constructor for QryException.
     * @param message
     * @param sql
     */
    public QryException( String message, String sql )
    {
        super( message );
        this.sql = sql;
    }

    public String toString(  )
    {
        if ( sql == null )
        {
            return super.toString(  );
        }
        else
        {
            return super.toString(  ) + "  <---- Caused by SQL: " + sql.toString(  ) + " ---->";
        }
    }

    public void printStackTrace(  )
    {
        super.printStackTrace(  );

        if ( sql != null )
        {
            System.err.println( "<---- Caused by SQL:" );
            System.err.println(sql);
            System.err.println( "---->" );
        }
    }

}
