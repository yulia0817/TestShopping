package com.ttp.shoppingapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttp.shoppingapp.Interface.ItemClickListener;
import com.ttp.shoppingapp.R;


/**
 * Created by 0047TiTANplateform_ on 2018-01-25.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView foodName, foodPrice;
    public ImageView foodImage, favImage;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        foodName = itemView.findViewById(R.id.food_name);
        foodPrice = itemView.findViewById(R.id.food_price);
        foodImage = itemView.findViewById(R.id.food_image);
        favImage = itemView.findViewById(R.id.fav_image);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
       itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
