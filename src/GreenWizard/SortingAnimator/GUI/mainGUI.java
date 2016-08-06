package GreenWizard.SortingAnimator.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.text.NumberFormatter;

import GreenWizard.SortingAnimator.SortAlgoritms.BubbleSort;
import GreenWizard.SortingAnimator.SortAlgoritms.HeapSort;
import GreenWizard.SortingAnimator.SortAlgoritms.InsertionSort;
import GreenWizard.SortingAnimator.SortAlgoritms.QuickSort;
import GreenWizard.SortingAnimator.SortAlgoritms.SelectionSort;
import GreenWizard.SortingAnimator.SortAlgoritms.ShellSort;
import GreenWizard.SortingAnimator.commons.BaseSortingThread;
import GreenWizard.SortingAnimator.commons.GlobalOptions;

@SuppressWarnings("serial")
public class mainGUI extends JFrame {
	private JPanel AlgoContainer = new JPanel(new GridLayout(2, 4));

	public mainGUI() {
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);

		setTitle("Sorting algorithms animation.");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(402, 550));
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		add(createControls(), BorderLayout.NORTH);

		addAlgorithm(new BubbleSort());
		addAlgorithm(new InsertionSort());
		addAlgorithm(new SelectionSort());
		addAlgorithm(new ShellSort());
		addAlgorithm(new HeapSort());
		addAlgorithm(new QuickSort());

		add(AlgoContainer);
	}

	private void addAlgorithm(BaseSortingThread base) {
		AlgoContainer.add(new SortingAnimator(base));
	}

	// TODO: need refactoring
	private Component createControls() {
		JPanel pnl = new JPanel();
		pnl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		GridBagLayout layout = new GridBagLayout();
		pnl.setLayout(layout);

		JLabel lblSize = new JLabel("Size [5..1000]:");
		//////////////////////
		NumberFormat numFormat = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(numFormat);
		formatter.setValueClass(Integer.class);
		formatter.setAllowsInvalid(true);
		formatter.setMinimum(5);
		formatter.setMaximum(1000);

		JFormattedTextField edtSize = new JFormattedTextField(formatter);
		edtSize.setValue(5);

		pnl.add(lblSize, GBC.grid(1, 1).insets(5).get());
		pnl.add(edtSize,
				GBC.grid(2, 1).fillHorizontal()
				.size(GridBagConstraints.REMAINDER, 1)
				.weightMax().insets(5).get());
		
		////////////////////////////////////////////////////
		JFormattedTextField edtDelay = new JFormattedTextField(formatter);
		edtDelay.addPropertyChangeListener("value", new PropertyChangeListener(){			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Object nv = evt.getNewValue();
				if(null != nv){
					GlobalOptions.setDelay((Integer)nv);
				}
			}
		});
		edtDelay.setValue(250);
		
		pnl.add(new JLabel("Delay [5..1000]:"), GBC.grid(1, 2).insets(5).get());

		pnl.add(edtDelay,
				GBC.grid(2, 2).fillHorizontal()
				.size(GridBagConstraints.REMAINDER, 1)
				.weightMax().insets(5).get());

		JButton startAll = new JButton("Run all");
		JButton resetData = new JButton("Reset data");
		resetData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GlobalOptions.createData((Integer)edtSize.getValue());
					GlobalOptions.setDelay((Integer)edtDelay.getValue());
					startAll.setEnabled(true);
				} catch (Exception e2) {
				}
			}
		});

		pnl.add(resetData, 
				GBC.grid(1, 10).size(3, 1).fillHorizontal()
				.weightMax().insets(5).get());

		startAll.setEnabled(false);
		startAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startAll.setEnabled(false);
				GlobalOptions.setDelay((Integer) edtDelay.getValue());
				Component[] all = AlgoContainer.getComponents();
				for (int i = 0; i < all.length; i++) {
					SortingAnimator alg = (SortingAnimator) all[i];
					alg.restart();
				}
			}
		});

		pnl.add(startAll, 
				GBC.grid(6, 10).size(3, 1).fillHorizontal()
				.weightMax().insets(5).get());

		return pnl;
	}

	public static void main(String[] args) {
		mainGUI gui = new mainGUI();
		gui.setVisible(true);
	}

}