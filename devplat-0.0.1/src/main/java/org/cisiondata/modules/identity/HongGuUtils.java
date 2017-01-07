package org.cisiondata.modules.identity;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import org.cisiondata.utils.encryption.DESUtils;

public class HongGuUtils {


	private static String PCauth = "&cms10B61EE124857B40FC675D34FBADBF2D7D4E0B61E09CEAFC675E124857B40FC675FC675D34FBFC675E124809CEAADBF2D7D4ErE1248cD7D4ED34FBdE124857B40D34FBD34FBFC6750B61E09CEA0B61EE12480B61EADBF257B40D34FBc1FC675E1248ADBF2D7D4E57B40E1248D7D4ED7D4ED34FBADBF2fwafawfawfawfawfawfawfwfasvaegwagsehegtrjhtyjktjmyklyfkjmxdh5e4sghwzsaftawerf2345wrcms2&cms3506174551860BC11226FB9911B02890914510648cms4&cms5111111111111111111111111111111111111111111111111111111cms6cms7D34FBFC67557B40ADBF2E1248D7D4E0B61E09CEAcms8cms9nonocms10acmsC44184E737BB21066ECE6BC0AFBA564214734bcms";

	/**
	 * 根据操作编号访问宏达PC端对应服务器
	 * 
	 * @param param
	 *            String
	 * @param op
	 *            int
	 * @return response String 以下是操作数对应操作的说明 1,朋友网url查找qq号 2,微博url查找qq号
	 *         3,qq查找朋友网 4,qq反找微博 5,对查找qq发起临时会话 6,qq查找最后说说 7,手机号找qq
	 */
	public static String requestPCHonggu(String param, int op) {
		String ip1 = "118.244.140.159";
		int port1 = 30001;
		int port2 = 30002;
		String ip3 = "123.56.247.69";
		int port3 = 30006;
		int port4 = 30207;
		int port5 = 30003;
		int port6 = 30008;
		int port7 = 30011;
		String ip = null;
		int port = 0;
		switch (op) {
		case 1: {
			port = port1;
			ip = ip1;
		}
			break;
		case 2: {
			port = port2;
			ip = ip1;
		}
			break;
		case 3: {
			port = port3;
			ip = ip3;
		}
			break;
		case 4: {
			port = port4;
			ip = ip3;
		}
			break;
		case 5: {
			port = port5;
			ip = ip1;
		}
			break;
		case 6: {
			port = port6;
			ip = ip1;
		}
			break;
		case 7: {
			port = port7;
			ip = ip3;
		}
			break;
		default:
			break;
		}
		// PC发出请求

		String request = encodePcHongdaMessage(param);
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		String d = null;
		try {
			socket = new Socket(ip, port);
			socket.setOOBInline(true);
			socket.setKeepAlive(true);
			os = socket.getOutputStream();

			os.write(request.getBytes());
			os.flush();
			// 等待服务器发送结果
			System.out.println(ip + " " + port);
			if (op == 1) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				os.write(request.getBytes());
				os.flush();
			}

			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			is = socket.getInputStream();
			int size = is.available();
			byte[] requestBuffer = new byte[size];
			is.read(requestBuffer);
			String result = new String(requestBuffer);
			d = decodePcHongdaMessage(result);
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return d.replace("\b", "").replace((char) (5), ' ').trim();
	}

	/**
	 * 将Base64编码转换为byte数组
	 * 
	 * @param input
	 *            String
	 * @return output byte[]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] FromBase64(String input)
			throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Class clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod = clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, input);
		return (byte[]) retObj;
	}

	/**
	 * 将用来传输的参数按照宏达PC端数据包的格式进行封装和加密
	 * 
	 * @param input
	 *            String
	 * @return sent String
	 */
	public static String encodePcHongdaMessage(String input) {
		String request1 = input + PCauth;
		String password = "wufuzhe1";
		byte[] c = DESUtils.encrypt(request1.getBytes(), password);
		String sent = "";
		try {
			sent = ToBase64(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sent;
	}

	/**
	 * 将用来传输或接收到的宏达PC端数据包解析和解密
	 * 
	 * @param input
	 *            String
	 * @return output String
	 */
	public static String decodePcHongdaMessage(String input) {
		byte[] cov = null;
		try {
			cov = FromBase64(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] decryResult = null;
		String password = "wufuzhe1";
		try {
			decryResult = DESUtils.decrypt(cov, password);
			// System.out.println("解密后："+new String(decryResult));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String utf8 = null;
		String re = null;
		try {
			utf8 = new String(decryResult, "GBK");
			re = new String(utf8.getBytes("utf-8"), "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 将byte数组转换为Base64编码
	 * 
	 * @param input
	 *            byte[]
	 * @return output String
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String ToBase64(byte[] input) throws Exception {
		Class clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod = clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, new Object[] { input });
		return (String) retObj;
	}

}
