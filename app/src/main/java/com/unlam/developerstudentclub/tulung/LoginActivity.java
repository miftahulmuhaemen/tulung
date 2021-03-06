package com.unlam.developerstudentclub.tulung;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unlam.developerstudentclub.tulung.Entity.ItemProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.tulung.Utils.Util.ACCOUNT_FIELD_FIREBASE;
import static com.unlam.developerstudentclub.tulung.Utils.Util.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_daftar) Button btn_daftar;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.ti_email)  TextFieldBoxes ti_email;
    @BindView(R.id.ti_password) TextFieldBoxes ti_password;
    @BindView(R.id.edt_email) ExtendedEditText edt_email;
    @BindView(R.id.edt_password) ExtendedEditText edt_password;
    @BindView(R.id.progressbarLogin)  ProgressBar progressBar;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String email = edt_email.getText().toString(),
                       password = edt_password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    dbRef.child(ACCOUNT_FIELD_FIREBASE).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.hasChildren()){
                                                ItemProfile item = dataSnapshot.getValue(ItemProfile.class);
//                                                Log.d("BROKE", item.getAlergimakanan() + "");
                                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                progressBar.setVisibility(View.GONE);
                                                getBaseContext().startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"E-mail atau password anda salah.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
