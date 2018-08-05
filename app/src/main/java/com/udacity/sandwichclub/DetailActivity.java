package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Creating instance of Sandwich object
    Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        // Call JsonUtils for JSON parsing
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        // Check if there is Image resource or make Image placeholder
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.error_image)         // optional
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // Initialize view objects
        TextView origin = findViewById(R.id.origin_tv);
        origin.setText(sandwich.getMainName());

        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAs_Lists = sandwich.getAlsoKnownAs();
        StringBuilder stringBuilder = new StringBuilder();

        // Check the size and makes separate comma for each Ingredients
        int checkComma = alsoKnownAs_Lists.size();
        for (int i = 0; i < alsoKnownAs_Lists.size(); i++) {
            String alsoKnownAs_List = alsoKnownAs_Lists.get(i);
            if ( i == checkComma -1){
                stringBuilder.append(alsoKnownAs_List);
            } else {
                stringBuilder.append(alsoKnownAs_List).append(", ");
            }
            alsoKnownAs.setText(stringBuilder);
        }

        TextView placeOfOrigin = findViewById(R.id.detail_place_of_origin);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        TextView ingredients = findViewById(R.id.ingredients_tv);
        List<String> ingredients_Lists = sandwich.getIngredients();
        StringBuilder stringBuilder2 = new StringBuilder();

        // Check the size and makes separate comma for each Ingredients
        int checkComma2 = ingredients_Lists.size();
        for (int i = 0; i < ingredients_Lists.size(); i++) {
            String ingredients_List = ingredients_Lists.get(i);
            if (i == checkComma2 - 1) {
                stringBuilder2.append(ingredients_List).append(". ");
            } else {
                stringBuilder2.append(ingredients_List).append(", ");
            }
            ingredients.setText(stringBuilder2);
        }
    }
}
