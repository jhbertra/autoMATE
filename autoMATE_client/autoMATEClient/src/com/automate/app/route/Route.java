package com.automate.app.route;

/**
 * Class that contains informatioon for directing application routing.
 * @param <T> the type of the args, typically some kind of container class.
 */
public class Route <T> {

	/**
	 * The lookup key for the route controller instance.
	 */
	public final RouteController controller;
	
	/**
	 * The action to take.
	 */
	public final Action action;
	
	/**
	 * The args for the action.
	 */
	private T args;
	
	/**
	 * Creates a new Rotue
	 * @param controller - the controller to handle the route.
	 * @param action - the action for the route controller to handle.
	 * @param args - the args for the action.
	 */
	public Route(RouteController controller, Action action, T args) {
		this.controller = controller;
		this.action = action;
		this.args = args;
	}

	/**
	 * @return the args.
	 */
	public T getArgs() {
		return args;
	}
	
	/**
	 * @param args the args to set.
	 */
	private void setArgs(T args) {
		this.args = args;
	}
	
}
