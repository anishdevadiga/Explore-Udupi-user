package com.example.project39;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class CabAdapter extends FirebaseRecyclerAdapter<CabModel,CabAdapter.myView> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CabAdapter(@NonNull FirebaseRecyclerOptions<CabModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myView holder, int position, @NonNull CabModel model) {

        holder.cabname.setText(model.getCabname());
       // holder.cabstatus.setText(model.getCabstatus());
       // holder.cabratings.setText(model.getCabrating());
//        holder.cabprice.setText(model.getCabprice());
        Glide.with(holder.cabpic.getContext()).load(model.getCabpic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.cabpic);
        holder.cabname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.cabpic.getContext())
                        .setContentHolder(new ViewHolder(R.layout.showcab))
                        .setExpanded(true, 900).create();
                View view = dialogPlus.getHolderView();
                TextView cabname=view.findViewById(R.id.cabname);
                TextView cabprice=view.findViewById(R.id.cabprice);
                TextView cabnuumber=view.findViewById(R.id.cabnumber);
                cabname.setText(model.getCabname());
                cabnuumber.setText(model.getCabnumber());
                cabprice.setText(model.getCabprice());
                dialogPlus.show();
                cabname.setOnClickListener(new View.OnClickListener() {
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
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cabitem,parent,false);
        return new myView(view);
    }

    class myView extends RecyclerView.ViewHolder{
        ShapeableImageView cabpic;
        TextView cabname,cabratings,cabprice,cabstatus;
        Button btnedit,btndel;
        public myView(@NonNull View itemView) {
            super(itemView);
            cabpic=(ShapeableImageView)itemView.findViewById(R.id.cabimg);
            cabname=(TextView)itemView.findViewById(R.id.cabname);
            //cabratings=(TextView)itemView.findViewById(R.id.cabratings);
           // cabprice=(TextView)itemView.findViewById(R.id.cabprice);
            //cabstatus=(TextView)itemView.findViewById(R.id.cabstatus);
        }
    }
}
