package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
Intent intent;
EditText signname, signemail, signpassword, signusername;
TextView loginRedirectText;
    View user;
Button btnSignUp;
SignInButton googleBtn;
FirebaseDatabase database;
DatabaseReference reference;

    FirebaseAuth auth;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);



        googleBtn = findViewById(R.id.googleBtn);
        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(SignUp.this,gso);



        btnSignUp=findViewById(R.id.btnSignUp);
      signname= findViewById(R.id.signname);
      signemail= findViewById(R.id.signemail);
      signpassword = findViewById(R.id.signpassword);
      signusername= findViewById(R.id.signusername);
      loginRedirectText=findViewById(R.id.loginRedirectText);


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() && !validateEmail() && !validateUsername() && !validatePassword()){

                }else{

              reference= database.getReference("users");

              String Name = signname.getText().toString();
              String Email = signemail.getText().toString();
              String Password = signpassword.getText().toString();
              String UserName= signusername.getText().toString();

              User user= new User(Name,Email,Password,UserName);
              reference.child(Name).setValue(user);

              Toast.makeText(SignUp.this, "SignUp Successful!", Toast.LENGTH_SHORT).show();
              intent= new Intent(SignUp.this, Login.class);
              startActivity(intent);
              finish();
               }
            }

            private boolean validateName() {
                String val = signname.getText().toString();
                if (val.isEmpty()) {
                    signname.setError("Name is required");
                    return false;
                } else {
                    signname.setError(null);
                    return true;
                }
            }
            private boolean validateEmail() {
                String val = signemail.getText().toString();
                if (val.isEmpty()) {
                    signemail.setError("Email is required");
                    return false;
                } else {
                    signemail.setError(null);
                    return true;
                }
            }
            private boolean validateUsername() {
                String val = signusername.getText().toString();
                if (val.isEmpty()) {
                    signusername.setError("UserName is required");
                    return false;
                } else {
                    signusername.setError(null);
                    return true;
                }
            }
            private boolean validatePassword() {
                String val = signpassword.getText().toString();
                if (val.isEmpty()) {
                    signpassword.setError("Password is required");
                    return false;
                } else {
                    signpassword.setError(null);
                    return true;

                }
            }
        });


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              intent = new Intent(SignUp.this, Login.class);
              startActivity(intent);

          }
      });
    }


    public void signIn(){
        intent = gsc.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                auth(account.getIdToken());
            }catch (ApiException e) {
                Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void auth (String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user= auth.getCurrentUser();
                            HashMap<String,Object> map= new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());
                            database.getReference().child("users").child(user.getUid()).setValue(map);

                            intent = new Intent(SignUp.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}