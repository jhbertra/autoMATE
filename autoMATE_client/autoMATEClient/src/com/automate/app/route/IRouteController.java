package com.automate.app.route;

public interface IRouteController {
	
	IRouteDelegate handleAction(Action action, Object args);
	
}
