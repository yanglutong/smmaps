package com.sm.smmap.smmap.drive_test;
/*用于路测 保存每一次tac lac的值*/
public class DriveTest4GBean {
    public DriveTest4GBean() {
        
    }

    public DriveTest4GBean(String lteMccString, String lteMncString, String lteTac, String ltePci, String lteCi, String lteEarfac, String lteBand, String operatorName, String operatorType) {
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

    private String lteMccString;
    private String lteMncString;
    private String lteTac;
    private String ltePci;
    private String lteCi;
    private String lteEarfac;
    private String lteBand;
    private String operatorName;
    private String operatorType;

    @Override
    public String toString() {
        return "DriveTest4GBean{" +
                "lteMccString='" + lteMccString + '\'' +
                ", lteMncString='" + lteMncString + '\'' +
                ", lteTac='" + lteTac + '\'' +
                ", ltePci='" + ltePci + '\'' +
                ", lteCi='" + lteCi + '\'' +
                ", lteEarfac='" + lteEarfac + '\'' +
                ", lteBand='" + lteBand + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", operatorType='" + operatorType + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    private String dataType;

    public DriveTest4GBean(String lteMccString, String lteMncString, String lteTac, String ltePci, String lteCi, String lteEarfac, String lteBand, String operatorName, String operatorType, String dataType) {
        this.lteMccString = lteMccString;
        this.lteMncString = lteMncString;
        this.lteTac = lteTac;
        this.ltePci = ltePci;
        this.lteCi = lteCi;
        this.lteEarfac = lteEarfac;
        this.lteBand = lteBand;
        this.operatorName = operatorName;
        this.operatorType = operatorType;
        this.dataType = dataType;
    }
}
