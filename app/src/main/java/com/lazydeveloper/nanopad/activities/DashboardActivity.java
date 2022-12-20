package com.lazydeveloper.nanopad.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lazydeveloper.nanopad.R;
import com.lazydeveloper.nanopad.adapter.RecyclerAdapter;
import com.lazydeveloper.nanopad.databinding.ActivityDashboardBinding;
import com.lazydeveloper.nanopad.databinding.ActivityEditBinding;
import com.lazydeveloper.nanopad.model.User;
import com.lazydeveloper.nanopad.utilitites.AdapterListener;
import com.lazydeveloper.nanopad.utilitites.CommonFunction;
import com.lazydeveloper.nanopad.utilitites.UserDao;
import com.lazydeveloper.nanopad.utilitites.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity implements AdapterListener {

//    List<User> userList = new ArrayList<>();

    String FLAG = "0";

    private UserDatabase userDatabase;
    private UserDao userDao;
    private RecyclerAdapter recyclerAdapter;

    ActivityDashboardBinding activityDashboardBinding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonFunction.setStatusBarGradiant(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());

        userDatabase = UserDatabase.getInstance(this);
        userDao = userDatabase.getDao();

        recyclerAdapter = new RecyclerAdapter(this, this);
        activityDashboardBinding.recyclerView.setAdapter(recyclerAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        activityDashboardBinding.recyclerView.setLayoutManager(staggeredGridLayoutManager);

//        fetchData();
//        FLAG = "1";

        init();
    }

    public void init(){
        activityDashboardBinding.addNote.setOnClickListener(V ->
        {
            Intent intent = new Intent(DashboardActivity.this, EditActivity.class);
            intent.putExtra("FLAG", "CREATE");
            startActivity(intent);
        });
    }

    private void fetchData()
    {
        //Calling this function from adapter to clear the previous data
        recyclerAdapter.clearData();

        List<User> userList = userDao.getAllUsers();
        Log.d("lifecycle",userList.size()+"");
        Log.d("lifecycle>",userList.toString());

//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        for (int i = 0; i<userList.size(); i++)
        {
            User user = userList.get(i);
            recyclerAdapter.addUser(user);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        Log.d("lifecycle","onResume invoked");
        Log.d("lifecycle",FLAG);

        if(FLAG.equalsIgnoreCase("0"))
        {
            Log.d("lifecycle","FLAG :: 0");
            FLAG = "1";
        }else if(FLAG.equalsIgnoreCase("1"))
        {
            Log.d("lifecycle","FLAG :: 1");
            fetchData();
        }*/
        fetchData();
    }

    @Override
    public void onUpdate(User users) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("USER_DATA", users);
        intent.putExtra("FLAG", "UPDATE");
        startActivity(intent);
    }

    @Override
    public void onDelete(int id, int position) {
        userDao.delete(id);
        recyclerAdapter.removeUser(position);
    }
}