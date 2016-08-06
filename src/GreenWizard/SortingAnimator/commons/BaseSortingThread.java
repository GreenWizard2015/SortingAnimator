package GreenWizard.SortingAnimator.commons;

import java.util.concurrent.atomic.AtomicBoolean;

// FIXME: using Thread.Sleep might be bad idea 
// TODO: add restart method
public abstract class BaseSortingThread extends Thread {
	protected AtomicBoolean Paused = new AtomicBoolean(true);
	protected AtomicBoolean InProgress = new AtomicBoolean(false);

	protected DataSource dataSource;

	public void setPaused(boolean isPaused) {
		Paused.set(isPaused);
	}

	public boolean isPaused() {
		return Paused.get();
	}

	public boolean isInProgress() {
		return InProgress.get();
	}

	public void setInProgress(boolean F) {
		InProgress.set(F);
	}

	/////////////////////////////////////
	private void checkData() throws Exception {
		do {
			if (isInterrupted() || !isInProgress())
				throw new Exception();

			if (dataSource.getUID() != GlobalOptions.getDataUID())
				throw new Exception();

			if (isPaused())
				Thread.sleep(15);
		} while (isPaused());
	}

	protected int compare(int indexA, int indexB) throws Exception {
		checkData();
		int r = dataSource.onCompare(indexA, indexB);
		Thread.sleep(GlobalOptions.getCompareDelay());
		return r;
	}

	protected int compareValue(int A, int indexB) throws Exception {
		checkData();
		int r = dataSource.onCompareValues(A, indexB);
		Thread.sleep(GlobalOptions.getCompareDelay());
		return r;
	}

	protected void swap(int indexA, int indexB) throws Exception {
		checkAccess();
		dataSource.onSwap(indexA, indexB);
		Thread.sleep(GlobalOptions.getSwapDelay());
	}

	protected int getLength() throws Exception {
		checkData();
		return dataSource.getLength();
	}

	protected void markAsSorted(int index) throws Exception {
		checkData();
		dataSource.markAsSorted(index);
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			if (isInProgress()) {
				boolean isOk = true;
				try {
					setPaused(false);
					dataSource.reset();
					executeSorting();
				} catch (Exception e) {
					dataSource.reset();
					isOk = false;
				} finally {
					setPaused(true);
					setInProgress(false);
					dataSource.onFinish(isOk);
				}
			}

			try {
				if (dataSource.getUID() != GlobalOptions.getDataUID())
					dataSource.reset();
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}

	public void setSource(DataSource source) {
		dataSource = source;
	}

	protected int get(int index) throws Exception {
		checkAccess();
		return dataSource.get(index);
	}

	protected void set(int index, int val) throws Exception {
		checkAccess();
		dataSource.set(index, val);
		Thread.sleep(GlobalOptions.getSwapDelay());
	}
	
	public abstract String getTitle();
	protected abstract void executeSorting() throws Exception;
}
