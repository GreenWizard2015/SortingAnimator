package GreenWizard.SortingAnimator.commons;

public interface DataSource {
	/** return current dataset ID */
	public int getUID();
	/** reset stored data to default */
	public void reset();
	/** perform comparison of two elements in stored dataset */
	public int onCompare(int indexA, int indexB);
	/** perform comparison of Value with stored dataset element */
	public int onCompareValues(int Value, int indexB);
	/** swap two elements in stored dataset */
	public void onSwap(int indexA, int indexB);

	public int getLength();
	public void markAsSorted(int index);

	public void onFinish(boolean isOk);
	/** get value by index from dataset */
	public int get(int index);
	/** put value into dataset by index */
	public void set(int index, int value);
}
