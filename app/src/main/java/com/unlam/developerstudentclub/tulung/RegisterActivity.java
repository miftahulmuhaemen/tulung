package com.unlam.developerstudentclub.tulung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rd.PageIndicatorView;
import com.unlam.developerstudentclub.tulung.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.tulung.Entity.ItemProfile;
import com.unlam.developerstudentclub.tulung.Fragment.RegisterFragment;
import com.unlam.developerstudentclub.tulung.Utils.ImplicitlyListenerComposite;
import com.unlam.developerstudentclub.tulung.Utils.Implictly;
import com.unlam.developerstudentclub.tulung.Utils.LockableViewPager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.unlam.developerstudentclub.tulung.Utils.Util.ACCOUNT_FIELD_FIREBASE;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_THIRD;

public class RegisterActivity extends AppCompatActivity implements RegisterFragment.onCompleteResponse {

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.fab_left)
    FloatingActionButton fab_left;

    @BindView(R.id.fab_right)
    FloatingActionButton fab_right;

    @BindView(R.id.btn_done)
    Button btn_done;

    @BindView(R.id.btn_masuk)
    Button btn_masuk;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @BindView(R.id.progressbar_Register)
    ProgressBar progressbar;

    /* Validation of Each Global Fragment to the Upload Session */
    boolean FRAGMENT_firstSeal = false;
    boolean FRAGMENT_secondSeal = false;
    boolean FRAGMENT_thirdSeal = false;

    ItemProfile form = new ItemProfile();
    Implictly implictly; // Interface Composite GLOBAL_FRAGMENT
    ImplicitlyListenerComposite implicitlyListenerComposite = new ImplicitlyListenerComposite(); // Composite Listener
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupViewPager(viewPager);

        viewPager.setSwipeable(false);
        viewPager.setOffscreenPageLimit(4);
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fab_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 1)
                    fab_left.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        fab_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentPage = viewPager.getCurrentItem();
                int totalPage = viewPager.getAdapter().getCount();

                if(currentPage == totalPage - 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle(R.string.kirim_data)
                            .setMessage(R.string.dialog_register)
                            .setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    implicitlyListenerComposite.onRegisterActivityResponse(true);
                                }
                            })
                            .setNegativeButton(R.string.tidak, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    fab_left.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        RegisterFragment mFragment = new RegisterFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FIRST);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part1");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new RegisterFragment();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_SECOND);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part2");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new RegisterFragment();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_THIRD);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part3");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new RegisterFragment();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FORTH);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part4");

        viewPager.setAdapter(adapter);
    }

    void attachListenerOnFragment(RegisterFragment fragment){
        try {
            implictly = fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.toString()
                    + " Needs to implement the methods");
        }
    }

    @Override
    public void onCompleteFormResponse(ItemProfile data, int Fragment) {

        if(Fragment == FRAGMENT_REGISTER_FIRST){
            form.setNama(data.getNama());
            form.setTempat(data.getTempat());
            form.setTanggallahir(data.getTanggallahir());
            form.setBeratbadan(data.getBeratbadan());
            form.setGolongandarah(data.getGolongandarah());
            form.setAlamat(data.getAlamat());
            FRAGMENT_firstSeal = true;
        }

        if(Fragment == FRAGMENT_REGISTER_SECOND){
            form.setRiwayatpenyakit(data.getRiwayatpenyakit());
            form.setAlergimakanan(data.getAlergimakanan());
            form.setAlergiobat(data.getAlergiobat());
            form.setCatatandokter(data.getCatatandokter());
            FRAGMENT_secondSeal = true;
        }

        if(Fragment == FRAGMENT_REGISTER_THIRD){
            form.setEmail(data.getEmail());
            form.setPassword(data.getPassword());
            form.setKontakkerabat(data.getKontakkerabat());
            FRAGMENT_thirdSeal = true;
        }

        if(FRAGMENT_firstSeal && FRAGMENT_secondSeal && FRAGMENT_thirdSeal){

            dbRef.child(ACCOUNT_FIELD_FIREBASE).orderByChild("email").equalTo(form.getEmail())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChildren()) {
                                dbRef.child(ACCOUNT_FIELD_FIREBASE).push().setValue(form).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Ada masalah koneksi dengan pusat", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }

    @Override
    public void onErrorFieldResponses(int FragmentIdentifier) {
        if(FragmentIdentifier == FRAGMENT_REGISTER_THIRD){
            if(!FRAGMENT_thirdSeal)
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.toast_data_belum_benar), Toast.LENGTH_SHORT).show();
            else {
                if(!FRAGMENT_secondSeal)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                else {
                    if(!FRAGMENT_firstSeal)
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 2);
                }
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.toast_data_belum_benar), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
