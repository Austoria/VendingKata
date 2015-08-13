package TestSrc;

import static org.junit.Assert.*;
import org.junit.Test;
import vendingSrc.VendingMachine;

public class vendTest {
	
	@Test
	public void whenVendingMachineIsCheckedItDoesSomething(){
		VendingMachine vender = new VendingMachine();
		assertEquals("INSERT COIN", vender.check());
	}
	
	@Test
	public void vendingMachineAcceptsCoinsAndDisplaysValue(){
		//Machine accepts coin based on weight in milligrams
		VendingMachine vender = new VendingMachine();
		vender.insertCoin(5670);
		assertEquals("25", vender.check());
	}
}
