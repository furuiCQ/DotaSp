package com.example.dotasp;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

public class SpUtils {
	// execShellCmd("input keyevent 3");//home //���home��
	// execShellCmd("input text  'helloworld!' ");//��������
	// execShellCmd("input tap 168 252"); //�����Ļ
	// execShellCmd("input swipe 100 250 200 280"); //������Ļ
	public void editUserNameAndPassword(HashMap<String, String> map) {
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator
					.next();
			String key = entry.getKey();
			String value = entry.getValue();
			// execShellCmd("input text'"+key+"'");
			// execShellCmd("input text'"+value+"'");

		}
	}

	/**
	 * ��¼�˺�
	 */
	public void loginAccount(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		Log.i("width", "" + width);
		Log.i("height", "" + height);
		execShellCmd("input tap 660 300"); //loginAcount
	}

	public void execShellCmd(String cmd) {
		try {
			// �����ȡrootȨ�ޣ���һ������Ҫ����Ȼ��û������
			Process process = Runtime.getRuntime().exec("su");
			// ��ȡ�����
			OutputStream outputStream = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			dataOutputStream.writeBytes(cmd);
			dataOutputStream.flush();
			dataOutputStream.close();
			outputStream.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
