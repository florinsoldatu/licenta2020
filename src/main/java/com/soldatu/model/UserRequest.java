package com.soldatu.model;

import lombok.Data;

import java.util.List;

/**
 * Created by florin.soldatu on 06/02/2020.
 */
@Data
public class UserRequest {
    private String mealType;
    private String occasion;
    private String diet;
    private List<String> ingredients;
    private List<String> restrictions;
}
