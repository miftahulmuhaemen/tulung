package com.unlam.developerstudentclub.tulung;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.mainAlert)
    FloatingActionButton mainAlert;
    @BindView(R.id.barStatus)
    View barStatus;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.information_text)
    TextView information_text;
    @BindView(R.id.progressMain)
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();
                        if (id == R.id.my_move) {

                        } else if (id == R.id.his_move){

                        }
                        drawer.closeDrawers();
                        return true;
                    }
                });

        PushDownAnim.setPushDownAnimTo(mainAlert)
        .setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View view ){
                Toast.makeText( MainActivity.this, "PUSH DOWN !!", Toast.LENGTH_SHORT ).show();
            }

        } );

    }


}
