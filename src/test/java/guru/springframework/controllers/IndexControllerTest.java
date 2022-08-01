package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;




import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

@BeforeEach
void initService() throws Exception{

    indexController = new IndexController(recipeService);
   //MockitoAnnotations.openMocks(this);

}

    @Test
    void getIndexPage() throws  Exception {

    //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(4L);

        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor =ArgumentCaptor.forClass(Set.class);
        //when
        String viewName = indexController.getIndexPage(model);


        //then
   assertEquals(indexController.getIndexPage(model), "index");
   verify(recipeService, times (2)).getRecipes();
   verify(model, times(2)).addAttribute(eq("recipes"), argumentCaptor.capture());

   Set<Recipe> setInController = argumentCaptor.getValue();
   assertEquals(2, setInController.size());

        }

    @Test
    void testMockMVC() throws  Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void GetModel() throws Exception
    {



    }


    //Model is called one time
    //get recipes is called once
    //get index will return a string index.
}