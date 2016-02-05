import java.util.Random;


public class SortClient {
	public static void main(String args[]) {
		int seed = Integer.parseInt(args[0]);
		
		Insertion insert = new Insertion();
		Random rand = new Random(seed);
		
		Double[] client = new Double[50];
		
		for (int i = 0; i < 50; i++) {
			client[i] = rand.nextDouble();
		}
		
		Integer[] simp = {9, 8, 7, 6, 5, 4, 3, 2, 1};
		
		insert.sort(client);
	}
}
