package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ipDetails extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Analise analise = new Analise();
	private JButton btnViewDbInfo;
	private JPanel buttonPanel;
	private Database database = new Database();
	private DataStore dataStore;
	private JPanel highLevelPanel;
	private String ip;
	private IPFunctions ipFunctions;
	private JLabel lbCounrtyCode;
	private JLabel lblAllHitsFor;
	private JLabel lblRiskFactor;
	private JLabel lblTimesReported;
	private boolean pane2Loaded = false;
	private JPanel panel;
	private JPanel panel_5;
	private JPanel panelRiskBar;
	private JPanel panelTop;
	private JPanel rawDataPanel;
	private JProgressBar riskBar;
	private JScrollPane sp;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTextField textFieldCounrtyCode;
	private JTextField textFieldTimes;
	private JTextField textFieldTimesVisted;
	private JTextField textFieldTotalData;
	private int timesVisted;
	private JLabel title;
	private int totalData;
	private JTextArea txtrAllHits;

	/**
	 * @param dataStore
	 */
	public ipDetails(DataStore dataStore, String ip) {

		this.dataStore = dataStore;
		this.ip = ip;

		makeUi();
	}

	/**
	 * @return the timesVisted
	 */
	public int getTimesVisted() {
		return timesVisted;
	}

	/**
	 * @return the totalData
	 */
	public int getTotalData() {
		return totalData;
	}

	public void makeUi() {
		setAlwaysOnTop(true);
		setBounds(100, 100, 1169, 686);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Ip details for " + ip);
		setLocationRelativeTo(null);
		// Get data
		setTimesVisted(dataStore.getOrrcancesOfip().get(ip));
		setTotalData(analise.getTotalDataForIP(dataStore.getHits(), ip));
		ArrayList<Hits> hits = dataStore.getHits();
		panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);

		title = new JLabel("Ip details for " + ip);
		panelTop.add(title);

		panelRiskBar = new JPanel();
		panelRiskBar.setMaximumSize(new Dimension(100, 32767));
		getContentPane().add(panelRiskBar, BorderLayout.SOUTH);
		panelRiskBar.setLayout(new BorderLayout(0, 0));
		double riskRaw = analise.risk(ip, dataStore);

		double dataBaseRisk = database.getRiskIP(ip);
		int risk = (int) (riskRaw + (dataBaseRisk * analise.getDbRiskMod()));
		lblRiskFactor = new JLabel("Risk Factor: " + risk);
		lblRiskFactor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRiskFactor.setHorizontalAlignment(SwingConstants.CENTER);
		lblRiskFactor.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRiskBar.add(lblRiskFactor, BorderLayout.NORTH);

		riskBar = new JProgressBar();
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
		panelRiskBar.add(riskBar, BorderLayout.SOUTH);

		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		panel_5 = new JPanel();
		tabbedPane.addTab("HL veiw", null, panel_5, null);
		panel_5.setLayout(new GridLayout(1, 0, 0, 0));

		panel = new JPanel();
		panel_5.add(panel);
		panel.setMinimumSize(new Dimension(10, 200));
		panel.setLayout(new BorderLayout(0, 0));

		buttonPanel = new JPanel();
		panel.add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		btnViewDbInfo = new JButton("View db info on " + ip);
		buttonPanel.add(btnViewDbInfo, BorderLayout.EAST);

		JButton btnReportIp = new JButton("Report IP");
		btnReportIp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(getContentPane(),
						"Report iP" + ip, "Comfrim", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Database database = new Database();
					database.updateRiskIP(ip, dataStore);
					dataStore.addReportedIP(ip);
					btnReportIp.setEnabled(false);
				}

			}
		});
		if (dataStore.getReportedIps().contains(ip)) {
			btnReportIp.setEnabled(false);
		} else {
			btnReportIp.setEnabled(true);
		}
		buttonPanel.add(btnReportIp, BorderLayout.WEST);

		highLevelPanel = new JPanel();
		panel.add(highLevelPanel, BorderLayout.CENTER);
		highLevelPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblTimesVisted = new JLabel("Times visted");
		highLevelPanel.add(lblTimesVisted);

		textFieldTimesVisted = new JTextField();
		lblTimesVisted.setLabelFor(textFieldTimesVisted);
		textFieldTimesVisted.setColumns(10);
		textFieldTimesVisted.setText(Integer.toString(timesVisted));
		textFieldTimesVisted.setEditable(false);
		highLevelPanel.add(textFieldTimesVisted);

		JLabel lblTotalData = new JLabel("Total data sent");
		highLevelPanel.add(lblTotalData);

		textFieldTotalData = new JTextField();
		lblTotalData.setLabelFor(textFieldTotalData);
		textFieldTotalData.setColumns(10);
		textFieldTotalData.setText(Integer.toString(totalData));
		textFieldTotalData.setEditable(false);
		highLevelPanel.add(textFieldTotalData);
		
		lbCounrtyCode = new JLabel("Counrty Code");
		highLevelPanel.add(lbCounrtyCode);
		
		textFieldCounrtyCode = new JTextField();
		textFieldCounrtyCode.setEditable(false);
		highLevelPanel.add(textFieldCounrtyCode);
		textFieldCounrtyCode.setColumns(10);
		ipFunctions = new IPFunctions();
		textFieldCounrtyCode.setText(ipFunctions.getLocation(ip));
		
		lblTimesReported = new JLabel("Times reported");
		lblTimesReported.setLabelFor(textFieldTimes);
		highLevelPanel.add(lblTimesReported);
		
		textFieldTimes = new JTextField();
		highLevelPanel.add(textFieldTimes);
		textFieldTimes.setColumns(10);
		textFieldTimes.setText(database.getOcourances(ip)+"");

		rawDataPanel = new JPanel();
		tabbedPane.addTab("Raw Data", null, rawDataPanel, "View raw data here");
		rawDataPanel.setLayout(new BorderLayout(0, 0));

		lblAllHitsFor = new JLabel("All hits for this IP");
		lblAllHitsFor.setHorizontalAlignment(SwingConstants.CENTER);
		rawDataPanel.add(lblAllHitsFor, BorderLayout.NORTH);

		txtrAllHits = new JTextArea(6, 0);
		txtrAllHits.setMaximumSize(new Dimension(100, 21));
		txtrAllHits.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtrAllHits.setAlignmentY(Component.TOP_ALIGNMENT);
		txtrAllHits.setWrapStyleWord(true);
		txtrAllHits.setLineWrap(true);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int pane = tabbedPane.getSelectedIndex();
				if (pane == 1 && !pane2Loaded) {
					for (int i = 0; i < hits.size(); i++) {
						if (hits.get(i).getiPaddr().equals(ip)) {
							txtrAllHits.append(hits.get(i) + "\n");
						}

						txtrAllHits.setSelectionStart(0);
						txtrAllHits.setSelectionEnd(0);
					}
				}
			}
		});
		sp = new JScrollPane(txtrAllHits,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblAllHitsFor.setLabelFor(sp);
		sp.setMaximumSize(new Dimension(100, 21));
		rawDataPanel.add(sp);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				LogData logData = new LogData(dataStore);
				dispose();
			}
		});
	}

	/**
	 * @param timesVisted
	 *            the timesVisted to set
	 */
	public void setTimesVisted(int timesVisted) {
		this.timesVisted = timesVisted;
	}

	/**
	 * @param totalData
	 *            the totalData to set
	 */
	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}
}