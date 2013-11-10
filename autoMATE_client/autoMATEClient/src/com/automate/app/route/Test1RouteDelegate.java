package com.automate.app.route;

import android.content.Context;
import android.content.Intent;

import com.automate.Test1Activity;
import com.automate.app.IApplication;

public class Test1RouteDelegate implements IRouteDelegate {

	@Override
	public void start(IApplication application) throws RouteDelegateException {
		Intent intent = new Intent(Test1Activity.class.getName());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		((Context)application).startActivity(intent);
	}

}
