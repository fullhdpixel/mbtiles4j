package com.myroutes.mbtiles4j;

import com.myroutes.mbtiles4j.*;

/**
 * An exception for write errors
 */
public class MBTilesWriteException extends com.myroutes.mbtiles4j.MBTilesException {

    public MBTilesWriteException(Throwable e) {
        super(e);
    }

    public MBTilesWriteException(String msg, Throwable e) {
        super(msg, e);
    }
}
