package com.soldatu.database;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.soldatu.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by florin.soldatu on 04/02/2020.
 */

@Component
public class RepositoryService {

    protected static final String TABLE_NAME = "Recipes";

    private static final String NAME_FIELD = "Name";
    private static final String DESCRIPTION_FIELD = "Description";
    private static final String IMAGE_URL_FIELD = "imageUrl";
    private static final String DIETS_FIELD = "specialDiets";
    private static final String OCCASIONS_FIELD = "occasions";
    private static final String INGREDIENTS_FIELD = "ingredients";
    private static final String MEAL_TYPES_FIELD = "mealTypes";

    public static List<Recipe> getAllRecipes() {
        Map<String, AttributeValue> lastKeyEvaluated = null;
        List<Recipe> recipes = new ArrayList<>();
        do {
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(TABLE_NAME)
                    .withLimit(10)
                    .withExclusiveStartKey(lastKeyEvaluated);

            ScanResult result = connectToDynamo().scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                Recipe recipe = new Recipe();

                AttributeValue nameAttribute = item.get(NAME_FIELD);
                recipe.setName(nameAttribute.getS());

                AttributeValue descriptionAttribute = item.get(DESCRIPTION_FIELD);
                recipe.setDescription(descriptionAttribute.getS());

                AttributeValue imageUrlAttribute = item.get(IMAGE_URL_FIELD);
                recipe.setImageUrl(imageUrlAttribute.getS());

                List<String> specialDiets = new ArrayList<>();
                AttributeValue dietsAttribute = item.get(DIETS_FIELD);
                dietsAttribute.getL().forEach(attributeValue -> specialDiets.add(attributeValue.getS()));
                recipe.setSpecialDiets(specialDiets);

                List<String> occasions = new ArrayList<>();
                AttributeValue occasionsAttribute = item.get(OCCASIONS_FIELD);
                occasionsAttribute.getL().forEach(attributeValue -> occasions.add(attributeValue.getS()));
                recipe.setOccasions(occasions);

                List<String> ingredients = new ArrayList<>();
                AttributeValue ingredientsAttribute = item.get(INGREDIENTS_FIELD);
                ingredientsAttribute.getL().forEach(attributeValue -> ingredients.add(attributeValue.getS()));
                recipe.setIngredients(ingredients);

                List<String> mealTypes = new ArrayList<>();
                AttributeValue mealTypesAttribute = item.get(MEAL_TYPES_FIELD);
                mealTypesAttribute.getL().forEach(attributeValue -> mealTypes.add(attributeValue.getS()));
                recipe.setMealTypes(mealTypes);

                recipes.add(recipe);
            }
            lastKeyEvaluated = result.getLastEvaluatedKey();
        } while (lastKeyEvaluated != null);
        return recipes;
    }

    protected static AmazonDynamoDB connectToDynamo(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider
                        (new BasicAWSCredentials("AKIATH5GYMGLDJXAR6ED","yC1J/xR1y8f/3Ai5aDmYcu275VGxKD+mpaYRDbiY")))
                .build();
        return client;
    }
}
