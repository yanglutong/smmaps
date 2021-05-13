package com.sm.smmap.smmap.drive_test;

public class Stu  {
    private String lteMccString;
    private String lteMncString;
    private String lteTac;
    private String ltePci;
    private String lteCi;
    private String lteEarfac;
    private String lteBand;
    private String operatorName;
    private String operatorType;
    public Stu(String lteMccString, String lteMncString, String lteTac, String ltePci, String lteCi, String lteEarfac, String lteBand, String operatorName, String operatorType) {
        this.lteMccString = lteMccString;
        this.lteMncString = lteMncString;
        this.lteTac = lteTac;
        this.ltePci = ltePci;
        this.lteCi = lteCi;
        this.lteEarfac = lteEarfac;
        this.lteBand = lteBand;
        this.operatorName = operatorName;
        this.operatorType = operatorType;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
}
