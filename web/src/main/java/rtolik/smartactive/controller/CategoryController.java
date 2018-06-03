package rtolik.smartactive.controller;

import org.springframework.web.bind.annotation.*;
import rtolik.smartactive.config.websocket.ChatHandler;
import rtolik.smartactive.config.websocket.utils.model.CategoryMessage;
import rtolik.smartactive.models.Category;
import rtolik.smartactive.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@RequestMapping("/categories")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    private ResponseEntity<Category> add(@RequestParam(required = false) String name){
        if(name == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Category category = categoryService.save(new Category(name));
        ChatHandler.categoryMessages.add(new CategoryMessage(category));
        return  new ResponseEntity<>(category,HttpStatus.OK);
    }

    @GetMapping("/findOne/[id]")
    private ResponseEntity<Category> findOne(@PathVariable Integer id){
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(categoryService.findOne(id),HttpStatus.OK);
    }

    @GetMapping("/findAll")
    private ResponseEntity<List<Category>> findAll(){
        if(categoryService.findAll()==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }

    @PostMapping("/filterByWord")
    private ResponseEntity<List<String>> filterByWord(@RequestParam String word){

        if(categoryService.findAll()==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.filterByWord(word),HttpStatus.OK);
    }

    @GetMapping("/findAllByIds")
    private ResponseEntity<List<Category>> findByIds(@RequestParam(required = false) List<Integer> ids){
        if(categoryService.findCategories(ids)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.findCategories(ids),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity delete(@PathVariable Integer id){
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
