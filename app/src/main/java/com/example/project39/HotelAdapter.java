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

public class HotelAdapter  extends FirebaseRecyclerAdapter<Hotelmodel,HotelAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HotelAdapter(@NonNull FirebaseRecyclerOptions<Hotelmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder,  final int position, @NonNull Hotelmodel model) {
        holder.hotelname.setText(model.getHotelname());
        //holder.hoteltime.setText(model.getHoteltime());
       // holder.desc.setText(model.getHoteldescription());
       // holder.loc.setText(model.getHotellocation());
        Glide.with(holder.hotelimg.getContext()).load(model.getHotelpic()).placeholder(com.firebase.ui.auth.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.google.firebase.storage.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.hotelimg);
        holder.hotelname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.hotelimg.getContext())
                        .setContentHolder(new ViewHolder(R.layout.showhotel))
                        .setExpanded(true, 1000).create();

                View view = dialogPlus.getHolderView();
                TextView hotelname=view.findViewById(R.id.hotelname);
                TextView hoteldesc=view.findViewById(R.id.hoteldesc);

                TextView hoteltime=view.findViewById(R.id.hoteltime);
                hotelname.setText(model.getHotelname());
                hoteltime.setText(model.getHoteltime());
                hoteldesc.setText(model.getHoteldescription());
                dialogPlus.show();
                hotelname.setOnClickListener(new View.OnClickListener() {
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView hotelimg;
        TextView hotelname,hoteltime,desc,loc;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelimg=(ShapeableImageView) itemView.findViewById(R.id.hotelimg);
            hotelname=(TextView)itemView.findViewById(R.id.hotelname);
          //desc=(TextView)itemView.findViewById(R.id.desc);
            //hoteltime=(TextView) itemView.findViewById(R.id.hoteltime);
           // loc=(TextView) itemView.findViewById(R.id.location);


        }
    }
}
