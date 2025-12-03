# OOP Concepts Used in Wellness App

This document outlines which Object-Oriented Programming concepts from your course are demonstrated in this project.

## 1. Introduction to Object-Oriented Principles and Java Programming ✓

- **Classes and Objects**: All model classes (User, FitnessData, MealData, Habit, HabitTracker)
- **Constructors**: Every class has constructors (default and parameterized)
- **Methods**: All classes contain methods for operations
- **Control Flow**: if/else statements used throughout for validation and logic
- **Primitive Data Types**: int, double, boolean used extensively
- **Equality Operators**: Used in password validation and comparisons

## 2. Class Design and Object Manipulation ✓

- **Accessors (Getters)**: All model classes have getter methods
- **Mutators (Setters)**: All model classes have setter methods
- **Static Fields**: FileHandler uses singleton pattern with static instance
- **Static Methods**: Validator and Calculator classes use static methods
- **Final Instance Fields**: User class has final height and weight fields
- **User Input Handling**: JTextField, JPasswordField for input

## 3. Working with Java Packages, Arrays, and String Handling ✓

- **Packages**: Code organized into packages (wellnessapp.models, wellnessapp.gui, etc.)
- **Importing Packages**: All classes import necessary packages
- **Arrays**: ArrayList used in MealData and HabitTracker
- **Strings**: String handling throughout (immutable strings)
- **StringBuffer**: Not used (could be added for string manipulation if needed)

## 4. Collections Framework and Access Control ✓

- **ArrayList**: Used in MealData (meals list) and HabitTracker (habits list)
- **Iterators**: Used in HabitTracker.removeHabit() method
- **Access Specifiers**: 
  - `private` for fields (encapsulation)
  - `public` for methods that need external access
  - `default` package access where appropriate
- **HashMap**: Used in FileHandler for storing user data maps

## 5. Inheritance and Polymorphism ✓

- **Inheritance**: User and Habit implement Comparable interface
- **Method Overriding**: 
  - `compareTo()` method in User and Habit
  - `toString()` method in User and Habit
  - `equals()` method in User
- **Runtime Polymorphism**: Interface implementations allow polymorphic behavior
- **Abstract Classes**: Not used (could be added for base panel class)

## 6. Interfaces, Inner Classes, and Advanced Java Class Design ✓

- **Interfaces**: 
  - `Comparable` interface implemented by User and Habit
  - `Serializable` interface for data persistence
- **Inner Classes**: 
  - `CreateUserDialog` is an inner class in LoginFrame
  - Anonymous classes used for ActionListener implementations
- **Comparator**: Not used (could be added for custom sorting)

## 7. Exception Handling and Error Management ✓

- **User-Defined Exceptions**: `InvalidInputException` class
- **try-catch Blocks**: Used extensively in:
  - FileHandler for file operations
  - Validator for input validation
  - GUI panels for handling user input errors
- **throw Keyword**: Used in Validator methods
- **throws Keyword**: Methods declare thrown exceptions
- **Exception Types**: 
  - Checked exceptions: IOException, FileNotFoundException
  - Unchecked exceptions: NumberFormatException
  - Custom exceptions: InvalidInputException

## 8. Core Java Classes and Multithreading Concepts

- **Object Class**: All classes implicitly extend Object
- **Shallow vs Deep Copy**: Not explicitly demonstrated (could be added)
- **Thread Basics**: SwingUtilities.invokeLater() used in Main class for thread-safe GUI
- **Thread Synchronization**: Not needed for this application

## 9. Event Handling and User Interface Components ✓

- **Event Handling**: ActionListener interface used throughout
- **Delegation Event Model**: All GUI components use this model
- **GUI Components**: 
  - JFrame, JPanel, JButton, JTextField, JLabel
  - JPasswordField, JTextArea, JScrollPane, JList
  - JRadioButton, ButtonGroup, JSeparator
- **Action Events**: Button clicks trigger action events
- **Listeners**: ActionListener implementations for all interactive components

## 10. Design Patterns and Advanced GUI Components ✓

- **Design Patterns**:
  - **Singleton Pattern**: FileHandler uses singleton pattern
  - **Composite Pattern**: GUI structure (panels within frames)
  - **Strategy Pattern**: Layout managers (BorderLayout, GridLayout, FlowLayout)
- **Layout Managers**: 
  - BorderLayout for main structure
  - GridLayout for organized grids
  - FlowLayout for button panels
  - GridBagLayout for complex layouts

## 11. File Handling, JDBC, and Object-Oriented Analysis ✓

- **File Handling**: 
  - FileHandler class handles all file operations
  - Object serialization for data persistence
  - Reading and writing binary files (.dat files)
- **JDBC**: Not used (database not required for this project)
- **Class Relationships**: 
  - User has composition relationship with data classes
  - GUI classes use model classes
  - Utility classes provide services

## 12. Software Design and Modeling with UML

- **Use Cases**: Implicitly defined through features
- **CRC Cards**: Not explicitly created (could be added)
- **UML Class Diagrams**: Not included (could be added)

## 13. Advanced UML Modeling and Software Design Principles ✓

- **Design Principles**:
  - **Accessors and Mutators Separation**: Clear separation in all model classes
  - **Avoiding Side Effects**: Methods are designed to be predictable
  - **Cohesion**: Classes have single, well-defined responsibilities
  - **Completeness**: All required features implemented
  - **Clarity**: Code is well-commented and organized
  - **Consistency**: Naming conventions and patterns followed throughout

## 14. Software Correctness and Additional Design Patterns ✓

- **Preconditions**: Input validation ensures preconditions
- **Postconditions**: Methods guarantee certain states after execution
- **Assertions**: Not used (could be added with assert keyword)
- **Design Patterns**:
  - **Singleton**: FileHandler
  - **Factory Method**: Could be added for creating panels
  - **Adapter**: Not needed for this project
  - **Command**: ActionListener pattern is similar to Command pattern
  - **Observer**: Not explicitly used (Swing's event model uses it internally)

## Summary

**Concepts Fully Implemented**: 11 out of 14 major concept areas
**Concepts Partially Implemented**: 2 areas (UML modeling, some design patterns)
**Concepts Not Applicable**: 1 area (JDBC - not required for this project)

The application demonstrates a comprehensive understanding of:
- Core OOP principles
- Java language features
- GUI programming
- Exception handling
- File I/O
- Collections framework
- Design patterns
- Software design principles

