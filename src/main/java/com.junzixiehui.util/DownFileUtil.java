package com.junzixiehui.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * @author: by qulibin
 * @date: 2018/7/29  下午3:19
 * @version: 1.0
 */

public class DownFileUtil {


	public static File saveUrlAs(String url, String firstPath, String secondPath, String fileName, String method) {
		//创建不同的文件夹目录
		String filePath = firstPath + "/" + secondPath;
		File file = new File(filePath);
		//判断文件夹是否存在
		if (!file.exists()) {
			//如果文件夹不存在，则创建新的的文件夹
			file.mkdirs();
		}
		FileOutputStream fileOut = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try {
			// 建立链接
			URL httpUrl = new URL(url);
			conn = (HttpURLConnection) httpUrl.openConnection();
			//以Post方式提交表单，默认get方式
			conn.setRequestMethod(method);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// post方式不能使用缓存
			conn.setUseCaches(false);
			//连接指定的资源
			conn.connect();
			//获取网络输入流
			inputStream = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream);


			//判断文件的保存路径后面是否以/结尾
			if (!filePath.endsWith("/")) {
				filePath += "/";
			}

			//写入到文件（注意文件保存路径的后面一定要加上文件的名称）
			fileOut = new FileOutputStream(filePath + fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fileOut);

			byte[] buf = new byte[4096];
			int length = bis.read(buf);
			//保存文件
			while (length != -1) {
				bos.write(buf, 0, length);
				length = bis.read(buf);
			}
			bos.close();
			bis.close();
		} catch (Exception e) {
			System.out.println("exception:" + filePath + "/" +fileName + "|" +  e.getMessage());
		} finally {
			conn.disconnect();
		}
		return file;
	}


	public static void main(String[] args) {
		/*String photoUrl = "https://note.youdao.com/yws/api/personal/file/B4E92DB12C4B4DDD8C97FF125BFF3BF1?"
				+ "method=download&read=true&shareKey=0d64bf41cd4d1fb75198c0d0d4414c9c&cstk=PCeekVDC";
		String filePath = "/Users/qulibin/IdeaProjects/java-eye/youdaoNote";

		String secondPath = "笔记37-高可用";

		String fileName = "高可用高并发.md";
		File file = saveUrlAs(photoUrl, filePath, secondPath, fileName,"GET");
		System.out.println("save over " + file.getName());*/

		String mainId = "0d64bf41cd4d1fb75198c0d0d4414c9c";
		String filePath = "/Users/qulibin/IdeaProjects/java-eye/youdaoNote";


		String bathPath = "https://note.youdao.com/yws/public/notebook/" + mainId + "/"
				+ "subdir/D2CB4578F1B740A7A8CA17887C85FBDB?cstk=PCeekVDC";

		List<Object> resultList = getFirstItem(bathPath);

		for (Object obj : resultList) {
			if (obj instanceof List) {
				List<Map<String, Object>> mapList = (List<Map<String, Object>>) obj;

				for (Map<String, Object> map : mapList) {
					String p = String.valueOf(map.get("p"));
					String secondPath = String.valueOf(map.get("tl"));


					String secondUrl =
							"https://note.youdao.com/yws/public/notebook/" + mainId + "/subdir" + p + "?cstk=PCeekVDC";
					List<Object> secondResultList = getFirstItem(secondUrl);

					for (Object object : secondResultList) {
						if (object instanceof List) {
							List<Map<String, Object>> secondMapList = (List<Map<String, Object>>) object;

							for (Map<String, Object> map1 : secondMapList) {

								String p1 = String.valueOf(map1.get("p"));
								String fileName = String.valueOf(map1.get("tl"));
								if (fileName.endsWith("note")){
									continue;
								}
								String path = p1.substring(p1.lastIndexOf("/") + 1);

								String downUrl = "https://note.youdao.com/yws/api/personal/file/" + path
										+ "?method=download&read=true&shareKey=" + mainId + "&cstk=PCeekVDC";

								File file = saveUrlAs(downUrl, filePath, secondPath, fileName, "GET");
								System.out.println("save over..." + file.getName() + ":" +fileName);

							}
						}
					}
					try {
						Thread.sleep(1000);
					} catch (Exception e){

					}

				}
			}
		}
	}


	public static List<Object> getFirstItem(String basePath) {

		HttpClientUtils httpClientUtils = new HttpClientUtils();
		String result = httpClientUtils.httpGet(basePath);

		List<Object> resultList = FastJson.jsonStr2Object(result, List.class);
		return resultList;
	}

}
