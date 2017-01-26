package org.cisiondata.utils.exception;

/** 数据访问异常*/
public class DataException extends GenericException {

	private static final long serialVersionUID = 1L;

	public DataException(String module, String code, Object[] args, String defaultMessage) {
		super(module, code, args, defaultMessage);
    }
	
	public DataException(String module, String code, Object[] args) {
        super(module, code, args, null);
    }

    public DataException(String module, String defaultMessage) {
    	super(module, null, null, defaultMessage);
    }

    public DataException(String code, Object[] args) {
    	super(null, code, args, null);
    }

    public DataException(String defaultMessage) {
    	super(null, null, null, defaultMessage);
    }

}
