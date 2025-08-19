package tp.eni_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOArticle;
import tp.eni_store.dao.DAOSaveResult;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    DAOArticle daoArticle;

    public ServiceResponse<List<Article>> getAll() {

        List<Article> articles = daoArticle.selectAll();

        return ServiceHelper.buildResponse("200", articles);


    }

    public ServiceResponse<Article> getById(int id) {

        Article article = daoArticle.selectById(id);

        if (article == null) {
            return ServiceHelper.buildResponse("703", article);
        }

        return ServiceHelper.buildResponse("202", article);
    }

    public ServiceResponse<String> deleteById(int id) {

        boolean result = daoArticle.deleteById(id);

        if (result) {
            return ServiceHelper.buildResponse("202", "Article deleted successfully");
        }

        return ServiceHelper.buildResponse("703", "The provided ID does not exist");

    }

    public ServiceResponse<DAOSaveResult<Article>> save(Article article) {

        DAOSaveResult result = daoArticle.insertOrUpdate(article);

        if(result.equals("updated")) {
            return ServiceHelper.buildResponse("202", result);
        }

        return ServiceHelper.buildResponse("703", result);
    }
}
