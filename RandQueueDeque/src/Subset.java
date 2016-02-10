import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Subset {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		String input = "";
		for (int i = 0; i < k; i++) {
			input = StdIn.readString();
			rq.enqueue(input);
		}
		Iterator iter = rq.iterator();
		
		int print = 0;
		while (iter.hasNext() && print < k) {
			System.out.println(iter.next());
			print++;
		} 
	}
}
