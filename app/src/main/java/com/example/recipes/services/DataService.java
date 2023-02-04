package com.example.recipes.services;

import android.os.StrictMode;

import com.example.recipes.models.Recipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataService {

    ArrayList<Recipes> arrRecipes = new ArrayList();

    public ArrayList<Recipes> getArrRecipes() {

        String sURL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=d29ff10c8b9441ef8fec12578002c7db&instructionsRequired=true&addRecipeInformation=true";

        URL url = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            url = new URL (sURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

                JsonArray rootobj = root.getAsJsonArray();

                for (JsonElement je : rootobj) {
                    JsonObject obj = je.getAsJsonObject();
                    JsonElement title = obj.get ("title");
                    JsonElement summary = obj.get ("summary");
                    JsonElement image = obj.get ("image");

                    String titleS = title.toString().replace("\"", "").trim();
                    String summaryS = summary.toString().replace("\"", "").trim();
                    String imageS = image.toString().replace("\"", "").trim();

                    ArrayList<String> arrInstructions = new ArrayList<String>();
                    JsonElement JInstructions = obj.get ("analyzedInstructions");
                    if (JInstructions != null) {
                        JsonArray instructionsArray = JInstructions.getAsJsonArray();
                        for( JsonElement j : instructionsArray) {
                            String s = j.toString().replace("\"", "").trim();
                            arrInstructions.add(s);
                        }
                    }
                    arrRecipes.add (new Recipes(titleS , summaryS, arrInstructions, imageS));

                }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrRecipes;
    }


}
