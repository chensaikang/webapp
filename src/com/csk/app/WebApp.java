/*** <p>Title: WebApp</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author CSK
 * @date  2016��4��26������2:58:29
 */
package com.csk.app;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class WebApp extends Application {

	@Override
	public void onCreate() {

		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}
}
