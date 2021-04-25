package com.sm.smmap.smmap.Utils.Bean;

public class SpBean {
    private String Down;
    private String Priority;
    private String Rsrp;
    private String Plmn;
    private String Band;
    private String Rsrq;
    private boolean zw=false;



    private int cid;

    private String Pci;


    @Override
    public String toString() {
        return "SpBean{" +
                "Down='" + Down + '\'' +
                ", Priority='" + Priority + '\'' +
                ", Rsrp='" + Rsrp + '\'' +
                ", Plmn='" + Plmn + '\'' +
                ", Band='" + Band + '\'' +
                ", Rsrq='" + Rsrq + '\'' +
                ", zw=" + zw +
                ", cid=" + cid +
                ", Pci='" + Pci + '\'' +
                ", Tac=" + Tac +
                ", Up='" + Up + '\'' +
                '}';
    }

    private int Tac;

    public String getDown() {
        return Down;
    }

    public void setDown(String down) {
        Down = down;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getRsrp() {
        return Rsrp;
    }

    public void setRsrp(String rsrp) {
        Rsrp = rsrp;
    }

    public String getPlmn() {
        return Plmn;
    }

    public void setPlmn(String plmn) {
        Plmn = plmn;
    }

    public String getBand() {
        return Band;
    }

    public void setBand(String band) {
        Band = band;
    }

    public String getRsrq() {
        return Rsrq;
    }

    public void setRsrq(String rsrq) {
        Rsrq = rsrq;
    }

    public boolean isZw() {
        return zw;
    }

    public void setZw(boolean zw) {
        this.zw = zw;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getPci() {
        return Pci;
    }

    public void setPci(String pci) {
        Pci = pci;
    }

    public int getTac() {
        return Tac;
    }

    public void setTac(int tac) {
        Tac = tac;
    }

    public String getUp() {
        return Up;
    }

    public void setUp(String up) {
        Up = up;
    }

    private String Up;



}
