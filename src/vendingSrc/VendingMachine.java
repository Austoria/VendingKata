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
	HashMap<Integer, Integer> values = new HashMap <Integer, Integer>(); //Value of coins inserted
	HashMap<Integer, Integer> coinReturn = new HashMap <Integer, Integer>(); //Count of coins in coin return
	HashMap<String, Integer> inventory = new HashMap <String, Integer>(); //Inventory items and price
	HashMap<String, Integer> invQuant = new HashMap <String, Integer>(); //Inventory items and quantity
	HashMap<String, Integer> dispensor = new HashMap <String, Integer>(); //Dispensed items will be placed here
	
	public void init() {
		//Initialize HashMap for US Currency, change key weight and value (values only) for foreign currencies
		fillMachinesCoins();
		
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
		//Checks the display of the machine, default is INSERT COIN
		String display = "INSERT COIN";
		if (paid > 0)
			display = displayAsCurrency(paid);
		else if (!canMakeChange()){
			display = "EXACT CHANGE ONLY";
		}
		return display;
	}

	public void insertCoin(int weight) {	
		//Inserts coin, if invalid coin it returns the coin immediately to coin return
		if (!coins.containsKey(weight)){
			if (!coinReturn.containsKey(weight))
				coinReturn.put(weight, 1);
			else
				coinReturn.put(weight, coins.get(weight)+1);
		}
		else {
			//If valid coin, coin is added to machine and 'paid' is increased accordingly
			coins.put(weight, coins.get(weight)+1);
			paid += values.get(weight);
		}
	}
	
	private String displayAsCurrency(int vendTotal) {
		//Displays the amount as a string of form "$(dollars).(cents)(cents)"
		String price = null;
		double decimalTotal = ((double)vendTotal)/100;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		price = formatter.format(decimalTotal);
		return price;
	}
	
	public HashMap<Integer, Integer> checkCoinReturn(){
		//Returns the coinReturn object, if 'hashmap'.putAll(coinReturn) is used, coinReturn will be emptied
		return coinReturn;
	}

	public String checkInventory(String choice){
		//Displays the price of the object, only used to allow user to check inventory
		return displayAsCurrency(inventory.get(choice));
	}
	
	public String vend(String choice){
		//If the user has entered inexact change when exact change is required
		if (!canMakeChange() && paid != inventory.get(choice)){
			makeChange();
			return "NOT EXACT CHANGE";
		}
		//Else allow the user to get their choice
		else if ((paid >= inventory.get(choice)) && (invQuant.get(choice)>0)) {
			//Because emptying the dispensor hashMap can cause nullPointer exeptions, check  to see if choice is a key
			if (dispensor.containsKey(choice))
				dispensor.put(choice, dispensor.get(choice)+1);
			else
				dispensor.put(choice, 1);
			//Take the price out of the users paid total
			paid -= inventory.get(choice);
			//Lower the inventory quantity accordingly
			invQuant.put(choice, invQuant.get(choice)-1);
			//Make change from all of the coins in the machine
			makeChange();
			return "THANK YOU";
		}
		else if(invQuant.get(choice) == 0)
			return "SOLD OUT";
		else
			return displayAsCurrency(inventory.get(choice));
	}
	
	private void makeChange() {
		//Algorithm to give optimal coins as change, returns mostly quarters if possible, prioritizes keeping nickels
		
		int addVal = 0;
		
		//Determine how many quarters to return, if more than in machine add the difference to the remainder
		int quartersToReturn = paid / (values.get(quarter));
		while (quartersToReturn > coins.get(quarter) && quartersToReturn > 0) {
			quartersToReturn--;
			addVal += values.get(quarter);
		}
		
		//Determine how many dimes to return, if more than in machine add the difference to the remainder
		int dimesToReturn = ((paid % values.get(quarter)) + addVal) / (values.get(dime));
		addVal = 0;
		while (dimesToReturn > coins.get(dime) && dimesToReturn > 0) {
			dimesToReturn--;
			addVal += values.get(dime);
		}
		
		//Determine how many nickels to return to reach total
		int nickelsToReturn = (((paid % values.get(quarter)) % values.get(dime)) + addVal) / values.get(nickel);			
			
		//Fills the coinReturn with the chosen coins
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
		
		//subtract returned coins from machine stores
		coins.put(quarter, coins.get(quarter)-quartersToReturn);
		coins.put(dime, coins.get(dime)-dimesToReturn);
		coins.put(nickel, coins.get(nickel)-nickelsToReturn);
	}
	
	public void returnCoins(){
		//Makes change for the full amount inserted by the user
		makeChange();
	}

	public HashMap<String, Integer> checkDispensor(){
		//Allow user to access the dispensor object to remove purchase
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
		//Test that machine can make change for all possible values 5-25
		if (coins.get(nickel) >= 4)
			return true;
		else if (coins.get(dime)>=1 && coins.get(nickel)>=2)
			return true;
		else if (coins.get(dime)>=2 && coins.get(nickel)>=1)
			return true;
		return false;
	}
}
