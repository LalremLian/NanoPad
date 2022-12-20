package com.lazydeveloper.nanopad.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.lazydeveloper.nanopad.R;
import com.lazydeveloper.nanopad.databinding.ActivityEditBinding;
import com.lazydeveloper.nanopad.model.User;
import com.lazydeveloper.nanopad.utilitites.CommonFunction;
import com.lazydeveloper.nanopad.utilitites.UserDao;
import com.lazydeveloper.nanopad.utilitites.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding activityEditBinding;
    ProgressDialog progressDialog;

    private UserDatabase userDatabase;
    private UserDao userDao;

    String stTitle = "";
    String stText = "";
    String stTime = "";
    String stDate = "";

    User user;
    String FLAG = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonFunction.setStatusBarGradiant(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        activityEditBinding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(activityEditBinding.getRoot());

        stTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
        stDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        Log.e("TAG", stTime);
        Log.e("TAG", stDate);

        setSupportActionBar(activityEditBinding.toolbar.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        user = (User) getIntent().getSerializableExtra("USER_DATA");
        FLAG = getIntent().getStringExtra("FLAG");

        Log.e("TAG","FLAG :: " + FLAG);
        Log.e("TAG","USER DATA :: " + user);

        if(FLAG.equalsIgnoreCase("UPDATE")){
            activityEditBinding.edTitle.setText(user.getTitle());
            activityEditBinding.edText.setText(user.getText());
        }

        userDatabase = UserDatabase.getInstance(this);
        userDao = userDatabase.getDao();

        init();
    }

    public void init()
    {
        if (FLAG.equalsIgnoreCase("UPDATE")) {
            activityEditBinding.toolbar.iconDelete.setVisibility(View.VISIBLE);
        }
        activityEditBinding.toolbar.iconSave.setOnClickListener(v ->
        {
            stTitle = activityEditBinding.edTitle.getText().toString();
            stText = activityEditBinding.edText.getText().toString();

            if(FLAG.equalsIgnoreCase("UPDATE"))
            {
                User userModel = new User(user.getId(), stTitle, stText, stTime, stDate);
                userDao.update(userModel);
                Toast.makeText(this,"Successfully Updated",Toast.LENGTH_LONG).show();
                finish();
            }else if (FLAG.equalsIgnoreCase("CREATE"))
            {
                User user = new User(0, stTitle, stText, stTime, stDate);
//            recyclerAdapter.addUser(user);
                userDao.insert(user);
                activityEditBinding.edTitle.setText("");
                activityEditBinding.edText.setText("");
                Toast.makeText(this,"Created Successfully",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        activityEditBinding.toolbar.iconDelete.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert !");
            builder.setMessage("Are you sure you want to delete \n" +"'" + user.getTitle() + "'" + " ?");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                userDao.delete(user.getId());
                Toast.makeText(this,"Successfully Deleted",Toast.LENGTH_LONG).show();
                new Handler().postDelayed(this::finish,500);
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        stText = activityEditBinding.edText.getText().toString();
        if(!stText.equalsIgnoreCase("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert !");
            builder.setMessage("Press " + "'" + "yes" + "'" + " if you want to save your progress");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", (dialog, which) -> {
//            userDao.delete(user.getId());
//            Toast.makeText(this,"Successfully Deleted",Toast.LENGTH_LONG).show();
//            new Handler().postDelayed(this::finish,500);
                onBackPressed();
            });

            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            onBackPressed();
        }



        return true;
    }

    private void showLoader()
    {
        progressDialog = new ProgressDialog(EditActivity.this);
        progressDialog.setMessage("Searching" + "" + "\nPlease wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}