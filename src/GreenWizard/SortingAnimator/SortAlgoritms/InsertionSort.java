package GreenWizard.SortingAnimator.SortAlgoritms;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

public class InsertionSort extends BaseSortingThread {
	@Override
	public String getTitle() {
		return "Insertion sort";
	}

	@Override
	protected void executeSorting() throws Exception {
		int Len = getLength();
		for (int j = 1; j < Len; j++) {
			int tmp = get(j);
			int i;
			for (i = j - 1; i >= 0; i--) {
				if (compareValue(tmp, i) < 0)
					break;
				set(i + 1, get(i));
			}
			
			set(i + 1, tmp);
		}
	}

}
