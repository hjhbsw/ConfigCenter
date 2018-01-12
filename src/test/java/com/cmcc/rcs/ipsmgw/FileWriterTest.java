package com.cmcc.rcs.ipsmgw;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FileWriterTest {

	@Test
	public void test() {
		Path path = Paths.get("/Users/hejiahuan/Documents/workspace-sts-3.8.4.RELEASE/ConfigCenter/aa/bb", "cc");
		try {
			Files.createDirectories(path);
			
//			Files.createFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
