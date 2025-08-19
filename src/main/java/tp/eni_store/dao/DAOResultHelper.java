package tp.eni_store.dao;

public class DAOResultHelper {

    static <T> DAOSaveResult<T> buildDAOResult(boolean isUpdated, String message, T data){
        DAOSaveResult daoResult = new DAOSaveResult();
        daoResult.isUpdated = isUpdated;
        daoResult.message = message;
        daoResult.data = data;

        return daoResult;
    }
}
