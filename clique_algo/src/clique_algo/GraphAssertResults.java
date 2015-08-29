package clique_algo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class GraphAssertResults {

	@Test
	public void testGraphStringDoubleInt() {
		Graph g1 = new Graph ("test1.csv", 0.7);
		assertTrue(g1.sizeOfE() == 6872);
		Graph g2 = new Graph ("test1.csv", 0.982);
		assertTrue(g2.sizeOfE() == 1);


		
		//fail("Not yet implemented");
	}

}
