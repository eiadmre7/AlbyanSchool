package com.example.mysplashscreen;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Model_Grades {
    String studentPhone;
    ArrayList<String> magamot;
    ArrayList<Integer> grades;
    String EnglishStage;
    String MathStage;
    private float comu_avg, scinse_avg, phisic_avg, comp_avg;

    public Model_Grades() {
        grades=new ArrayList<>();
        magamot=new ArrayList<>();
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public ArrayList<String> getMagamot() {
        if(getComu_avg()>=75)
            magamot.add("تم القبول");
        else magamot.add("غير مقبول");
        if(getScinse_avg()>=85)
            magamot.add("تم القبول");
        else magamot.add("غير مقبول");
        if(getPhisic_avg()>=90)
            magamot.add("تم القبول");
        else magamot.add("غير مقبول");
        if(getComp_avg()>=95)
            magamot.add("تم القبول");
        else magamot.add("غير مقبول");
        return magamot;
    }

    public void setMagamot(ArrayList<String> magamot) {
        this.magamot = magamot;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Integer> grades) {
        this.grades = grades;
    }

    public String getEnglishStage() {
        return EnglishStage;
    }

    public void setEnglishStage(String englishStage) {
        EnglishStage = englishStage;
    }

    public String getMathStage() {
        return MathStage;
    }

    public void setMathStage(String mathStage) {
        MathStage = mathStage;
    }

//calculate the homane subjects avg.
    private float calc_Homani() {
        float homaniAvg = 0;
        for (int i = 0; i < 4; i++) {
            homaniAvg += grades.get(i);
        }
        homaniAvg = homaniAvg / 4;
        return homaniAvg;
    }

    private float calc_EngGrade() {
        float grade = 0;
        switch (getEnglishStage()) {
            case "Stage A":
                grade = 1.0F * grades.get(10);
                break;
            case "Stage B":
                grade = 0.75F*grades.get(10);
                break;
            case "Stage C":
                grade = 0.60F * grades.get(10);
                break;
            default:
                break;
        }
        return grade;
    }

    private float calc_MathGrade() {
        float grade = 0;
        switch (getMathStage()) {
            case "عادي":
                grade = 0.80F * grades.get(9);
                break;
            case "متميزون":
                grade = 1.0F * grades.get(9);
                break;
            default:
                break;
        }
        return grade;
    }

    public float getComu_avg() {
        float homani_avg = calc_Homani();
        float eng_grade = calc_EngGrade();
        float math_grade=calc_MathGrade();
        comu_avg=(homani_avg+eng_grade+math_grade+grades.get(4)+grades.get(5)+grades.get(6))/6;
        comu_avg=convert_2digits(comu_avg);
        return comu_avg;
    }

    public void setComu_avg(float comu_avg) {
        this.comu_avg = comu_avg;
    }

    public float getScinse_avg() {
        float homani_avg = calc_Homani();
        float eng_grade = calc_EngGrade();
        float math_grade=calc_MathGrade();
        scinse_avg=(homani_avg+eng_grade+math_grade+grades.get(4)+grades.get(5)+grades.get(6)+grades.get(7))/7;
        scinse_avg=convert_2digits(scinse_avg);
        return scinse_avg;
    }

    public void setScinse_avg(float scinse_avg) {
        this.scinse_avg = scinse_avg;
    }

    public float getPhisic_avg() {
        float homani_avg = calc_Homani();
        float eng_grade = calc_EngGrade();
        float math_grade=calc_MathGrade();
        phisic_avg=(homani_avg+eng_grade+math_grade+grades.get(4)+grades.get(5)+grades.get(7)+grades.get(8))/7;
        phisic_avg=convert_2digits(phisic_avg);
        return phisic_avg;
    }

    public void setPhisic_avg(float phisic_avg) {
        this.phisic_avg = phisic_avg;
    }

    public float getComp_avg() {
        float eng_grade = calc_EngGrade();
        float math_grade=calc_MathGrade();
        comp_avg=(eng_grade+math_grade+grades.get(4)+grades.get(5)+grades.get(7)+grades.get(8))/6;
        comp_avg=convert_2digits(comp_avg);
        return comp_avg;
    }

    public void setComp_avg(float comp_avg) {
        this.comp_avg = comp_avg;
    }

    public float convert_2digits(float num){
        DecimalFormat df = new DecimalFormat("0.00");
        //df.setMaximumFractionDigits(2);
        String str_2digit_num=df.format(num);
        float dec_2digit_num=Float.parseFloat(str_2digit_num);
        return dec_2digit_num;
    }
}