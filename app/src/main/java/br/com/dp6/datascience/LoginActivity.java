package br.com.dp6.datascience;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String SCREEN_NAME = "Login";

    private UserLoginTask mAuthTask = null;

    // UI references.
    private TextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String loginOldValue;
    private String passwordOldValue;

    private final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GTMHelper.setUserProperty("user_pseudo_id", GTMHelper.getUserPseudoId());
        GTMHelper.pushScreenview(this, SCREEN_NAME);


        // Define promotion with relevant parameters
/*
        Bundle promotion = new Bundle();
        promotion.putString( FirebaseAnalytics.Param.ITEM_ID, "PROMO_1234" ); // promotion ID; either ITEM_ID or ITEM_NAME is required
        promotion.putString( FirebaseAnalytics.Param.ITEM_NAME, "Summer Sale" ); // promotion name
        promotion.putString( FirebaseAnalytics.Param.CREATIVE_NAME, "summer_banner2" );
        promotion.putString( FirebaseAnalytics.Param.CREATIVE_SLOT, "banner_slot1" );

// Prepare ecommerce bundle

        ArrayList <Bundle>promotions = new ArrayList<Bundle>();
        promotions.add(promotion);

        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putParcelableArrayList("promotions", promotions );

// Log view_item, view_item_list, or view_search_results event with ecommerce bundle

        GTMHelper.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, ecommerceBundle);
        GTMHelper.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, ecommerceBundle);
        GTMHelper.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, ecommerceBundle);
        */
        Bundle itemJeggings = new Bundle();
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_NAME, "jeggings");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "pants");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "black");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        itemJeggings.putDouble(FirebaseAnalytics.Param.PRICE, 9.99);
        itemJeggings.putString("dimension11", "cor");

        Bundle itemBoots = new Bundle();
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_456");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_NAME, "boots");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, null);
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "brown");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        itemBoots.putDouble(FirebaseAnalytics.Param.PRICE, 24.99);
        itemBoots.putString("dimension11", "cor");

        Bundle itemSocks = new Bundle();
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_789");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_NAME, "ankle_socks");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "socks");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "red");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        itemSocks.putDouble(FirebaseAnalytics.Param.PRICE, 5.99);
        Bundle itemJeggingsCart = new Bundle(itemJeggings);
        itemJeggingsCart.putLong(FirebaseAnalytics.Param.QUANTITY, 2);

        Bundle itemJeggingsWithIndex = new Bundle(itemJeggings);
        itemJeggingsWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 1);

        Bundle itemBootsWithIndex = new Bundle(itemBoots);
        itemBootsWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 2);

        Bundle itemSocksWithIndex = new Bundle(itemSocks);
        itemSocksWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 3);

/*
        Bundle viewItemListParams = new Bundle();
        viewItemListParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, "L001");
        viewItemListParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, "Related products");
        ArrayList<Bundle> itemsWithIndex = new ArrayList<>();
        itemsWithIndex.add(itemJeggingsWithIndex);
        itemsWithIndex.add(itemBootsWithIndex);
        itemsWithIndex.add(itemSocksWithIndex);
        viewItemListParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, itemsWithIndex);
        GTMHelper.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListParams);

        Bundle selectItemParams = new Bundle();
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, "L001");
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, "Related products");
        ArrayList<Bundle> selectItemsWithIndex = new ArrayList<>();
        selectItemsWithIndex.add(itemJeggingsWithIndex);
        selectItemParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, selectItemsWithIndex);
        GTMHelper.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, selectItemParams);

        Bundle viewItemParams = new Bundle();
        viewItemParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        viewItemParams.putDouble(FirebaseAnalytics.Param.VALUE, 9.99);
        ArrayList<Bundle> viewItemList = new ArrayList<>();
        viewItemList.add(itemJeggingsWithIndex);
        viewItemParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, viewItemList);
        GTMHelper.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewItemParams);
*/
        Bundle itemJeggingsWishlist = new Bundle(itemJeggings);
        itemJeggingsWishlist.putLong(FirebaseAnalytics.Param.QUANTITY, 2);

        Bundle addToWishlistParams = new Bundle();
        addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, 2 * 9.99);
        ArrayList<Bundle> addItemList = new ArrayList<>();
        addItemList.add(itemJeggingsWithIndex);
        addToWishlistParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,addItemList);
        GTMHelper.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);

        addToWishlistParams = new Bundle();
        addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, 2 * 9.99);
        addItemList = new ArrayList<>();
        itemBootsWithIndex = new Bundle(itemBoots);
        itemBootsWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 1);
        addItemList.add(itemBootsWithIndex);

        addToWishlistParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,addItemList);
        GTMHelper.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);

/*
        Bundle beginCheckoutParams = new Bundle();
        beginCheckoutParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        beginCheckoutParams.putDouble(FirebaseAnalytics.Param.VALUE, 14.98);
        beginCheckoutParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");
        ArrayList<Bundle> checkoutItemList = new ArrayList<>();
        checkoutItemList.add(itemJeggingsWithIndex);
        beginCheckoutParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, checkoutItemList);
        GTMHelper.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, beginCheckoutParams);

        ArrayList<Bundle> items = new ArrayList<>();
        items.add(itemJeggingsCart);

        Bundle purchaseParams = new Bundle();
        purchaseParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "T12345");
        purchaseParams.putString(FirebaseAnalytics.Param.AFFILIATION, "Google Store");
        purchaseParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        purchaseParams.putDouble(FirebaseAnalytics.Param.VALUE, 14.98);
        purchaseParams.putDouble(FirebaseAnalytics.Param.TAX, 2.58);
        purchaseParams.putDouble(FirebaseAnalytics.Param.SHIPPING, 5.34);
        purchaseParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");
        purchaseParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, items);
        GTMHelper.logEvent(FirebaseAnalytics.Event.PURCHASE, purchaseParams);

        // Define product with relevant parameters

        Bundle product1 = new Bundle();
        product1.putString( FirebaseAnalytics.Param.ITEM_ID, "sku1234"); // ITEM_ID or ITEM_NAME is required
        product1.putString( FirebaseAnalytics.Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
        product1.putString( FirebaseAnalytics.Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
        product1.putString( FirebaseAnalytics.Param.ITEM_VARIANT, "Blue");
        product1.putString( FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        product1.putDouble( FirebaseAnalytics.Param.PRICE, 29.99 );
        product1.putString( FirebaseAnalytics.Param.CURRENCY, "USD" ); // Item-level currency unused today
        product1.putLong( FirebaseAnalytics.Param.INDEX, 1 ); // Position of the item in the list

// Prepare ecommerce bundle

        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putBundle( "items", product1 );

// Log select_content event with ecommerce bundle

        GTMHelper.logEvent( FirebaseAnalytics.Event.SELECT_CONTENT, ecommerceBundle );

        // Define product with relevant parameters

        Bundle product1Purchase = new Bundle();
        product1Purchase.putString( FirebaseAnalytics.Param.ITEM_ID, "sku1234"); // ITEM_ID or ITEM_NAME is required
        product1Purchase.putString( FirebaseAnalytics.Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
        product1Purchase.putString( FirebaseAnalytics.Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
        product1Purchase.putString( FirebaseAnalytics.Param.ITEM_VARIANT, "Blue");
        product1Purchase.putString( FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        product1Purchase.putDouble( FirebaseAnalytics.Param.PRICE, 29.99 );
        product1Purchase.putString( FirebaseAnalytics.Param.CURRENCY, "USD" ); // Item-level currency unused today
        product1Purchase.putLong( FirebaseAnalytics.Param.QUANTITY, 1 );

        Bundle product2Purchase = new Bundle();
        product2Purchase.putString( FirebaseAnalytics.Param.ITEM_ID, "sku5678");
        product2Purchase.putString( FirebaseAnalytics.Param.ITEM_NAME, "Android Workout Capris");
        product2Purchase.putString( FirebaseAnalytics.Param.ITEM_CATEGORY, "Apparel/Women/Pants");
        product2Purchase.putString( FirebaseAnalytics.Param.ITEM_VARIANT, "Black");
        product2Purchase.putString( FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        product2Purchase.putDouble( FirebaseAnalytics.Param.PRICE, 39.99 );
        product2Purchase.putString( FirebaseAnalytics.Param.CURRENCY, "USD" ); // Item-level currency unused today
        product2Purchase.putLong( FirebaseAnalytics.Param.QUANTITY, 1 );

// Prepare ecommerce bundle

        ArrayList<Bundle> itemsPurchase = new ArrayList<>();
        itemsPurchase.add(product1Purchase);
        itemsPurchase.add(product2Purchase);

        Bundle ecommerceBundlePurchase = new Bundle();
        ecommerceBundlePurchase.putParcelableArrayList( "items", itemsPurchase );

// Set relevant transaction-level parameters

        ecommerceBundlePurchase.putString( FirebaseAnalytics.Param.TRANSACTION_ID, "T12345" );
        ecommerceBundlePurchase.putString( FirebaseAnalytics.Param.AFFILIATION, "Google Store - Online" );
        ecommerceBundlePurchase.putDouble( FirebaseAnalytics.Param.VALUE, 37.39 );    // Revenue
        ecommerceBundlePurchase.putDouble( FirebaseAnalytics.Param.TAX, 2.85 );
        ecommerceBundlePurchase.putDouble( FirebaseAnalytics.Param.SHIPPING, 5.34 );
        ecommerceBundlePurchase.putString( FirebaseAnalytics.Param.CURRENCY, "USD" );
        ecommerceBundlePurchase.putString( FirebaseAnalytics.Param.COUPON, "SUMMER2017" );

// Log ecommerce_purchase event with ecommerce bundle
        // nao existe mais o static  FirebaseAnalytics.Event.ECOMMERCE_PURCHASE
        GTMHelper.logEvent("ecommerce_purchase", ecommerceBundle );

        // Define promotion with relevant parameters

        Bundle promotion = new Bundle();
        promotion.putString( FirebaseAnalytics.Param.ITEM_ID, "PROMO_1234" ); // promotion ID; either ITEM_ID or ITEM_NAME is required
        promotion.putString( FirebaseAnalytics.Param.ITEM_NAME, "Summer Sale" ); // promotion name
        promotion.putString( FirebaseAnalytics.Param.CREATIVE_NAME, "summer_banner2" );
        promotion.putString( FirebaseAnalytics.Param.CREATIVE_SLOT, "banner_slot1" );

// Prepare ecommerce bundle

        ArrayList <Bundle>promotions = new ArrayList<Bundle>();
        promotions.add(promotion);

        Bundle ecommerceBundlePromotion = new Bundle();
        ecommerceBundlePromotion.putParcelableArrayList("promotions", promotions );

// Log view_item, view_item_list, or view_search_results event with ecommerce bundle

        GTMHelper.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, ecommerceBundlePromotion );

        Bundle promotionClique = new Bundle();
        promotionClique.putString( FirebaseAnalytics.Param.ITEM_ID, "PROMO_1234"); // promotion ID; either ITEM_ID or ITEM_NAME is required
        promotionClique.putString( FirebaseAnalytics.Param.ITEM_NAME, "Summer Sale"); // promotion name
        promotionClique.putString( FirebaseAnalytics.Param.CREATIVE_NAME, "summer_banner2");
        promotionClique.putString( FirebaseAnalytics.Param.CREATIVE_SLOT, "banner_slot1");

// Prepare ecommerce bundle

        ArrayList <Bundle>promotionsCliques = new ArrayList<Bundle>();
        promotionsCliques.add(promotionClique);

        Bundle ecommerceBundleCliques = new Bundle();
        ecommerceBundleCliques.putParcelableArrayList("promotions", promotionsCliques );

// Set properties for the event to be shown in the Google Analytics (Firebase) reports.
// These properties will not impact the Universal Analytics reporting.

        ecommerceBundleCliques.putString( FirebaseAnalytics.Param.CONTENT_TYPE, "Internal Promotions" );
        ecommerceBundleCliques.putString( FirebaseAnalytics.Param.ITEM_ID, "PROMO_1234" );

// Log select_content, view_item_list, or view_search_results event with ecommerce bundle

        GTMHelper.logEvent( FirebaseAnalytics.Event.SELECT_CONTENT, ecommerceBundleCliques );

*/
        mLoginView = findViewById(R.id.login);
        mPasswordView = findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        remoteConfig.setConfigSettingsAsync(new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(1).build());

        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("cor_botao_login", "#FFFFFF");
        defaults.put("texto_botao_login", "entrar");
        defaults.put("testeab_cartao_vitrine", "esconder");
        remoteConfig.setDefaultsAsync(defaults);

        remoteConfig
                .fetchAndActivate()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        boolean updated = task.getResult();
                        Log.d("RemoteConfig", "Config params updated: " + updated);
                        Toast.makeText(LoginActivity.this, "Fetch and activate succeeded: " + updated,
                                Toast.LENGTH_SHORT).show();

                        String cor_botao = remoteConfig.getString("cor_botao_login");
                        mEmailSignInButton.setBackgroundColor(Color.parseColor(cor_botao));
                        mEmailSignInButton.setText(remoteConfig.getString("texto_botao_login"));
                        GTMHelper.pushEvent("teste_ab", "teste_cor_botao", cor_botao);

                        /*
                        String testeab_cartao_vitrine = remoteConfig.getString("testeab_cartao_vitrine")
                        if (testeab_cartao_vitrine.equals("exibir")) {
                            // fazer o cartão aparecer
                        } else {
                            // fazer o cartão sumir
                        }
                        */
                    } else {
                        Toast.makeText(LoginActivity.this, "Fetch failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        mLoginView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
                String currentValue = mLoginView.getText().toString();
                if (!currentValue.equals(loginOldValue)) {
                    loginOldValue = currentValue;
                    GTMHelper.pushEvent("Login", "Change", "Login Field");
                }
            }
        });

        mPasswordView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
                String currentValue = mPasswordView.getText().toString();
                if (!currentValue.equals(passwordOldValue)) {
                    passwordOldValue = currentValue;
                    GTMHelper.pushEvent("Login", "Change", "Password Field");
                }
            }
        });



        mEmailSignInButton.setOnClickListener(view -> {
            GTMHelper.pushEvent("Login", "Click", "Sign In");
            Log.i("GTM", "Evento");
            attemptLogin();
        });

        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
    }

    private void changeScreen() {
        Intent i = new Intent(this, ContentActivity.class);
        startActivity(i);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }

            Bundle bundle = new Bundle();
            bundle.putString("noInteraction", "true");

            boolean success = mEmail.equals("1234") && mPassword.equals("1234");
            GTMHelper.pushEvent("Login", success ? "Success" : "Fail", "Login Attempt", bundle);
            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                changeScreen();
            } else {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

