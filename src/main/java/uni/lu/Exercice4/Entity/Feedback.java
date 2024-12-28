package uni.lu.Exercice4.Entity;

import org.springframework.data.annotation.Id;

public class Feedback {
    private User author;
    private String message;
    
    public Feedback(User author, String message) {
        this.author = author;
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }   
}
