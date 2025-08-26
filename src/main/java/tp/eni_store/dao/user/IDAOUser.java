package tp.eni_store.dao.user;

import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;
import tp.eni_store.bo.User;
import tp.eni_store.dao.DAOSaveResult;

import java.util.List;

@Component
public interface IDAOUser {

    List<User> selectAll();

    User selectById(String id);

    void delete(User user);

    DAOSaveResult<User> insertOrUpdate(User user);

    User selectPersonByLogin(String email,  String password);

    User selectUserByEmail(String email);
}
