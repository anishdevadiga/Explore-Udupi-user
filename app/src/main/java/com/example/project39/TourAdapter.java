package com.example.project39;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

public class TourAdapter extends FirebaseRecyclerAdapter<Tourmodel,TourAdapter.myViewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private Context context;
    public TourAdapter(@NonNull FirebaseRecyclerOptions<Tourmodel> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Tourmodel model) {
        holder.tourname.setText(model.getPlacename());
      //  holder.tourtime.setText(model.getPlacetime());
        //holder.tourdesc.setText(model.getPlacedescription());
        Glide.with(holder.tourimg.getContext()).load(model.getPlacepicurl())
                .placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.tourimg);
        holder.tourname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.tourimg.getContext())
                        .setContentHolder(new ViewHolder(R.layout.showtour))
                        .setExpanded(true, 1200).create();

                View view = dialogPlus.getHolderView();
                TextView tourtime=view.findViewById(R.id.tourtime);
                TextView tourname=view.findViewById(R.id.tourname);
                TextView tourdesc=view.findViewById(R.id.tourdesc);
                tourname.setText(model.getPlacename());
                tourdesc.setText(model.getPlacedescription());
                tourtime.setText(model.getPlacetime());
                dialogPlus.show();
                tourname.setOnClickListener(new View.OnClickListener() {
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
        View tourview= LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_user_item,parent,false);
        return  new myViewHolder(tourview);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView tourimg;
        TextView tourname,tourtime,tourdesc,tourloc;
        Button btnloc;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tourimg=(ImageView)itemView.findViewById(R.id.tourimg1);
            tourname=(TextView)itemView.findViewById(R.id.tourname);
            tourtime=(TextView)itemView.findViewById(R.id.tourtime);
            //tourloc=(TextView) itemView.findViewById(R.id.tourlocc);
            tourdesc=(TextView) itemView.findViewById(R.id.tourdesc);


        }
    }


}
