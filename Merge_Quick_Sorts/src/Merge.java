
public class Merge {
	
	private Merge() { }
	
	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi, String s) 
	{
		
		if (lo >= hi) {
			System.out.println(s + "  Sort RETURN " + lo + " - " + hi);
			
			return;
		}
		int mid = lo + (hi - lo) / 2;
		System.out.println("Sort: " + lo + " - " + mid + " - " + hi);
		sort(a, aux, lo, mid, "L");
		sort(a, aux, mid+1, hi, "R");
		System.out.print("STATE before merge : ");
		merge(a, aux, lo, mid, hi);
	}
	
	public static void sort(Comparable[] a) 
	{
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1, "start");
		assert isSorted(a, 0, a.length - 1);
	}
	

	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) 
	{
		System.out.println("merge: " + lo +  " : " + mid + " : " + hi );
		assert isSorted(a, lo, mid);
		assert isSorted(a, mid+1, hi);
		
		for (int k = lo; k <= hi; k++ )
		{
			aux[k] = a[k];
			System.out.printf("%.2f",a[k]);
			System.out.print(" ");
		}
		System.out.println();
		
		
		//maintain 3 indices i, j, k
		//i front of L half of aux, j front of R half of aux, k current location in a[]
		int i = lo;
		int j = mid + 1;
		
		for (int k = lo; k <= hi; k++) 
		{
			//no more elements on L half of array
			if (i > mid)                   a[k] = aux[j++];
			//no more elements on R half of array
			else if (j > hi)               a[k] = aux[i++];
			//element on R is less than element on L
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			//element on L is less than element on R
			else                           a[k] = aux[i++];
		}
		
		assert isSorted(a, lo, hi);
		
	}
	
	private static boolean isSorted(Comparable[] a, int begin, int end)
	{
		
		for (int i = begin + 1; i < end; i++) {
			if ( less(a[i], a[i-1]) ) return false;
		}
		return true;
	}
	
	private static boolean less(Comparable v, Comparable w) 
	{
		return v.compareTo(w) < 0;
	}


	//test client
	public static void main(String[] args) {
		System.out.println("hi");
		Double[] a = new Double[8];
		for (int i = 0; i < 8; i++) {
			a[i] = StdRandom.uniform();
		}
		Merge.sort(a);
		assert isSorted(a, 0, a.length);
	}

}
