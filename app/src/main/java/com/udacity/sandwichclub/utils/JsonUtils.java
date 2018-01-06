package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_AKA = "alsoKnownAs";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin" ;
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE_PATH = "image";
    private static final String KEY_INGREDIENT = "ingredients";


    /* Parses the Json object into Sandwich object :
     *
      * Sample data -
      * {"name":{"mainName":"Bosna","alsoKnownAs":["Bosner"]},"placeOfOrigin":"Austria","description":"Bosna is a spicy Austrian fast food dish, said to have originated in either Salzburg or Linz. It is now popular all over western Austria and southern Bavaria.","image":"https://upload.wikimedia.org/wikipedia/commons/c/ca/Bosna_mit_2_Bratw%C3%BCrsten.jpg","ingredients":["White bread","Bratwurst","Onions","Tomato ketchup","Mustard","Curry powder"]}*/

    public static Sandwich parseSandwichJson(String json) {

        try {
            //Create JSONObject of whole json string
            JSONObject mainJsonObject = new JSONObject(json);

            //Get name JsonObject
            JSONObject nameJsonObject = mainJsonObject.getJSONObject(KEY_NAME);

            //Get mainName and aka values
            String mainName = nameJsonObject.getString(KEY_MAIN_NAME);
            JSONArray aka = nameJsonObject.getJSONArray(KEY_AKA);

            //Get PlaceOfOrigin
            String placeOfOrigin = mainJsonObject.getString(KEY_PLACE_OF_ORIGIN);

            //Get Description
            String description = mainJsonObject.getString(KEY_DESCRIPTION);

            //GET image path
            String imagePath = mainJsonObject.getString(KEY_IMAGE_PATH);

            //Get ingredient array
            JSONArray ingredientArray = mainJsonObject.getJSONArray(KEY_INGREDIENT);
            List<String> akaList = new ArrayList<>();

            //Iterate through the array of aka and add it to list
            for(int i=0; i<aka.length(); i++){
                String alsoKnownAs = aka.getString(i);
                akaList.add(alsoKnownAs);
            }

            //Iterate through the array of ingredients and add it to list

            List<String> ingredientList = new ArrayList<>();
            for (int i=0;i<ingredientArray.length(); i++){
                ingredientList.add(ingredientArray.getString(i));
            }

            Sandwich sandwich = new Sandwich(mainName,akaList,placeOfOrigin,description,imagePath,ingredientList);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
