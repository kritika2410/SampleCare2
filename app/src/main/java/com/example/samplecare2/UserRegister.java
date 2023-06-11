package com.example.samplecare2;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class UserRegister extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION = 2;

    private EditText etFullName, etCity, etEmail, etPassword;
    private ImageView imageViews;
    private Bitmap selectedImage;
    private DatabaseReference databaseReference;

    private final Observer<Boolean> permissionObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isGranted) {
            if (isGranted) {
                openImagePicker();
            } else {
                Toast.makeText(UserRegister.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean isGranted) {
                            if (isGranted) {
                                openImagePicker();
                            } else {
                                Toast.makeText(UserRegister.this, "Permission denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        imageViews.setImageBitmap(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);

        etFullName = findViewById(R.id.etFullName);
        etCity = findViewById(R.id.etCity);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        imageViews = findViewById(R.id.imageViews);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        imageViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UserRegister.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    openImagePicker();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullName.isEmpty() || city.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImage == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = databaseReference.push().getKey();
        String imageUri = "images/" + userId + ".jpg"; // Example path for storing images in Firebase Storage
        // Store the image in Firebase Storage (e.g., using Firebase Storage SDK)

        User user = new User(userId, fullName, city, email, password, imageUri);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms and Conditions");
        builder.setMessage("Please read and accept the terms and conditions.");

        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                databaseReference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(UserRegister.this, "Parent registered successfully", Toast.LENGTH_SHORT).show();
                            clearFields();
                            launchParentLoginScreen();


                        } else {
                            Toast.makeText(UserRegister.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity", "Error: " + task.getException().getMessage());
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }





    private void clearFields() {
        etFullName.setText("");
        etCity.setText("");
        etEmail.setText("");
        etPassword.setText("");
        //imageViews.setImageResource(R.drawable.imageViews);
    }

    private void launchParentLoginScreen()
    {
        Intent intent1 = new Intent(UserRegister.this, UserLoginActivity.class);
        startActivity(intent1);
    }
}