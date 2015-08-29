package clique_algo;


import org.junit.Test;
import static org.junit.Assert.*;

public class VertexSetTest {
	@Test
	public void addtest(){
		int j;
		VertexSet ver1 = new VertexSet();
		for (j = 0; j < 7; j++) {
			ver1.add((int)Math.random());
		}
		 assertTrue(ver1.size() == 7);
		//fail("Not yet implemented");
	}

	@Test
	public void intersectiontest() {
		VertexSet ver1 = new VertexSet();
		ver1.add(2);
		ver1.add(5);
		ver1.add(8);
		ver1.add(9);
		VertexSet ver2 = new VertexSet();
		ver2.add(1);
		ver2.add(3);
		ver2.add(4);
		ver2.add(5);
		VertexSet ver3 = new VertexSet();
		ver3.add(2);
		ver3.add(3);
		ver3.add(4);
		//String ans = "Set: |3| 2, 3, 4, ";
		assertEquals("it must be 2, 3, 4", ver3.toString(), "Set: |3| 2, 3, 4, ");
		//fail("Not yet implemented");
	}
	
		
}


