package com.automate;

import com.automate.app.AutoMATEApplication;
import com.automate.app.route.Action;
import com.automate.app.route.Route;
import com.automate.app.route.RouteController;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AutoMATEApplication)getApplication()).handleRoute(new Route<Void>(RouteController.TEST, Action.TEST1, null));
        finish();
    }
    
}
