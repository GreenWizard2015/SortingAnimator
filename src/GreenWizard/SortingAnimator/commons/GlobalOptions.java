package GreenWizard.SortingAnimator.commons;

import java.util.Arrays;
import java.util.Random;

public class GlobalOptions {
	public static final int MIN_VALUE = 5;
	public static final int MAX_VALUE = 100;
	public static final int VALUES_RANGE = MAX_VALUE - MIN_VALUE;

	private static Random r = new Random();
	private static int[] data;
	private static int dataUID;
	private static long DelayValue = 250;

	public static long getCompareDelay() {
		return DelayValue;
	}

	public static long getSwapDelay() {
		return DelayValue;
	}

	public synchronized static void createData(int Size) {
		data = new int[Size];
		for (int i = 0; i < data.length; i++) {
			data[i] = MIN_VALUE + r.nextInt(VALUES_RANGE);
		}
		dataUID = r.nextInt();
	}

	public synchronized static int getDataUID() {
		return dataUID;
	}

	public synchronized static int[] getDataCopy() {
		if (null == data)
			createData(r.nextInt(20) + 10);
		return Arrays.copyOf(data, data.length);
	}

	public static void setDelay(int value) {
		DelayValue = value;
	}
}
