package uni.lu.Exercice4;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import uni.lu.Exercice4.Entity.User;
import uni.lu.Exercice4.Repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public Optional<User> getUser(@Argument String id){
        return userRepository.findById(id);
    }
     
    @MutationMapping
    public User createUser(@Argument String id, @Argument String name, @Argument String email, @Argument String phone) {
        User newUser = new User();
        newUser.setId(id);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        return userRepository.save(newUser); // Save the user and return it
    }

}
