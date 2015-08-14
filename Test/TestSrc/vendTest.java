package TestSrc;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;
import vendingSrc.VendingMachine;

public class vendTest {
	VendingMachine vender;
	
	@Before
	public void setUp(){
		vender = new VendingMachine();
		vender.init();
	}
	
	@Test
	public void whenVendingMachineIsCheckedItDoesSomething(){
		assertEquals("INSERT COIN", vender.check());
	}
	
	@Test
	public void vendingMachineAcceptsCoinsAndDisplaysValue(){
		//Machine accepts coin based on weight in milligrams (5670->Quarters, 2268->Dimes, 5000->nickels)
		vender.insertCoin(5670);
		assertEquals("$0.25", vender.check());
	}	
	
	@Test
	public void vendingMachineDisplaysTotalCoinValueAndInCorrectForm(){
		vender.insertCoin(5670);
		vender.insertCoin(5000);
		vender.insertCoin(2268);
		assertEquals("$0.40", vender.check());
	}

	@Test
	public void rejectedCoinsArePlacedInCoinReturn(){
		vender.insertCoin(6000);
		vender.insertCoin(50);
		vender.insertCoin(300);
		HashMap<Integer, Integer> pocket = new HashMap<Integer, Integer>();
		pocket.putAll(vender.checkCoinReturn());
		(vender.checkCoinReturn()).clear();
		assertEquals(3, pocket.size());
		assertEquals(0, (vender.checkCoinReturn()).size());
	}
	
	@Test
	public void vendingMachineHasAnInventory(){
		assertEquals("$1.00", vender.checkInventory("Cola"));
		assertEquals("$0.50", vender.checkInventory("Chips"));
		assertEquals("$0.65", vender.checkInventory("Candy"));
	}

	
	@Test
	public void userSelectionIsVendedIfPaymentIsSufficientOtherwisePriceDisplayed(){
		assertEquals("$1.00", vender.vend("Cola"));
		vender.insertCoin(5670);
		vender.insertCoin(5670);
		vender.insertCoin(5670);
		vender.insertCoin(5670);
		assertEquals("THANK YOU", vender.vend("Cola"));
		assertEquals(1, (vender.checkDispensor()).size());
		assertEquals(true, (vender.checkDispensor()).containsKey("Cola"));
	}
	
	@Test
	public void returnsChangeWhenThereIsMoneyRemainingAfterPurchase(){
		vender.insertCoin(5670);
		vender.insertCoin(5670);
		vender.insertCoin(2268);
		vender.insertCoin(2268);
		vender.insertCoin(2268);
		assertEquals("THANK YOU", vender.vend("Candy"));
		HashMap<Integer, Integer> pocket = new HashMap<Integer, Integer>();
		pocket.putAll(vender.checkCoinReturn());
		int quarters=pocket.get(5670);
		int dimes=pocket.get(2268);
		int nickels=pocket.get(5000);
		assertEquals(0, quarters);
		assertEquals(1, dimes);
		assertEquals(1, nickels);
	}
	
	@Test
	public void returnsCoinsWhenUserPressesReturnCoin(){
		vender.insertCoin(5670);
		vender.insertCoin(2268);
		vender.insertCoin(5000);
		vender.returnCoins();
		assertEquals("INSERT COIN", vender.check());
		HashMap<Integer, Integer> pocket = new HashMap<Integer, Integer>();
		pocket.putAll(vender.checkCoinReturn());
		int quarters = pocket.get(5670);
		int dimes = pocket.get(2268);
		int nickels = pocket.get(5000);
		assertEquals(1, quarters);
		assertEquals(1, dimes);
		assertEquals(1, nickels);
	}
}