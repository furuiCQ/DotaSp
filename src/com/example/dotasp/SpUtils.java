package com.example.dotasp;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SpUtils {
//	execShellCmd("input keyevent 3");//home  //点击home键
//	execShellCmd("input text  'helloworld!' ");//输入文字  
//	execShellCmd("input tap 168 252");  //点击屏幕
//	execShellCmd("input swipe 100 250 200 280"); //滑动屏幕 
	public void editUserNameAndPassword(HashMap<String, String> map) {
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			//execShellCmd("input text'"+key+"'");
			//execShellCmd("input text'"+value+"'");

		}
	}
	/**
	 * 登录账号
	 */
	public void loginAccount(){
		execShellCmd("input tap 168 252");  
	}

	public void execShellCmd(String cmd) {
		try {
			// 申请获取root权限，这一步很重要，不然会没有作用
			Process process = Runtime.getRuntime().exec("su");
			// 获取输出流
			OutputStream outputStream = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeBytes(cmd);
			dataOutputStream.flush();
			dataOutputStream.close();
			outputStream.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
