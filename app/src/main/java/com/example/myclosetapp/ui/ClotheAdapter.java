package com.example.myclosetapp.ui;


import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.utils.FormatDateTime;
import com.example.myclosetapp.utils.ImageConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClotheAdapter extends RecyclerView.Adapter<ClotheAdapter.ClotheHolder> {
    private List<Clothe> clothes = new ArrayList<>();


    @NonNull
    @Override
    public ClotheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.clothe_item,parent,false);
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
//        Date date = FormatDateTime.fromTimestampToDate( currClothe.getDateLastWorn().toString());
//        holder.dateLastWorn.setText(date.toString());
        if(currClothe.getToClean()) {
            holder.textViewIsClean.setText("Βρώμικο");
        }
        else {
            holder.textViewIsClean.setText("Καθαρό");
        }
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public void setClothe(List<Clothe> clothes){
        this.clothes=clothes;
        notifyDataSetChanged();
    }



    class ClotheHolder extends RecyclerView.ViewHolder{
        private TextView textViewColor;
        private TextView textViewCategory;
        private TextView textViewDescription;
        private TextView textViewIsClean;
        private ImageView imageViewClothe;
        private TextView  textViewDateLastWorn;

        public ClotheHolder(@NonNull View itemView) {
            super(itemView);
            textViewColor= itemView.findViewById(R.id.textview_color);
            textViewCategory=itemView.findViewById(R.id.textview_category);
            textViewDescription=itemView.findViewById(R.id.textview_description);
            textViewIsClean=itemView.findViewById(R.id.textview_cleanstate);
            imageViewClothe=itemView.findViewById(R.id.clothe_image);
            textViewDateLastWorn=itemView.findViewById(R.id.text_view_date_last_worn);
        }

    }

}
