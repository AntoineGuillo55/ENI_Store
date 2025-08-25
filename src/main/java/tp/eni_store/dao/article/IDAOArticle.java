package tp.eni_store.dao.article;

import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOSaveResult;

import java.util.List;

@Component
public interface IDAOArticle {

    List<Article> selectAll();

    Article selectById(String id);

    void delete(Article article);

    DAOSaveResult<Article> insertOrUpdate(Article article);
}
