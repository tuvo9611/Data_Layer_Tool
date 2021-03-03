package com.example.demo.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheUtil {
	private MustacheUtil() {
		// empty constructor
	}

	private static final MustacheFactory mf = new DefaultMustacheFactory();

	public static MustacheFactory getMustacheFactory() {
		return mf;
	}

	/**
	 * Export file with mustache template 
	 * Input data and mustacheTemplate 
	 * Output store in folder and file name
	 * @param data 
	 * @param mustacheTemplate
	 * @param folder
	 * @param fileName
	 * @throws IOException
	 */
	public static void exportFileWithMustache(Object data, String mustacheTemplate, String folder, String fileName)
			throws IOException {
		Path outputDir = Paths.get(folder);
		Files.createDirectories(outputDir);
		Path outputPath = outputDir.resolve(fileName);
		Writer writer = new OutputStreamWriter(new FileOutputStream(outputPath.toFile()));
		Mustache m = MustacheUtil.getMustacheFactory().compile(mustacheTemplate);

		m.execute(writer, data).flush();
		writer.close();
	}
}
