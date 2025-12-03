package wellnessapp.models;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    private String username;
    private String password;
    private String name;
    private int age;
    private String gender;
    private final double height;
    private final double weight;
    
    public User(String username, String password, String name, int age, String gender, double height, double weight) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }
    
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
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
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
        return "User{username='" + username + "', name='" + name + "', age=" + age + ", gender='" + gender + "', height=" + height + ", weight=" + weight + "}";
    }
}
