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

    TextView mAlsoKnownAsTextView;
    TextView mPlaceOfOriginTextView;
    TextView mIngredientsTextView;
    TextView mDescriptionTextView;

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
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);


        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        String alsoKnownString = "";
        if (alsoKnownAsList.isEmpty()) {
            mAlsoKnownAsTextView.setText(R.string.no_info);
        } else {
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                alsoKnownString = alsoKnownString + alsoKnownAsList.get(i) + "\n";
            }
            mAlsoKnownAsTextView.setText(alsoKnownString);
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginTextView.setText(R.string.no_info);
        } else {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        List<String> ingredientsList = sandwich.getIngredients();
        String ingredientsString = "";
        if (ingredientsList.isEmpty()) {
            mIngredientsTextView.setText(R.string.no_info);
        } else {
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredientsString = ingredientsString + ingredientsList.get(i) + "\n";
            }
            mIngredientsTextView.setText(ingredientsString);
        }

        if (sandwich.getDescription().isEmpty()) {
            mDescriptionTextView.setText(R.string.no_info);
        } else {
            mDescriptionTextView.setText(sandwich.getDescription());
        }
    }
}
