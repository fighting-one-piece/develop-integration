package org.cisiondata.utils.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(IPUtils.class);

	private IPUtils() {
	}

	public static String getIPAddress(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 通过IP地址获取MAC地址
	 * @param ip 127.0.0.1格式
	 * @return mac 
	 * @throws Exception
	 */
	public static String getMACAddress(HttpServletRequest request) {
		String ip = getIPAddress(request);
		String macAddress = "";
		try {
			final String LOOPBACK_ADDRESS_1 = "127.0.0.1";
			final String LOOPBACK_ADDRESS_2 = "0:0:0:0:0:0:0:1";
			// 如果为127.0.0.1,则获取本地MAC地址。
			if (LOOPBACK_ADDRESS_1.equals(ip) || LOOPBACK_ADDRESS_2.equals(ip)) {
				InetAddress inetAddress = InetAddress.getLocalHost();
				// 貌似此方法需要JDK1.6。
				byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
				// 下面代码是把mac地址拼装成String
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						sb.append("-");
					}
					// mac[i] & 0xFF 是为了把byte转化为正整数
					String s = Integer.toHexString(mac[i] & 0xFF);
					sb.append(s.length() == 1 ? 0 + s : s);
				}
				// 把字符串所有小写字母改为大写成为正规的mac地址并返回
				return sb.toString().trim().toUpperCase();
			}
			// 获取非本地IP的MAC地址
			final String MAC_ADDRESS_PREFIX_EN = "MAC Address = ";
			final String MAC_ADDRESS_PREFIX_CH = "MAC 地址 = ";
			Process process = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader isr = new InputStreamReader(process.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				int index = line.indexOf(MAC_ADDRESS_PREFIX_EN);
				if (index != -1) {
					macAddress = line.substring(index + MAC_ADDRESS_PREFIX_EN.length()).trim().toUpperCase();
				} else {
					index = line.indexOf(MAC_ADDRESS_PREFIX_CH);
					if (index != -1) {
						macAddress = line.substring(index + MAC_ADDRESS_PREFIX_CH.length()).trim().toUpperCase();
					}
				}
				if (!"".equals(macAddress)) break;
			}
			br.close();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} 
		return macAddress;
	}
	
}
