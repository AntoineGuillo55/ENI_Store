package tp.eni_store.dao.article.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOResultHelper;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.article.IDAOArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("mock")
@Component
public class DAOArticleMock implements IDAOArticle {

    private List<Article> articles;

    public DAOArticleMock() {

        articles = new ArrayList<>();

        for (int i = 0 ; i < 3 ;  i++) {
            Article article = new Article();
            article.id = String.valueOf(i);
            article.title = String.format("Title %s", i);
            articles.add(article);
        }
    }

    @Override
    public List<Article> selectAll() {
        return articles;
    }

    @Override
    public Article selectById(String id) {


        Article article = articles.stream().filter(article1 -> article1.id == id).findFirst().orElse(null);

        return article;
    }

    @Override
    public void delete(Article article) {

        articles.removeIf(article1 -> article1.id == article.id);
    }

    @Override
    public DAOSaveResult<Article> insertOrUpdate(Article article) {

        Optional<Article> optionalArticle = articles.stream().filter(article1 -> article1.id == article.id).findFirst();

        if (optionalArticle.isPresent()) {
            optionalArticle.get().title = article.title;
            return DAOResultHelper.buildDAOResult(true, "Article updated", article);
        } else {
            articles.add(article);
            return DAOResultHelper.buildDAOResult(false, "Article added", article);
        }


    }
}
