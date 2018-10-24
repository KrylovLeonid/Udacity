package com.silvershadow.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silvershadow.sandwichclub.R;
import com.squareup.picasso.Picasso;
import com.silvershadow.sandwichclub.Model.Sandwich;
import com.silvershadow.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }


        Picasso.get().load(sandwich.getImage()).into(ingredientsIv);
        populateUI(sandwich);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI( Sandwich sandwich) {
        TextView originTV = findViewById(R.id.origin_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);

        originTV.setText(sandwich.getPlaceOfOrigin());
        descriptionTV.setText(sandwich.getDescription());

        String alsoKnown = sandwich.getAlsoKnownAs().toString();
        String ingredients = sandwich.getIngredients().toString();

        alsoKnownAsTV.setText(alsoKnown.substring(1,alsoKnown.length()-1));
        ingredientsTV.setText(ingredients.substring(1,ingredients.length()-1));


    }
}