package com.example.myclosetapp.ui;


import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.utils.ClotheMatchingOptions;
import com.example.myclosetapp.utils.FormatDateTime;
import com.example.myclosetapp.utils.ImageConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClotheAdapter extends RecyclerView.Adapter<ClotheAdapter.ClotheHolder> {
    private List<Clothe> clothes = new ArrayList<>();
    private OnClotheClickListener listener;


    @NonNull
    @Override
    public ClotheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clothe_item, parent, false);
        return new ClotheHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClotheHolder holder, int position) {

        Clothe currClothe = clothes.get(position);
        holder.textViewColor.setText(currClothe.getColor());
        holder.textViewCategory.setText(currClothe.getCategory());
        holder.textViewDescription.setText(currClothe.getDescription());
        Bitmap bmap = ImageConverter.convertByteArrayToImage(currClothe.getImage());
        holder.imageViewClothe.setImageBitmap(bmap);
        String lastWorn = FormatDateTime.dateToTimestamp(currClothe.getDateLastWorn());
       // String lastWornNiceFormat =  lastWorn.substring(0, 10) + lastWorn.substring(23, lastWorn.length());
        holder.textViewDateLastWorn.setText(lastWorn);
        if (currClothe.getToClean()) {
            holder.textViewIsClean.setText("Βρώμικο");
        } else {
            holder.textViewIsClean.setText("Καθαρό");
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

    //todo come here to check for styles

//    public void setClotheByStyle(String style, List<Clothe> clothes) {
//        List<Clothe> tempClothe = new ArrayList<>();
//        Collections.shuffle(clothes);
//        if (style.equals("ΕΠΕΛΕΞΕ")) {
//            this.clothes = clothes;
//        } else if (style.equals("ΑΝΔΡΙΚΑ")) {
//
//            for (String clotheInStyles : ClotheMatchingOptions.menStyles) {
//                for (Clothe clothe : clothes) {
//                    if (clotheInStyles.contains(clothe.getCategory())) {
//                        tempClothe.add(clothe);
//                    }
//                    if (tempClothe.size() >= 2) {
//                        this.clothes = tempClothe;
//                    }
//                }
//            }
//        } else {
//            for (String clotheInStyles : ClotheMatchingOptions.womenStyles) {
//                for (Clothe clothe : clothes) {
//                    if (clotheInStyles.contains(clothe.getCategory())) {
//                        tempClothe.add(clothe);
//                    }
//                    if (tempClothe.size() >= 2) {
//                        this.clothes = tempClothe;
//                    }
//                }
//            }
//        }
//
//    }


    public Clothe getClotheAt(int position) {
        return clothes.get(position);
    }


    class ClotheHolder extends RecyclerView.ViewHolder {
        private TextView textViewColor;
        private TextView textViewCategory;
        private TextView textViewDescription;
        private TextView textViewIsClean;
        private ImageView imageViewClothe;
        private TextView textViewDateLastWorn;
        private ImageButton buttonDeleteClothe;
        private ImageButton buttonEditClothe;


        public ClotheHolder(@NonNull View itemView) {
            super(itemView);
            textViewColor = itemView.findViewById(R.id.textview_color);
            textViewCategory = itemView.findViewById(R.id.textview_category);
            textViewDescription = itemView.findViewById(R.id.textview_description);
            textViewIsClean = itemView.findViewById(R.id.textview_cleanstate);
            imageViewClothe = itemView.findViewById(R.id.clothe_image);
            textViewDateLastWorn = itemView.findViewById(R.id.text_view_date_last_worn);
            buttonDeleteClothe = itemView.findViewById(R.id.button_delete_clothe);
            buttonEditClothe = itemView.findViewById(R.id.button_edit_clothe);

            buttonEditClothe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null&& position!=RecyclerView.NO_POSITION){
                        listener.onClotheClickUpdate(clothes.get(position));
                    }
                }
            });

            buttonDeleteClothe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClotheClickDelete(clothes.get(position));
                    }
                }
            });
        }

    }

    public interface OnClotheClickListener {
        void onClotheClickDelete(Clothe clothe);
        void onClotheClickUpdate(Clothe clothe);
    }

    public void setOnItemClickListener(OnClotheClickListener clotheListener) {
        this.listener = clotheListener;
    }


}
