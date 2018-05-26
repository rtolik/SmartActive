package mplus.hackathon.controller;

import mplus.hackathon.config.websocket.ChatHandler;
import mplus.hackathon.config.websocket.utils.model.CategoryMessage;
import mplus.hackathon.models.Category;
import mplus.hackathon.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@RequestMapping("/categories")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    private ResponseEntity<Category> add(@RequestParam(required = false) String name){
        if(name == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Category category = categoryService.save(new Category(name));
        ChatHandler.categoryMessages.add(new CategoryMessage(category));
        return  new ResponseEntity<>(category,HttpStatus.OK);
    }

    @RequestMapping(value = "/findOne",method = RequestMethod.GET)
    private ResponseEntity<Category> findOne(@RequestParam(required = false) Integer id){
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(categoryService.findOne(id),HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    private ResponseEntity<List<Category>> findAll(){

        if(categoryService.findAll()==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }

    @RequestMapping(value = "/filterByWord",method = RequestMethod.POST)
    private ResponseEntity<List<String>> filterByWord(@RequestParam String word){

        if(categoryService.findAll()==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.filterByWord(word),HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllByIds",method = RequestMethod.GET)
    private ResponseEntity<List<Category>> findByIds(@RequestParam(required = false) List<Integer> ids){
        if(categoryService.findCategories(ids)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.findCategories(ids),HttpStatus.OK);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    private ResponseEntity delete(@RequestParam Integer id){
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
