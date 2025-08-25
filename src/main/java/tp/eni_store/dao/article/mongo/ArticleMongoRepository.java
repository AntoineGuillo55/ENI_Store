package tp.eni_store.dao.article.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.eni_store.bo.Article;

public interface ArticleMongoRepository extends MongoRepository<Article,String> {
}
