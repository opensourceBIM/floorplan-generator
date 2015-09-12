package org.bimserver.cobie.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.bimserver.plugins.VirtualFile;
import org.junit.Test;

public class ResourceManagerTest {

	@Test
	public void test() throws IOException 
	{
		VirtualFile file = new VirtualFile(new File("C:\\BIMServer1.4Test\\bimserver-1.4.0-FINAL-2015-05-14\\plugins\\bimserver-floorplanplugin-0.1.0-2015-08-23.jar"));
	//	VirtualFile scriptExample = file.listFiles()
		//System.out.println(scriptExample.getName());
	}
	
	@Test
	public void testEnumerateJarContents() throws FileNotFoundException, IOException
	{
		JarInputStream jis = new JarInputStream(new FileInputStream("C:\\BIMServer1.4Test\\bimserver-1.4.0-FINAL-2015-05-14\\plugins\\bimserver-floorplanplugin-0.1.0-2015-08-23.jar"));
		JarEntry jEntry = jis.getNextJarEntry();
		while (jEntry != null)
		{
			System.out.println(jEntry.getName());
			jEntry = jis.getNextJarEntry();
		}
		jis.close();
	}

}
