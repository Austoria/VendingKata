package TestSrc;

import static org.junit.Assert.*;

import org.junit.Test;

import vendingSrc.VendingMachine;

public class vendTest {
	@Test
	public void whenVendingMachineIsCheckedItDoesSomething(){
		assertEquals("INSERT COIN", VendingMachine.check());
	}
}
