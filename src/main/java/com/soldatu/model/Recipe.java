package com.soldatu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by florin.soldatu on 06/02/2020.
 */
@Data
public class Recipe {

    private String name;
    private String description;
    private String imageUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> mealTypes = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> occasions = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> specialDiets = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> ingredients = new ArrayList<>();

    public boolean matchesMealType(String mealType){
        return mealType != null && mealTypes.stream().anyMatch(type -> mealType.equalsIgnoreCase(type));
    }

    public boolean matchesOccasion(String occasion){
        return occasion != null && occasions.stream().anyMatch(type -> occasion.equalsIgnoreCase(type));
    }

    public boolean matchesSpecialDiets(String diet){
        return diet != null && specialDiets.stream().anyMatch(type -> diet.equalsIgnoreCase(type));
    }

    public boolean containsIngredients(List<String> desiredIngredients){
        if (desiredIngredients == null){
            return true;
        }
        for (String desiredIngredient: desiredIngredients){
            if (!ingredients.contains(desiredIngredient.toLowerCase())){
                return false;
            }
        }
        return true;
    }

    public boolean doesNotContainIngredients(List<String> restrictedIngredients){
        if (restrictedIngredients == null){
            return true;
        }
        for(String restriction: restrictedIngredients){
            if (ingredients.contains(restriction.toLowerCase())){
                return false;
            }

        }
        return true;
    }
}
