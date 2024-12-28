package uni.lu.Exercice4.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uni.lu.Exercice4.Entity.Tweet;

@Repository
public interface TweetRepository extends CrudRepository<Tweet,String>{
}
