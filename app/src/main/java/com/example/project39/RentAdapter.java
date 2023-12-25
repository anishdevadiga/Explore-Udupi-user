package com.example.project39;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Text;

public class RentAdapter extends FirebaseRecyclerAdapter<RentModel,RentAdapter.myViewHolder>{


    public RentAdapter(@NonNull FirebaseRecyclerOptions<RentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull RentModel model) {
        holder.rentname.setText(model.getName());
     // holder.time.setText(model.getTime());
      //holder.loc.setText(model.getLocation());
     // holder.price.setText(model.getName());
     // holder.num.setText(model.getNumber());

        Glide.with(holder.rentpic.getContext()).load(model.getPic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.rentpic);
        holder.rentname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.rentpic.getContext())
                        .setContentHolder(new ViewHolder(R.layout.showrent))
                        .setExpanded(true, 900).create();

                View view = dialogPlus.getHolderView();
                TextView rentname=view.findViewById(R.id.rentname);
                TextView renttime=view.findViewById(R.id.renttime);
                TextView rentnum=view.findViewById(R.id.rentnumber);
                TextView rentprice=view.findViewById(R.id.cabprice);
                rentprice.setText(model.getPrice());
                rentname.setText(model.getName());
                renttime.setText(model.getTime());
                rentnum.setText(model.getNumber());
                dialogPlus.show();
                rentname.setOnClickListener(new View.OnClickListener() {
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
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.rentitem,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView rentpic;
        TextView rentname,time,loc,num,price;


        public myViewHolder(@NonNull View itemView) {

            super(itemView);
            rentpic=(ShapeableImageView)itemView.findViewById(R.id.rentpic) ;
            rentname=(TextView) itemView.findViewById(R.id.rentname);
           // time=(TextView) itemView.findViewById(R.id.time);
            //loc=(TextView) itemView.findViewById(R.id.location);
           // num=(TextView) itemView.findViewById(R.id.phonenumber);
            //price=(TextView) itemView.findViewById(R.id.price);

        }
    }
}