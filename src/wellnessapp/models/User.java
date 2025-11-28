package wellnessapp.models;

import java.io.Serializable;

/**
 * User class representing a user in the wellness app
 * Demonstrates: Classes, constructors, accessors, mutators, final fields
 */
public class User implements Serializable, Comparable<User> {
    private String username;
    private String password;
    private final double height; // final field for immutability
    private final double weight; // final field for immutability
    
    public User(String username, String password, double height, double weight) {
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
    }
    
    // Accessors (getters)
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getWeight() {
        return weight;
    }
    
    // Mutators (setters)
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Comparable interface implementation
    @Override
    public int compareTo(User other) {
        return this.username.compareTo(other.username);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username);
    }
    
    @Override
    public String toString() {
        return "User{username='" + username + "', height=" + height + ", weight=" + weight + "}";
    }
}

