package tp.eni_store.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.service.ArticleService;
import tp.eni_store.service.ServiceResponse;

import java.util.List;

@RestController
public class ApiRestController {

    @Autowired
    ArticleService articleService;

    @Operation(summary = "Endpoint pour récupérer tous les articles")
    @GetMapping("api/article/get-all")
    public ServiceResponse<List<Article>> getAllArticles(){

        ServiceResponse<List<Article>> articles = articleService.getAll();

        return articles;
    }

    @Operation(summary = "Endpoint pour récupérer un article avec son id")
    @GetMapping("api/article/{id}")
    public Article getArticleById(@PathVariable int id){

        ServiceResponse<Article> article = articleService.getById(id);
        return article.data;
    }

    @Operation(summary = "Endpoint ajouter u nouvel article, ou mettre à jour un article existant")
    @PostMapping("api/article/save")
    public ServiceResponse<DAOSaveResult<Article>> saveArticle(@RequestBody Article article){

        ServiceResponse<DAOSaveResult<Article>> result = articleService.save(article);

        return result;
    }

    @Operation(summary = "Endpoint pour supprimer un article")
    @DeleteMapping("api/article/{id}")
    public ServiceResponse<Boolean> deleteArticleById(@PathVariable int id){

        ServiceResponse<Boolean> response = articleService.deleteById(id);
        return response;
    }
}
