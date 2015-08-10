package com.lloydsbank;

import static org.junit.Assert.*;


import java.math.BigDecimal;
import java.util.logging.Level;








import org.junit.Before;
import org.junit.Test;


import com.lloydsbank.screenscraping.client.LloydsBankClient;

public class LloydsBankClientTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLloydsBankClient() {

		String username = System.getenv("lloydsUser");
		String password = System.getenv("lloydsPass");
		String sharesec = System.getenv("lloydsSecret");
		BigDecimal balance = new LloydsBankClient(username,password,sharesec).fetchBalance();
		System.out.println("Lloyds Balance = " + balance);
		
		
	
	}

}
