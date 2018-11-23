package com.unlam.developerstudentclub.tulung.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unlam.developerstudentclub.tulung.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_THIRD;

public class RegisterFragment extends Fragment {

    @BindView(R.id.ti_nama) TextFieldBoxes ti_nama;
    @BindView(R.id.ti_tempat) TextFieldBoxes ti_tempat;
    @BindView(R.id.ti_tanggalLahir) TextFieldBoxes ti_tanggalLahir;
    @BindView(R.id.ti_beratbadan) TextFieldBoxes ti_beratbadan;
    @BindView(R.id.ti_alamat) TextFieldBoxes ti_alamat;
    @BindView(R.id.ti_riwayatpenyakit) TextFieldBoxes ti_riwayatpenyakit;
    @BindView(R.id.ti_alergimakanan) TextFieldBoxes ti_alergimakanan;
    @BindView(R.id.ti_alergiobat) TextFieldBoxes ti_alergiobat;
    @BindView(R.id.ti_catatandokter) TextFieldBoxes ti_catatandokter;
    @BindView(R.id.ti_email) TextFieldBoxes ti_email;
    @BindView(R.id.ti_password) TextFieldBoxes ti_password;
    @BindView(R.id.ti_kontakkerabat) TextFieldBoxes ti_kontakkerabat;

    @BindView(R.id.edt_nama) ExtendedEditText edt_nama;
    @BindView(R.id.edt_tempat) ExtendedEditText edt_tempat;
    @BindView(R.id.edt_tanggalLahir) ExtendedEditText edt_tanggalLahir;
    @BindView(R.id.edt_beratbadan) ExtendedEditText edt_beratbadan;
    @BindView(R.id.edt_alamat) ExtendedEditText edt_alamat;
    @BindView(R.id.edt_riwayatpenyakit) ExtendedEditText edt_riwayatpenyakit;
    @BindView(R.id.edt_alergimakanan) ExtendedEditText edt_alergimakanan;
    @BindView(R.id.edt_alergiobat) ExtendedEditText edt_alergiobat;
    @BindView(R.id.edt_catatandokter) ExtendedEditText edt_catatandokter;
    @BindView(R.id.edt_email) ExtendedEditText edt_email;
    @BindView(R.id.edt_password) ExtendedEditText edt_password;
    @BindView(R.id.edt_kontakkerabat) ExtendedEditText edt_kontakkerabat;

    @BindView(R.id.spinner_golongandarah) MaterialSpinner spinner_golongandarah;
    final Calendar myCalendar = Calendar.getInstance();

    public RegisterFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        final int CHECK = getArguments().getInt(FRAGEMENT_IDENTITY,0);

        switch (CHECK){
            case FRAGMENT_REGISTER_FIRST :
                view = inflater.inflate(R.layout.frag_register_first, container, false);
                ButterKnife.bind(this,view);
                edt_tanggalLahir.setEnabled(false);
                ti_tanggalLahir.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                            }
                        };
                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                spinner_golongandarah.setItems(getResources().getStringArray(R.array.GolonganDarah));
                break;
            case FRAGMENT_REGISTER_SECOND :
                view = inflater.inflate(R.layout.frag_register_second,container,false);
                ButterKnife.bind(this,view);
                break;
            case FRAGMENT_REGISTER_THIRD :
                view = inflater.inflate(R.layout.frag_register_third, container, false);
                ButterKnife.bind(this,view);
                ti_password.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edt_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                });
                break;
            case FRAGMENT_REGISTER_FORTH :
                view = inflater.inflate(R.layout.frag_register_last, container, false);
                ButterKnife.bind(this,view);
                break;

            default: view =  null;
                break;
        }

        return view;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        edt_tanggalLahir.setText(sdf.format(myCalendar.getTime()));
    }
}
