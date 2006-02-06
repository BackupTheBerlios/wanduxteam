package pfe.migration.client.pre.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopy {

	public FileCopy(String src, String dst) throws IOException {
		FileChannel in = null, out = null;
		in = new FileInputStream(src).getChannel();
		out = new FileOutputStream(dst).getChannel();

		in = new FileInputStream(src).getChannel();
		out = new FileOutputStream(dst).getChannel();
		in.transferTo(0, in.size(), out);
		in.close();
		out.close();
	}
}
