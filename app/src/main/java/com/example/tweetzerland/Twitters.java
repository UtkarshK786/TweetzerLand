package com.example.tweetzerland;

public class Twitters {
   private String email;
    private String password;
    private String follows;
    private String tweets;

    public Twitters() {
    }

    public String getEmail() {
        return email;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



