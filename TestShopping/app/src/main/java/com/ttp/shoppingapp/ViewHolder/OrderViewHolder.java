package com.ttp.shoppingapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ttp.shoppingapp.R;


/**
 * Created by 0047TiTANplateform_ on 2018-01-26.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder
       // implements View.OnClickListener
{

    public Button btnEdit, btnRemove, btnDetail, btnDirection;
    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress;

//    private ItemClickListener itemClickListener;
//
//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress = itemView.findViewById(R.id.order_address);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderStatus = itemView.findViewById(R.id.order_status);


        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnRemove = itemView.findViewById(R.id.btnRemove_);
        btnDetail= itemView.findViewById(R.id.btnDetail_);
        btnDirection = itemView.findViewById(R.id.btn_Direction);

    //    itemView.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        itemClickListener.onClick(v, getAdapterPosition(), false);
//    }
}
