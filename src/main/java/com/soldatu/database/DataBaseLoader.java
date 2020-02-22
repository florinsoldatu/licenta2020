package com.soldatu.database;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soldatu.model.Recipe;
import com.soldatu.model.RecipePayload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by florin.soldatu on 09/02/2020.
 */
public class DataBaseLoader {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger l = Logger.getLogger("My Log");

    private static final String JSON_FILE = "/Users/crenguta.soldatu/Other/HelpMeCook/src/main/resources/recipeList.json";


    private static List<Recipe> readJsonFile() {

        try {
            File file = new File(JSON_FILE);
            RecipePayload recipesPayload = objectMapper.readValue(file, RecipePayload.class);
            return recipesPayload.getItems();
        } catch (IOException e) {
            String errMessage = "There was an exception when reading the json file " + e;
            l.severe(errMessage);
            System.out.println(errMessage);
            return new ArrayList<>();
        }
    }

    private static void loadDataBase(List<Recipe> recipes, Table dynamoDBTable){
        for (Recipe recipe : recipes){
            Item item = new Item()
                    .withPrimaryKey("Name", recipe.getName())
                    .withString("Description", recipe.getDescription())
                    .withString("imageUrl", recipe.getImageUrl())
                    .withList("mealTypes", recipe.getMealTypes())
                    .withList("occasions", recipe.getOccasions())
                    .withList("specialDiets", recipe.getSpecialDiets())
                    .withList("ingredients", recipe.getIngredients());

            dynamoDBTable.putItem(item);
        }
    }

    public static void main(String[] args) {
        List<Recipe> recipes = readJsonFile();

        DynamoDB dynamoDB = new DynamoDB(RepositoryService.connectToDynamo());
        Table dynamoDBTable = dynamoDB.getTable(RepositoryService.TABLE_NAME);

        try {
            loadDataBase(recipes, dynamoDBTable);
        } catch (AmazonServiceException e) {
            String errMessage = "There was an exception when loading the dynamo database " + e.getErrorMessage();
            l.severe(errMessage);
            System.out.println(errMessage);
        }
    }

}
