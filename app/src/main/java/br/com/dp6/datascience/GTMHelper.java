package br.com.dp6.datascience;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

class GTMHelper {

    private static FirebaseAnalytics firebaseAnalytics;

    private static String screenName = "(entrance)";
    private static String previousScreenName = "(entrance)";
    private static final String EVENT_NAME_SCREENVIEW = "ScreenView";
    private static final String EVENT_NAME_INTERACTION = "Interaction";

    static void initiateFirebase(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public static void logEvent(String eventName, Bundle bundle) {
        bundle.putString("previousScreenName", previousScreenName);
        bundle.putString("screenName", screenName);
        firebaseAnalytics.logEvent(eventName, bundle);
    }

    public static void pushScreenview(Activity activity, String currentScreenName) {
        pushScreenview(activity, EVENT_NAME_SCREENVIEW, currentScreenName);
    }

    public static void pushScreenview(Activity activity, String currentScreenName, Bundle bundle) {
        pushScreenview(activity, EVENT_NAME_SCREENVIEW, currentScreenName, bundle);
    }

    public static void pushScreenview(Activity activity,  String eventName, String currentScreenName) {
        pushScreenview(activity, eventName, currentScreenName, new Bundle());
    }

    public static void pushScreenview(Activity activity,  String eventName, String currentScreenName, Bundle bundle) {
        previousScreenName = screenName;
        screenName = currentScreenName;
        Log.d("pushScreenview", eventName);
        logEvent(eventName, bundle);
        Log.d("pushScreenview", screenName);
        firebaseAnalytics.setCurrentScreen(null, screenName, screenName);
    }

    public static void pushEvent(String category, String action, String label) {
        pushEvent(EVENT_NAME_INTERACTION, category, action, label, new Bundle());
    }

    public static void pushEvent(String category, String action, String label, Bundle bundle) {
        pushEvent(EVENT_NAME_INTERACTION, category, action, label, bundle);
    }

    public static void pushEvent(String eventName, String category, String action, String label) {
        pushEvent(eventName, category, action, label, new Bundle());
    }

    public static void pushEvent(String eventName, String category, String action, String label, Bundle bundle) {
        bundle.putString("eventCategory", category);
        bundle.putString("eventAction", action);
        bundle.putString("eventLabel", label);
        logEvent(eventName, bundle);
    }

    public static void setUserProperty(String propertyName, String propertyValue){
        firebaseAnalytics.setUserProperty(propertyName,propertyValue);
    }
}

