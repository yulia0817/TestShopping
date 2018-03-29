package com.ttp.getapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ttp.getapp.Common.Common;
import com.ttp.getapp.Interface.ItemClickListenter;
import com.ttp.getapp.R;

/**
 * Created by 0047TiTANplateform_ on 2018-03-16.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements
        //View.OnClickListener,
//        View.OnLongClickListener,
        View.OnCreateContextMenuListener{


        public Button btnEdit, btnRemove, btnDetail, btnDirection;
    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress;
 //   private ItemClickListenter itemClickListener;

/*
    public void setItemClickListener(ItemClickListenter itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
*/

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

   //     itemView.setOnCreateContextMenuListener(this);
    //    itemView.setOnClickListener(this);
    }

    /*
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menu.setHeaderTitle("메뉴");
        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
        menu.add(0,1,getAdapterPosition(), Common.DELETE);

    }
//
//    @Override
//    public boolean onLongClick(View v) {
//        itemClickListener.onClick(v, getAdapterPosition(), true);
//        return true;
//    }
}
