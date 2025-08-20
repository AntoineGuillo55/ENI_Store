package tp.eni_store.dao;

public class DAOResultHelper {

    public static <T> DAOSaveResult<T> buildDAOResult(boolean isUpdated, String message, T data){
        DAOSaveResult<T> daoResult = new DAOSaveResult<T>();
        daoResult.isUpdated = isUpdated;
        daoResult.message = message;
        daoResult.data = data;

        return daoResult;
    }
}
