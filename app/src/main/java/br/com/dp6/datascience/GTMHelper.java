package br.com.dp6.datascience;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.logging.Level;

class GTMHelper {

    private static FirebaseAnalytics firebaseAnalytics;

    private static String screenName = "(entrance)";
    private static String previousScreenName = "(entrance)";
    private static final String EVENT_NAME_INTERACTION = "interaction";
    private static String user_pseudo_id = "";
    private static String session_id = "";

    static void initiateFirebase(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
        firebaseAnalytics.getAppInstanceId().addOnSuccessListener(instanceId -> user_pseudo_id = instanceId);
        firebaseAnalytics.getAppInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user_pseudo_id = task.getResult();
            } else {
                Log.e("user_pseudo_id", "deu ruim", task.getException());
            }
        });
        firebaseAnalytics.getSessionId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                session_id = task.getResult().toString();
            } else {
                Log.e("user_pseudo_id", "deu ruim", task.getException());
            }
        });
    }

    public static void logEvent(String eventName, Bundle bundle) {
        bundle.putString("previous_screen_name", previousScreenName);
        bundle.putString("screen_name", screenName);
        bundle.putString("session_id", session_id);
        firebaseAnalytics.logEvent(eventName, bundle);
    }

    public static void pushScreenview(Activity activity, String currentScreenName) {
        pushScreenview(activity, FirebaseAnalytics.Event.SCREEN_VIEW, currentScreenName);
    }

    public static void pushScreenview(Activity activity, String currentScreenName, Bundle bundle) {
        pushScreenview(activity, FirebaseAnalytics.Event.SCREEN_VIEW, currentScreenName, bundle);
    }

    public static void pushScreenview(Activity activity,  String eventName, String currentScreenName) {
        pushScreenview(activity, eventName, currentScreenName, new Bundle());
    }

    public static void pushScreenview(Activity activity,  String eventName, String currentScreenName, Bundle bundle) {
        previousScreenName = screenName;
        screenName = currentScreenName;
        Log.d("pushScreenview", eventName);
        logEvent(eventName, bundle);
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
        bundle.putString("gau_event_category", category);
        bundle.putString("gau_event_action", action);
        bundle.putString("gau_event_label", label);
        logEvent(eventName, bundle);
    }

    public static void setUserProperty(String propertyName, String propertyValue){
        firebaseAnalytics.setUserProperty(propertyName,propertyValue);
    }
    public static void setUserId(String userId) {
        firebaseAnalytics.setUserId(userId);
    }

    public static String getUserPseudoId() {
        return user_pseudo_id;
    }

    public static String getSessionId() {
        return session_id;
    }
}

