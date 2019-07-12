import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ipDetails extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataStore dataStore;
	private String ip;
	private int timesVisted;
	private int totalData;
	private Analise analise = new Analise();
	private String hitData;
	private JTextField textFieldTimesVisted;
	private JTextField textFieldTotalData;

	/**
	 * @param dataStore
	 */
	public ipDetails(DataStore dataStore, String ip) {
		this.dataStore = dataStore;
		this.ip = ip;

		makeUi();
	}

	public void makeUi() {
		setTitle("Ip details for " + ip);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(600, 560));
		setLocationRelativeTo(null);
		// Get data
		setTimesVisted(dataStore.getOrrcancesOfip().get(ip));
		setTotalData(analise.getTotalDataForIP(dataStore.getHits(), ip));
		ArrayList<Hits> hits = dataStore.getHits();
		hitData = "";
		for (int i = 0; i < hits.size(); i++) {
			if (hits.get(i).getiPaddr().equals(ip)) {
				hitData += hits.get(i) + "\n";
			}
		}
		JPanel panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);

		JLabel title = new JLabel("Ip details for " + ip);
		panelTop.add(title);

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(10, 200));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));

		JButton btnViewDbInfo = new JButton("View db info on " + ip);
		panel_3.add(btnViewDbInfo, BorderLayout.EAST);

		JButton btnReportIp = new JButton("Report IP");
		btnReportIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		panel_3.add(btnReportIp, BorderLayout.WEST);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblTimesVisted = new JLabel("Times visted");
		panel_2.add(lblTimesVisted);

		textFieldTimesVisted = new JTextField();
		lblTimesVisted.setLabelFor(textFieldTimesVisted);
		textFieldTimesVisted.setColumns(10);
		textFieldTimesVisted.setText(Integer.toString(timesVisted));
		textFieldTimesVisted.setEditable(false);
		panel_2.add(textFieldTimesVisted);

		JLabel lblTotalData = new JLabel("Total data sent");
		panel_2.add(lblTotalData);

		textFieldTotalData = new JTextField();
		lblTotalData.setLabelFor(textFieldTotalData);
		textFieldTotalData.setColumns(10);
		textFieldTotalData.setText(Integer.toString(totalData));
		textFieldTotalData.setEditable(false);
		panel_2.add(textFieldTotalData);

		JPanel panel_4 = new JPanel();

		panel.add(panel_4, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(100, 32767));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		int risk = analise.risk(ip, dataStore);
		JLabel lblRiskFactor = new JLabel(
				"Risk Factor: " + risk);
		lblRiskFactor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRiskFactor.setHorizontalAlignment(SwingConstants.CENTER);
		lblRiskFactor.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblRiskFactor, BorderLayout.NORTH);

		JProgressBar riskBar = new JProgressBar();
		lblRiskFactor.setLabelFor(riskBar);
		riskBar.setValue(risk);
		riskBar.setOpaque(false);
		int value = riskBar.getValue();
		if (value < 25) {
			riskBar.setForeground(Color.GREEN);
		} else if (value < 50) {
			riskBar.setForeground(Color.YELLOW);
		} else if (value < 75) {
			riskBar.setForeground(Color.ORANGE);
		} else {
			riskBar.setForeground(Color.RED);
		}
		panel_1.add(riskBar, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));

		JLabel lblAllHitsFor = new JLabel("All hits for this IP");
		lblAllHitsFor.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblAllHitsFor, BorderLayout.NORTH);

		JTextArea txtrAllHits = new JTextArea(6, 100);
		txtrAllHits.setMaximumSize(new Dimension(100, 21));
		txtrAllHits.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtrAllHits.setAlignmentY(Component.TOP_ALIGNMENT);
		txtrAllHits.setWrapStyleWord(true);
		txtrAllHits.setLineWrap(true);
		txtrAllHits.setText(hitData);
		txtrAllHits.setSelectionStart(0);
		txtrAllHits.setSelectionEnd(0);

		JScrollPane sp = new JScrollPane(txtrAllHits,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblAllHitsFor.setLabelFor(sp);
		sp.setMaximumSize(new Dimension(100, 21));
		panel_4.add(sp);
	}

	/**
	 * @return the timesVisted
	 */
	public int getTimesVisted() {
		return timesVisted;
	}

	/**
	 * @param timesVisted
	 *            the timesVisted to set
	 */
	public void setTimesVisted(int timesVisted) {
		this.timesVisted = timesVisted;
	}

	/**
	 * @return the totalData
	 */
	public int getTotalData() {
		return totalData;
	}

	/**
	 * @param totalData
	 *            the totalData to set
	 */
	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
