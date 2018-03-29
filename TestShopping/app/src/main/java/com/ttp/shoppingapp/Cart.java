package com.ttp.shoppingapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.ttp.shoppingapp.Remote.APIService;
import com.ttp.shoppingapp.Common.Common;
import com.ttp.shoppingapp.Database.Database;
import com.ttp.shoppingapp.Model.MyResponse;
import com.ttp.shoppingapp.Model.Notification;
import com.ttp.shoppingapp.Model.Order;
import com.ttp.shoppingapp.Model.Request;
import com.ttp.shoppingapp.Model.Sender;
import com.ttp.shoppingapp.Model.Token;
import com.ttp.shoppingapp.ViewHolder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView textTotalPrice;
    Button btnPlace;

    List<Order> carts = new ArrayList<>();
    CartAdapter adapter;

    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set fonts step1
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/cf.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());
        setContentView(R.layout.activity_cart);
        mService = Common.getFCMService();

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        textTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carts.size() > 0) showAlertDialog();
                else Toast.makeText(Cart.this, "장바구니가 비어 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListFood();


    }

    //set fonts step2
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address");

        LayoutInflater inflater = this.getLayoutInflater();
        View order_address_comment = inflater.inflate(R.layout.order_adress_comment, null);
        final MaterialEditText edtAddress = order_address_comment.findViewById(R.id.edtAddress);
        final MaterialEditText edtComment = order_address_comment.findViewById(R.id.edtComment);

        alertDialog.setView(order_address_comment);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),
                        edtAddress.getText().toString(),
                        textTotalPrice.getText().toString(),
                        "0",
                        edtComment.getText().toString(),
                        carts
                );

                //to Firebase
                String order_number = String.valueOf(System.currentTimeMillis());
                requests.child(order_number).setValue(request);
                new Database(getBaseContext()).cleanCart();
                sendNotificationOrder(order_number);
//                Toast.makeText(Cart.this, "order completed", Toast.LENGTH_SHORT).show();
//                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void sendNotificationOrder(final String order_number) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query data = tokens.orderByChild("isServerToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Token serverToken = postSnapShot.getValue(Token.class);
                    //create raw payload

                    Notification notification = new Notification("새로운 주문", "주문이 들어왔습니다. 주문번호:" + order_number);
                    Sender content = new Sender(serverToken.getToken(), notification);

                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code()==200)
                                    if (response.body().success == 1) {
                                        Toast.makeText(Cart.this, "주문완료", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else{
                                        Toast.makeText(Cart.this, "주문실패", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.e("ERROR", t.getMessage());

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadListFood() {
        carts = new Database(this).getCarts();
        adapter = new CartAdapter(carts, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        int total = 0;
        for (Order order : carts)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("kr", "KR");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        textTotalPrice.setText(fmt.format(total));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE)) deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        carts.remove(position);
        new Database(this).cleanCart();
        for (Order item : carts) new Database(this).addToCart(item);
        loadListFood();
    }
}
