package wellnessapp.models;

import java.util.HashMap;

public class MealsData {
    private static HashMap<String, HashMap<String, MealItem>> mealItems;
    
    static {
        mealItems = new HashMap<>();
        
        HashMap<String, MealItem> breakfast = new HashMap<>();
        breakfast.put("Scrambled Eggs", new MealItem("Scrambled Eggs", "2 eggs", 180));
        breakfast.put("Omelette", new MealItem("Omelette", "2 eggs", 200));
        breakfast.put("Boiled Eggs", new MealItem("Boiled Eggs", "2 eggs", 140));
        breakfast.put("Pancakes", new MealItem("Pancakes", "2 medium", 180));
        breakfast.put("Waffles", new MealItem("Waffles", "1 piece", 200));
        breakfast.put("French Toast", new MealItem("French Toast", "2 slices", 220));
        breakfast.put("Cereal + Milk", new MealItem("Cereal + Milk", "1 bowl", 230));
        breakfast.put("Oats with Milk", new MealItem("Oats with Milk", "1 bowl", 250));
        breakfast.put("Peanut Butter Toast", new MealItem("Peanut Butter Toast", "2 slices", 280));
        breakfast.put("Avocado Toast", new MealItem("Avocado Toast", "1 slice", 250));
        breakfast.put("Yogurt Fruit Bowl", new MealItem("Yogurt Fruit Bowl", "1 bowl", 200));
        breakfast.put("Smoothie Bowl", new MealItem("Smoothie Bowl", "1 bowl", 300));
        breakfast.put("Aloo Paratha", new MealItem("Aloo Paratha", "1 piece", 300));
        breakfast.put("Plain Paratha", new MealItem("Plain Paratha", "1 piece", 260));
        breakfast.put("Poha", new MealItem("Poha", "1 plate", 250));
        breakfast.put("Upma", new MealItem("Upma", "1 plate", 240));
        breakfast.put("Idli", new MealItem("Idli", "2 pieces", 140));
        breakfast.put("Dosa", new MealItem("Dosa", "1 medium", 180));
        breakfast.put("Masala Dosa", new MealItem("Masala Dosa", "1 medium", 350));
        breakfast.put("Uttapam", new MealItem("Uttapam", "1 medium", 250));
        breakfast.put("Chole Bhature", new MealItem("Chole Bhature", "1 plate", 500));
        breakfast.put("Vada Pav", new MealItem("Vada Pav", "1 piece", 290));
        breakfast.put("Medu Vada", new MealItem("Medu Vada", "2 pieces", 320));
        breakfast.put("Veg Sandwich", new MealItem("Veg Sandwich", "1 sandwich", 250));
        breakfast.put("Cheese Sandwich", new MealItem("Cheese Sandwich", "1 sandwich", 320));
        breakfast.put("Porridge", new MealItem("Porridge", "1 bowl", 200));
        mealItems.put("Breakfast", breakfast);
        
        HashMap<String, MealItem> lunch = new HashMap<>();
        lunch.put("Grilled Chicken", new MealItem("Grilled Chicken", "150g", 280));
        lunch.put("Chicken Curry", new MealItem("Chicken Curry", "1 bowl", 300));
        lunch.put("Chicken Biryani", new MealItem("Chicken Biryani", "1 plate", 650));
        lunch.put("Veg Biryani", new MealItem("Veg Biryani", "1 plate", 550));
        lunch.put("Paneer Butter Masala", new MealItem("Paneer Butter Masala", "1 bowl", 420));
        lunch.put("Rajma Chawal", new MealItem("Rajma Chawal", "1 plate", 550));
        lunch.put("Dal Chawal", new MealItem("Dal Chawal", "1 plate", 450));
        lunch.put("Roti", new MealItem("Roti", "1 piece", 90));
        lunch.put("Naan", new MealItem("Naan", "1 piece", 280));
        lunch.put("Fried Rice", new MealItem("Fried Rice", "1 plate", 550));
        lunch.put("Veg Thali", new MealItem("Veg Thali", "1 thali", 800));
        lunch.put("Pasta White Sauce", new MealItem("Pasta White Sauce", "1 plate", 650));
        lunch.put("Pasta Red Sauce", new MealItem("Pasta Red Sauce", "1 plate", 450));
        lunch.put("Salad Bowl", new MealItem("Salad Bowl", "1 bowl", 150));
        lunch.put("Fish Curry", new MealItem("Fish Curry", "1 bowl", 300));
        lunch.put("Mutton Curry", new MealItem("Mutton Curry", "1 bowl", 450));
        lunch.put("Butter Chicken", new MealItem("Butter Chicken", "1 bowl", 520));
        lunch.put("Dal Makhani", new MealItem("Dal Makhani", "1 bowl", 390));
        lunch.put("Egg Curry", new MealItem("Egg Curry", "1 bowl", 280));
        lunch.put("Chapati + Sabzi", new MealItem("Chapati + Sabzi", "2 chapati", 250));
        lunch.put("Shawarma Roll", new MealItem("Shawarma Roll", "1 roll", 420));
        lunch.put("Burrito", new MealItem("Burrito", "1 medium", 600));
        lunch.put("Sushi", new MealItem("Sushi", "6 pcs", 300));
        lunch.put("Steak", new MealItem("Steak", "200g", 500));
        lunch.put("Tandoori Chicken", new MealItem("Tandoori Chicken", "2 pieces", 320));
        mealItems.put("Lunch", lunch);
        
        HashMap<String, MealItem> dinner = new HashMap<>();
        dinner.put("Grilled Chicken Bowl", new MealItem("Grilled Chicken Bowl", "1 bowl", 350));
        dinner.put("Veg + Tofu Stir Fry", new MealItem("Veg + Tofu Stir Fry", "1 bowl", 280));
        dinner.put("Paneer Tikka", new MealItem("Paneer Tikka", "6 pcs", 300));
        dinner.put("Chicken Tikka", new MealItem("Chicken Tikka", "6 pcs", 320));
        dinner.put("Khichdi", new MealItem("Khichdi", "1 bowl", 250));
        dinner.put("Dal + Roti", new MealItem("Dal + Roti", "2 roti", 300));
        dinner.put("Veg Pulao", new MealItem("Veg Pulao", "1 plate", 400));
        dinner.put("Fish Fry", new MealItem("Fish Fry", "2 pieces", 330));
        dinner.put("Veg Soup", new MealItem("Veg Soup", "1 bowl", 120));
        dinner.put("Chicken Soup", new MealItem("Chicken Soup", "1 bowl", 150));
        dinner.put("Roti + Paneer", new MealItem("Roti + Paneer", "2 roti", 350));
        dinner.put("Idli", new MealItem("Idli", "2 pcs", 140));
        dinner.put("Dosa", new MealItem("Dosa", "1 medium", 180));
        dinner.put("Veg Curry", new MealItem("Veg Curry", "1 bowl", 200));
        dinner.put("Egg Fried Rice", new MealItem("Egg Fried Rice", "1 plate", 520));
        dinner.put("Chicken Fried Rice", new MealItem("Chicken Fried Rice", "1 plate", 580));
        dinner.put("Grilled Fish", new MealItem("Grilled Fish", "150g", 250));
        dinner.put("Quinoa Bowl", new MealItem("Quinoa Bowl", "1 bowl", 350));
        dinner.put("Light Ramen", new MealItem("Light Ramen", "1 bowl", 450));
        dinner.put("Light Sandwich", new MealItem("Light Sandwich", "1 sandwich", 220));
        mealItems.put("Dinner", dinner);
        
        HashMap<String, MealItem> snacks = new HashMap<>();
        snacks.put("Banana", new MealItem("Banana", "1 piece", 105));
        snacks.put("Apple", new MealItem("Apple", "1 piece", 95));
        snacks.put("Protein Bar", new MealItem("Protein Bar", "1 bar", 200));
        snacks.put("Granola Bar", new MealItem("Granola Bar", "1 bar", 150));
        snacks.put("Chips", new MealItem("Chips", "1 small pack", 180));
        snacks.put("Nuts Mixed", new MealItem("Nuts Mixed", "30g", 170));
        snacks.put("Popcorn", new MealItem("Popcorn", "1 bowl", 150));
        snacks.put("Tea + Biscuits", new MealItem("Tea + Biscuits", "3 biscuits", 120));
        snacks.put("Coffee w/ Milk + Sugar", new MealItem("Coffee w/ Milk + Sugar", "1 cup", 110));
        snacks.put("Samosa", new MealItem("Samosa", "1 piece", 150));
        snacks.put("Pakoda", new MealItem("Pakoda", "1 plate", 250));
        snacks.put("Bhelpuri", new MealItem("Bhelpuri", "1 plate", 200));
        snacks.put("Sev Puri", new MealItem("Sev Puri", "1 plate", 250));
        snacks.put("Mini Sandwich", new MealItem("Mini Sandwich", "1 mini", 180));
        snacks.put("Lassi", new MealItem("Lassi", "1 glass", 250));
        snacks.put("Fruit Bowl", new MealItem("Fruit Bowl", "1 bowl", 120));
        snacks.put("Paneer Roll", new MealItem("Paneer Roll", "1 roll", 320));
        snacks.put("Chicken Roll", new MealItem("Chicken Roll", "1 roll", 350));
        snacks.put("Idli", new MealItem("Idli", "1 piece", 70));
        snacks.put("Dhokla", new MealItem("Dhokla", "4 pieces", 160));
        mealItems.put("Snacks", snacks);
    }
    
    public static HashMap<String, MealItem> getMealItems(String category) {
        return mealItems.get(category);
    }
    
    public static HashMap<String, HashMap<String, MealItem>> getAllMealItems() {
        return mealItems;
    }
}

