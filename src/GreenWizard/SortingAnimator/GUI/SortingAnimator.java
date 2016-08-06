package GreenWizard.SortingAnimator.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import GreenWizard.SortingAnimator.commons.BaseSortingThread;

@SuppressWarnings("serial")
public class SortingAnimator extends JComponent {
	private JLabel lblTitle = new JLabel("", SwingConstants.CENTER);
	private JButton btnSuspending = new JButton();
	private JButton btnRunStatus = new JButton();

	// TODO: Hide thread inside ArrayDrawer
	private BaseSortingThread thread;
	
	// Bind GUI updates to sorting process 
	private ArrayDrawer animator = new ArrayDrawer() {
		// fix deadlock on AWTTreeLock
		private Runnable updateTask = new Runnable() {			
			@Override
			public void run() {
				SortingAnimator.this.updateBtnText();
			}
		};
		public synchronized void onFinish(boolean isOk) {
			super.onFinish(isOk);
			SwingUtilities.invokeLater(updateTask);
		};

		public synchronized void reset() {
			super.reset();
			SwingUtilities.invokeLater(updateTask); 
		};
	};

	private void updateBtnText() {
		btnSuspending.setEnabled(thread.isInProgress());
		btnSuspending.setText(thread.isPaused() ? "Continue" : "Pause");
		btnRunStatus.setText(thread.isInProgress() ? "Stop" : "Start");
	}

	public SortingAnimator(BaseSortingThread _thread) {		
		this.thread = _thread;

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.red));

		lblTitle.setText(thread.getTitle());
		add(lblTitle, BorderLayout.NORTH);
		///////////////////////////////////////////
		add(animator, BorderLayout.CENTER);
		thread.setSource(animator);
		///////////////////////////////////////////
		add(createButtonsBar(), BorderLayout.SOUTH);

		_thread.start();
	}

	private Component createButtonsBar() {
		JPanel btnHolder = new JPanel();
		btnHolder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		btnHolder.setLayout(new GridLayout(2, 0, 5, 5));
		// Run/Pause
		btnRunStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread.setInProgress(!thread.isInProgress());
				updateBtnText();
			}
		});
		btnHolder.add(btnRunStatus);
		/////////////////////////////////////////
		// Pause/Continue
		btnSuspending.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread.setPaused(!thread.isPaused());
				updateBtnText();
			}
		});
		btnHolder.add(btnSuspending);
		updateBtnText();
		
		return btnHolder;
	}

	// FIXME: Must stop current sorting and start new
	public void restart() {
		thread.setInProgress(true);
	}
}
