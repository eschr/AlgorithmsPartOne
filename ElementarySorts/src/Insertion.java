/**
 * Insertion sort algorithm
 * 
 * @author Eric Schraeder Feb 2016
 * 
 * Analysis of Insertion sort
 *  ~ 1/4 N^2 compares and ~ 1/4 N^2 exchanges on avg
 *
 */
public class Insertion {
	
	public static void sort(Comparable [] a) {
		int N = a.length;
		/**
		 * Scans left to right
		 * Invariants : entries to left of i inclusive are in ascending order
		 * 				entries to right of i have not yet been sorted
		 */
		for (int i = 1; i < N; i++) {
			int j = i;
			//continue to follow and swap a[j] all the way down (to index 0 if needed) until it is in correct position
			//moves right to left, swapping a[j] with the larger element to its left
			while (j > 0 && less(a[j], a[j - 1])) {
				swap(a, j, j-1);
				j--;
			}
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
