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
		assertEquals("$0.25", vender.check());
	}
	
	@Test
	public void vendingMachineDisplaysTotalCoinValueAndInCorrectForm(){
		vender.insertCoin(5670);
		vender.insertCoin(5000);
		vender.insertCoin(2268);
		assertEquals("$0.40", vender.check());
	}
}
