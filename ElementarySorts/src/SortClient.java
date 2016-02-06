import java.util.Random;


public class SortClient {
	public static void main(String args[]) {
		int seed = Integer.parseInt(args[0]);
		
		Insertion insert = new Insertion();
		//Selection select = new Selection();
		Shell shell = new Shell();
		
		Random rand = new Random(seed);
		
		Double[] clientA = new Double[80000];
		Double[] clientB = new Double[80000];
		
		for (int i = 0; i < 80000; i++) {
			clientA[i] = rand.nextDouble();
			clientB[i] = rand.nextDouble();
		}
		long start = System.nanoTime();
		shell.sort(clientA);
		long stop = System.nanoTime();
		System.out.println("Shell sort time : " + (stop - start));
		
		start = System.nanoTime();
		insert.sort(clientB);
		stop = System.nanoTime();
		System.out.println("Insertion sort time : " + (stop - start));;
		
		
	}
}
