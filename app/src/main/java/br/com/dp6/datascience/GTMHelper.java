package br.com.dp6.datascience;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;

class GTMHelper {
    private static FirebaseAnalytics firebaseAnalytics;

    static void initiateFirebase(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    static void pushScreenview(Activity activity, String screenName) {
        Bundle bundle = new Bundle();
        bundle.putString("screenName", screenName);
        firebaseAnalytics.logEvent("ScreenView", bundle);
        firebaseAnalytics.setCurrentScreen(activity, screenName, screenName);

    }

    static void pushEvent(String category, String action, String label) {
        pushEvent(category, action, label, 0, false);
    }

    static void pushEvent(String category, String action, String label, int value) {
        pushEvent(category, action, label, value, false);
    }

    static void pushEvent(String category, String action, String label, int value, boolean noInteraction) {
        Bundle bundle = new Bundle();
        bundle.putString("eventCategory", category);
        bundle.putString("eventAction", action);
        bundle.putString("eventLabel", label);
        bundle.putString("eventValue", String.valueOf(value));
        bundle.putString("eventNoInteraction", noInteraction ? "true" : "false");
        firebaseAnalytics.logEvent("Interaction", bundle);
    }

    static void pushEcommerce() {
        Bundle product1 = new Bundle();
        product1.putString(Param.ITEM_ID, "sku1234"); // ITEM_ID or ITEM_NAME is required
        product1.putString(Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
        product1.putString(Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
        product1.putString(Param.ITEM_VARIANT, "Blue");
        product1.putString(Param.ITEM_BRAND, "Google");
        product1.putDouble(Param.PRICE, 29.99);
        product1.putString(Param.CURRENCY, "USD"); // Item-level currency unused today
        product1.putString("dimension1", "USD"); // Item-level currency unused today
        product1.putDouble("metric1", 1.0); // Item-level currency unused today
        product1.putLong(Param.QUANTITY, 1);

        ArrayList items = new ArrayList();
        items.add(product1);

        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putParcelableArrayList("items", items);

        ecommerceBundle.putString(Param.TRANSACTION_ID, "T12345");
        ecommerceBundle.putString(Param.AFFILIATION, "Google Store - Online");
        ecommerceBundle.putDouble(Param.VALUE, 37.39); // Revenue
        ecommerceBundle.putDouble(Param.TAX, 2.85);
        ecommerceBundle.putDouble(Param.SHIPPING, 5.34);
        ecommerceBundle.putString(Param.CURRENCY, "USD");
        ecommerceBundle.putString(Param.COUPON, "SUMMER2017");

        firebaseAnalytics.logEvent(Event.ECOMMERCE_PURCHASE, ecommerceBundle);
    }
}

