package org.phpmaven.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple digester for strings.
 * 
 * Copied from org.eclipse.aether.internal.impl
 */
class SimpleDigest
{

    private MessageDigest digest;

    private long hash;

    public SimpleDigest()
    {
        try
        {
            digest = MessageDigest.getInstance( "SHA-1" );
        }
        catch ( NoSuchAlgorithmException e )
        {
            try
            {
                digest = MessageDigest.getInstance( "MD5" );
            }
            catch ( NoSuchAlgorithmException ne )
            {
                digest = null;
                hash = 13;
            }
        }
    }

    public void update( String data )
    {
        if ( data == null || data.length() <= 0 )
        {
            return;
        }
        if ( digest != null )
        {
            try
            {
                digest.update( data.getBytes( "UTF-8" ) );
            }
            catch ( UnsupportedEncodingException e )
            {
                // broken JVM
            }
        }
        else
        {
            hash = hash * 31 + data.hashCode();
        }
    }

    public String digest()
    {
        if ( digest != null )
        {
            StringBuilder buffer = new StringBuilder( 64 );

            byte[] bytes = digest.digest();
            for ( int i = 0; i < bytes.length; i++ )
            {
                int b = bytes[i] & 0xFF;

                if ( b < 0x10 )
                {
                    buffer.append( '0' );
                }

                buffer.append( Integer.toHexString( b ) );
            }

            return buffer.toString();
        }
        else
        {
            return Long.toHexString( hash );
        }
    }

}
