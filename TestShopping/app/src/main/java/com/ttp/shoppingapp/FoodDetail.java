package com.ttp.shoppingapp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.ttp.shoppingapp.Common.Common;
import com.ttp.shoppingapp.Database.Database;
import com.ttp.shoppingapp.Model.Food;
import com.ttp.shoppingapp.Model.Order;
import com.ttp.shoppingapp.Model.Rating;

import java.util.Arrays;

public class FoodDetail extends AppCompatActivity implements RatingDialogListener {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart, btnRating;
    ElegantNumberButton numberButton;
    RatingBar ratingBar;

    String foodId = "";

    FirebaseDatabase database;
    DatabaseReference food;
    DatabaseReference ratingTble;

    Food currentfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        database = FirebaseDatabase.getInstance();
        food = database.getReference("Food");
        ratingTble = database.getReference("Rating");

        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);
        food_description = findViewById(R.id.food_description);
        btnCart = findViewById(R.id.btnCart);
        btnRating = findViewById(R.id.btn_rating);
        ratingBar = findViewById(R.id.ratingBar);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });


        numberButton = findViewById(R.id.number_button);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentfood.getName(), numberButton.getNumber(), currentfood.getPrice(), currentfood.getDiscount()));

                Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }


        });

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() != null) foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()) {
            if (Common.isConnectionToInternet(getBaseContext())) {
                getDetailFood(foodId);
                getRatingFood(foodId);
            } else {
                Toast.makeText(FoodDetail.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void getRatingFood(String foodId) {
        Query foodRating = ratingTble.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count = 0, sum = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if(count !=0){
                float average = sum/count;
                ratingBar.setRating(average);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("별점주기")
                .setNegativeButtonText("닫기")
                .setNoteDescriptions(Arrays.asList("개선이 필요해요.", "조금 아쉬워요.", "보통이에요.", "마음에 들어요", "너무 마음에 들어요."))
                .setDefaultRating(1) //1~5점까지 default
                .setTitle("REVIEW")
                .setDescription("별점과 코멘트를 남겨주세요.")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("여기에 코멘트를 적어주세요.")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(FoodDetail.this)
                .show();


    }

    private void getDetailFood(String foodId) {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentfood = dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(currentfood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentfood.getName());
                food_price.setText(currentfood.getPrice());

                food_name.setText(currentfood.getName());
                food_description.setText(currentfood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    ;

    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        final Rating rating = new Rating(Common.currentuser.getPhone(), foodId, String.valueOf(value), comments);
        ratingTble.child(Common.currentuser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentuser.getPhone()).exists()) {
                    ratingTble.child(Common.currentuser.getPhone()).removeValue();
                    ratingTble.child(Common.currentuser.getPhone()).setValue(rating);
                } else {
                    ratingTble.child(Common.currentuser.getPhone()).setValue(rating);
                }
                Toast.makeText(FoodDetail.this, "평가 감사합니다.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
