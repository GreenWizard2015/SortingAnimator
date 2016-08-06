package GreenWizard.SortingAnimator.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.ToolTipManager;

import GreenWizard.SortingAnimator.commons.DataSource;
import GreenWizard.SortingAnimator.commons.GlobalOptions;

@SuppressWarnings("serial")
public class ArrayDrawer extends JComponent implements DataSource {
	private static final Color NORMAL_COLOR = Color.red;
	private static final Color MARKED_COLOR = Color.green;
	private static final Color SWAP_COLOR = Color.MAGENTA;
	private static final Color COMPARE_COLOR = Color.BLACK;

	private int[] storedData;
	private boolean[] isMarked;
	private int lastOperationA, lastOperationB;

	private int swapN, compareN, readN, writeN;

	private boolean lastOpWasSwap;
	private int dataUID;

	private void resetOperations() {
		lastOperationA = -1;
		lastOperationB = -1;
	}

	public ArrayDrawer() {
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		if (null == storedData)
			return;

		float scalingX = (float) (this.getWidth() - 10) / (GlobalOptions.VALUES_RANGE + 1);
		float scalingY = (float) (this.getHeight() - 10) / storedData.length;
		
		int lineH = Math.round(Math.max(1, scalingY - 2));
		
		int minValue = GlobalOptions.MIN_VALUE - 1;

		for (int i = 0; i < storedData.length; i++) {
			int Y = Math.round(i * scalingY);
			int W = Math.round((storedData[i] - minValue) * scalingX);

			if ((i == lastOperationA) || (i == lastOperationB))
				g2.setColor(lastOpWasSwap ? SWAP_COLOR : COMPARE_COLOR);
			else
				g2.setColor(isMarked[i] ? MARKED_COLOR : NORMAL_COLOR);
			g2.fillRect(5, Y, W, lineH);
		}
	}

	@Override
	public synchronized int getUID() {
		return dataUID;
	}

	@Override
	public synchronized void reset() {
		storedData = GlobalOptions.getDataCopy();
		dataUID = GlobalOptions.getDataUID();
		isMarked = new boolean[storedData.length];
		for (int i = 0; i < isMarked.length; i++) {
			isMarked[i] = false;
		}

		compareN = swapN = readN = writeN = 0;
		resetOperations();
		repaint();
	}

	@Override
	public synchronized int onCompare(int indexA, int indexB) {
		compareN++;
		readN += 2;
		setOperation(indexA, indexB, false);
		return storedData[indexA] - storedData[indexB];
	}

	@Override
	public synchronized int onCompareValues(int A, int indexB) {
		compareN++;
		readN++;
		setOperation(-1, indexB, false);
		return A - storedData[indexB];
	}

	private void setOperation(int indexA, int indexB, boolean b) {
		lastOperationA = indexA;
		lastOperationB = indexB;
		lastOpWasSwap = b;
		repaint();
	}

	@Override
	public void onSwap(int indexA, int indexB) {
		setOperation(indexA, indexB, true);

		int A = storedData[indexA];
		storedData[indexA] = storedData[indexB];
		storedData[indexB] = A;

		readN += 2;
		writeN += 2;
		swapN++;
		repaint();
	}

	@Override
	public synchronized int getLength() {
		return storedData.length;
	}

	@Override
	public synchronized void markAsSorted(int index) {
		isMarked[index] = true;
		resetOperations();
		repaint();
	}

	@Override
	public synchronized void onFinish(boolean isOk) {
		resetOperations();
		for (int i = 0; i < storedData.length - 1; i++) {
			isMarked[i] = isOk && (storedData[i] >= storedData[i + 1]);
		}
		isMarked[isMarked.length - 1] = isMarked[isMarked.length - 2];
		repaint();
	}

	@Override
	public synchronized int get(int index) {
		readN++;
		return storedData[index];
	}

	@Override
	public synchronized void set(int index, int value) {
		writeN++;
		storedData[index] = value;
		setOperation(-1, index, true);
	}

	private static final String TOOLTIP_FORMAT = 
			"<html>Count: %d<br>Comparasions: %d<br>Swaps: %d<br>"
			+ "Read: %d<br>Write: %d<html>";
	
	@Override
	public String getToolTipText(MouseEvent event) {
		// FIXME: might cause error
		int Size = (null == storedData) ? 0 : storedData.length;		
		return String.format(TOOLTIP_FORMAT, 
							 Size, compareN, swapN, readN, writeN);
	}
}
