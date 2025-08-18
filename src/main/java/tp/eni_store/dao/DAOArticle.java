package tp.eni_store.dao;

import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;

import java.util.ArrayList;
import java.util.List;

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

        if(articles.stream().noneMatch(article1 -> article1.id == id)) {
            return false;
        }

        articles.removeIf(article1 -> article1.id == id);

        return true;
    }
}
