package com.unlam.developerstudentclub.tulung;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.unlam.developerstudentclub.tulung.Entity.ItemAlert;
import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import static com.unlam.developerstudentclub.tulung.Utils.Util.PERMISSIONS_REQUEST;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.btn_grantPermission)
    Button btn_grantPermission;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ItemAlert itemAlert = new ItemAlert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        PushDownAnim.setPushDownAnimTo(mainAlert)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            mainAlert.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                            status.setText(getResources().getString(R.string.menunggu));
                            information_text.setText(getResources().getString(R.string.dialog_menunggu));
                            barStatus.setBackground(getResources().getDrawable(R.color.red));
                            toolbar.setBackground(getResources().getDrawable(R.color.red));
                            mainAlert.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);

                            alertMessage();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setItems(R.array.AlertMenu, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i){
                                        case 0 :
                                            itemAlert.setJenis(getResources().getString(R.string.tersesat));
                                            alertMessage();
                                            onAskingTotalPeople();
                                            break;
                                        case 1 :
                                            onDetailAlertMenu(R.array.Kecelakaan);
                                            break;
                                        case 2 :
                                            onDetailAlertMenu(R.array.SeranganBinatang);
                                            break;
                                        case 3 :
                                            onDetailAlertMenu(R.array.Bencana);
                                            break;
                                        case 4 :
                                            itemAlert.setJenis(getResources().getString(R.string.kesurupan));
                                            alertMessage();
                                            onAskingTotalPeople();
                                            break;
                                        case 5 :
                                            final AlertDialog.Builder anyBuilder = new AlertDialog.Builder(MainActivity.this);
                                            final View view1 = View.inflate(MainActivity.this, R.layout.alertminibox, null);
                                            anyBuilder.setView(view1)
                                                    .setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            ExtendedEditText edt_minibox = view1.findViewById(R.id.edt_miniBox);
                                                            itemAlert.setJenis(edt_minibox.getText().toString().trim());
                                                            alertMessage();
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.tidak, null).create().show();
                                            break;
                                    }
                                }
                            }).create().show();
                        }
                    })
                    .setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {

                            mainAlert.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                            barStatus.setBackground(getResources().getDrawable(R.color.colorPrimary));
                            mainAlert.setImageResource(R.drawable.ic_wifi_tethering_black_24dp);
                            toolbar.setBackground(getResources().getDrawable(R.color.colorPrimary));

                            itemAlert = new ItemAlert();
                            itemAlert.setAktif(0);

                            ref.child("Alert").child(user.getUid()).setValue(itemAlert).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mainAlert.setEnabled(true);
                                        information_text.setText(getResources().getString(R.string.dialog_normal));
                                        status.setText(getResources().getString(R.string.normal));
                                        status.setVisibility(View.VISIBLE);
                                        btn_grantPermission.setVisibility(View.INVISIBLE);
                                    } else {
                                        Toast.makeText(MainActivity.this, "You lost the Signal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            return true;
                        }
                    });

        checkNecessaryPermission();
        btn_grantPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNecessaryPermission();
            }
        });
    }

    public void onAskingTotalPeople(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.nanyaOrang)
               .setItems(R.array.orang, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0 :
                        itemAlert.setOrang(1);
                        break;
                    case 1 :
                        itemAlert.setOrang(2);
                        break;
                    case 2 :
                        itemAlert.setOrang(3);
                        break;
                    case 3 :
                        itemAlert.setOrang(4);
                        break;
                    case 4 :
                        break;
                }
                alertMessage();
            }
        }).create().show();
    }

    public void onDetailAlertMenu(final int Menu) {
        AlertDialog.Builder second_builder = new AlertDialog.Builder(MainActivity.this);
        second_builder.setItems(Menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (Menu) {
                    case R.array.Kecelakaan :
                        switch (i){
                            case 0 :
                                itemAlert.setJenis(getResources().getString(R.string.LukaKecil));
                                break;
                            case 1 :
                                itemAlert.setJenis(getResources().getString(R.string.LukaBerat));
                                break;
                            case 2 :
                                itemAlert.setJenis(getResources().getString(R.string.PatahTulang));
                                break;
                            case 3 :
                                itemAlert.setJenis(getResources().getString(R.string.Terkilir));
                                break;
                        }
                        break;
                    case R.array.SeranganBinatang :
                        switch (i){
                            case 0 :
                                itemAlert.setJenis(getResources().getString(R.string.HewanBesar));
                                break;
                            case 1 :
                                itemAlert.setJenis(getResources().getString(R.string.Reptil));
                                break;
                            case 2 :
                                itemAlert.setJenis(getResources().getString(R.string.Serangga));
                                break;
                        }
                        break;
                    case R.array.Bencana :
                        switch (i){
                            case 0 :
                                itemAlert.setJenis(getResources().getString(R.string.Banjir));
                                break;
                            case 1 :
                                itemAlert.setJenis(getResources().getString(R.string.TanahLongsor));
                                break;
                            case 2 :
                                itemAlert.setJenis(getResources().getString(R.string.KebakaranHutan));
                                break;
                            case 3 :
                                itemAlert.setJenis(getResources().getString(R.string.GasAlam));
                                break;
                            case 4 :
                                itemAlert.setJenis(getResources().getString(R.string.Gempa));
                                break;
                        }
                        break;
                }
                alertMessage();
                onAskingTotalPeople();
            }
        }).create().show();
    }

    public void alertMessage(){

        LocationRequest request = new LocationRequest();
        //Specify how often your app should request the device’s location//
//        request.setInterval(10000);
        //Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        //If the app currently has access to the location permission...//
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //...then request location updates//
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {

                        //Save the location data to the database//
                        itemAlert.setAktif(1);
                        itemAlert.setLatitude(location.getLatitude());
                        itemAlert.setLongitude(location.getLongitude());
                        itemAlert.setAltitude(location.getAltitude());

                        ref.child("Alert").child(user.getUid()).setValue(itemAlert).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mainAlert.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.MainAlertButton)));
                                    status.setText(getResources().getString(R.string.perhatian));
                                    information_text.setText(getResources().getString(R.string.dialog_perhatian));
                                    barStatus.setBackground(getResources().getDrawable(R.color.MainAlertButton));
                                    mainAlert.setImageResource(R.drawable.ic_priority_high_black_24dp);
                                    toolbar.setBackground(getResources().getDrawable(R.color.MainAlertButton));
                                }
                            }
                        });

                    }
                }
            }, null);
        }
    }

    public void checkNecessaryPermission () {
                //Check whether GPS tracking is enabled//
                LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mainAlert.setEnabled(false);
                    information_text.setText(getResources().getString(R.string.dialog_GPS_NotEnabled));
                    status.setVisibility(View.VISIBLE);
                    btn_grantPermission.setVisibility(View.VISIBLE);
                }

                //Check whether this app has access to the location permission//
                int permission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                //If the location permission has been granted, then start the TrackerService//
                if (permission == PackageManager.PERMISSION_GRANTED) {

                    mainAlert.setEnabled(true);
                    information_text.setText(getResources().getString(R.string.dialog_normal));
                    status.setText(getResources().getString(R.string.normal));
                    status.setVisibility(View.VISIBLE);
                    btn_grantPermission.setVisibility(View.INVISIBLE);

                } else {

                    mainAlert.setEnabled(false);
                    information_text.setText(getResources().getString(R.string.dialog_PermissionNotPermitted));
                    status.setVisibility(View.INVISIBLE);
                    btn_grantPermission.setVisibility(View.VISIBLE);

                    //If the app doesn’t currently have access to the user’s location, then request access//
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST);

                }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //If the permission has been granted...//
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainAlert.setEnabled(true);
                    information_text.setText(getResources().getString(R.string.dialog_normal));
                    status.setText(getResources().getString(R.string.normal));
        } else {
        //If the user denies the permission request, then display a toast with some more information//
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.konfirmasiKeluar)
                .setPositiveButton(R.string.ya, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                        MainActivity.this.startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                })
                .setNegativeButton(R.string.tidak, null).create().show();

        return super.onOptionsItemSelected(item);
    }
}
