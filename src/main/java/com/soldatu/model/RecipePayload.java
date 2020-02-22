package com.soldatu.model;

import lombok.Data;

import java.util.List;

/**
 * Created by florin.soldatu on 08/02/2020.
 */
@Data
public class RecipePayload {
    private List<Recipe> items;

}
