package com.abdproject.gestionstock.services.Implementation;


import com.abdproject.gestionstock.dto.CategoryDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;

import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.Article;
import com.abdproject.gestionstock.model.Category;
import com.abdproject.gestionstock.model.LigneCommandeClient;
import com.abdproject.gestionstock.repository.ArticleRepository;
import com.abdproject.gestionstock.repository.CategoryRepository;
import com.abdproject.gestionstock.services.CategoryService;

import com.abdproject.gestionstock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository) {this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("la catégorie n'est pas valide {}", dto);
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(dto)
                )
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if (id == null){
            log.error("Category id is null");
            return null;
        }

        Optional<Category> category = categoryRepository.findById(id);


        return Optional.of(CategoryDto.fromEntity(category.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucune catégorie avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryDto findByCode(String code) {
        {
            if (StringUtils.hasLength(code)){
                log.error("Categorie CODE is null");
                return null;
            }
            Optional<Category> category = categoryRepository.findCategoryByCode(code);


            return Optional.of(CategoryDto.fromEntity(category.get())).orElseThrow(() ->
                    new EntityNotFoundException("Aucune categorie avec ce CODE = " + code + "n'a été trouvé dans la BDD",
                            ErrorCodes.CATEGORY_NOT_FOUND));    }

    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Category id is null");
            return;
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une category deja utilisé par des articles", ErrorCodes.CATEGORY_IN_USE);
        }
        categoryRepository.deleteById(id);
    }
}
