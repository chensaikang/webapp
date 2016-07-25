package com.example.webapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.csk.jpush.JpushUtils;

public class MainActivity extends Activity {

	private WebView webView;
	private SharedPreferences sp;
	private ListenerInterface listenerInterface = new ListenerInterface();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web_layout);

		webView = (WebView) findViewById(R.id.web);
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("UTF-8");
		webView.setWebViewClient(new MyWebClient());
		settings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(listenerInterface, "native");
		// 地址链接
		webView.loadUrl("http://byappweb.baoyi188.com/Home/Index");

		registerReceiver();// 注册
		JpushUtils.customJPushSetting(this);// 设置推送样式

		sp = getSharedPreferences("web", 0);
		if (sp.getString("name", null) != null) {
			listenerInterface.sendUserInfo();
		}
	}

	// 注册广播
	public void registerReceiver() {
		receiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(receiver, filter);
	}

	// 保存登陆信息
	public void infoCache(String name, String pwd) {
		sp = getSharedPreferences("web", 0);
		Editor edit = sp.edit();
		edit.putString("name", name);
		edit.putString("pwd", pwd);
		edit.commit();

	}

	class MyWebClient extends WebViewClient {

		/**
		 * 重写后return true表示让当前程序处理，return false表示让当前webView处理
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO 自动生成的方法存根
			view.loadUrl(url);
			return true;
		}

	}

	class ListenerInterface {
		@JavascriptInterface
		public void getUserInfo(String name, String pwd) {
			// Toast.makeText(MainActivity.this, name + "+++++" + pwd,
			// 0).show();
			infoCache(name, pwd);
			JpushUtils.setAlias(MainActivity.this, name);
		}

		@JavascriptInterface
		public void sendUserInfo() {
			// Toast.makeText(MainActivity.this, name + "+++++" + pwd,
			// 0).show();
			sp = getSharedPreferences("web", 0);
			String name = sp.getString("name", "");
			String pwd = sp.getString("pwd", "");
			webView.loadUrl("javascript:getLoginInfoFromApp('" + name + ","
					+ pwd + "')");
		}
	}

	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	private MessageReceiver receiver;
	public static boolean isForeground = false;

	class MessageReceiver extends BroadcastReceiver {

		public void onReceive(Context context, Intent intent) {

			if (MESSAGE_RECEIVED_ACTION == intent.getAction()) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);

				showMsg(messge);
			}
		}

		private void showMsg(String s) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("保益大宗");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setMessage(s);
			builder.show();
		}
	}

	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		// 销毁前解除广播
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	public static final long TWO_SECOND = 2 * 1000;
	public long preTime;

	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();

		if ((currentTime - preTime) > TWO_SECOND) {

			Toast.makeText(this, "再次点击可退出程序", 0).show();
			preTime = currentTime;
			return;
		} else {
			finish();
		}
	}

}
