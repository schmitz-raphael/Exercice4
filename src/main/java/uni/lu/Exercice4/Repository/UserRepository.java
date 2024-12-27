package uni.lu.Exercice4.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uni.lu.Exercice4.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {}