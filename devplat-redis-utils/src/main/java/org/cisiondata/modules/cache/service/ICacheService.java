package org.cisiondata.modules.cache.service;

import java.util.Set;

public interface ICacheService {
	
	/**
	 * 
	 * @param pattern
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readKeys(String pattern) throws RuntimeException;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	public Object readValueByKey(String key) throws RuntimeException;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	public Object readValueByNotSerKey(String key) throws RuntimeException;
	
	/**
	 * 
	 * @param pattern
	 * @return
	 * @throws BusinessException
	 */
	public Object readValuesByKey(String pattern) throws RuntimeException;

	/**
	 * 
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	public Long deleteNotSerKey(String key) throws RuntimeException;
	
	/**
	 * 
	 * @param pattern
	 * @return
	 * @throws BusinessException
	 */
	public int deleteKeys(String pattern) throws RuntimeException;
	
}
