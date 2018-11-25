package com.unlam.developerstudentclub.tulung.Entity;

import lombok.Getter;
import lombok.Setter;

public class ItemProfile {

    @Getter  @Setter String nama, kontakpribadi,  tempat, tanggallahir, alamat, riwayatpenyakit, alergimakanan, alergiobat, catatandokter, email, password, kontakkerabat, golongandarah;
    @Getter  @Setter int tinggi, beratbadan;

}
