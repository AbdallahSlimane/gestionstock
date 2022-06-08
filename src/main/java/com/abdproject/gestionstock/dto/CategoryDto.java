package com.abdproject.gestionstock.dto;


import com.abdproject.gestionstock.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class CategoryDto {

    private Integer id;

    private String code;

    private String descritpion;

    private Integer identreprise;


    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category){
        if(category == null){
            return null;
            // TODO throw an exception
        }

        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .descritpion(category.getDescritpion())
                .identreprise(category.getIdentreprise())
                .articles(
                        category.getArticles() != null ?
                        category.getArticles().stream()
                                .map(ArticleDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto){
        if (categoryDto == null){
            // TODO throw an exception

        }

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDescritpion(categoryDto.getDescritpion());
        category.setIdentreprise(categoryDto.getIdentreprise());
        category.setArticles(categoryDto.getArticles() != null ?
                categoryDto.getArticles().stream()
                        .map(ArticleDto::toEntity)
                        .collect(Collectors.toList()) : null);

        return category;
    }
}
