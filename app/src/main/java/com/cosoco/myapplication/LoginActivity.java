package com.cosoco.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private TextView googleLoginTitle;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mFirebaseAuth;
    private SignInButton mBtnGoogleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mBtnGoogleLogin = findViewById(R.id.btn_google_login);

        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.default_web_client_id)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        googleLoginTitle = (TextView) mBtnGoogleLogin.getChildAt(0);
        googleLoginTitle.setText((getString(R.string.google_sign_in)));
        googleLoginTitle.setTextSize(Dimension.SP,20);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser == null) {
            mBtnGoogleLogin.setOnClickListener(view -> {
                signIn();
            });
        }else{
            updateUI(currentUser);
        }
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startForResult.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> startForResult
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == Activity.RESULT_OK){
            Intent data = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if(account != null){
                    firebaseAuthWithGoogle(account.getIdToken(), account);
                }
            }catch (ApiException e){
                Log.e(TAG,"구글 signedIn 안됨");
            }
        }else if(result.getResultCode() == RESULT_CANCELED){

        }
    });

    private void firebaseAuthWithGoogle(String idToken, GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG,"signInWithCredential:success");
                        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
                        updateUI(currentUser);
                    }else{
                        Log.d(TAG,"로그인 실패");
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}