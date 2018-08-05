package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName, image, placeOfOrigin, description;
        List<String> alsoKnownAs_List = new ArrayList<>();
        List<String> ingredients_List = new ArrayList<>();
        Sandwich sandwich = new Sandwich();

        try {
            // Create JSONObject
            JSONObject jsonObject = new JSONObject(json);

            // Call name JSONObject base on JSON format
            JSONObject name  = jsonObject.getJSONObject("name");

            // Get the value of MainName from Name JSONObject
            mainName = name.getString("mainName");

                // Get the value of alsoKnownAs from Name JSONObject
                if(name.getString("alsoKnownAs").matches(".*\\w.*")){
                    JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
                    for(int i = 0 ; i < alsoKnownAs.length() ; i++){
                        String knownname = alsoKnownAs.getString(i);
                        alsoKnownAs_List.add(knownname);
                    }
                } else {
                    alsoKnownAs_List.add("Unknown");
                }

            // Get the value of PlaceOfOrigin from JSONObject
            if(!(jsonObject.getString("placeOfOrigin").trim().length() > 0)){
                    placeOfOrigin = "Unknown";
            } else {
                placeOfOrigin = jsonObject.getString("placeOfOrigin");
            }

            // Get the value of Description from JSONObject
            description = jsonObject.getString("description");

            // Get the value of Ingredients from Name JSONObject
            if(!jsonObject.isNull("ingredients")){
                JSONArray ingredients = jsonObject.getJSONArray("ingredients");
                for(int i = 0 ; i < ingredients.length() ; i++){
                    String ingredient = ingredients.getString(i);
                    ingredients_List.add(ingredient);
                }
            }


            // Get the url of image from JSONObject
            image = jsonObject.getString("image");

            // Set the JSON object to Sandwich object
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs_List);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setIngredients(ingredients_List);
            sandwich.setImage(image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
