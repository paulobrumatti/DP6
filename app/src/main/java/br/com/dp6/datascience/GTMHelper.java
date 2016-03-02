package br.com.dp6.datascience;

import android.content.Context;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

public class GTMHelper {
    public static final String CONTAINER_ID = "GTM-T97SDK";
    public static TagManager tagManager;
    public static DataLayer dataLayer;

    public static void initiateTagManager(Context context, ResultCallback<ContainerHolder> resultCallback) {
        tagManager = TagManager.getInstance(context);

        // Modify the log level of the logger to print out not only
        // warning and error messages, but also verbose, debug, info messages.
        tagManager.setVerboseLoggingEnabled(true);
        dataLayer = tagManager.getDataLayer();

        PendingResult<ContainerHolder> pending =
                tagManager.loadContainerPreferFresh(CONTAINER_ID,
                        R.raw.gtm_holder);

        // The onResult method will be called as soon as one of the following happens:
        //     1. a saved container is loaded
        //     2. if there is no saved container, a network container is loaded
        //     3. the request times out. The example below uses a constant to manage the timeout period.
        pending.setResultCallback(resultCallback, 2, TimeUnit.SECONDS);
    }

    public static void push(String... data) {
        dataLayer.push(DataLayer.mapOf(data));
    }

    public static void pushScreenview(String screenName) {
        dataLayer.pushEvent("openScreen", DataLayer.mapOf(
                "screenName", screenName
        ));
    }

    public static void pushEvent(String category, String action, String label) {
        dataLayer.pushEvent("eventGA", DataLayer.mapOf(
                "gaCategory", category,
                "gaAction", action,
                "gaLabel", label
        ));
    }

    public static void pushEvent(String category, String action, String label, int value) {
        dataLayer.pushEvent("eventGA", DataLayer.mapOf(
                "gaCategory", category,
                "gaAction", action,
                "gaLabel", label,
                "gaValue", String.valueOf(value)
        ));
    }

    public static void pushEvent(String category, String action, String label, int value, boolean noInteraction) {
        dataLayer.pushEvent("eventGA", DataLayer.mapOf(
                "gaCategory", category,
                "gaAction", action,
                "gaLabel", label,
                "gaValue", String.valueOf(value),
                "gaValue", noInteraction ? "true" : "false"
        ));
    }
}

