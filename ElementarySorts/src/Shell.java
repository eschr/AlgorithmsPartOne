
public class Shell {
	
	public static void sort(Comparable[] a) {
		int h = 1;
		int N = a.length;
		
		while (h < N/3) {
			h = 3 * h + 1;
		}
		
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				int j = i;
				while (j >= h && less(a[j], a[j - h])) {
					swap(a, j, j-h);
					j -= h;
				}
			}
			h = h / 3;
		}
		
		//pprint(a, N);
		
	}

	private static void pprint(Comparable [] p, int N) {
		for (int i = 0; i < N; i++) {
			System.out.println(p[i]);
		}
	}
	
	
	//helper functions for sort( ) 
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void swap(Comparable [] a, int i, int j) {
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
