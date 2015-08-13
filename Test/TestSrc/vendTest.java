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
		vender.clearCoinReturn();
		assertEquals(3, pocket.size());
		assertEquals(0, (vender.checkCoinReturn()).size());
	}
}
