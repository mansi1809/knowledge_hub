package com.example.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MachineLearning extends AppCompatActivity {
    Button btnExit;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

 //       setContentView(R.layout.activity_machine_learning);

        btnExit = findViewById(R.id.btnExit);
    btnExit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           AlertDialog dialog = createDialog();
           dialog.show();
        }
    });

    }

    AlertDialog createDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(MachineLearning.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

}