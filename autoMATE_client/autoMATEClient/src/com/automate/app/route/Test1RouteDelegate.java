package com.automate.app.route;

import android.content.Context;
import android.content.Intent;

import com.automate.app.IApplication;

public class Test1RouteDelegate implements IRouteDelegate {

	@Override
	public void start(IApplication application) throws RouteDelegateException {
		Intent intent = new Intent((Context) application, com.automate.test.Test1Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		((Context)application).startActivity(intent);
	}

}
