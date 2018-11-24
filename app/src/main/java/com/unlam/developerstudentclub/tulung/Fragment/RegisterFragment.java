package com.unlam.developerstudentclub.tulung.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unlam.developerstudentclub.tulung.Entity.ItemProfile;
import com.unlam.developerstudentclub.tulung.R;
import com.unlam.developerstudentclub.tulung.Utils.Implictly;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.tulung.Utils.Util.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.tulung.Utils.Util.ERROR_FIELD_KOSONG;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.tulung.Utils.Util.FRAGMENT_REGISTER_THIRD;

public class RegisterFragment extends Fragment implements Implictly {

    @Nullable @BindView(R.id.ti_nama) TextFieldBoxes ti_nama;
    @Nullable @BindView(R.id.ti_tempat) TextFieldBoxes ti_tempat;
    @Nullable @BindView(R.id.ti_tanggalLahir) TextFieldBoxes ti_tanggalLahir;
    @Nullable @BindView(R.id.ti_beratbadan) TextFieldBoxes ti_beratbadan;
    @Nullable @BindView(R.id.ti_alamat) TextFieldBoxes ti_alamat;
    @Nullable @BindView(R.id.ti_riwayatpenyakit) TextFieldBoxes ti_riwayatpenyakit;
    @Nullable @BindView(R.id.ti_alergimakanan) TextFieldBoxes ti_alergimakanan;
    @Nullable @BindView(R.id.ti_alergiobat) TextFieldBoxes ti_alergiobat;
    @Nullable @BindView(R.id.ti_catatandokter) TextFieldBoxes ti_catatandokter;
    @Nullable @BindView(R.id.ti_email) TextFieldBoxes ti_email;
    @Nullable @BindView(R.id.ti_password) TextFieldBoxes ti_password;
    @Nullable @BindView(R.id.ti_kontakkerabat) TextFieldBoxes ti_kontakkerabat;

    @Nullable @BindView(R.id.edt_nama) ExtendedEditText edt_nama;
    @Nullable @BindView(R.id.edt_tempat) ExtendedEditText edt_tempat;
    @Nullable @BindView(R.id.edt_tanggalLahir) ExtendedEditText edt_tanggalLahir;
    @Nullable @BindView(R.id.edt_beratbadan) ExtendedEditText edt_beratbadan;
    @Nullable @BindView(R.id.edt_alamat) ExtendedEditText edt_alamat;
    @Nullable @BindView(R.id.edt_riwayatpenyakit) ExtendedEditText edt_riwayatpenyakit;
    @Nullable @BindView(R.id.edt_alergimakanan) ExtendedEditText edt_alergimakanan;
    @Nullable @BindView(R.id.edt_alergiobat) ExtendedEditText edt_alergiobat;
    @Nullable @BindView(R.id.edt_catatandokter) ExtendedEditText edt_catatandokter;
    @Nullable @BindView(R.id.edt_email) ExtendedEditText edt_email;
    @Nullable @BindView(R.id.edt_password) ExtendedEditText edt_password;
    @Nullable @BindView(R.id.edt_kontakkerabat) ExtendedEditText edt_kontakkerabat;

    @Nullable @BindView(R.id.spinner_golongandarah) MaterialSpinner spinner_golongandarah;
    @Getter @Setter  onCompleteResponse Responses;
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
                edt_beratbadan.setFilters(new InputFilter[] {
                        new InputFilter.LengthFilter(3)
                });
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

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onRegisterActivityResponse(Boolean text) {
        final int Fragment = getArguments().getInt(FRAGEMENT_IDENTITY,0);
        boolean isComplete = true;
        ItemProfile data = new ItemProfile();

        switch (Fragment){
            case FRAGMENT_REGISTER_FIRST :
                String nama = edt_nama.getText().toString().trim(),
                       tempat = edt_tempat.getText().toString().trim(),
                       tanggallahir = edt_tanggalLahir.getText().toString().trim(),
                       golongandarah = spinner_golongandarah.getText().toString().trim(),
                       alamat = edt_alamat.getText().toString().trim(),
                       beratbadan = edt_beratbadan.getText().toString().trim();

                if(TextUtils.isEmpty(nama)){
                    isComplete = false;
                    ti_nama.setError(ERROR_FIELD_KOSONG,false);
                }

                if(TextUtils.isEmpty(tempat)){
                    isComplete = false;
                    ti_tempat.setError(ERROR_FIELD_KOSONG,false);
                }

                if(TextUtils.isEmpty(tanggallahir)){
                    isComplete = false;
                    ti_tanggalLahir.setError(ERROR_FIELD_KOSONG,false);
                }

                if(TextUtils.isEmpty(alamat)){
                    isComplete = false;
                    ti_alamat.setError(ERROR_FIELD_KOSONG,false);
                }

                if(TextUtils.isEmpty(beratbadan)){
                    isComplete = false;
                    ti_beratbadan.setError(ERROR_FIELD_KOSONG,false);
                }

                if(isComplete){
                    data.setNama(nama);
                    data.setTempat(tempat);
                    data.setTanggallahir(tanggallahir);
                    data.setBeratbadan(Integer.parseInt(beratbadan));
                    data.setGolongandarah(golongandarah);
                    data.setAlamat(alamat);
                }
                break;

            case FRAGMENT_REGISTER_SECOND :
                String riwayatpenyakit = edt_riwayatpenyakit.getText().toString().trim(),
                       alergimakanan = edt_alergimakanan.getText().toString().trim(),
                       alergiobat = edt_alergiobat.getText().toString().trim(),
                       catatandokter = edt_catatandokter.getText().toString().trim();

                if(TextUtils.isEmpty(riwayatpenyakit)){
                    isComplete = false;
                    ti_riwayatpenyakit.setError(ERROR_FIELD_KOSONG,true);
                }

                if(TextUtils.isEmpty(alergimakanan)){
                    isComplete = false;
                    ti_alergimakanan.setError(ERROR_FIELD_KOSONG,false);
                }

                if(TextUtils.isEmpty(alergiobat)){
                    isComplete = false;
                    ti_alergiobat.setError(ERROR_FIELD_KOSONG, false);
                }

                if(TextUtils.isEmpty(catatandokter)){
                    isComplete = false;
                    ti_catatandokter.setError(ERROR_FIELD_KOSONG,false);
                }
                if(isComplete){
                    data.setRiwayatpenyakit(riwayatpenyakit);
                    data.setAlergimakanan(alergimakanan);
                    data.setAlergiobat(alergiobat);
                    data.setCatatandokter(catatandokter);
                }
                break;

            case FRAGMENT_REGISTER_THIRD :
                String email = edt_email.getText().toString().trim(),
                       password = edt_password.getText().toString().trim(),
                       kontakkerabat = edt_kontakkerabat.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    isComplete = false;
                    ti_email.setError(ERROR_FIELD_KOSONG, false);
                } else if (!isValidEmail(email)){
                    isComplete = false;
                    ti_email.setError(ERROR_FIELD_EMAIL_NOTVALID, false);
                }

                if(TextUtils.isEmpty(password)){
                    isComplete = false;
                }

                if(TextUtils.isEmpty(kontakkerabat)){
                    isComplete = false;
                    ti_kontakkerabat.setError(ERROR_FIELD_KOSONG, false);
                }

                if(isComplete){
                    data.setEmail(email);
                    data.setPassword(password);
                    data.setKontakkerabat(kontakkerabat);
                }

                break;
        }

        if(isComplete){
            getResponses().onCompleteFormResponse(data, Fragment);
        }

        getResponses().onErrorFieldResponses(Fragment);
    }


    public interface onCompleteResponse {
        void onCompleteFormResponse(ItemProfile data, int FragmentIdentifier);
        void onErrorFieldResponses(int FragmentIdentifier);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Responses = (onCompleteResponse) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onCompleteResponse");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Responses = null;
    }

}
