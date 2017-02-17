package org.cisiondata.utils.token;

import org.cisiondata.utils.endecrypt.SHAUtils;

public class TokenUtils {

	public static String genToken(String account, String password, String salt) {
		return SHAUtils.SHA512(new StringBuilder().append(account).append(password).append(salt).toString());
    }
	
}
