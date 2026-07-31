package com.chaoui.artico.controller;

import com.chaoui.artico.dto.request.ArticleDTORequest;
import com.chaoui.artico.dto.response.ArticleDTOResponse;
import com.chaoui.artico.entity.Article;
import com.chaoui.artico.service.ArticleService;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/all")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("")
    public Page<Article> getAllArticles(@RequestParam Integer page,
                                        @RequestParam Integer size,
                                        @RequestParam @Nullable Sort.Direction sort) {
        return articleService.getArticles(page, size, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articleService.getArticleById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<ArticleDTOResponse> createArticle(@RequestBody ArticleDTORequest article) {
        try {
            ArticleDTOResponse created = articleService.createArticle(article);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        try {
            return ResponseEntity.ok(articleService.updateArticle(id, article));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/hide")
    public ResponseEntity<Void> hideArticle(@PathVariable Long id, @RequestParam boolean hidden) {
        try {
            articleService.hideArticle(id, hidden);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
