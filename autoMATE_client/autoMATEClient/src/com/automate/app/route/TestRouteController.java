package com.automate.app.route;

import com.automate.util.LoggingTags;

import android.util.Log;

public class TestRouteController implements IRouteController {

	@Override
	public IRouteDelegate handleAction(Action action, Object args) {
		if(action == Action.TEST1) {
			return new Test1RouteDelegate();
		} else {
			Log.e(LoggingTags.APPLICATION_ROUTING, this.getClass().getName() + " Cannot handle route action " + action.name());
		}
		return null;
	}

}
