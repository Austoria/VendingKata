package TestSrc;

import static org.junit.Assert.*;
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
		//Machine accepts coin based on weight in milligrams
		vender.insertCoin(5670);
		assertEquals("25", vender.check());
	}
}
