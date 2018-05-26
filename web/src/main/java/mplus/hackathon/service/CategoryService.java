package mplus.hackathon.service;

import mplus.hackathon.models.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    Category findOne(Integer id);

    List<Category> findAll();

    void delete(Integer id);

    List<Category> findCategories(List<Integer> idList);

    List<String> filterByWord(String word);
}
