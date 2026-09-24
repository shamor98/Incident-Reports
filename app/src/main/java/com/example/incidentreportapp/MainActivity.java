package com.example.incidentreportapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editIndividuals;
    EditText editDate;
    EditText editTime;
    EditText editLocation;
    EditText editDescription;
    EditText editReporter;
    EditText editActionTaken;

    Button btnSave;
    Button btnViewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editIndividuals = findViewById(R.id.editIndividuals);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);
        editLocation = findViewById(R.id.editLocation);
        editDescription = findViewById(R.id.editDescription);
        editReporter = findViewById(R.id.editReporter);
        editActionTaken = findViewById(R.id.editActionTaken);

        btnSave = findViewById(R.id.btnSave);
        btnViewReports = findViewById(R.id.btnViewReports);

        btnSave.setOnClickListener(v -> saveReport());

        btnViewReports.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ViewReportsActivity.class
            );
            startActivity(intent);
        });
    }

    private void saveReport() {

        String individuals = editIndividuals.getText().toString().trim();
        String date = editDate.getText().toString().trim();
        String time = editTime.getText().toString().trim();
        String location = editLocation.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String reporter = editReporter.getText().toString().trim();
        String actionTaken = editActionTaken.getText().toString().trim();

        if (individuals.isEmpty()
                || date.isEmpty()
                || time.isEmpty()
                || location.isEmpty()
                || description.isEmpty()
                || reporter.isEmpty()
                || actionTaken.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please complete all fields.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        SharedPreferences preferences =
                getSharedPreferences("IncidentReports", MODE_PRIVATE);

        String savedReports =
                preferences.getString("reports", "[]");

        try {

            JSONArray reportsArray =
                    new JSONArray(savedReports);

            JSONObject report =
                    new JSONObject();

            int reportNumber =
                    reportsArray.length() + 1001;

            report.put("reportNumber", reportNumber);
            report.put("individuals", individuals);
            report.put("date", date);
            report.put("time", time);
            report.put("location", location);
            report.put("description", description);
            report.put("reporter", reporter);
            report.put("actionTaken", actionTaken);

            reportsArray.put(report);

            preferences.edit()
                    .putString("reports", reportsArray.toString())
                    .apply();

            Toast.makeText(
                    this,
                    "Report #" + reportNumber + " saved.",
                    Toast.LENGTH_LONG
            ).show();

            clearFields();

        } catch (JSONException e) {

            Toast.makeText(
                    this,
                    "Error saving report.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void clearFields() {

        editIndividuals.setText("");
        editDate.setText("");
        editTime.setText("");
        editLocation.setText("");
        editDescription.setText("");
        editReporter.setText("");
        editActionTaken.setText("");
    }
}