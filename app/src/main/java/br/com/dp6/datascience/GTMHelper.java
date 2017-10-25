package br.com.dp6.datascience;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

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

    public static void getDynamicLink(Intent intent, Activity activity) {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(activity, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }
}

