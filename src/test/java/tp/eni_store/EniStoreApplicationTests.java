package tp.eni_store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.service.ArticleService;
import tp.eni_store.service.ServiceResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EniStoreApplicationTests {

    @Autowired
    ArticleService articleService;

	@Test
	void contextLoads() {
	}

    @Test
    void getAllArticles_test() {

        ServiceResponse<List<Article>> result = articleService.getAll();

        assertThat(result.code).isEqualTo("202");
    }

    @Test
    void getArticleById_test() {

        ServiceResponse<Article> result_1 = articleService.getById("1");
        assertThat(result_1.code).isEqualTo("202");
        assertThat(result_1).isNotNull();

        ServiceResponse<Article> result_2 = articleService.getById("52");
        assertThat(result_2.code).isEqualTo("703");

    }

    @Test
    void save_test(){

        Article article1 = articleService.getById("2").data;
        article1.title = "New title";

        ServiceResponse<DAOSaveResult<Article>> result_1 = articleService.save(article1);
        assertThat(result_1.code).isEqualTo("203");
        assertThat(result_1.data.isUpdated).isEqualTo(true);

        Article article2 = new Article();
        article2.id = "45";
        article2.title = "Title 45";

        ServiceResponse<DAOSaveResult<Article>> result_2 = articleService.save(article2);
        assertThat(result_2.code).isEqualTo("202");
        assertThat(result_2.data.isUpdated).isEqualTo(false);
    }

    @Test
    void deleteById_test() {

        ServiceResponse<Article> result_1 = articleService.deleteById("1");
        assertThat(result_1.code).isEqualTo("202");

        ServiceResponse<Article> result_2 = articleService.deleteById("52");
        assertThat(result_2.code).isEqualTo("703");
    }
}
