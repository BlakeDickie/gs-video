/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.anidb;

/**
 *
 * @author bdickie
 */
public class AniDBConnectionError extends RuntimeException {

    public AniDBConnectionError(Throwable cause) {
        super(cause);
    }

    public AniDBConnectionError(String message, Throwable cause) {
        super(message, cause);
    }

    public AniDBConnectionError(String message) {
        super(message);
    }

    public AniDBConnectionError(AniDBReply reply) {
        super(String.format("%03d %s", reply.getReturnCode(), reply.getReturnMessage()));
    }
    
}
