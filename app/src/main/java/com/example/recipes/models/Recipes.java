package com.example.recipes.models;

import java.util.ArrayList;

public class Recipes {

    private String title;
    private String summary;
    private ArrayList<String> analyzedInstructions;
    private String image;

    public Recipes(String title, String summary, ArrayList<String> analyzedInstructions, String image) {
        this.title = title;
        this.summary = summary;
        this.analyzedInstructions = analyzedInstructions;
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAnalyzedInstructions(ArrayList<String> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<String> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public String getImage() {
        return image;
    }
}
