package com.example.incidentreportapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewReportsActivity extends AppCompatActivity {

    private TextView textReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        textReports = findViewById(R.id.textReports);

        displayReports();
    }

    private void displayReports() {

        SharedPreferences preferences =
                getSharedPreferences("IncidentReports", MODE_PRIVATE);

        String savedReports =
                preferences.getString("reports", "[]");

        try {

            JSONArray reportsArray =
                    new JSONArray(savedReports);

            if (reportsArray.length() == 0) {
                textReports.setText("No incident reports have been saved.");
                return;
            }

            StringBuilder builder = new StringBuilder();

            for (int i = reportsArray.length() - 1; i >= 0; i--) {

                JSONObject report =
                        reportsArray.getJSONObject(i);

                builder.append("INCIDENT REPORT #")
                        .append(report.getInt("reportNumber"))
                        .append("\n");

                builder.append("-----------------------------\n");

                builder.append("Date: ")
                        .append(report.getString("date"))
                        .append("\n");

                builder.append("Time: ")
                        .append(report.getString("time"))
                        .append("\n");

                builder.append("Location: ")
                        .append(report.getString("location"))
                        .append("\n\n");

                builder.append("Individuals Involved:\n")
                        .append(report.getString("individuals"))
                        .append("\n\n");

                builder.append("Description:\n")
                        .append(report.getString("description"))
                        .append("\n\n");

                builder.append("Action Taken:\n")
                        .append(report.getString("actionTaken"))
                        .append("\n\n");

                builder.append("Reporter:\n")
                        .append(report.getString("reporter"))
                        .append("\n");

                builder.append("\n=============================\n\n");
            }

            textReports.setText(builder.toString());

        } catch (JSONException e) {

            textReports.setText("Unable to display reports.");
        }
    }
}