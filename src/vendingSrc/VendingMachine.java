package vendingSrc;

import java.text.NumberFormat;
import java.util.*;

public class VendingMachine {
	int value;
	//Vending machine has three HashMaps to represent coin totals and values and one for coin return
	HashMap<Integer, Integer> coins = new HashMap <Integer, Integer>(); //Count of coins inserted
	HashMap<Integer, Integer> values = new HashMap <Integer, Integer>(); //Value of coins inserted
	HashMap<Integer, Integer> coinReturn = new HashMap <Integer, Integer>(); //Count of coins in coin return
	HashMap<String, Integer> inventory = new HashMap <String, Integer>(); //Inventory items and price
	
	public void init() {
		//Initialize HashMap for US Currency, change key weight and value (values only) for foreign currencies
		coins.put(5670, 0);
		coins.put(5000, 0);
		coins.put(2268, 0);
		values.put(5670, 25);
		values.put(5000, 5);
		values.put(2268, 10);
		inventory.put("Cola", 100);
		inventory.put("Chips", 50);
		inventory.put("Candy", 65);
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
	
	public String displayAsCurrency(int vendTotal) {
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
}
