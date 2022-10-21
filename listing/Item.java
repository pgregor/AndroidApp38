package com.example.androidproject.listing;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Item {
    private Integer id;
    private Type type;
    private String name;
    private String description;
    private ArrayList<String> imageFiles;
    private Condition condition;
    private ArrayList<Style> styles;
    private ArrayList<Material> materials;
    private Boolean availability;

    public Item() {
        this.id = null;
        this.type = null;
        this.name = "";
        this.description = "";
        this.imageFiles = new ArrayList<>();
        this.condition = null;
        this.styles = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.availability = false;
    }


    /**
     * Getters
     */

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getImageFiles() {
        return imageFiles;
    }

    public Condition getCondition() {
        return condition;
    }

    public ArrayList<Style> getStyles() {
        return styles;
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public Boolean getAvailability() {
        return availability;
    }


    /**
     * Methods
     */

    public Item getItemFromListingId(@NonNull Context context, Integer listingId) {
        // read from JSON file
        Gson gson = new Gson();
        JsonReader jsonReader = null;
        ArrayList<Item> items = new ArrayList<>();
        try {
            jsonReader = new JsonReader(new InputStreamReader(
                    context.getAssets().open("item_data.json")
            ));
            items = gson.fromJson(jsonReader
                    , new TypeToken<ArrayList<Item>>(){}.getType()
            );
            System.out.println("!!!!! " + items);
            System.out.println("!!!!! " + items.toString());
            System.out.println("!!!!! " + items.get(0));

            int n = items.size();
            for (int i=0; i<n; i++) {
                Item currentItem = items.get(i);
                if (currentItem.id.equals(listingId)) {
                    return currentItem;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Enums
     *
     * By default, Gson serializes enums by name in lowercase.
     * Gson can serialize and deserialize enums using the @SerializedName annotation.
     */


    public enum Type {
        @SerializedName("armchair")
        ARMCHAIR,
        @SerializedName("officechair")
        OFFICECHAIR,
        @SerializedName("sofa")
        SOFA,
        @SerializedName("decorativeitem")
        DECORATIVEITEM,
        @SerializedName("coffeetable")
        COFFEETABLE,
        @SerializedName("diningtable")
        DININGTABLE,
        @SerializedName("sculpture")
        SCULPTURE,
        @SerializedName("wallart")
        WALLART,
        @SerializedName("diningchair")
        DININGCHAIR,
    }

    public enum Condition {
        @SerializedName("excellent")
        EXCELLENT,
        @SerializedName("good")
        GOOD,
        @SerializedName("acceptable")
        ACCEPTABLE,
        @SerializedName("poor")
        POOR,
        @SerializedName("verypoor")
        VERYPOOR
    }

    public enum Material {
        @SerializedName("ash")
        ASH,
        @SerializedName("bamboo")
        BAMBOO,
        @SerializedName("brass")
        BRASS,
        @SerializedName("card")
        CARD,
        @SerializedName("ceramic")
        CERAMIC,
        @SerializedName("concrete")
        CONCRETE,
        @SerializedName("elm")
        ELM,
        @SerializedName("fabric")
        FABRIC,
        @SerializedName("foam")
        FOAM,
        @SerializedName("glass")
        GLASS,
        @SerializedName("granite")
        GRANITE,
        @SerializedName("leather")
        LEATHER,
        @SerializedName("limestone")
        LIMESTONE,
        @SerializedName("marble")
        MARBLE,
        @SerializedName("metal")
        METAL,
        @SerializedName("mdf")
        MDF,
        @SerializedName("paper")
        PAPER,
        @SerializedName("pine")
        PINE,
        @SerializedName("plastic")
        PLASTIC,
        @SerializedName("porcelain")
        PORCELAIN,
        @SerializedName("rattan")
        RATTAN,
        @SerializedName("rubber")
        RUBBER,
        @SerializedName("stone")
        STONE,
        @SerializedName("wood")
        WOOD,
    }

    public enum Shape {
        @SerializedName("circle")
        CIRCLE,
        @SerializedName("ellipse")
        ELLIPSE,
        @SerializedName("rectangle")
        RECTANGLE,
        @SerializedName("square")
        SQUARE
    }

    public enum Style {
        @SerializedName("artdeco")
        ARTDECO,
        @SerializedName("bohemian")
        BOHEMIAN,
        @SerializedName("classical")
        CLASSICAL,
        @SerializedName("coastal")
        COASTAL,
        @SerializedName("contemporary")
        CONTEMPORARY,
        @SerializedName("country")
        COUNTRY,
        @SerializedName("cottage")
        COTTAGE,
        @SerializedName("ecclectic")
        ECCLECTIC,
        @SerializedName("farmhouse")
        FARMHOUSE,
        @SerializedName("hamptons")
        HAMPTONS,
        @SerializedName("industrial")
        INDUSTRIAL,
        @SerializedName("maximalist")
        MAXIMALIST,
        @SerializedName("midcentury")
        MIDCENTURY,
        @SerializedName("minimalist")
        MINIMALIST,
        @SerializedName("minimalist")
        MONOCHROME,
        @SerializedName("modern")
        MODERN,
        @SerializedName("nautical")
        NAUTICAL,
        @SerializedName("nordic")
        NORDIC,
        @SerializedName("oriental")
        ORIENTAL,
        @SerializedName("provincial")
        PROVINCIAL,
        @SerializedName("retro")
        RETRO,
        @SerializedName("rustic")
        RUSTIC,
        @SerializedName("shabbychic")
        SHABBYCHIC,
        @SerializedName("transitional")
        TRANSITIONAL,
        @SerializedName("vintage")
        VINTAGE,
    }


}
