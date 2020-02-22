package com.soldatu.controller;

import com.soldatu.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.soldatu.utils.Constants.NAME;

/**
 * Created by florin.soldatu on 09/02/2020.
 */
@Slf4j
@RestController
@RequestMapping(value= "/")
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(method = RequestMethod.GET, value = "/recipe")
    @ResponseStatus(value = HttpStatus.OK)
    public void getRecipe(@RequestParam(name = NAME) String name) {
        log.info("Get recipe by name request received: name :  " + name);

        recipeService.getRecipeByName(name);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
    @ResponseBody
    public void handleRuntimeException(RuntimeException e) {
        System.out.println("got exception " + e);
    }
}
