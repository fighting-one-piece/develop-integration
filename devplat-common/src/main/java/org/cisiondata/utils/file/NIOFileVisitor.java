package org.cisiondata.utils.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOFileVisitor extends SimpleFileVisitor<Path> {
	
	private Logger LOG = LoggerFactory.getLogger(NIOFileVisitor.class);
	
	private static TreeMap<Long, String> map = new TreeMap<Long, String>();

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		System.out.println("preVisitDirectory: " + dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		LOG.debug("visitFile: " + file);
		map.put(attrs.size(), file.toString());
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		return FileVisitResult.TERMINATE;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		System.out.println("postVisitDirectory: " + dir);
		return FileVisitResult.CONTINUE;
	}
	
	public static void main(String[] args) {
		try {
			Files.walkFileTree(Paths.get("I:\\车主"), new NIOFileVisitor());
			for(Map.Entry<Long, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
