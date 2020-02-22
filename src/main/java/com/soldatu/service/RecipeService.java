package com.soldatu.service;

import com.soldatu.database.RepositoryService;
import com.soldatu.model.Recipe;
import com.soldatu.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by florin.soldatu on 06/02/2020.
 */

@Component
public class RecipeService {

    @Autowired
    private RepositoryService repositoryService;

    public List<Recipe> getRecipesForRequest(UserRequest request){
        List<Recipe> recipes = repositoryService.getAllRecipes();
        return filterByRequest(recipes, request);

    }

    private List<Recipe> filterByRequest(List<Recipe> recipes, UserRequest userRequest){
        String mealType = userRequest.getMealType();
        String occasion = userRequest.getOccasion();
        String diet = userRequest.getDiet();

        List<Recipe> filteredRecipes = recipes.stream()
                .filter(recipe -> {
                    boolean matches = true;

                    //filter by meal type
                    if (matches && mealType!=null){
                        matches = recipe.matchesMealType(mealType);
                    }

                    //filter by occasion
                    if (matches && occasion!=null){
                        matches = recipe.matchesOccasion(occasion);
                    }

                    //filter by special diet
                    if (matches && diet!=null){
                        matches = recipe.matchesSpecialDiets(diet);
                    }

                    //filter by desired ingredients
                    if (matches){
                        matches = recipe.containsIngredients(userRequest.getIngredients());
                    }

                    //filter by restricted ingredients
                    if (matches){
                        matches = recipe.doesNotContainIngredients(userRequest.getRestrictions());
                    }
                    return matches;
                })
                .collect(Collectors.toList());

        //only if we found exactly 1 match, we return the entire description, otherwise return only name+imageUrl
        if (filteredRecipes.size() > 1){
            filteredRecipes.forEach(filteredRecipe -> filteredRecipe.setDescription(null));
        }
        return filteredRecipes;
    }

    public List<Recipe> getRecipeByName(String name){
        List<Recipe> recipes = repositoryService.getAllRecipes();
        return filterByName(recipes, name);
    }

    private List<Recipe> filterByName(List<Recipe> recipes, String name){
        //if we find a match by name, we return that one
        for (Recipe recipe: recipes){
            if (recipe.getName().equalsIgnoreCase(name)){
                return Arrays.asList(recipe);
            }
        }

        //otherwise, search for names that might contain the given search term
        List<Recipe> filteredByName = recipes.stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        //only if we found exactly 1 match, we return the entire description, otherwise return only name+imageUrl
        if (filteredByName.size() > 1){
            filteredByName.forEach(filteredRecipe -> filteredRecipe.setDescription(null));
        }
        return filteredByName;
    }
}
