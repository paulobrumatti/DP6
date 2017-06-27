package br.com.dp6.datascience;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

public class GTMHelper {
    public static FirebaseAnalytics firebaseAnalytics;

    public static void initiateFirebase(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public static void pushScreenview(Activity activity, String screenName) {
        Bundle bundle = new Bundle();
        bundle.putString("screenName", screenName);
        firebaseAnalytics.logEvent("ScreenView", bundle);
        firebaseAnalytics.setCurrentScreen(activity, screenName, screenName);

    }

    public static void pushEvent(String category, String action, String label) {
        pushEvent(category, action, label, 0, false);
    }

    public static void pushEvent(String category, String action, String label, int value) {
        pushEvent(category, action, label, value, false);
    }

    public static void pushEvent(String category, String action, String label, int value, boolean noInteraction) {
        Bundle bundle = new Bundle();
        bundle.putString("eventCategory", category);
        bundle.putString("eventAction", action);
        bundle.putString("eventLabel", label);
        bundle.putString("eventValue", String.valueOf(value));
        bundle.putString("eventNoInteraction", noInteraction ? "true" : "false");
        firebaseAnalytics.logEvent("Interaction", bundle);
    }
}

