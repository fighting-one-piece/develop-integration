package org.cisiondata.utils.exception;

/** 业务访问异常*/
public class BusinessException extends GenericException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String module, String code, Object[] args, String defaultMessage) {
		super(module, code, args, defaultMessage);
    }
	
	public BusinessException(String module, String code, Object[] args) {
        super(module, code, args, null);
    }

    public BusinessException(String module, String defaultMessage) {
    	super(module, null, null, defaultMessage);
    }

    public BusinessException(String code, Object[] args) {
    	super(null, code, args, null);
    }

    public BusinessException(String defaultMessage) {
    	super(null, null, null, defaultMessage);
    }

}
