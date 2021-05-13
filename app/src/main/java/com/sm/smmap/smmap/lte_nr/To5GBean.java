package com.sm.smmap.smmap.lte_nr;

public class To5GBean {
    private String BAND;
    private String NR_ARFCN;
    private String PCI;
    private String RSRP;
    private String RSRQ;
    private String SINR;
    private String CID;
    private int lac;
    private int psc;
    private String nr;
    private String nrType;
    private String mnc;
    private String tac;


    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getBAND() {
        return BAND;
    }

    public void setBAND(String BAND) {
        this.BAND = BAND;
    }

    public String getNR_ARFCN() {
        return NR_ARFCN;
    }

    public void setNR_ARFCN(String NR_ARFCN) {
        this.NR_ARFCN = NR_ARFCN;
    }

    public String getPCI() {
        return PCI;
    }

    public void setPCI(String PCI) {
        this.PCI = PCI;
    }

    public String getRSRP() {
        return RSRP;
    }

    public void setRSRP(String RSRP) {
        this.RSRP = RSRP;
    }

    public String getRSRQ() {
        return RSRQ;
    }

    public void setRSRQ(String RSRQ) {
        this.RSRQ = RSRQ;
    }

    public String getSINR() {
        return SINR;
    }

    public void setSINR(String SINR) {
        this.SINR = SINR;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getNrType() {
        return nrType;
    }

    public void setNrType(String nrType) {
        this.nrType = nrType;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public To5GBean(String tac,String BAND, String NR_ARFCN, String PCI, String RSRP, String RSRQ, String SINR, String CID, int lac, int psc, String nr, String nrType, String mnc) {
        this.tac=tac;
        this.BAND = BAND;
        this.NR_ARFCN = NR_ARFCN;
        this.PCI = PCI;
        this.RSRP = RSRP;
        this.RSRQ = RSRQ;
        this.SINR = SINR;
        this.CID = CID;
        this.lac = lac;
        this.psc = psc;
        this.nr = nr;
        this.nrType = nrType;
        this.mnc = mnc;
    }
}
