package com.sm.smmap.smmap.Utils;

public class BillObject {
	public String food,clothes,house,vehicle;
    public String tac,ci;
    public String id;

    @Override
    public String toString() {
        return "BillObject{" +
                "food='" + food + '\'' +
                ", clothes='" + clothes + '\'' +
                ", house='" + house + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", tac='" + tac + '\'' +
                ", ci='" + ci + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getClothes() {
		return clothes;
	}

	public void setClothes(String clothes) {
		this.clothes = clothes;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

}
