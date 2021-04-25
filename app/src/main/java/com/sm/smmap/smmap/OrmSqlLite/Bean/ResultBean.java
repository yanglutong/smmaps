package com.sm.smmap.smmap.OrmSqlLite.Bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "resultbean")
public class ResultBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private String mcc;

    @DatabaseField
    private String mnc;

    @Override
    public String toString() {
        return "ResultBean{" +
                "id=" + id +
                ", mcc='" + mcc + '\'' +
                ", mnc='" + mnc + '\'' +
                ", lac='" + lac + '\'' +
                ", ci='" + ci + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", radius='" + radius + '\'' +
                ", address='" + address + '\'' +
                ", type=" + type +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @DatabaseField
    private String lac;

    @DatabaseField
    private String ci;

    @DatabaseField
    private String lat;

    @DatabaseField
    private String lon;

    @DatabaseField
    private String radius;

    @DatabaseField
    private String address;

    @DatabaseField
    private int type;

}
