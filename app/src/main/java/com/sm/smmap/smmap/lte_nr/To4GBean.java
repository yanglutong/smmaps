package com.sm.smmap.smmap.lte_nr;

public class To4GBean {
    private String lteMccString;
    private String lteMncString;
    private String lteTac;
    private String ltePci;
    private String lteCi;
    private String lteEarfac;
    private String lteBand;
    private String lteRssi;
    private String lteRsrp;
    private String lteRsrq;
    private String lteRssnr;
    private String operator;
    private String lte;
    private String lteType;

    public To4GBean(String lteMccString, String lteMncString, String lteTac, String ltePci, String lteCi, String lteEarfac, String lteBand, String lteRssi, String lteRsrp, String lteRsrq, String lteRssnr, String operator, String lte, String lteType) {
        this.lteMccString = lteMccString;
        this.lteMncString = lteMncString;
        this.lteTac = lteTac;
        this.ltePci = ltePci;
        this.lteCi = lteCi;
        this.lteEarfac = lteEarfac;
        this.lteBand = lteBand;
        this.lteRssi = lteRssi;
        this.lteRsrp = lteRsrp;
        this.lteRsrq = lteRsrq;
        this.lteRssnr = lteRssnr;
        this.operator = operator;
        this.lte = lte;
        this.lteType = lteType;
    }

    public String getLteMccString() {
        return lteMccString;
    }

    public void setLteMccString(String lteMccString) {
        this.lteMccString = lteMccString;
    }

    public String getLteMncString() {
        return lteMncString;
    }

    public void setLteMncString(String lteMncString) {
        this.lteMncString = lteMncString;
    }

    public String getLteTac() {
        return lteTac;
    }

    public String getLte() {
        return lte;
    }

    public void setLte(String lte) {
        this.lte = lte;
    }

    public String getLteType() {
        return lteType;
    }

    public void setLteType(String lteType) {
        this.lteType = lteType;
    }

    public void setLteTac(String lteTac) {
        this.lteTac = lteTac;
    }

    public String getLtePci() {
        return ltePci;
    }

    public void setLtePci(String ltePci) {
        this.ltePci = ltePci;
    }

    public String getLteCi() {
        return lteCi;
    }

    public void setLteCi(String lteCi) {
        this.lteCi = lteCi;
    }

    public String getLteEarfac() {
        return lteEarfac;
    }

    public void setLteEarfac(String lteEarfac) {
        this.lteEarfac = lteEarfac;
    }

    public String getLteBand() {
        return lteBand;
    }

    public void setLteBand(String lteBand) {
        this.lteBand = lteBand;
    }

    public String getLteRssi() {
        return lteRssi;
    }

    public void setLteRssi(String lteRssi) {
        this.lteRssi = lteRssi;
    }

    public String getLteRsrp() {
        return lteRsrp;
    }

    public void setLteRsrp(String lteRsrp) {
        this.lteRsrp = lteRsrp;
    }

    public String getLteRsrq() {
        return lteRsrq;
    }

    public void setLteRsrq(String lteRsrq) {
        this.lteRsrq = lteRsrq;
    }

    public String getLteRssnr() {
        return lteRssnr;
    }

    public void setLteRssnr(String lteRssnr) {
        this.lteRssnr = lteRssnr;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
