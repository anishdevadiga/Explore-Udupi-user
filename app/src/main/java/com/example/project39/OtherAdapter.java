package com.example.project39;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class OtherAdapter extends FirebaseRecyclerAdapter<Othermodel,OtherAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Context context;
    public OtherAdapter(@NonNull FirebaseRecyclerOptions<Othermodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")  final int position, @NonNull Othermodel model) {
        holder.name.setText(model.getName());
       // holder.time.setText(model.getTime());
       // holder.desc.setText(model.getDescription());
       // holder.loc.setText(model.getLocation());
        Glide.with(holder.otherpic.getContext()).load(model.getPic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.otherpic);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.otherpic.getContext())
                        .setContentHolder(new ViewHolder(R.layout.showother))
                        .setExpanded(true, 1000).create();
                View view = dialogPlus.getHolderView();
                TextView name=view.findViewById(R.id.othername);
                TextView desc=view.findViewById(R.id.otherdesc);
                TextView time=view.findViewById(R.id.othertime);
                name.setText(model.getName());
                desc.setText(model.getDescription());
                time.setText(model.getTime());
                dialogPlus.show();
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.other,parent,false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
       ShapeableImageView otherpic;
       TextView name,time,desc,loc;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            otherpic=(ShapeableImageView) itemView.findViewById(R.id.otherpic);
            name=(TextView) itemView.findViewById(R.id.othername);
           // time=(TextView) itemView.findViewById(R.id.time);
            //desc=(TextView) itemView.findViewById(R.id.desc);
          //  loc=(TextView) itemView.findViewById(R.id.location);


        }
    }

}
