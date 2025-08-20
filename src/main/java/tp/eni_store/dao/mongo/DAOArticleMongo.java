package tp.eni_store.dao.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOResultHelper;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.IDAOArticle;

import java.util.List;
import java.util.Optional;

@Profile("mongo")
@Component
public class DAOArticleMongo implements IDAOArticle {

    ArticleMongoRepository articleMongoRepository;

    public DAOArticleMongo(ArticleMongoRepository articleMongoRepository) {
        this.articleMongoRepository = articleMongoRepository;
    }

    @Override
    public List<Article> selectAll() {
        return articleMongoRepository.findAll();
    }

    @Override
    public Article selectById(String id) {

        return articleMongoRepository.findById(id).get();
    }

    @Override
    public void delete(Article article) {
        articleMongoRepository.delete(article);
    }

    @Override
    public DAOSaveResult<Article> insertOrUpdate(Article article) {

        articleMongoRepository.save(article);

        if (article.id == null) {
            return DAOResultHelper.buildDAOResult(false, "Article added", article);
        }

        Optional<Article> optionalArticle = articleMongoRepository.findById(article.id);

        if (optionalArticle.isPresent()) {
            return DAOResultHelper.buildDAOResult(true, "Article updated", article);
        }

        return DAOResultHelper.buildDAOResult(false, "Article added", article);

        }



    }
}
