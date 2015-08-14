package vendingSrc;

import java.text.NumberFormat;
import java.util.*;

public class VendingMachine {
	int value;
	int quarter = 5670;
	int dime = 2268;
	int nickel = 5000;
	//Vending machine has three HashMaps to represent coin totals and values and one for coin return
	HashMap<Integer, Integer> coins = new HashMap <Integer, Integer>(); //Count of coins inserted
	HashMap<Integer, Integer> values = new HashMap <Integer, Integer>(); //Value of coins inserted
	HashMap<Integer, Integer> coinReturn = new HashMap <Integer, Integer>(); //Count of coins in coin return
	HashMap<String, Integer> inventory = new HashMap <String, Integer>(); //Inventory items and price
	HashMap<String, Integer> invQuant = new HashMap <String, Integer>(); //Inventory items and quantity
	HashMap<String, Integer> dispensor = new HashMap <String, Integer>(); //Dispensed items will be placed here
	
	public void init() {
		//Initialize HashMap for US Currency, change key weight and value (values only) for foreign currencies
		coins.put(quarter, 0);
		coins.put(dime, 0);
		coins.put(nickel, 0);
		values.put(quarter, 25);
		values.put(nickel, 5);
		values.put(dime, 10);
		inventory.put("Cola", 100);
		inventory.put("Chips", 50);
		inventory.put("Candy", 65);
		invQuant.put("Cola", 2);
		invQuant.put("Chips", 2);
		invQuant.put("Candy", 2);
	}
	
	public String check() {
		String display = "INSERT COIN";
		if (value > 0)
			return displayAsCurrency(value);
		return display;
	}

	public void insertCoin(int weight) {	
		if (!coins.containsKey(weight)){
			if (!coinReturn.containsKey(weight))
				coinReturn.put(weight, 1);
			else
				coinReturn.put(weight, coins.get(weight)+1);
		}
		else {
			coins.put(weight, coins.get(weight)+1);
			value += values.get(weight);
		}
	}
	
	private String displayAsCurrency(int vendTotal) {
		String price = null;
		double decimalTotal = ((double)vendTotal)/100;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		price = formatter.format(decimalTotal);
		return price;
	}
	
	public HashMap<Integer, Integer> checkCoinReturn(){
		return coinReturn;
	}

	public String checkInventory(String choice){
		return displayAsCurrency(inventory.get(choice));
	}
	
	public String vend(String choice){
		if ((value >= inventory.get(choice)) && (invQuant.get(choice)>0)) {
			if (dispensor.containsKey(choice))
				dispensor.put(choice, dispensor.get(choice)+1);
			else
				dispensor.put(choice, 1);
			value -= inventory.get(choice);
			invQuant.put(choice, invQuant.get(choice)-1);
			makeChange();
			return "THANK YOU";
		}
		else if(invQuant.get(choice) == 0)
			return "SOLD OUT";
		else
			return displayAsCurrency(inventory.get(choice));
	}
	
	private void makeChange() {
		
		int quartersToReturn = value/(values.get(quarter));
		int dimesToReturn = (value%values.get(quarter))/(values.get(dime));
		int nickelsToReturn = ((value % values.get(quarter)) % values.get(dime)) / values.get(nickel);			
		
		if (coinReturn.containsKey(quarter))
			coinReturn.put(quarter, coinReturn.get(quarter)+quartersToReturn);
		else
			coinReturn.put(quarter, quartersToReturn);
		
		if (coinReturn.containsKey(dime))
			coinReturn.put(dime, coinReturn.get(dime)+dimesToReturn);
		else
			coinReturn.put(dime, dimesToReturn);
		
		if (coinReturn.containsKey(nickel))
			coinReturn.put(nickel, coinReturn.get(nickel)+nickelsToReturn);
		else
			coinReturn.put(nickel, nickelsToReturn);
		value = 0;
	}
	
	public void returnCoins(){
		makeChange();
	}

	public HashMap<String, Integer> checkDispensor(){
		return dispensor;
	} 
}
