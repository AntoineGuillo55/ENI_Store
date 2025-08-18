package tp.eni_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOArticle;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    DAOArticle daoArticle;

    public ServiceResponse<List<Article>> getAll() {

        ServiceResponse<List<Article>> response = new ServiceResponse<>();
        List<Article> articles = daoArticle.selectAll();

        response.code = "202";
        response.data = articles;

        return response;
    }

    public ServiceResponse<Article> getById(int id) {

        ServiceResponse<Article> response = new ServiceResponse<>();
        Article article = daoArticle.selectById(id);

        if (article != null) {
            response.code = "202";
            response.data = article;
        } else {
            response.code = "703";
            response.data = article;
        }

        return response;
    }

    public ServiceResponse<Article> deleteById(int id) {

        boolean result = daoArticle.deleteById(id);

        ServiceResponse<Article> response = new ServiceResponse<>();

        if (result) {
            response.code = "202";
        } else  {
            response.code = "703";
        }

        return response;
    }
}
