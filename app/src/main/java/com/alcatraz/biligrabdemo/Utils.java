package com.alcatraz.biligrabdemo;
import com.alcatraz.biligrabdemo.bean.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;
import android.graphics.*;
import java.util.*;
import android.util.*;
import com.alcatraz.biligrabdemo.search.*;
import com.google.gson.*;

public class Utils
{
	public static class NetWork{
		public static void runSearch(final String key,final NetWorkCallBack ncb){
			final String url="http://m.bilibili.com/search/searchengine";
			new Thread(new Runnable(){
					@Override
					public void run()
					{
						String content="";
						String line="";
						try{
							URL realUrl = new URL(url);
							URLConnection conn = realUrl.openConnection();
							conn.setRequestProperty("accept", "*/*");
							conn.setRequestProperty("connection", "Keep-Alive");
							conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
							conn.setRequestProperty("Content-Type","application/json");
							conn.setRequestProperty("Accpet","application/json");
							conn.setDoOutput(true);
							conn.connect();
							OutputStream dos=conn.getOutputStream();
							dos.write(StringCutting.getSearchPack(key).getBytes());
							dos.flush();
							InputStreamReader is=new InputStreamReader(conn.getInputStream());
							BufferedReader reader=new BufferedReader(is);
							while((line=reader.readLine())!=null){
								content=content+"\n";
								content+=line;
							}
							dos.close();
						}catch(Exception e){
							e.printStackTrace();
						}
						ncb.onReceiveRawContent(content);
					}
				}).start();
		}
		public static void returnBitMap(final String url,final BitmapReceive bmp) { 
			
			new Thread(new Runnable(){

					@Override
					public void run()
					{
						try {
							Bitmap bitmap=null;
							HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection(); 
							conn.setDoInput(true); 
							conn.connect(); 
							InputStream is = conn.getInputStream(); 
							bitmap = BitmapFactory.decodeStream(is); 
							is.close(); 
							bmp.onReceiveBitmap(bitmap);
						} catch (IOException e) { 
							e.printStackTrace(); 
						} 
						// TODO: Implement this method
					}
				}).start();
		} 
		public static void connectToBili(final String url,final NetWorkCallBack ncb){
			new Thread(new Runnable(){
					@Override
					public void run()
					{
						String content="";
						String line="";
						try{
							URL realUrl = new URL(url);
							URLConnection conn = realUrl.openConnection();
							conn.setRequestProperty("accept", "*/*");
							conn.setRequestProperty("connection", "Keep-Alive");
							conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
							conn.connect();
							InputStreamReader is=new InputStreamReader(conn.getInputStream());
							BufferedReader reader=new BufferedReader(is);
							while((line=reader.readLine())!=null){
								content=content+"\n";
								content+=line;
							}
							
						}catch(Exception e){
							e.printStackTrace();
						}
						ncb.onReceiveRawContent(content);
					}
				}).start();
		}
	}
	public static class UnicodeProcess{
		public static String stringToUnicode(String string) {
			StringBuffer unicode = new StringBuffer();
			for (int i = 0; i < string.length(); i++) {
				char c = string.charAt(i);  // 取出每一个字符
				unicode.append("\\u" +Integer.toHexString(c));// 转换为unicode
			}
			return unicode.toString();
		}

		//unicode 转字符串
		public static String unicodeToString(String unicode) {
			StringBuffer string = new StringBuffer();
			String[] hex = unicode.split("\\\\u");
			for (int i = 1; i < hex.length; i++) {
				int data = Integer.parseInt(hex[i], 16);// 转换出每一个代码点
				string.append((char) data);// 追加成string
			}
			return string.toString();
		}
	}
	public static class StringCutting{
		public static String unWrapBiliCallBack(String raw){
			String[] process_1=raw.split("\\(");
			String[] process_2=process_1[1].split("\\)");
			return process_2[0];
		}
		public static String getSearchPack(String key){
			try
			{
				return "{\n  \"keyword\": \"" + URLEncoder.encode(key, "utf-8")+"\",\n  \"page\": 1,\n  \"pagesize\": 20,\n  \"platform\": \"h5\",\n  \"search_type\": \"bangumi\",\n  \"main_ver\": \"v3\",\n  \"order\": \"totalrank\"\n}";
			}
			catch (UnsupportedEncodingException e)
			{}
			return null;
		}
		public static String getBangumiIdFromIntentExtra(String extra){
			String[] g=extra.split(" ");
			String ur=g[g.length-1];
			String[] process=ur.split("/");
			return process[process.length-1];
		}
	}
	public static Season getSeasonObjFromCallback(String callback){
		Gson gson=new Gson();
		Season season=gson.fromJson(callback,Season.class);
		return season;
	}
	public static SearchJson getSeachFromCallback(String callback){
		Gson gson=new Gson();
		SearchJson sj=gson.fromJson(callback,SearchJson.class);
		return sj;
	}
	public static String getCallbackAddressByBangumiId(String id){
		return "http://bangumi.bilibili.com/jsonp/seasoninfo/"+id+".ver?callback=seasonListCallback";
	}
	public static interface NetWorkCallBack{
		public void onReceiveRawContent(String raw);
	}
	public static interface BitmapReceive{
		public void onReceiveBitmap(Bitmap bm);
	}
}
