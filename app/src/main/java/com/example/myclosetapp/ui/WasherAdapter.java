package com.example.myclosetapp.ui;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.utils.ImageConverter;

import java.util.ArrayList;
import java.util.List;

public class WasherAdapter extends RecyclerView.Adapter<WasherAdapter.WasherHolder>{

    private List<Clothe> clothes = new ArrayList<>();
    private OnWasherClotheClickListener listener;

    @NonNull
    @Override
    public WasherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clothe_item_washer,parent,false);
        return new WasherHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WasherHolder holder, int position) {
        Clothe currClothe = clothes.get(position);
        Bitmap bmap = ImageConverter.convertByteArrayToImage(currClothe.getImage());
        holder.washerClotheImage.setImageBitmap(bmap);
        if (currClothe.getToClean()) {
            holder.textViewCleanState.setText("Βρώμικο");
        } else {
            holder.textViewCleanState.setText("Καθαρό");
        }
    }



    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public void setClothe(List<Clothe> clothes) {
        this.clothes = clothes;
        notifyDataSetChanged();
    }


    class WasherHolder extends RecyclerView.ViewHolder{
        private TextView textViewCleanState;
        private Button  buttonAddToClean;
        private ImageView washerClotheImage;

        public WasherHolder (@NonNull View itemView){
            super(itemView);
            washerClotheImage=itemView.findViewById(R.id.clothe_washer_image);
            textViewCleanState=itemView.findViewById(R.id.text_view_washer_item_isClean);
            buttonAddToClean=itemView.findViewById(R.id.button_add_clothe_to_washer);
            buttonAddToClean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null&& position!=RecyclerView.NO_POSITION){
                        listener.onWasherClotheClickAddToWasher(clothes.get(position));
                    }
                }
            });
        }
    }

    public interface OnWasherClotheClickListener {
        void onWasherClotheClickAddToWasher(Clothe clothe);
    }

    public void setOnWasherItemClickListener(OnWasherClotheClickListener washerClotheListener){
        this.listener=washerClotheListener;
    }

}

