package uni.lu.Exercice4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import uni.lu.Exercice4.Entity.Feedback;
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


    //Methods to improve readability

    //method to check if a text contains a keyword independent of the case
    public boolean containsCaseInsensitive(String text, String keyword){
        return text.toLowerCase().contains(keyword.toLowerCase());
    }

    @QueryMapping
    public Optional<User> getUser(@Argument String id){
        return userRepository.findById(id);
    }

    @QueryMapping
    public Optional<Tweet> getTweet(@Argument String id){
        return tweetRepository.findById(id);
    }
    
    @QueryMapping
    public List<Tweet> searchTweets(@Argument String query){
        Iterable<Tweet> tweetsIterable = tweetRepository.findAll();
        
        ArrayList<Tweet> outputTweets = new ArrayList<>();
        tweetsIterable.forEach(tweet -> {
            if (containsCaseInsensitive(tweet.getMessage(), query) || containsCaseInsensitive(tweet.getTitle(), query)){
                outputTweets.add(tweet);
            }
        });
        return outputTweets;
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
        User author = getUser(authorId).get();

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
    @MutationMapping
    public Tweet addFeedback(@Argument String tweetId, @Argument String authorId, @Argument String message){
        Tweet tweet = getTweet(tweetId).get();
        User author = getUser(authorId).get();

        if (tweet == null || author == null || message.length() > 100){
            throw new IllegalArgumentException();
        }
        Feedback feedback = new Feedback(author, message);
        tweet.addFeedback(feedback);
        return tweetRepository.save(tweet);
    }
}
