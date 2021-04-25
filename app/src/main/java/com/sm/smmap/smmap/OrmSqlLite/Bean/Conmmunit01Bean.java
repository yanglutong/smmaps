package com.sm.smmap.smmap.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cumm01bean")
public class Conmmunit01Bean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private String tac;
    @DatabaseField
    private String pci;

    @Override
    public String toString() {
        return "Conmmunit01Bean{" +
                "id=" + id +
                ", tac='" + tac + '\'' +
                ", pci='" + pci + '\'' +
                ", cid='" + cid + '\'' +
                ", uepmax='" + uepmax + '\'' +
                ", enodebpmax='" + enodebpmax + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUepmax() {
        return uepmax;
    }

    public void setUepmax(String uepmax) {
        this.uepmax = uepmax;
    }

    public String getEnodebpmax() {
        return enodebpmax;
    }

    public void setEnodebpmax(String enodebpmax) {
        this.enodebpmax = enodebpmax;
    }

    @DatabaseField
    private String cid;
    @DatabaseField
    private String uepmax  ;

    @DatabaseField
    private String enodebpmax;
}
