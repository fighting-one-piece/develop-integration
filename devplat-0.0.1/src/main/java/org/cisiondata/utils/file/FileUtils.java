package org.cisiondata.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {
	
	public static void filter(String src, String dest) {
		InputStream in = null;
		BufferedReader br = null;
		OutputStream out = null;
		BufferedWriter bw = null;
		try {
			in = new FileInputStream(new File(src));
			br = new BufferedReader(new InputStreamReader(in));
			out = new FileOutputStream(new File(dest));
			bw = new BufferedWriter(new OutputStreamWriter(out));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (line.indexOf(" ") == -1) {
					
				}
			}
			bw.write(sb.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) in.close();
				if (null != br) br.close();
				if (null != out) out.close();
				if (null != bw) bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
	}
	
}
