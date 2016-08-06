package GreenWizard.SortingAnimator.SortAlgoritms;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

public class BubbleSort extends BaseSortingThread {
	@Override
	public String getTitle() {
		return "Bubble sort";
	}

	@Override
	protected void executeSorting() throws Exception {
		int Len = getLength() - 1;
		for (int i = 0; i < Len; i++) {
			boolean swapped = false;
			for (int j = 0; j < Len - i; j++) {
				if (compare(j, j + 1) < 0) {
					swap(j, j + 1);
					swapped = true;
				}
			}

			if (!swapped)
				break;
			markAsSorted(Len - i);
		}
	}
}
