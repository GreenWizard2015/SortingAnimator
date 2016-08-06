package GreenWizard.SortingAnimator.SortAlgoritms;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

public class HeapSort extends BaseSortingThread {
	@Override
	public String getTitle() {
		return "Heap sort";
	}

	@Override
	protected void executeSorting() throws Exception {
		int N = getLength() - 1;
		for (int k = 1 + (N / 2); k >= 0; k--)
			sink(k, N);
		while (N > 0) {
			swap(0, N--);
			sink(0, N);
		}
	}

	private void sink(int k, int N) throws Exception{
		while (2 * k <= N) {
			int j = 2 * k;
			if ((j < N) && (compare(j, j + 1) > 0))
				j++;
			if (compare(k, j) <= 0)
				break;
			swap(k, j);
			k = j;
		}
	}
}
