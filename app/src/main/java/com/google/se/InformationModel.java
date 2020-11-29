package com.google.se;

public class InformationModel {
    private String name;
    private String lastname;
    private String weight;
    private String height;
    private String age;
    private String sex;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    String Id;


    public static String KEY_NAME="Name";
    public static String KEY_LNAME="LastName";
    public static String KEY_W="Weight";
    public static String KEY_H="Height";
    public static String KEY_AGE="Age";
    public static String KEY_SEX="Sex";
    public static String KEY_ID="Id";



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }



}
