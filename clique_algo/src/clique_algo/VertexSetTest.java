package clique_algo;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class VertexSetTest {

	@Test
	public void inittest() {
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
		assertEquols("must be 2,3,4 ", ver3.toString());
		//fail("Not yet implemented");
	}
	
	
}


