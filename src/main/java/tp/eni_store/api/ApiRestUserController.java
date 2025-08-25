package tp.eni_store.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import tp.eni_store.bo.User;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.service.UserService;
import tp.eni_store.service.ServiceResponse;
import java.util.List;

@RestController
public class ApiRestUserController {


    UserService userService;

    public ApiRestUserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Endpoint pour récupérer tous les users")
    @GetMapping("api/user/get-all")
    public ServiceResponse<List<User>> getAllUsers(){

        return userService.getAll();
    }

    @Operation(summary = "Endpoint pour récupérer un user avec son id")
    @GetMapping("api/user/{id}")
    public User getUserById(@PathVariable String id){

        ServiceResponse<User> user = userService.getById(id);
        return user.data;
    }

    @Operation(summary = "Endpoint ajouter u nouvel user, ou mettre à jour un user existant")
    @PostMapping("api/user/save")
    public ServiceResponse<DAOSaveResult<User>> saveUser(@RequestBody User user){

        return userService.save(user);
    }

    @Operation(summary = "Endpoint pour supprimer un user")
    @DeleteMapping("api/user/{id}")
    public ServiceResponse<User> deleteUserById(@PathVariable String id){

        return userService.deleteById(id);
    }


}
