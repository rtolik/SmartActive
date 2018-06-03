package rtolik.smartactive.service;

import rtolik.smartactive.models.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    Category findOne(Integer id);

    List<Category> findAll();

    void delete(Integer id);

    List<Category> findCategories(List<Integer> idList);

    List<String> filterByWord(String word);

    Category findOrCreate(String name);
}
