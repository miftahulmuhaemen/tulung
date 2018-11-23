package com.unlam.developerstudentclub.tulung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.rd.PageIndicatorView;
import com.unlam.developerstudentclub.tulung.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.tulung.Fragment.RegisterFragment;
import com.unlam.developerstudentclub.tulung.Utils.LockableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_THIRD;

public class RegisterActivity extends AppCompatActivity {

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

//                if(currentPage == totalPage - 2){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setTitle(R.string.kirim_data)
//                            .setMessage(R.string.dialog_register)
//                            .setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    implicitlyListenerComposite.onRegisterActivityResponse(true);
//                                }
//                            })
//                            .setNegativeButton(R.string.Tidak, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // User cancelled the dialog
//                                }
//                            });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }
//                else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    fab_left.setVisibility(View.VISIBLE);
//                }

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

        bundle = new Bundle();
        mFragment = new RegisterFragment();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_SECOND);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part2");

        bundle = new Bundle();
        mFragment = new RegisterFragment();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_THIRD);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part3");

        bundle = new Bundle();
        mFragment = new RegisterFragment();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FORTH);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part4");

        viewPager.setAdapter(adapter);
    }

}
