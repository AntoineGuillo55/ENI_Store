package tp.eni_store.service;

public class ServiceHelper {

    static <T> ServiceResponse<T> buildResponse(String code, String message, T data) {

        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.code = code;
        serviceResponse.message = message;
        serviceResponse.data = data;

        System.out.println(serviceResponse);

        return serviceResponse;
    }
}
