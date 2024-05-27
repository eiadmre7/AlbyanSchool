package com.example.mysplashscreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GradesTest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG="Albyan_Test";
    private Spinner spinner_1,spinner_2;
    private Button btnCalc;
    private ArrayList<EditText> subject;
    private ArrayList<Integer> grades;
    private ArrayList<String>arr;
    private Model_Grades model_1;
    private String stuID;
    private String engStage="المستوى",mathStage="المستوى";
    private float f_engStage,f_mathStage;
    private float comu_avg,scinse_avg,phisic_avg,comp_avg;
    FirebaseFirestore db;
    //private int His,Mad,Reg,Geo,Arb,Heb,Eng,Math,Bio,Chm,Phis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_test);
        db=FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        stuID=intent.getStringExtra("ST_ID");
        initViews();
        model_1=new Model_Grades();
        model_1.setStudentPhone(stuID);
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
                show_dialog();
                ArrayList<String>arr_2=new ArrayList<>();
                String str=arr.get(0).toString();
                if(str.equals("تم القبول")) arr_2.add("إتصال");
                str=arr.get(1).toString();
                if(str.equals("تم القبول")) arr_2.add("بيولوجيا/كيمياء");
                str=arr.get(2).toString();
                if(str.equals("تم القبول")) arr_2.add("فيزياء/كيمياء");
                str=arr.get(3).toString();
                if(str.equals("تم القبول")) arr_2.add("علم الحاسوب");
                UpdateMagamot(arr_2);
            }
        });
    }

    private void calculate() {
        grades=new ArrayList<>();
        if(chkFields()){
            for (int i = 0; i < subject.size(); i++) {
                grades.add(Integer.parseInt(subject.get(i).getText().toString().trim()));
            }
            model_1.setGrades(grades);
            model_1.setEnglishStage(engStage);
            model_1.setMathStage(mathStage);
        }
    }

    private void show_dialog() {
        arr=new ArrayList<>();
        ArrayList<TextView>tvAccepted;
        Dialog d=new Dialog(this);
        d.setContentView(R.layout.result_dialog);
        TextView textView1=d.findViewById(R.id.tvAvg_1);
        textView1.setText("" +model_1.getComu_avg());
        TextView textView2=d.findViewById(R.id.tvAvg_2);
        textView2.setText(""+model_1.getScinse_avg());
        TextView textView3=d.findViewById(R.id.tvAvg_3);
        textView3.setText(""+model_1.getPhisic_avg());
        TextView textView4=d.findViewById(R.id.tvAvg_4);
        textView4.setText("" +model_1.getComp_avg());
        //get magamoot from db.
        arr=model_1.getMagamot();
        tvAccepted=new ArrayList<>();
        tvAccepted.add(d.findViewById(R.id.tvAccept_1));
        tvAccepted.add(d.findViewById(R.id.tvAccept_2));
        tvAccepted.add(d.findViewById(R.id.tvAccept_3));
        tvAccepted.add(d.findViewById(R.id.tvAccept_4));
        for (int i=0;i<4;i++) {
            tvAccepted.get(i).setText(arr.get(i).toString());
        }
        d.setCancelable(true);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        d.show();
        d.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    private void UpdateMagamot(ArrayList<String> magamot) {
        Map<String, Object> student = new HashMap<>();
        student.put("magamot", magamot);
        db.collection("Students").document(stuID)
                .set(student, SetOptions.merge());
    }

    private boolean chkFields() {
        for (EditText grade:subject)
        {
            String st=grade.getText().toString();
            if(st.isEmpty()){
                Toast.makeText(this, "الرجاء تعبئة جميع العلامات", Toast.LENGTH_SHORT).show();
                grade.setError("ادخل العلامة");
                return false;
            }
        }
        if(engStage.equals("المستوى") || mathStage.equals("المستوى") ) {
            Toast.makeText(this, engStage+"الرجاء إدخال المستوى", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initViews() {
        subject=new ArrayList<>();
        spinner_1=findViewById(R.id.spinner_1);
        spinner_1.setOnItemSelectedListener(GradesTest.this);
        spinner_2=findViewById(R.id.spinner_2);
        spinner_2.setOnItemSelectedListener(GradesTest.this);
        btnCalc=findViewById(R.id.btnCalc);
        subject.add(findViewById(R.id.etHist));//0 - تاريخ
        subject.add(findViewById(R.id.etMdniat));//1 - مدنيات
        subject.add(findViewById(R.id.etGeo));//2 - جغرافيا
        subject.add(findViewById(R.id.etReleg));//3 - دين
        subject.add(findViewById(R.id.etArab));//4 - لغة عربية
        subject.add(findViewById(R.id.etHeb));//5 - لغة عبرية
        subject.add(findViewById(R.id.etBio));//6 - بيولوجيا
        subject.add(findViewById(R.id.etChemist));//7 - كيمياء
        subject.add(findViewById(R.id.etPhisic));//8 - فيزياء
        subject.add(findViewById(R.id.etMath));//9 - رياضيات
        subject.add(findViewById(R.id.etEng));//10 - لغة غنجليزية
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_eng = ArrayAdapter.createFromResource(this,
                R.array.english_array, R.layout.spinner_eglish);
        // Specify the layout to use when the list of choices appears
        adapter_eng.setDropDownViewResource(R.layout.spinner_dropdown_english);
        // Apply the adapter to the spinner
        spinner_1.setAdapter(adapter_eng);
        ArrayAdapter<CharSequence> adapter_math = ArrayAdapter.createFromResource(this,
                R.array.math_array, R.layout.spinner_eglish);
        adapter_math.setDropDownViewResource(R.layout.spinner_dropdown_english);
        spinner_2.setAdapter(adapter_math);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        if(parent.getId()== R.id.spinner_1){
            engStage= parent.getItemAtPosition(pos).toString();
            Log.d("Grades", "onItemSelected: "+ engStage);
        }
        if(parent.getId()== R.id.spinner_2){
            mathStage= parent.getItemAtPosition(pos).toString();
            Log.d("Grades", "onItemSelected: "+ mathStage);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}