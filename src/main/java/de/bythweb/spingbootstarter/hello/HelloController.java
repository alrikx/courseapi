package de.bythweb.spingbootstarter.hello;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.bythweb.spingbootstarter.CourseApiApp;

@RestController
@MultipartConfig(fileSizeThreshold = 20971520)
public class HelloController {

	@RequestMapping("/hello")
	public String sayHi() {
		return "Hi";
	}

	@RequestMapping("/bintest")
	public List<String> getBinary(@RequestParam("uploadedFile") MultipartFile uploadedFileRef) throws IOException {

		List<String> content = new ArrayList<String>();

		// Path where the uploaded file will be stored.
		File destDir = new File(CourseApiApp.args[0]);

		// This buffer will store the data read from 'uploadedFileRef'
		byte[] buffer = new byte[1000];

		ZipInputStream zis = new ZipInputStream(uploadedFileRef.getInputStream());
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {

			File newFile = newFile(destDir, zipEntry);
			content.add(zipEntry.getName());
			if (zipEntry.isDirectory()) {
				if (newFile.mkdirs() == false) {
					throw new IOException("target dir not created: " + zipEntry.getName());
				}
			} else {
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}

			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();

		return content;

	}

	private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

}
