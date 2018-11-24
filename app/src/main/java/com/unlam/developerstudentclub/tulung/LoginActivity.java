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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_daftar) Button btn_daftar;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.ti_email)  TextFieldBoxes ti_email;
    @BindView(R.id.ti_password) TextFieldBoxes ti_password;
    @BindView(R.id.edt_email) ExtendedEditText edt_email;
    @BindView(R.id.edt_password) ExtendedEditText edt_password;
    @BindView(R.id.progressbarLogin)  ProgressBar progressBar;


    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.child(ACCOUNT_FIELD_FIREBASE).orderByChild("email").equalTo(edt_email.getText().toString())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChildren()) {
                                    ItemProfile item = new ItemProfile();
                                    for (DataSnapshot adSnapshot : dataSnapshot.getChildren())
                                        item = adSnapshot.getValue(ItemProfile.class);
                                    if (!TextUtils.isEmpty(edt_password.getText().toString()))
                                        if (item.getPassword().equals(edt_password.getText().toString())) {
                                            Log.d("KONTOL", "FAAAAAAK");
                                        }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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
