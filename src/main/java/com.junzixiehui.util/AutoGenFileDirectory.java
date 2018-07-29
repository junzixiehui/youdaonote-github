package com.junzixiehui.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: </p>
 * @author: by qulibin
 * @date: 2018/7/29  下午8:53
 * @version: 1.0
 */
public class AutoGenFileDirectory {


	private static String BASE_DIR = "/Users/qulibin/IdeaProjects/java-eye/youdaoNote";
	private static String SECOND_TITLE_FORMAT = "## %s:";
	private static String ARTICLE_NAME_FORMAT = "* [%s](https://github.com/junzixiehui/java-eye/blob/master/youdaoNote/%s/%s)";
	private static String APPEND_FILE = "/Users/qulibin/IdeaProjects/java-eye/README1.md";


	public static void main(String[] args) {

		File fileAppend = new File(APPEND_FILE);
		if (fileAppend.exists()) {
			clearInfoForFile(APPEND_FILE);
		}

		appendFirst();

		List<String> dirList = getCurrentDirList(BASE_DIR);
		if (CollectionUtils.isEmpty(dirList)) {
			return;
		}

		dirList = sortList(dirList);
		for (String dirName : dirList) {
			if (!dirName.contains("笔记")) {
				continue;
			}
			String secondTitle = String.format(SECOND_TITLE_FORMAT, dirName);
			appendStr(secondTitle);

			File[] fileList = getCurrentFileList(BASE_DIR + "/" + dirName);
			if (ArrayUtils.isEmpty(fileList)) {
				continue;
			}

			appendBlank();
			for (File file : fileList) {
				String articleName = String.format(ARTICLE_NAME_FORMAT, file.getName(), dirName, file.getName());
				appendStr(articleName);
			}
			appendBlank();
		}


		appendEnd();
	}


	public static void clearInfoForFile(String fileName) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void appendFirst() {
		appendStr("## java-eye");
		appendStr("## 程序员笔记-全段养成");
		appendBlank();
		appendStr(":loudspeaker: 大部分内容结合网上 书上 加一些总结而来 持续更新中... 欢迎提意见。\n");


		appendStr("### 高效进步:");
		appendBlank();
		appendStr("* [如何高效编程](https://github.com/junzixiehui/java-eye/blob/master/note/study/如何高效编程.md)\n");
		appendStr("* [如何高效学习](https://github.com/junzixiehui/java-eye/blob/master/note/study/如何高效学习.md)\n");
		appendStr("* [精简程序设计](https://github.com/junzixiehui/java-eye/blob/master/note/study/精简程序设计.md)\n");
		appendStr("* [读书计划](https://github.com/junzixiehui/java-eye/blob/master/note/study/读书计划.md)\n");


	}

	private static void appendEnd() {

		appendStr("## 后记 :memo:");
		appendStr("**关于仓库**");
		appendStr("- [java笔记链接](http://note.youdao.com/share/?id=0d64bf41cd4d1fb75198c0d0d4414c9c&type=notebook#/)\n");
		appendStr("**Q群联系:180125351**");

	}

	private static List<String> sortList(List<String> dirList) {
		return dirList.parallelStream().sorted().collect(Collectors.toList());
	}


	public static void appendStr(String content) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(APPEND_FILE, true)));
			out.write(content + "\r\n");
			System.out.println(content + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void appendBlank() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(APPEND_FILE, true)));
			out.write("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static List<String> getCurrentDirList(String baseDir) {

		File fileDir = new File(baseDir);

		if (!fileDir.exists()) {
			return ListUtils.EMPTY_LIST;
		}

		String[] dirStr = fileDir.list();
		if (ArrayUtils.isEmpty(dirStr)) {
			return ListUtils.EMPTY_LIST;
		}

		return Lists.newArrayList(dirStr);
	}


	public static File[] getCurrentFileList(String baseDir) {

		File fileDir = new File(baseDir);

		if (!fileDir.exists()) {
			return null;
		}

		File[] dirStr = fileDir.listFiles();
		if (ArrayUtils.isEmpty(dirStr)) {
			return null;
		}

		return dirStr;
	}


}
