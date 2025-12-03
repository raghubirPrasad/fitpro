# FitPro - The Ultimate Wellness App

A comprehensive wellness tracking application built with Java Swing, demonstrating advanced Object-Oriented Programming concepts including abstract classes, inheritance hierarchies, and design patterns.

## Features

1. **User Management**
   - Login and create new user accounts
   - Store user information (username, password, name, age, gender, height, weight)
   - Secure password handling

2. **Fitness Tracking**
   - Track daily steps with target setting
   - Record workouts (Push-ups, Squats, Burpees, etc.) with MET-based calorie calculations
   - Track sports activities (Running, Cycling, Swimming, etc.) with time/distance inputs
   - Manual calorie entry option
   - View "Fitness Today" list with all activities
   - Remove selected activities from the list
   - Set and monitor target steps and calories
   - "New Day" button to reset daily data

3. **Meal Tracking**
   - Add meals from predefined categories (Breakfast, Lunch, Dinner, Snacks)
   - Manual meal entry option (Misc category)
   - Track water intake
   - Set targets for water and calorie intake
   - View "Meals Today" list with all meals consumed
   - Remove selected meals from the list
   - "New Day" button to reset daily data

4. **Habit Tracking**
   - Create new habits
   - Toggle habits as completed/uncompleted
   - Remove habits
   - View completion status and progress
   - "New Day" button to reset habit completion

5. **Mindfulness & Meditation**
   - Track daily meditation time
   - Set target meditation time
   - Track mood (Happy, Neutral, Sad, Anxious, Angry)
   - View current mood status
   - "New Day" button to reset meditation data

6. **Health Calculators**
   - BMI (Body Mass Index) calculator with category classification
   - BMR (Basal Metabolic Rate) calculator (Harris-Benedict equation)
   - TDEE (Total Daily Energy Expenditure) calculator with activity level selection
   - Animated result displays

7. **Dashboard**
   - View key metrics at a glance with animated progress bars:
     - Steps taken vs target
     - Calories burned vs target
     - Water intake vs target
     - Calories eaten vs target
     - Habit progress (completed habits)
     - Mindfulness meter (meditation time)

8. **Profile Management**
   - View user profile information
   - Edit targets for all tracking categories
   - View calculated health metrics (BMI, BMR, TDEE)

## OOP Concepts Used

### Core OOP Principles
- **Classes and Objects**: User, FitnessData, MealData, MindfulnessData, Habit, HabitTracker, MealItem
- **Encapsulation**: Private fields with public accessors and mutators in all model classes
- **Inheritance**: 
  - Class inheritance: BasePanel → all panels, TrackingData → ListTrackingData → FitnessData/MealData
  - Interface inheritance: Comparable, Serializable implementations
- **Polymorphism**: 
  - Runtime polymorphism through abstract classes and interfaces
  - Method overriding in inheritance hierarchy
- **Abstraction**: 
  - Abstract classes: BasePanel, TrackingData, ListTrackingData
  - Abstract methods: loadData(), updateDisplay(), reset(), getCurrentValue(), getTargetValue()

### Advanced Concepts
- **Static Fields and Methods**: Validator, Calculator, FileHandler (Singleton), static data maps
- **Final Fields**: User class has final height and weight (immutability)
- **Collections Framework**: ArrayList, HashMap, Iterator usage
- **Exception Handling**: 
  - Custom exceptions: InvalidInputException, NegativeValueException, DecimalValueException, NumericValueException, SpecialCharacterException
  - try-catch blocks with multiple exception types
  - throw/throws keywords for exception propagation
- **Interfaces**: Comparable, Serializable, ActionListener, DocumentListener
- **Inner Classes**: CreateUserDialog in LoginFrame, anonymous inner classes for event handlers
- **Method Overriding**: toString(), equals(), compareTo(), paintComponent()
- **Access Modifiers**: private, public, protected (used in abstract base classes)

### Design Patterns
- **Singleton Pattern**: FileHandler class
- **Composite Pattern**: GUI component hierarchy (JFrame → JPanel → Components)
- **Strategy Pattern**: Layout managers (BorderLayout, GridLayout, FlowLayout, GridBagLayout)
- **Decorator Pattern**: JScrollPane decorating JList components
- **Command Pattern**: ActionListener implementations act as commands

### GUI and Event Handling
- **Swing Components**: JFrame, JPanel, JButton, JTextField, JLabel, JComboBox, JList, JScrollPane, JProgressBar, JRadioButton, ButtonGroup, JSeparator, JPasswordField, JOptionPane
- **Event Handling**: ActionListener, DocumentListener, delegation event model
- **Layout Managers**: BorderLayout, GridLayout, FlowLayout, GridBagLayout
- **Custom Components**: AnimatedButton extends JButton
- **Animations**: Fade-in effects, animated progress bars, number counting animations

### Code Organization
- **Packages**: Organized into models, gui, utils, exceptions, main packages
- **Code Reuse**: Abstract base classes eliminate code duplication (~140 lines saved)
- **Maintainability**: Consistent patterns, clear separation of concerns

## How to Compile and Run

### Compilation

```bash
# Navigate to the project root directory
cd fitpro

# Compile all Java files
javac -d bin src/wellnessapp/**/*.java
```

### Running the Application

```bash
# Run the main class
java -cp bin wellnessapp.main.Main
```

Or on Windows:
```cmd
java -cp bin wellnessapp.main.Main
```

## Project Structure

```
fitpro/
├── src/
│   └── wellnessapp/
│       ├── exceptions/
│       │   ├── InvalidInputException.java
│       │   ├── NegativeValueException.java
│       │   ├── DecimalValueException.java
│       │   ├── NumericValueException.java
│       │   └── SpecialCharacterException.java
│       ├── models/
│       │   ├── User.java
│       │   ├── FitnessData.java
│       │   ├── MealData.java
│       │   ├── MindfulnessData.java
│       │   ├── Habit.java
│       │   ├── HabitTracker.java
│       │   ├── MealItem.java
│       │   ├── MealsData.java
│       │   ├── TrackingData.java (Abstract)
│       │   └── ListTrackingData.java (Abstract)
│       ├── utils/
│       │   ├── Validator.java
│       │   ├── Calculator.java
│       │   ├── FileHandler.java
│       │   └── AnimatedButton.java
│       ├── gui/
│       │   ├── BasePanel.java (Abstract)
│       │   ├── LoginFrame.java
│       │   ├── MainFrame.java
│       │   ├── Dashboard.java
│       │   ├── FitnessPanel.java
│       │   ├── MealPanel.java
│       │   ├── HabitPanel.java
│       │   ├── MindfulnessPanel.java
│       │   ├── CalculatorPanel.java
│       │   └── ProfilePanel.java
│       └── main/
│           └── Main.java
├── CONCEPTS_USED.md
├── CONCEPTS_USED_NEW.md
└── README.md
```

### Key Architecture Highlights

- **Abstract Base Classes**: 
  - `BasePanel`: Common functionality for all GUI panels (fade-in animation, initialization)
  - `TrackingData`: Base class for all tracking data models
  - `ListTrackingData`: Extends TrackingData for list-based tracking (FitnessData, MealData)

- **Inheritance Hierarchy**:
  - GUI: `JPanel` → `BasePanel` → `FitnessPanel/MealPanel/etc.`
  - Data Models: `TrackingData` → `ListTrackingData` → `FitnessData/MealData`
  - Data Models: `TrackingData` → `MindfulnessData`

## Data Storage

The application currently uses **in-memory storage** for session data:
- Data persists during the application session
- All data models implement `Serializable` interface for future file persistence
- Data is organized by username in HashMap structures:
  - User accounts
  - Fitness tracking data
  - Meal tracking data
  - Habit tracking data
  - Mindfulness tracking data

*Note: The FileHandler class is structured to support file-based persistence. The current implementation uses in-memory HashMaps for simplicity during development.*

## Input Validation

The app includes comprehensive validation through the `Validator` utility class with multiple specific exception types:

### Numeric Field Validation:
- **Steps and Age**: 
  - No decimal values allowed (throws `DecimalValueException`)
  - No negative values allowed (throws `NegativeValueException`)
  - Must be whole numbers only
- **All Other Numeric Fields** (calories, water, meditation time, targets, etc.):
  - No negative values allowed (throws `NegativeValueException`)
  - Decimals allowed where appropriate (weight, height, time, distance, speed)

### Text Field Validation:
- **Name Field**: 
  - Only alphabets allowed (no numbers, no special characters)
  - Throws `NumericValueException` if numbers are entered
  - Throws `SpecialCharacterException` if special characters are entered
- **Username Field**: 
  - Only alphabets, numbers, and underscore allowed
  - Throws `SpecialCharacterException` if special characters (other than underscore) are entered

### General Validation:
- **Empty field validation**: Prevents empty required fields (throws `InvalidInputException`)
- **Type validation**: Ensures correct data types
- **User-friendly error messages**: Each exception type provides specific, clear error messages
- **Real-time validation**: DocumentListener in MealPanel for immediate feedback on misc item fields

## Usage Instructions

1. **First Time Setup**: 
   - Run the application
   - Click "Create User" button
   - Fill in all required information (username, password, name, age, gender, height, weight)
   - Click "Create" to register

2. **Login**: 
   - Enter your username and password
   - Click "Login" button

3. **Navigate**: 
   - Use the navigation buttons at the top to switch between sections:
     - Dashboard: Overview of all metrics
     - Fitness: Track steps, workouts, and sports
     - Meals: Track meals and water intake
     - Habits: Manage daily habits
     - Mindfulness: Track meditation and mood
     - Calculators: Health metric calculations
     - Profile: View and edit profile and targets

4. **Track Fitness**:
   - Add steps using the "Add Steps" button
   - Select workout/sport category and activity
   - Enter required information (reps, time, distance, speed)
   - Click "Calculate and Update Calories"
   - View all activities in "Fitness Today" section
   - Remove activities by selecting and clicking "Remove Selected"

5. **Track Meals**:
   - Select meal category (Breakfast, Lunch, Dinner, Snacks, Misc)
   - Select meal item or enter manually (for Misc)
   - Click "Add to Total"
   - Add water intake using "Add Water" button
   - View all meals in "Meals Today" section
   - Remove meals by selecting and clicking "Remove Selected"

6. **Track Habits**:
   - Enter habit name and click "Add Habit"
   - Click on habits in the list to toggle completion
   - Remove habits using the "Remove" button

7. **Track Mindfulness**:
   - Enter meditation time and click "Add Meditation Time"
   - Select mood from dropdown
   - View current meditation time and mood

8. **View Dashboard**: 
   - Check your progress with animated progress bars
   - View all key metrics at a glance

9. **Use Calculators**: 
   - Enter weight, height, age
   - Select gender (Male/Female)
   - Select activity level for TDEE
   - View calculated BMI, BMR, and TDEE with animations

10. **Manage Profile**: 
    - View your profile information
    - Edit targets for all tracking categories
    - View calculated health metrics

11. **New Day**: 
    - Click "New Day" button on Fitness, Meals, Habits, or Mindfulness pages
    - Confirms before resetting daily data

## Technical Highlights

- **Abstract Classes**: Demonstrates advanced OOP with BasePanel, TrackingData, and ListTrackingData
- **Code Reuse**: Abstract base classes eliminate ~140 lines of duplicate code
- **Inheritance Hierarchy**: Multi-level inheritance (3 levels) in both GUI and data model layers
- **Design Patterns**: Singleton, Composite, Strategy, Decorator, Command patterns
- **Animations**: Smooth fade-in effects, animated progress bars, number counting animations
- **Error Handling**: 
  - Comprehensive validation with 5 custom exception types
  - Specific exceptions for different error types (negative values, decimals, special characters, numeric values in text)
  - User-friendly, context-specific error messages displayed via JOptionPane
  - Multiple exception types caught in single catch blocks (exception chaining)
  - Validation rules enforced through Validator utility class:
    - Steps and Age: No decimals, no negatives
    - All numeric fields: No negatives
    - Name field: Alphabets only (no numbers, no special characters)
    - Username field: Alphabets, numbers, and underscore only (no special characters)
- **Clean Architecture**: Separation of concerns (models, GUI, utils, exceptions)

## Documentation

- **CONCEPTS_USED.md**: Overview of OOP concepts demonstrated
- **CONCEPTS_USED_NEW.md**: Comprehensive documentation of all course concepts with specific code locations

## Notes

- The application demonstrates advanced Java OOP concepts including abstract classes and inheritance hierarchies
- All data models implement Serializable for future file persistence
- The application uses Swing components with custom styling and animations
- Code follows consistent naming conventions and design patterns

