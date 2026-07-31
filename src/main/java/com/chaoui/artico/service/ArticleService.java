package com.chaoui.artico.service;

import com.chaoui.artico.dto.request.ArticleDTORequest;
import com.chaoui.artico.dto.response.ArticleDTOResponse;
import com.chaoui.artico.entity.Article;
import com.chaoui.artico.entity.Author;
import com.chaoui.artico.enums.ArticleStatus;
import com.chaoui.artico.repository.ArticleRepository;
import com.chaoui.artico.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
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

    public ArticleDTOResponse createArticle(ArticleDTORequest articleDTORequest) {

        if (articleDTORequest.getAuthorID() == null) {
            throw new IllegalArgumentException("Author id is required to create an articleDTORequest");
        }

        var authorId = articleDTORequest.getAuthorID();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + authorId));

        Article articleEntity = articleDTORequest.mapToEntity();
        articleEntity.setAuthor(author);


        try {
            var newArticle = articleRepository.save(articleEntity);
            ArticleDTOResponse response = new ArticleDTOResponse();
            response.mapFromEntity(newArticle);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void hideArticle(Long id, boolean hidden) {
        Article article = getArticleById(id);

        if(article != null) {
            try {
                if (hidden && article.getArticleStatus() != ArticleStatus.PRIVATE)
                    article.setArticleStatus(ArticleStatus.PRIVATE);
                else if(!hidden && article.getArticleStatus() == ArticleStatus.PRIVATE)
                    article.setArticleStatus(ArticleStatus.ON_PROGRESS);

                articleRepository.save(article);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new EntityNotFoundException("Article not found with id " + id);
        }

    }
}
