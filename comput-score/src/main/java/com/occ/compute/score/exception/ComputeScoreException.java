package com.occ.compute.score.exception;

/**
 * Application common exception class
 * 
 * @author thaku
 *
 */
public class ComputeScoreException extends RuntimeException {

	private static final long serialVersionUID = -1399748912567180815L;

	public ComputeScoreException(String errorMessage) {
        super(errorMessage);
    }
	
	public ComputeScoreException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
