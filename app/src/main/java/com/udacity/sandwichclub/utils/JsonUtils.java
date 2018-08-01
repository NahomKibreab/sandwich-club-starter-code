package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        Sandwich sandwich = new Sandwich();

        try {
            // Create JSONObject
            JSONObject jsonObject = new JSONObject(json);

            // Call name JSONObject base on JSON format
            JSONObject name  = jsonObject.getJSONObject("name");

            // Get the value of MainName from Name JSONObject
            mainName = name.getString("mainName");

            // Get the url of image from JSONObject
            String image = jsonObject.getString("image");

            // Set the JSON object to Sandwich object
            sandwich.setMainName(mainName);
            sandwich.setImage(image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
