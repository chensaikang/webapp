/*** <p>Title: JpushUtils</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author CSK
 * @date  2016��4��26������3:21:52
 * 			jpush�������
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

	// �����ǳ�����
	public static void setAlias(Context c, String name) {
		JPushInterface.setAlias(c, name, new TagAliasCallback() {

			// code=0: ���óɹ�
			@Override
			public void gotResult(int code, String alias, Set<String> tags) {
				if (code == 0) {
					// Toast.makeText(LoginActivity.this, "�ǳ�Ϊ" + num,
					// Toast.LENGTH_SHORT).show();
				}
			}
		});

		// JPushInterface.setAliasAndTags(arg0, arg1, arg2, arg3);
	}

	public static void customJPushSetting(Context c) {

		// �������Ϳɽ���֪ͨ����
		JPushInterface.setLatestNotificationNumber(c, 5);

		// ����Ĭ��֪ͨ flagΪ0

		// BasicPushNotificationBuilder Notifybuilder = new
		// BasicPushNotificationBuilder(
		// this);
		// Notifybuilder.statusBarDrawable = R.drawable.androidlogo;
		// JPushInterface.setDefaultPushNotificationBuilder(Notifybuilder);

		// ����֪ͨ��ʽ
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
				c, R.layout.push_layout, R.id.iv, R.id.title, R.id.msg);

		builder.notificationDefaults = Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;

		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // ���Զ���ʧ
		// JPushInterface.setDefaultPushNotificationBuilder(1, builder);
		JPushInterface.setPushNotificationBuilder(1, builder);// ��ʽһ

	}
}
