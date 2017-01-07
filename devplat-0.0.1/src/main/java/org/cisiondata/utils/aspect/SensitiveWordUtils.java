package org.cisiondata.utils.aspect;

import java.util.HashSet;
import java.util.Set;

public class SensitiveWordUtils {

	private static Set<String> SENSITIVE_WORD_REPO = new HashSet<String>();
	
	static {
		SENSITIVE_WORD_REPO.add("李其恩");
		SENSITIVE_WORD_REPO.add("4599926");
		SENSITIVE_WORD_REPO.add("125906088");
		SENSITIVE_WORD_REPO.add("13669905070");
		SENSITIVE_WORD_REPO.add("15682373333");
		SENSITIVE_WORD_REPO.add("15882319157");
	}
	
	public static boolean isSensitiveWord(String word) {
		return SENSITIVE_WORD_REPO.contains(word);
	}
	
}
