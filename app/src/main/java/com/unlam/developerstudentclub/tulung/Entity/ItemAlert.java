package com.unlam.developerstudentclub.tulung.Entity;
import lombok.Getter;
import lombok.Setter;

public class ItemAlert {

    @Getter @Setter String jenis;
    @Getter @Setter double longitude, latitude, altitude;
    @Getter @Setter int orang, aktif;

}
