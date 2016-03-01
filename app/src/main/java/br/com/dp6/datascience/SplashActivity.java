package br.com.dp6.datascience;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GTMHelper.initiateTagManager(this, new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                containerHolder.setContainerAvailableListener(new ContainerHolder.ContainerAvailableListener() {
                    @Override
                    public void onContainerAvailable(ContainerHolder containerHolder, String s) {
                        Log.i("TestDP6", s);
                    }
                });
                if (containerHolder.getStatus().isSuccess()) {
                    Log.i("TestDP6", "success loading container: " +
                            String.valueOf(containerHolder.getContainer().getLastRefreshTime()));
                } else {
                    Log.e("TestDP6", "failure loading container");
                }
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        finish();
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
