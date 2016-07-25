/*** <p>Title: JpushUtils</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author CSK
 * @date  2016年4月26日下午3:21:52
 * 			jpush相关设置
 */
package com.csk.jpush;

import java.util.Set;

import android.app.Notification;
import android.content.Context;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.webapp.R;

public class JpushUtils {

	// 推送昵称设置
	public static void setAlias(Context c, String name) {
		JPushInterface.setAlias(c, name, new TagAliasCallback() {

			// code=0: 设置成功
			@Override
			public void gotResult(int code, String alias, Set<String> tags) {
				if (code == 0) {
					// Toast.makeText(LoginActivity.this, "昵称为" + num,
					// Toast.LENGTH_SHORT).show();
				}
			}
		});

		// JPushInterface.setAliasAndTags(arg0, arg1, arg2, arg3);
	}

	public static void customJPushSetting(Context c) {

		// 设置推送可接受通知条数
		JPushInterface.setLatestNotificationNumber(c, 5);

		// 设置默认通知 flag为0

		// BasicPushNotificationBuilder Notifybuilder = new
		// BasicPushNotificationBuilder(
		// this);
		// Notifybuilder.statusBarDrawable = R.drawable.androidlogo;
		// JPushInterface.setDefaultPushNotificationBuilder(Notifybuilder);

		// 设置通知样式
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
				c, R.layout.push_layout, R.id.iv, R.id.title, R.id.msg);

		builder.notificationDefaults = Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;

		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 可自动消失
		// JPushInterface.setDefaultPushNotificationBuilder(1, builder);
		JPushInterface.setPushNotificationBuilder(1, builder);// 样式一

	}
}
