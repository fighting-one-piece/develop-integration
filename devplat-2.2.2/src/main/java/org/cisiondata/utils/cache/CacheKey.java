package org.cisiondata.utils.cache;

public class CacheKey {

	public interface USER {
		/** ID缓存用户信息 */
		public static final String ID = "user:c:id:%s";
		/** 账号缓存用户信息 */
		public static final String ACCOUNT = "user:c:account:%s";
		/** API accessId缓存用户信息 */
		public static final String ACCESSID = "user:c:accessId:%s";
	}

	public interface COMPANY {
		/** ID缓存单位信息 */
		public static final String ID = "company:c:id:%s";
	}

	public interface VERIFICATION {
		/** 手机发送验证码缓存验证码 */
		public static final String ACCOUNT_MOBILEPHONE = "verification:c:%s:%s";
		/** 手机发送验证码缓存手机号 */
		public static final String MOBILEPHONE = "verification:c:mobilePhone:%s";
	}

	public interface RESOURCE {
		/** ID缓存资源信息 */
		public static final String ID = "resource:c:id:%s";
		/** url和type缓存资源信息 */
		public static final String URL = "resource:c:url:%s:%s";
		/** identity和type缓存资源信息 */
		public static final String IDENTITY = "resource:c:identity:%s:%s";
	}

	public interface ESMETADATA {
		/* metadata **/
		public static final String METADATA = "metadata:c:id:%s";
		/** type */
		public static String TYPE = "metadata:c:type";
		/** index获取对应的全部type */
		public static String INDEXTYPE = "metadata:c:indextype";
		/** index */
		public static String INDEX = "metadata:c:index";
		/** type index */
		public static String INDEX_TYPE = "metadata:c:typeandindex";
		/** index type attren */
		public static String INDEX_TYPE_ATTREN = "metadata:c:indexandtypeandattr";
		/** index type field */
		public static String INDEX_TYPE_FIELD = "metadata:c:indexandtypeandfield";
		/** allIndex */
		public static String ALLINDEX = "metadata:c:allindex";

	}

	public interface CHARGING {
		/** 扣费费单位id */
		public static final String PAYCOMPANY = "limit:%s";
		/** 扣费单位购买的资源 */
		public static final String PAYRESOURCE = "resourceId:%s:type:%s";
	}

}
