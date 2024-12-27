package uni.lu.Exercice4;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import uni.lu.Exercice4.Entity.Tweet;
import uni.lu.Exercice4.Entity.User;
import uni.lu.Exercice4.Repository.TweetRepository;
import uni.lu.Exercice4.Repository.UserRepository;

@Controller
public class GraphQLController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @QueryMapping
    public Optional<User> getUser(@Argument String id){
        return userRepository.findById(id);
    }
    
    @QueryMapping
    public List<Tweet> searchTweets(@Argument String query){
        return tweetRepository.findByTitleContainingIgnoreCase(query);
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
    @MutationMapping
    public Tweet postTweet(@Argument String title, @Argument String message, @Argument String authorId, @Argument int validityLengthInDays) {
        // Fetch the author by ID
        Optional<User> authorOptional = userRepository.findById(authorId);
        
        // Handle case where the user does not exist
        if (authorOptional.isEmpty()) {
            throw new IllegalArgumentException("Author with ID " + authorId + " does not exist.");
        }
        
        User author = authorOptional.get();

        // Create a new Tweet instance
        Tweet newTweet = new Tweet(
            title,
            message,
            author,
            LocalDateTime.now(), // Current posting date
            validityLengthInDays
        );

        // Save the tweet in the repository
        return tweetRepository.save(newTweet);
    }
}
