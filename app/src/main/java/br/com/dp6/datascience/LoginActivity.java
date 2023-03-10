package br.com.dp6.datascience;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
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


        GTMHelper.pushScreenview(this, SCREEN_NAME);

        Bundle itemJeggings = new Bundle();
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_NAME, "jeggings");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "pants");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "black");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google");
        itemJeggings.putDouble(FirebaseAnalytics.Param.PRICE, 9.99);

        Bundle itemJeggingsCart = new Bundle(itemJeggings);
        itemJeggingsCart.putLong(FirebaseAnalytics.Param.QUANTITY, 2);

        Bundle purchaseParams = new Bundle();
        purchaseParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "T12345");
        purchaseParams.putString(FirebaseAnalytics.Param.AFFILIATION, "Google Store");
        purchaseParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
        purchaseParams.putDouble(FirebaseAnalytics.Param.VALUE, 14.98);
        purchaseParams.putDouble(FirebaseAnalytics.Param.TAX, 2.58);
        purchaseParams.putDouble(FirebaseAnalytics.Param.SHIPPING, 5.34);
        purchaseParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");
        ArrayList items = new ArrayList();
        items.add(itemJeggingsCart);
        purchaseParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, items);
        GTMHelper.logEvent(FirebaseAnalytics.Event.PURCHASE, purchaseParams);

        mLoginView = (EditText) findViewById(R.id.login);
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

