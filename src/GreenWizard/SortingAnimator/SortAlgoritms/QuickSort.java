package GreenWizard.SortingAnimator.SortAlgoritms;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

public class QuickSort extends BaseSortingThread {
	@Override
	public String getTitle() {
		return "Quick sort";
	}

	@Override
	protected void executeSorting() throws Exception {
		sort(0, getLength() - 1);
	}

	private void sort(int lo, int hi) throws Exception {
		if (hi <= lo)
			return;
		int j = partition(lo, hi);
		sort(lo, j - 1);
		sort(j + 1, hi);
	}

	// partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	// and return the index j.
	private int partition(int lo, int hi) throws Exception {
		int i = lo;
		int j = hi + 1;
		int v = get(lo);
		while (true) {
			// find item on lo to swap
			while (compareValue(v, ++i) < 0)
				if (i == hi)
					break;

			// find item on hi to swap
			while (compareValue(v, --j) > 0)
				if (j == lo)
					break;
			// check if pointers cross
			if (i >= j)
				break;

			swap(i, j);
		}

		// put partitioning item v at a[j]
		swap(lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}

}
