package com.example.dotasp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.dotasp.adapter.BrowseApplicationInfoAdapter;
import com.example.dotasp.subclass.AppInfo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	private ListView listview = null;

	private List<AppInfo> mlistAppInfo = null;
	private static final String TAG = "FloatWindowTest";
	WindowManager mWindowManager;
	WindowManager.LayoutParams wmParams;
	LinearLayout mFloatLayout;
	Button mFloatView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listview = (ListView) findViewById(R.id.listviewApp);
		mlistAppInfo = new ArrayList<AppInfo>();
		queryAppInfo(); // ��ѯ����Ӧ�ó�����Ϣ
		BrowseApplicationInfoAdapter browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
		listview.setOnItemClickListener(this);
	}

	private void createFloatView() {
		// ��ȡLayoutParams����
		wmParams = new WindowManager.LayoutParams();

		// ��ȡ����LocalWindowManager����
		
		mWindowManager =  (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);  
		Log.i(TAG, "mWindowManager1--->" + this.getWindowManager());
		// mWindowManager = getWindow().getWindowManager();
		Log.i(TAG, "mWindowManager2--->" + getWindow().getWindowManager());

		// ��ȡ����CompatModeWrapper����
		// mWindowManager = (WindowManager)
		// getApplication().getSystemService(Context.WINDOW_SERVICE);
		Log.i(TAG, "mWindowManager3--->" + mWindowManager);
		wmParams.type = LayoutParams.TYPE_PHONE;
		wmParams.format = PixelFormat.RGBA_8888;
		;
		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		wmParams.x = 0;
		wmParams.y = 0;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		LayoutInflater inflater = this.getLayoutInflater();// LayoutInflater.from(getApplication());

		mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
		mWindowManager.addView(mFloatLayout, wmParams);
		// setContentView(R.layout.main);
		mFloatView = (Button) mFloatLayout.findViewById(R.id.float_id);

		Log.i(TAG, "mFloatView" + mFloatView);
		Log.i(TAG, "mFloatView--parent-->" + mFloatView.getParent());
		Log.i(TAG, "mFloatView--parent--parent-->" + mFloatView.getParent().getParent());
		// �󶨴����ƶ�����
		mFloatView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				wmParams.x = (int) event.getRawX() - mFloatLayout.getWidth() / 2;
				// 25Ϊ״̬���߶�
				wmParams.y = (int) event.getRawY() - mFloatLayout.getHeight() / 2 - 40;
				mWindowManager.updateViewLayout(mFloatLayout, wmParams);
				return false;
			}
		});

		// �󶨵������
		mFloatView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(FloatWindowTest.this, ResultActivity.class);
//				startActivity(intent);
				SpUtils spUtils=new SpUtils();
				spUtils.loginAccount(getApplicationContext());
			}
		});

	}

	// �����ת����Ӧ�ó���
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		createFloatView();
		Intent intent = mlistAppInfo.get(position).getIntent();
		startActivity(intent);
	}

	// �����������Activity����Ϣ��������Launch����
	public void queryAppInfo() {	
		PackageManager pm = this.getPackageManager(); // ���PackageManager����
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// ͨ���ѯ���������ResolveInfo����.
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.GET_PERMISSIONS);
		// ����ϵͳ���� �� ���name����
		// ���������Ҫ������ֻ����ʾϵͳӦ�ã������г�����Ӧ�ó���
		Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
		if (mlistAppInfo != null) {
			mlistAppInfo.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				String activityName = reInfo.activityInfo.name; // ��ø�Ӧ�ó��������Activity��name
				String pkgName = reInfo.activityInfo.packageName; // ���Ӧ�ó���İ���
				String appLabel = (String) reInfo.loadLabel(pm); // ���Ӧ�ó����Label
				Drawable icon = reInfo.loadIcon(pm); // ���Ӧ�ó���ͼ��
				// ΪӦ�ó��������Activity ׼��Intent
				Intent launchIntent = new Intent();
				launchIntent.setComponent(new ComponentName(pkgName, activityName));
				// ����һ��AppInfo���󣬲���ֵ
				AppInfo appInfo = new AppInfo();
				appInfo.setAppLabel(appLabel);
				appInfo.setPkgName(pkgName);
				appInfo.setAppIcon(icon);
				appInfo.setIntent(launchIntent);
				mlistAppInfo.add(appInfo); // ������б���
			}
		}
	}

}
