package com.lazydeveloper.nanopad.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.lazydeveloper.nanopad.R;
import com.lazydeveloper.nanopad.activities.DashboardActivity;
import com.lazydeveloper.nanopad.model.User;
import com.lazydeveloper.nanopad.utilitites.AdapterListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;
    private AdapterListener adapterListener;

    public RecyclerAdapter(Context context, AdapterListener listener) {
        this.context = context;
        userList = new ArrayList<>();
        this.adapterListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addUser(User user){
        userList.add(user);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeUser(int position){
        userList.remove(position);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearData(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_layout_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        User user = userList.get(position);

        holder.txtTitle.setText(user.getTitle());
        holder.txtText.setText(user.getText());
        holder.txtTime.setText(user.getTime());

        holder.linearLayout.setOnClickListener(v -> adapterListener.onUpdate(user));

        holder.imageButton.setOnClickListener(v ->
        {
            // Create the object of AlertDialog Builder class
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Alert !");
            builder.setMessage("Are you sure you want to delete \n" +"'" + user.getTitle() + "'" + " ?");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                adapterListener.onDelete(user.getId(), position);
                Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_LONG).show();
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTitle;
        private TextView txtText;
        private TextView txtTime;
        private AppCompatImageButton imageButton;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.tv_title);
            txtText = itemView.findViewById(R.id.tv_text);
            txtTime = itemView.findViewById(R.id.tv_time);
            imageButton = itemView.findViewById(R.id.icon_delete);
            linearLayout = itemView.findViewById(R.id.user_linear_layout);

        }
    }
}
