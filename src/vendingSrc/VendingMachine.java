package vendingSrc;

import java.text.NumberFormat;
import java.util.*;

public class VendingMachine {
	int paid;
	int quarter = 5670;
	int dime = 2268;
	int nickel = 5000;
	//Vending machine has six HashMaps to represent inventory price and quantity, coin totals and values and one for coin return
	HashMap<Integer, Integer> coins = new HashMap <Integer, Integer>(); //Count of coins in machine
	HashMap<Integer, Integer> insertedCoins = new HashMap <Integer, Integer>(); //Coins inserted by user
	HashMap<Integer, Integer> values = new HashMap <Integer, Integer>(); //Value of coins inserted
	HashMap<Integer, Integer> coinReturn = new HashMap <Integer, Integer>(); //Count of coins in coin return
	HashMap<String, Integer> inventory = new HashMap <String, Integer>(); //Inventory items and price
	HashMap<String, Integer> invQuant = new HashMap <String, Integer>(); //Inventory items and quantity
	HashMap<String, Integer> dispensor = new HashMap <String, Integer>(); //Dispensed items will be placed here
	
	public void init() {
		//Initialize HashMap for US Currency, change key weight and value (values only) for foreign currencies
		fillMachinesCoins();
		insertedCoins.put(quarter, 0);
		insertedCoins.put(dime, 0);
		insertedCoins.put(nickel, 0);
		
		//initialize value of coins accepted
		values.put(quarter, 25);
		values.put(nickel, 5);
		values.put(dime, 10);
		
		//initialize inventory values
		inventory.put("Cola", 100);
		inventory.put("Chips", 50);
		inventory.put("Candy", 65);
		
		//initialize inventory quantities
		invQuant.put("Cola", 2);
		invQuant.put("Chips", 2);
		invQuant.put("Candy", 2);
	}
	
	public String check() {
		String display = "INSERT COIN";
		if (paid > 0)
			display = displayAsCurrency(paid);
		else if (!canMakeChange()){
			display = "EXACT CHANGE ONLY";
		}
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
			paid += values.get(weight);
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
		if (!canMakeChange() && paid != inventory.get(choice)){
			makeChange();
			return "NOT EXACT CHANGE";
		}
		if ((paid >= inventory.get(choice)) && (invQuant.get(choice)>0)) {
			if (dispensor.containsKey(choice))
				dispensor.put(choice, dispensor.get(choice)+1);
			else
				dispensor.put(choice, 1);
			paid -= inventory.get(choice);
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
		if (canMakeChange()) {
			coins.put(quarter, coins.get(quarter)+insertedCoins.get(quarter));
			coins.put(dime, coins.get(dime)+insertedCoins.get(dime));
			coins.put(nickel, coins.get(nickel)+insertedCoins.get(nickel));
		}
		else {
			coins.put(quarter, insertedCoins.get(quarter));
			coins.put(dime, insertedCoins.get(dime));
			coins.put(nickel, insertedCoins.get(nickel));	
		}
		insertedCoins.put(quarter, 0);
		insertedCoins.put(dime, 0);
		insertedCoins.put(nickel, 0);
		
		int addVal = 0;
		int quartersToReturn = paid / (values.get(quarter));
		while (quartersToReturn > coins.get(quarter) && quartersToReturn > 0) {
			quartersToReturn--;
			addVal += values.get(quarter);
		}
		int dimesToReturn = ((paid % values.get(quarter)) + addVal) / (values.get(dime));
		addVal = 0;
		while (dimesToReturn > coins.get(dime) && dimesToReturn > 0) {
			dimesToReturn--;
			addVal += values.get(dime);
		}
		int nickelsToReturn = (((paid % values.get(quarter)) % values.get(dime)) + addVal) / values.get(nickel);			
			
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
		paid = 0;
		coins.put(quarter, coins.get(quarter)-quartersToReturn);
		coins.put(dime, coins.get(dime)-dimesToReturn);
		coins.put(nickel, coins.get(nickel)-nickelsToReturn);
	}
	
	public void returnCoins(){
		makeChange();
	}

	public HashMap<String, Integer> checkDispensor(){
		return dispensor;
	}
	public void fillMachinesCoins(){
		coins.put(quarter, 3);
		coins.put(dime, 3);
		coins.put(nickel, 3);
	}
	
	public void emptyMachinesCoins(){
		coins.put(quarter, 0);
		coins.put(dime, 0);
		coins.put(nickel, 0);
	}
	
	public boolean canMakeChange() {
		if (coins.get(nickel) >= 4)
			return true;
		else if (coins.get(dime)>=1 && coins.get(nickel)>=2)
			return true;
		else if (coins.get(dime)>=2 && coins.get(nickel)>=1)
			return true;
		return false;
	}
}
