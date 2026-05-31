package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.model.Category;

import java.util.List;

@Dependent
public class CategoryService {
    @Inject
    EntityManager em;

    @Transactional
    public List<Category> getAllCategories() {
        return em.createNamedQuery(Category.GET_ALL_CATEGORIES, Category.class).getResultList();
    }

    @Transactional
    public Category getCategoryById(Long id) {
        return em.createNamedQuery("Category.findById", Category.class).setParameter("id", id).getSingleResult();
    }

    @Transactional
    public Category createCategory(Category category) {
        return em.merge(category);
    }

    @Transactional
    public List<Category> getCategoriesByCourseId(Long courseId) {
        return em.createNamedQuery("Category.findByCourseId", Category.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}
