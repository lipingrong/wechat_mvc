package com.pingrong.web.uitl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 为OneNet平台操作提供的java方法
 * 
 * @author lixu
 * 
 */
public class OneNetAPI {



	/**
	 * 向OneNet请求创建新的数据流
	 * 
	 * @param apiKey 
	 * @param datastreamsId:数据流id
	 * @param deviceId:设备ID
	 * @param unit:单位
	 * @param symbol:符号
	 * @return
	 */
	public static String createStreamsInOneNet(String apiKey, String datastreamsId,
			String deviceId, String unit, String symbol) {
		PrintWriter out = null;
		BufferedReader in = null;
		String url = "http://api.heclouds.com/devices/" + deviceId
				+ "/datastreams";
		String result = "";
		// 组织数据格式
		String query = "{\"id\":\"" + datastreamsId + "\",\"unit\":\"" + unit
				+ "\",\"unit_symbol\":\"" + symbol + "\"}";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("api-key", apiKey);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 发送请求参数
			out = new PrintWriter(conn.getOutputStream());
			out.write(query);
			out.flush();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("创建数据流 发送 POST请求出现异常！");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				System.out.println("IO关闭异常");
			}
		}
		return result;
	}

	/**
	 * 删除数据流
	 * 
	 * @param deviceId：设备ID
	 * @param apiKey
	 * @param datastreamId：数据流ID
	 * @return
	 */
	public static String deleteDataStreams(String deviceId, String apiKey,
			String datastreamId) {
		String result = "";
		BufferedReader in = null;
		try {
			String url = "http://api.heclouds.com/devices/" + deviceId
					+ "/datastreams/" + datastreamId;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("api-key", apiKey);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！ ");
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("IO close error !");
			}
		}
		return result;
	}
	public static void main(String[] args) {
		//获取信息 {"errno":0,"data":{"create_time":"2017-04-26 15:27:09","update_at":"2017-04-26 15:27:09","id":"wokanbudongl","current_value":10},"error":"succ"}
		System.out.println(OneNetAPI.getDataPoints("5582580", "qevqgEPrthij3lYIgE3SJ=Vdo24=" , "是否重复","2017-04-26T10:41:50","2017-04-27T14:47:50"));
		/*String boby = "{\"msg_signature\":\"PqRS+8bWS6TkODvHmlANcg==\",\"enc_msg\":\"EOuuvSFSH/pgMeZ/EQK8Gy/N6G9wWmDBBiiBRpNUjuFOMda2As5wWrOmhpn03pxyle9ynPFigdLdo63TkLXa0c76/JD4355ImVIOafrkya5349rbaTORQEW7YV+LuQqxgDtlsgN8XB8ZWaj3E3EvsQ==\",\"nonce\":\"OkCUjq(9\"}";
		String AESKey = "5xmTBxxZb7sUKsOEX6t3mFRmzpTwn6SHs4Bt2cLmPRS";
		try {
			//将流转换成对象
			OneNetCoding.BodyObj bodyObj = OneNetCoding.resolveBody(boby.toString(), true);
			//解码对象msg属性值
			String msg = OneNetCoding.decryptMsg(bodyObj, AESKey);
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	/**
	 * 获取数据流
	 *
	 * @param deviceId：设备ID
	 * @param apiKey
	 * @param datastreamId：数据流ID
	 * @return
	 */
	public static String getDataStreams(String deviceId, String apiKey,
			String datastreamId) {
		String result = "";
		BufferedReader in = null;
		try {
			String url = "http://api.heclouds.com/devices/" + deviceId
					+ "/datastreams/" + datastreamId;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("api-key", apiKey);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！ ");
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("IO close error !");
			}
		}
		return result;
	}

	/**
	 * 获取多条数据流
	 * 
	 * @param deviceId：设备ID
	 * @param apiKey
	 * @param //datastreamId：数据流ID
	 * @param dateStreams：数据流数组，如果要获取3个数据流s1，s2，s3的数据，例如：dateStreams = ["s1","s2","s3"]
	 * @return
	 */
	public static String getMulDataStreams(String deviceId, String apiKey, String[] dateStreams) {
		String result = "";
		BufferedReader in = null;
		try {
			String url = "http://api.heclouds.com/devices/" + deviceId
					+ "/datastreams?datastream_ids=";
			for (int i = 0; i < dateStreams.length - 1; i++) {
				url += dateStreams[i] + ",";
			}
			url += dateStreams[dateStreams.length - 1];
			System.out.println(url);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("api-key", apiKey);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！ ");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("IO close error !");
			}
		}
		return result;
	}

	/**
	 * 修改数据流
	 * 
	 * @param deviceId： 设备ID
	 * @param apiKey
	 * @param datastreamId：数据流ID
	 * @param unit：单位
	 * @param symbol：单位的符号
	 */
	public static String updateDataStreams(String deviceId, String apiKey,
			String datastreamId, String unit, String symbol) {
		String result = "";
		BufferedReader in = null;
		PrintWriter out = null;
		String query = "{\"unit\":\"" + unit + "\",\"unit_symbol\":\"" + symbol
				+ "\"}";
		try {
			String url = "http://api.heclouds.com/devices/" + deviceId
					+ "/datastreams/" + datastreamId;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("api-key", apiKey);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.write(query);
			out.flush();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送PUT请求出现异常！ ");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("IO close error !");
			}
		}
		return result;
	}

	/**
	 * 向OneNet发送数据点
	 * 
	 * @param apiKey
	 * @param deviceId：设备ID
	 * @param datapointId：该数据点所上传到的数据流ID
	 * @param value：数据点的值
	 * @return
	 */
	public static String addDataPointsToOneNet(String apiKey, String deviceId,
			String datapointId, String value) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String url = "http://api.heclouds.com/devices/" + deviceId
				+ "/datapoints";

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(d);
		String query = "{\"datastreams\":[{\"id\":\"" + datapointId
				+ "\",\"datapoints\":[{\"" + "at \"" + ":\"" + date
				+ "\",\"value\":\"" + value + "\"}]}]}";
		int length = query.length();
		String strlength = Integer.toString(length);
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("api-key", apiKey);
			conn.setRequestProperty("Content-Length", strlength);

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			out.write("\n");
			out.write(query);
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("添加数据点 发送 POST 请求出现异常！");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				System.out.println("IO关闭异常");
			}
		}
		return result;
	}

	/**
	 * 删除数据点
	 */
	public static String deleteDataPoints(String deviceId, String apiKey,
			String datastreamId, String startTime, String end) {
		String result = "";
		BufferedReader in = null;
		try {
			String url = "http://api.heclouds.com/devices/" + deviceId
					+ "/datapoints?datastream_id=" + datastreamId + "&start="
					+ startTime + "&end=" + end;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();

			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("api-key", apiKey);
			// 建立实际的连接

			connection.connect();

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！ ");
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("IO close error !");
			}
		}
		return result;
	}
	
	
	/**
	 * 获取数据点
	 *  
	 * @param deviceId：设备ID
	 * @param apiKey
	 * @param datastreamId：数据流ID
	 * @param startTime：开始时间2015-03-22T22:31:12
	 * @param end：结束时间2015-03-22T22:31:12
	 */
	public static String getDataPoints(String deviceId, String apiKey,
			String datastreamId, String startTime, String end) {
		String result = "";
		BufferedReader in = null;
		try {
			String url = "http://api.heclouds.com/devices/" + deviceId
					+ "/datapoints?datastream_id=" + datastreamId + "&start="
					+ startTime + "&end=" + end;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("api-key", apiKey);
			// 建立实际的连接
			connection.connect();

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！ ");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("IO close error !");
			}
		}
		return result;
	}
}