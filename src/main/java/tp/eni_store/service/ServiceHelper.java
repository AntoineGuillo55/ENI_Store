package tp.eni_store.service;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class ServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(ServiceHelper.class);

    static <T> ServiceResponse<T> buildResponse(String code, String message, T data) {

        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.code = code;
        serviceResponse.message = message;
        serviceResponse.data = data;

        logger.info(String.format("[%s] - [%s]", code, message));

        return serviceResponse;
    }
}
