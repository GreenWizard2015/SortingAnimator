package GreenWizard.SortingAnimator.SortAlgoritms;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

public class ShellSort extends BaseSortingThread {
	@Override
	public String getTitle() {
		return "Shell sort";
	}

	@Override
	protected void executeSorting() throws Exception {
		int Len = getLength();

		for (int gap = Len / 2; gap > 0; gap /= 2)
			for (int i = gap; i < Len; i++)
				for (int j = i - gap; j >= 0; j -= gap) {
					if (compare(j, j + gap) > 0)
						break;
					swap(j, j + gap);
				}
	}
}
