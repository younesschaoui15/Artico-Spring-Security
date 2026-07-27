package com.chaoui.artico.service;

import com.chaoui.artico.entity.Article;
import com.chaoui.artico.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Page<Article> getArticles(int page, int size, Sort.Direction sort) {
        sort = sort == null ? Sort.Direction.ASC : sort;

        System.out.println("# >> "+ page +" - "+ size +" - "+ sort);
        return articleRepository.findAll(PageRequest.of(page, size, sort, "id"));
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + id));
    }

    public Article createArticle(Article article) {
        article.setId(null);
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article article) {
        Article existing = getArticleById(id);

        existing.setTitle(article.getTitle());
        existing.setCategory(article.getCategory());
        existing.setContent(article.getContent());
        existing.setArticleStatus(article.getArticleStatus());
        existing.setAuthor(article.getAuthor());

        return articleRepository.save(existing);
    }

    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found with id " + id);
        }
        articleRepository.deleteById(id);
    }
}
