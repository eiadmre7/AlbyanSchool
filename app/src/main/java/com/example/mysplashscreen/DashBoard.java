package com.example.mysplashscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DashBoard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG="Albyan_Test";
    FirebaseFirestore db;
    Toolbar myToolbar;
    private EditText txtName,txtPhone;
    private Spinner spinner;
    private Button btnLogin;
    private String Name,Phone,School;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initViews();
        spinner.setOnItemSelectedListener(DashBoard.this);
        db=FirebaseFirestore.getInstance();
        btnLogin.setOnClickListener(View->LogIn());
        // set toolbar as actionbar in your activity
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
//creating menu on dashboard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id =item.getItemId();
        if(id==R.id.itmAdmin){
            Intent intent=new Intent(this,StudentsDetails.class);
            startActivity(intent);
        }
        if(id==R.id.itmExit){
            System.exit(0);
        }
        return true;
    }
//when click on login button
    private void LogIn() {
        if(chkFields()) {
            addData();
            Intent intent=new Intent(this,GradesTest.class);
            intent.putExtra("ST_ID",Phone);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "الرجاء ملأ جميع الحقول...", Toast.LENGTH_SHORT).show();

    }

//checking fields in main form
    private boolean chkFields() {
        Name=txtName.getText().toString();
        if(Name.isEmpty()){
            txtName.setError("أكتب اسمك رجاء..");
            return false;
        }
        Phone=txtPhone.getText().toString();
        if(Phone.isEmpty()){
            txtPhone.setError("أكتب اسمك رجاء..");
            return false;
        }

        return true;
    }

    private void initViews() {
        txtName=findViewById(R.id.etName);
        txtPhone=findViewById(R.id.etPhone);
        btnLogin=findViewById(R.id.button);
        spinner=findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schools_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Log.d(TAG, "initViews: ");
    }
    private void addData() {
        CollectionReference Students = db.collection("Students");
        Map<String, Object> student = new HashMap<>();
        student.put("name", Name);
        student.put("phone", Phone);
        student.put("school", School);

// Add a new document with a generated ID
        Students.document(Phone).set(student)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DashBoard.this, "data was uploaded..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        School= parent.getItemAtPosition(pos).toString();
        Log.d(TAG, "onItemSelected: " + School);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}