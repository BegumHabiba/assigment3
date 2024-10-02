package com.example.traveler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText travelerNameInput, travelerEmailInput, travelerPhoneInput, travelerOtpInput;
    private Spinner travelerTransportSpinner;
    private TextView travelerErrorText, travelerResultText;
    private LinearLayout travelerInputLayout, travelerOutputLayout;

    private static final String NAME_REGEX = "^[a-z A-Z._]+$";
    private static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    private static final String PHONE_REGEX = "^01[3456789][0-9]{8}$";
    private static final String PIN_REGEX = "^[0-9]{5}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        travelerNameInput = findViewById(R.id.travelerNameInput);
        travelerEmailInput = findViewById(R.id.travelerEmailInput);
        travelerPhoneInput = findViewById(R.id.travelerPhoneInput);
        travelerOtpInput = findViewById(R.id.travelerOtpInput);
        travelerTransportSpinner = findViewById(R.id.travelerPositionSpinner);
        Button travelerSubmitBtn = findViewById(R.id.travelerSubmitBtn);
        travelerErrorText = findViewById(R.id.travelerErrorText);
        travelerResultText = findViewById(R.id.travelerResultText);
        travelerInputLayout = findViewById(R.id.travelerInputLayout);
        travelerOutputLayout = findViewById(R.id.travelerOutputLayout);


        String[] transportModes = {"Select a Transport Mode", "Bus", "Car", "Airplane", "Train", "Ship"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transportModes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        travelerTransportSpinner.setAdapter(adapter);

        travelerSubmitBtn.setOnClickListener(v -> validateTravelerForm());
    }

    private void validateTravelerForm() {
        String name = travelerNameInput.getText().toString().trim();
        String email = travelerEmailInput.getText().toString().trim();
        String phone = travelerPhoneInput.getText().toString().trim();
        String Otp = travelerOtpInput.getText().toString().trim();
        String selectedTransport = travelerTransportSpinner.getSelectedItem().toString();

        travelerErrorText.setVisibility(View.GONE);
        travelerOutputLayout.setVisibility(View.GONE);

        if (name.isEmpty()) {
            showError("Traveler Name is required.");
            travelerNameInput.requestFocus();
        } else if (!Pattern.matches(NAME_REGEX, name)) {
            showError("Invalid name format. Only letters and spaces are allowed.");
            travelerNameInput.requestFocus();
        } else if (email.isEmpty()) {
            showError("Email is required.");
            travelerEmailInput.requestFocus();
        } else if (!Pattern.matches(EMAIL_REGEX, email)) {
            showError("Invalid email format.");
            travelerEmailInput.requestFocus();
        } else if (phone.isEmpty()) {
            showError("Phone number is required.");
            travelerPhoneInput.requestFocus();
        } else if (!Pattern.matches(PHONE_REGEX, phone)) {
            showError("Invalid phone number. It must be 11 digits starting with '01'.");
            travelerPhoneInput.requestFocus();
        } else if (Otp.isEmpty()) {
            showError("Otp number is required.");
            travelerOtpInput.requestFocus();
        } else if (!Pattern.matches(PIN_REGEX, Otp)) {
            showError("Invalid Otp. It must be exactly 5 digits.");
            travelerOtpInput.requestFocus();
        } else if (selectedTransport.equals("Select a Transport Mode")) {
            showError("You must select a transport mode.");
            travelerTransportSpinner.requestFocus();
        } else {
            travelerInputLayout.setVisibility(View.GONE);
            travelerOutputLayout.setVisibility(View.VISIBLE);

            travelerErrorText.setVisibility(View.GONE);
            String result = "Name: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nOtp: " + Otp + "\nTransport Mode: " + selectedTransport;
            travelerResultText.setText(result);
        }
    }

    private void showError(String message) {
        travelerErrorText.setText(message);
        travelerErrorText.setVisibility(View.VISIBLE);
        travelerOutputLayout.setVisibility(View.GONE);
    }
}
