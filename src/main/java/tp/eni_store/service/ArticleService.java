package tp.eni_store.service;

import org.springframework.stereotype.Service;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.article.IDAOArticle;
import tp.eni_store.locale.LocaleHelper;

import java.util.List;

@Service
public class ArticleService {


    IDAOArticle daoArticle;
    LocaleHelper localeHelper;

    public ArticleService(IDAOArticle daoArticle, LocaleHelper localeHelper) {
        this.daoArticle = daoArticle;
        this.localeHelper = localeHelper;
    }

    public ServiceResponse<List<Article>> getAll() {

        List<Article> articles = daoArticle.selectAll();
        String message = localeHelper.i18n("ArticleService_GetAll_202");
        return ServiceHelper.buildResponse("202", message, articles);
    }

    public ServiceResponse<Article> getById(String id) {

        Article article = daoArticle.selectById(id);

        if (article == null) {
            String message = localeHelper.i18n("ArticleService_GetById_703");
            return ServiceHelper.buildResponse("703", message, null);
        }
        String message = localeHelper.i18n("ArticleService_GetById_202");

        return ServiceHelper.buildResponse("202", message, article);
    }

    public ServiceResponse<Article> deleteById(String id) {

        Article article = daoArticle.selectById(id);
        daoArticle.delete(article);

        if (article != null) {
            String message = localeHelper.i18n("ArticleService_DeleteById_202");
            return ServiceHelper.buildResponse("202", message, article);
        }

        String message = localeHelper.i18n("ArticleService_DeleteById_703");
        return ServiceHelper.buildResponse("703", message, article);

    }

    public ServiceResponse<DAOSaveResult<Article>> save(Article article) {

        DAOSaveResult<Article> result = daoArticle.insertOrUpdate(article);

        if(result.isUpdated) {
            String message = localeHelper.i18n("ArticleService_Save_202_Updated");
            return ServiceHelper.buildResponse("203", message, result);
        }

        String message = localeHelper.i18n("ArticleService_Save_202_Added");
        return ServiceHelper.buildResponse("202", message, result);
    }
}
