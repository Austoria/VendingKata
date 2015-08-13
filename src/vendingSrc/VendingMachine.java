package vendingSrc;

import java.util.*;

public class VendingMachine {
	int value;
	HashMap<Integer, Integer> coins = new HashMap <Integer, Integer>();
	HashMap<Integer, Integer> values = new HashMap <Integer, Integer>();
	public void init() {
		values.put(5670, 25);
		values.put(5000, 5);
		values.put(2268, 10);
	}
	
	public String check() {
		String display = "INSERT COIN";
		if (value > 0)
			return String.valueOf(value);
		return display;
	}

	public void insertCoin(int weight) {	
		if (!coins.containsKey(weight))
			coins.put(weight, 1);
		else
			coins.put(weight, coins.get(weight)+1);
		value += values.get(weight);
	}

	
}
