package com.automate.app;

import java.util.HashMap;

import com.automate.app.route.IRouteController;
import com.automate.app.route.IRouteDelegate;
import com.automate.app.route.Route;
import com.automate.app.route.RouteController;
import com.automate.app.route.RouteDelegateException;
import com.automate.app.route.Test1RouteDelegate;
import com.automate.app.route.TestRouteController;
import com.automate.util.LoggingTags;

import android.app.Application;
import android.util.Log;


public class AutoMATEApplication extends Application implements IApplication {

	public HashMap<RouteController, IRouteController> routeControllers;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initialize();
	}

	@Override
	public void initialize() {
		routeControllers = new HashMap<RouteController, IRouteController>();
		routeControllers.put(RouteController.TEST, new TestRouteController());
	}

	@Override
	public void handleRoute(Route<?> route) {
		if(route == null) {
			Log.e(LoggingTags.APPLICATION_ROUTING, "Route was null in handleRoute");
			return;
		}
		RouteController controllerKey = route.controller;
		if(controllerKey == null) {
			Log.e(LoggingTags.APPLICATION_ROUTING, "route.controller was null in handleRoute");
			return;
		}
		IRouteController controller = routeControllers.get(controllerKey);
		if(controller == null) {
			Log.e(LoggingTags.APPLICATION_ROUTING, "routeControllers.get returned null in handleRoute");
			return;
		}
		IRouteDelegate delegate = controller.handleAction(route.action, route.getArgs());
		
		if(delegate != null) {
			try {
				delegate.start(this);
			} catch (RouteDelegateException e) {
				Log.e(LoggingTags.APPLICATION_ROUTING, "Error running route delegate in handleRoute.", e);
			}
		}
	}

	
}
