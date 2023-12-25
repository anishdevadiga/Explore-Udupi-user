package com.example.project39;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.project39.databinding.NetworkDialogBinding;

public class NetworkBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!isNetworkConnected(context)){
            NetworkDialogBinding binding=NetworkDialogBinding.inflate(LayoutInflater.from(context));
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setView(binding.getRoot());
            builder.setCancelable(false);
            Dialog dialog=builder.create();
            dialog.show();

            binding.btntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkConnected(context)){
                        dialog.dismiss();
                    }
                }
            });
        }

    }
    private boolean isNetworkConnected(Context context){
        try{
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return networkInfo!=null && networkInfo.isConnected();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
