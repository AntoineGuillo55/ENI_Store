package tp.eni_store.service;

import org.springframework.stereotype.Service;
import tp.eni_store.bo.User;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.user.IDAOUser;
import tp.eni_store.locale.LocaleHelper;

import java.util.List;

@Service
public class UserService {


    IDAOUser daoUser;
    LocaleHelper localeHelper;

    public UserService(IDAOUser daoUser, LocaleHelper localeHelper) {
        this.daoUser = daoUser;
        this.localeHelper = localeHelper;
    }

    public ServiceResponse<List<User>> getAll() {

        List<User> users = daoUser.selectAll();
        String message = localeHelper.i18n("UserService_GetAll_202");
        return ServiceHelper.buildResponse("202", message, users);
    }

    public ServiceResponse<User> getById(String id) {

        User user = daoUser.selectById(id);

        if (user == null) {
            String message = localeHelper.i18n("UserService_GetById_703");
            return ServiceHelper.buildResponse("703", message, null);
        }
        String message = localeHelper.i18n("UserService_GetById_202");

        return ServiceHelper.buildResponse("202", message, user);
    }

    public ServiceResponse<User> deleteById(String id) {

        User user = daoUser.selectById(id);
        daoUser.delete(user);

        if (user != null) {
            String message = localeHelper.i18n("UserService_DeleteById_202");
            return ServiceHelper.buildResponse("202", message, user);
        }

        String message = localeHelper.i18n("UserService_DeleteById_703");
        return ServiceHelper.buildResponse("703", message, user);

    }

    public ServiceResponse<DAOSaveResult<User>> save(User user) {

        DAOSaveResult<User> result = daoUser.insertOrUpdate(user);

        if(result.isUpdated) {
            String message = localeHelper.i18n("UserService_Save_202_Updated");
            return ServiceHelper.buildResponse("203", message, result);
        }

        String message = localeHelper.i18n("UserService_Save_202_Added");
        return ServiceHelper.buildResponse("202", message, result);
    }
}
