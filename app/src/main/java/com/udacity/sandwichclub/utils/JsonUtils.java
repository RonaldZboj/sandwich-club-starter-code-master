package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        //If the JSON string is empty or null return early
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        //Try to parse JSON
        try {
            //Initialize JSONObject from JSON String
            JSONObject sandwichJson = new JSONObject(json);

            //Extract sandwich properties
            JSONObject name = sandwichJson.getJSONObject("name");
            String sandwichName = name.getString("mainName");

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> sandwichAlsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                sandwichAlsoKnownAsList.add(alsoKnownAsArray.getString(i));
            }

            String sandwichPlaceOfOrigin = sandwichJson.getString("placeOfOrigin");
            String sandwichDescription = sandwichJson.getString("description");
            String sandwichImage = sandwichJson.getString("image");

            JSONArray sandwichIngredientsArray = sandwichJson.getJSONArray("ingredients");
            List<String> sandwichIngredientsList = new ArrayList<>();
            for (int i = 0; i < sandwichIngredientsArray.length(); i++) {
                sandwichIngredientsList.add(sandwichIngredientsArray.getString(i));
            }
            return new Sandwich(sandwichName, sandwichAlsoKnownAsList, sandwichPlaceOfOrigin, sandwichDescription, sandwichImage, sandwichIngredientsList);

        } catch (JSONException e) {
            // Throw massage to logs when problem with section this above
            Log.e("JsonUtils", "Problem parsing the sandwich JSON results", e);
        }

        return null;
    }
}
