package tp.eni_store.dao.user.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.eni_store.bo.User;
import tp.eni_store.dao.DAOResultHelper;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.user.IDAOUser;

import java.util.List;
import java.util.Optional;

@Profile("mongo")
@Component
public class DAOUserMongo implements IDAOUser {

    UserMongoRepository userMongoRepository;

    public DAOUserMongo(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public List<User> selectAll() {
        return userMongoRepository.findAll();
    }

    @Override
    public User selectById(String id) {

        return userMongoRepository.findById(id).get();
    }

    @Override
    public void delete(User user) {
        userMongoRepository.delete(user);
    }

    @Override
    public DAOSaveResult<User> insertOrUpdate(User user) {

        userMongoRepository.save(user);

        if (user.id == null) {
            return DAOResultHelper.buildDAOResult(false, "User added", user);
        }

        Optional<User> optionalUser = userMongoRepository.findById(user.id);

        if (optionalUser.isPresent()) {
            return DAOResultHelper.buildDAOResult(true, "User updated", user);
        }

        return DAOResultHelper.buildDAOResult(false, "User added", user);

    }

    @Override
    public User selectPersonByLogin(String email, String password) {
        return userMongoRepository.findByEmailAndPassword(email, password);
    }
}
