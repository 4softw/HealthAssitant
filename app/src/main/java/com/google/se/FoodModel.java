package com.google.se;

public class FoodModel {
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String ID;

    public String getMeal() {
        return Meal;
    }

    public void setMeal(String meal) {
        Meal = meal;
    }

    private String Meal;
    private String Name;
    private  String Gruop;
    private double Colorie;
    private double Fat;
    private double Protoein;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    private String Time;
    private double Carbohidrat;




    public String getGruop() {
        return Gruop;
    }

    public void setGruop(String gruop) {
        Gruop = gruop;
    }

    public double getColorie() {
        return Colorie;
    }

    public void setColorie(double colorie) {
        Colorie = colorie;
    }

    public double getFat() {
        return Fat;
    }

    public void setFat(double fat) {
        Fat = fat;
    }

    public double getProtoein() {
        return Protoein;
    }

    public void setProtoein(double protoein) {
        Protoein = protoein;
    }

    public double getCarbohidrat() {
        return Carbohidrat;
    }

    public void setCarbohidrat(double carbohidrat) {
        Carbohidrat = carbohidrat;
    }



    @Override
    public String toString() {
        return getName();
    }
}


