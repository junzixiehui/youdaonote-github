package com.junzixiehui.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * @author: by qulibin
 * @date: 2018/7/29  下午8:36
 * @version: 1.0
 */
public class YoudaoNote2github {


	/**
	 * 分享笔记主id
	 */
	private static String mainId = "";

	/**
	 * 副id
	 */
	private static String subdirId = "";

	/**
	 * 笔记输出位置 /Users/qulibin/IdeaProjects/java-eye/youdaoNote
	 */
	private static String filePath = "";


	public static void main(String[] args) {

		if (StringUtils.isBlank(mainId) || StringUtils.isBlank(subdirId)) {
			throw new RuntimeException("填写你的分享笔记的主id和副id");
		}

		if (StringUtils.isBlank(filePath)) {
			throw new RuntimeException("填写你的笔记输出位置");
		}
		DownFileUtil.downFileFromYoudaoNote(mainId, subdirId, filePath);
	}
}
