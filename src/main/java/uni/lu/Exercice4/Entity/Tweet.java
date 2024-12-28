package uni.lu.Exercice4.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Tweet")
public class Tweet implements Serializable{
    @Id
    private String id;

    private String title;
    private String message;
    private User author;
    private LocalDateTime postingDate;
    private int validityLengthInDays;
    private List<Feedback> feedbacks;
    
    public Tweet(String title, String message, User author, LocalDateTime postingDate, int validityLengthInDays) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.postingDate = postingDate;
        this.validityLengthInDays = validityLengthInDays;
        this.feedbacks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public int getValidityLengthInDays() {
        return validityLengthInDays;
    }

    public List<Feedback> getFeedback() {
        return feedbacks;
    }

    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }   
}
