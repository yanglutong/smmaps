package com.sm.smmap.smmap.Login;

public class TimeBean {

    @Override
    public String toString() {
        return "TimeBean{" +
                "sysTime2='" + sysTime2 + '\'' +
                ", sysTime1='" + sysTime1 + '\'' +
                '}';
    }

    /**
     * sysTime2 : 2021-04-26 10:37:28
     * sysTime1 : 20210426103728
     */

    private String sysTime2;
    private String sysTime1;

    public String getSysTime2() {
        return sysTime2;
    }

    public void setSysTime2(String sysTime2) {
        this.sysTime2 = sysTime2;
    }

    public String getSysTime1() {
        return sysTime1;
    }

    public void setSysTime1(String sysTime1) {
        this.sysTime1 = sysTime1;
    }
}
