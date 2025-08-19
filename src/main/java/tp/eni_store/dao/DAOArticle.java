package tp.eni_store.dao;

import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DAOArticle {

    private List<Article> articles;

    public DAOArticle() {

        articles = new ArrayList<>();

        for (int i = 0 ; i < 3 ;  i++) {
            Article article = new Article();
            article.id = i;
            article.title = String.format("Title %s", i);
            articles.add(article);
        }
    }

    public List<Article> selectAll() {
        return articles;
    }

    public Article selectById(int id) {


        Article article = articles.stream().filter(article1 -> article1.id == id).findFirst().orElse(null);

        return article;
    }

    public boolean deleteById(int id) {

        return articles.removeIf(article1 -> article1.id == id);
    }

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
