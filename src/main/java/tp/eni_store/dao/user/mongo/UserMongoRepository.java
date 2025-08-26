package tp.eni_store.dao.user.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.eni_store.bo.User;

public interface UserMongoRepository extends MongoRepository<User,String> {

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
