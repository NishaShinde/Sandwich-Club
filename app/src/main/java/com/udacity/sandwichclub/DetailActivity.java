package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Log.d(TAG, json);

            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ImageView sandwichImageView = findViewById(R.id.image_iv);
        TextView nameTextView = findViewById(R.id.name_tv);
        TextView akaTextView = findViewById(R.id.aka_tv);
        TextView placeOfOriginTextView = findViewById(R.id.place_of_origin);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientTextView = findViewById(R.id.ingredients_tv);


        String mainName = sandwich.getMainName();
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();
        String not_available = getString(R.string.not_available);
        String ingredients = "";

        for (String ingredient: sandwich.getIngredients()){
            ingredients = ingredients + ingredient +"\n";
        }

        String akas = "";

        for (String aka: sandwich.getAlsoKnownAs()){
            akas = akas + aka +"\n";
        }

        if(isEmpty(mainName)){
            mainName = isEmpty(mainName)?not_available:mainName;
        }
        if(isEmpty(placeOfOrigin)){
            placeOfOrigin = not_available;
        }
        if(isEmpty(description)){
            description = not_available;
        }
        if(isEmpty(ingredients)){
            ingredients = not_available;
        }
        if (isEmpty(akas)){
            akas = not_available;
        }


        nameTextView.setText(mainName);
        placeOfOriginTextView.setText(placeOfOrigin );
        descriptionTextView.setText(description);
        akaTextView.setText(akas);
        ingredientTextView.setText(ingredients);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.loading_image)
                .into(sandwichImageView);
    }

    private boolean isEmpty(String detail){
        if(detail.equals("")){
            return true;
        }
        return false;
    }
}
