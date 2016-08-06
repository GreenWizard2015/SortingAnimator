package GreenWizard.SortingAnimator.SortAlgoritms;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

public class SelectionSort extends BaseSortingThread {
	@Override
	public String getTitle() {
		return "Selection sort";
	}

	@Override
	protected void executeSorting() throws Exception {
		int Len = getLength();
		for (int i = 0; i < Len - 1; i++) {
			int index = i;
			for (int j = i + 1; j < Len; j++)
				if (compare(j, index) > 0)
					index = j;

			swap(index, i);
			markAsSorted(i);
		}
	}
}
