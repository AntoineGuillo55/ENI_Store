package tp.eni_store.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    public String id;
    public String email;
    public String password;
    public String firstName;
    public String lastName;
}
