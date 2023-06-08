package com.example.cs125revamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.cs125revamp.databinding.ActivityMainBinding;
import com.example.cs125revamp.databinding.ActivityRegisterBinding;
import com.example.cs125revamp.ui.recommendations.RecommendationsFragment;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        preferences.edit().clear().commit();

        Button btnBackSleep = findViewById(R.id.registerButton);
        EditText name_box=(EditText)findViewById(R.id.name_box);

        btnBackSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int user_id = -1;
                String n = "";
                if(!preferences.contains("user_id")) {
                    if(!Python.isStarted())
                        Python.start(new AndroidPlatform(getApplicationContext()));
                    String name = name_box.getText().toString();
                    String command = "create " + name;

                    Python py = Python.getInstance();
                    PyObject pyf = py.getModule("main");
                    PyObject result = pyf.callAttr("accept_one_command", command);

                    n = name;
                    user_id = result.toInt();

                    System.out.println("user_id:" + user_id);
                }
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("FirstTimeInstallDone", true);
                editor.putString("name", n);
                editor.putInt("user_id", user_id);
                editor.commit();
                System.out.println("done with first time install");

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}