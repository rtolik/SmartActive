package rtolik.smartactive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rtolik.smartactive.models.Category;
import rtolik.smartactive.repository.CategoryRepository;
import rtolik.smartactive.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findOne(Integer id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.delete(id);
    }

    @Override
    public List<Category> findCategories(List<Integer> idList) {

        List<Category> categories = new ArrayList<>();
        for (Category category :
                findAll()) {
            for (Integer id :
                    idList) {
                if (category.getId().equals(id))
                    categories.add(category);

            }
        }
        return categories;
    }

    @Override
    public List<String> filterByWord(String word) {

        List<String> categoryNames = new ArrayList<>();
        for (Category category : findAll()) {
            for (String splitedWorld : word.split(" ")) {
                if (!splitedWorld.equals("and")
                        && !splitedWorld.equals("и")
                        && !splitedWorld.equals("і")
                        && !splitedWorld.equals("ще")
                        && !splitedWorld.equals("ещё")
                        && !splitedWorld.equals("або")
                        && !splitedWorld.equals("или")
                        && !splitedWorld.equals(",")
                        && !splitedWorld.equals("."))
                    if (category.getName().equalsIgnoreCase(splitedWorld))
                        categoryNames.add(category.getName());


            }
        }
        return categoryNames;
    }

    @Override
    public Category findOrCreate(String name) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getName().equals(name))
                .findFirst().orElse(categoryRepository.save(new Category().setName(name)));
    }
}
