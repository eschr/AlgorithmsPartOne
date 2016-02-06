/**
 * Selection sort algorithm
 * 
 * @author Eric Schraeder Feb 2016
 *
 *	Analysis of selection sort
 *		(N-1) + (N-2) + (N-3) + ... + 1 + 0   ~ N^2 / 2  (quadratic running time)
 *
 *		
 */
public class Selection {
	public static void sort(Comparable[] a) {
		int N = a.length;
		int min;
		
		/**
		 * Invariants : all elements to the left of i are in sorted ascending order
		 * 				no element to right of i are larger than any element to left of i
		 */
		for (int i = 0; i < N; i++) {
			min = i;
			for (int j = i + 1; j < N; j++) {
				if (less(a[j], a[min])) {
					min = j;
				}
			}
			swap(a, i, min);
		}
		
		pprint(a, N);
	}
	
	private static void pprint(Comparable [] p, int N) {
		for (int i = 0; i < N; i++) {
			System.out.println(p[i]);
		}
	}
	
	
	//helper functions for sort( ) 
	//taken from Algorithms I course from Princeton University via coursera.org
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void swap(Comparable [] a, int i, int j) {
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}

