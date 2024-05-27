package com.example.mysplashscreen;

import java.util.ArrayList;
import java.util.Map;

public class Student {
    private String name;
    private String phone;
    private String school;
    private float average=0;
    private ArrayList<String> magamot;

    public Student() {
    }

    public Student(String name, String phone, String school) {
        this.name = name;
        this.phone = phone;
        this.school = school;
    }

   public Student(Map<String,Object>map){
       this.name = map.get("name").toString();
       this.phone = map.get("phone").toString();
       this.school =map.get("school").toString();
       this.average =(float) map.get("average");
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public ArrayList<String> getMagamot() {
        return magamot;
    }

    public void setMagamot(ArrayList<String> magamot) {
        this.magamot = magamot;
    }
}
