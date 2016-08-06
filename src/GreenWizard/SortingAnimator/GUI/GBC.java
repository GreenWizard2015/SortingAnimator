package GreenWizard.SortingAnimator.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBC {
	private GridBagConstraints bagConstraints = new GridBagConstraints();

	protected GBC() {
	}

	public static GBC grid(int x, int y) {
		GBC gbc = new GBC();
		gbc.bagConstraints.gridx = x;
		gbc.bagConstraints.gridy = y;
		return gbc;
	}

	public GridBagConstraints get() {
		return bagConstraints;
	}

	public GBC size(int x, int y) {
		bagConstraints.gridwidth = x;
		bagConstraints.gridheight = y;
		return this;
	}

	public GBC ipad(int x, int y) {
		bagConstraints.ipadx = x;
		bagConstraints.ipady = y;
		return this;
	}

	public GBC weight(double x, double y) {
		bagConstraints.weightx = x;
		bagConstraints.weighty = y;
		return this;
	}

	public GBC weightMax() {
		bagConstraints.weightx = 1;
		bagConstraints.weighty = 1;
		return this;
	}

	public GBC fill(int fType) {
		bagConstraints.fill = fType;
		return this;
	}

	public GBC fillHorizontal() {
		bagConstraints.fill = GridBagConstraints.HORIZONTAL;
		return this;
	}

	public GBC fillVertical() {
		bagConstraints.fill = GridBagConstraints.VERTICAL;
		return this;
	}

	public GBC fillAll() {
		bagConstraints.fill = GridBagConstraints.BOTH;
		return this;
	}

	public void setInsets(int top, int left, int bottom, int right) {
		bagConstraints.insets = new Insets(top, left, bottom, right);
	}

	public GBC insets(int top, int left, int bottom, int right) {
		setInsets(top, left, bottom, right);
		return this;
	}

	public GBC insets(int all) {
		setInsets(all, all, all, all);
		return this;
	}

	public GBC insets(int W, int H) {
		setInsets(H, W, H, W);
		return this;
	}

}
