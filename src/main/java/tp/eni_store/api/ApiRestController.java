package tp.eni_store.api;

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

    @GetMapping("api/article/get-all")
    public List<Article> getAllArticles(){

        ServiceResponse<List<Article>> articles = articleService.getAll();

        return articles.data;
    }

    @GetMapping("api/article/{id}")
    public Article getArticleById(@PathVariable int id){

        ServiceResponse<Article> article = articleService.getById(id);
        return article.data;
    }

    @PostMapping("api/article/save")
    public ServiceResponse<DAOSaveResult<Article>> saveArticle(@RequestBody Article article){

        ServiceResponse<DAOSaveResult<Article>> result = articleService.save(article);

        return result;
    }

    @DeleteMapping("api/article/{id}")
    public ServiceResponse<String> deleteArticleById(@PathVariable int id){

        ServiceResponse<String> response = articleService.deleteById(id);
        return response;
    }
}
