# Wellness App - Java OOP Project

A comprehensive wellness tracking application built with Java Swing, demonstrating Object-Oriented Programming concepts.

## Feature s

1. **User Management**
   - Login and create new user accounts
   - Store user information (username, password, height, weight)

2. **Fitness Tracking*
   - Track daily steps
   - Record workout types and calories burned
   - Track sports activities
   - Set and monitor target steps and calories
   - "New Day" button to reset daily data

3. **Meal Tracking**
   - Add meals with calorie information
   - Track water intake
   - Set targets for water and calorie intake
   - View list of meals consumed
   - "New Day" button to reset daily data

4. **Habit Tracking**
   - Create new habits
   - Toggle habits as completed
   - Remove habits
   - View completion status
   - "New Day" button to reset habit completion

5. **Health Calculators**
   - BMI (Body Mass Index) calculator
   - BMR (Basal Metabolic Rate) calculator
   - TDEE (Total Daily Energy Expenditure) calculator

6. **Dashboard**
   - View key metrics at a glance:
     - Steps taken vs target
     - Calories burned vs target
     - Water intake vs target
     - Calories eaten vs target

## OOP Concepts Used

- **Classes and Objects**: User, FitnessData, MealData, Habit, HabitTracker
- **Inheritance**: Comparable interface implementation
- **Polymorphism**: Interface implementations, method overriding
- **Encapsulation**: Private fields with public accessors and mutators
- **Static Methods**: Validator, Calculator, FileHandler (Singleton)
- **Collections**: ArrayList, HashMap, Iterator
- **Exception Handling**: Custom InvalidInputException, try-catch blocks
- **File Handling**: Serialization for data persistence
- **GUI Components**: Swing components (JFrame, JPanel, JButton, etc.)
- **Event Handling**: ActionListeners, event-driven programming
- **Design Patterns**: Singleton (FileHandler), Composite (GUI structure)
- **Packages**: Organized code into packages (models, gui, utils, exceptions)

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
│       │   └── InvalidInputException.java
│       ├── models/
│       │   ├── User.java
│       │   ├── FitnessData.java
│       │   ├── MealData.java
│       │   ├── Habit.java
│       │   └── HabitTracker.java
│       ├── utils/
│       │   ├── Validator.java
│       │   ├── Calculator.java
│       │   └── FileHandler.java
│       ├── gui/
│       │   ├── LoginFrame.java
│       │   ├── MainFrame.java
│       │   ├── Dashboard.java
│       │   ├── FitnessPanel.java
│       │   ├── MealPanel.java
│       │   ├── HabitPanel.java
│       │   └── CalculatorPanel.java
│       └── main/
│           └── Main.java
└── README.md
```

## Data Storage

All user data is stored in the `data/` directory:
- `users.dat` - User accounts
- `fitness.dat` - Fitness tracking data
- `meals.dat` - Meal tracking data
- `habits.dat` - Habit tracking data

## Input Validation

The app includes validation to prevent:
- Negative values for steps, calories, water intake
- Decimal values for steps and workout statistics
- Empty required fields

## Usage Instructions

1. **First Time Setup**: Run the application and click "Create User"
2. **Login**: Enter your username and password
3. **Navigate**: Use the navigation buttons to switch between sections
4. **Track Data**: Add your daily activities, meals, and habits
5. **View Dashboard**: Check your progress on the dashboard
6. **New Day**: Click "New Day" button on Fitness, Meals, or Habits pages to reset daily data
7. **Calculators**: Use the Calculators section to compute health metrics

## Notes

- The GUI is intentionally basic to reflect a beginner-level Java project
- All data persists between sessions using file serialization
- The application uses basic Swing components without advanced styling

