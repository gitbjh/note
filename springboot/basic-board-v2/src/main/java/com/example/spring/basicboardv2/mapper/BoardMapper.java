package com.example.spring.basicboardv2.mapper;

import com.example.spring.basicboardv2.model.Article;
import com.example.spring.basicboardv2.model.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void saveArticle(Article article);
    List<Article> getArticles(Paging page);
    int getArticleCnt();
    Article getArticleById(Long id);
    void updateArticle(Article article);
    void deleteBoardById(Long id);
}
