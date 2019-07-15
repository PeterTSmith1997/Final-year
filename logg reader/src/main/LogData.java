package main;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;

public class LogData extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmLogFileReader;
	private JScrollPane botsScrollPane;
	private JTable tbTopIps;
	private JLabel lblTopPages;
	private JScrollPane scrollPane_1;
	private JTable tblPages;
	private DefaultTableModel topIPsMd;
	private JPanel serchIPPL;
	private JTextField txtFilter;
	private JLabel lblSearchByTimes;
	private DefaultTableModel topPagesMd;
	private int timesHitIp = 0;
	private int timesHitPages = 0;
	private JPanel panel_1;
	private JLabel lblSerch;
	private JTextField pageFilter;
	private JScrollPane scrollPane_2;
	private JLabel lblTimeOfDay;
	private DefaultTableModel refererlMd;
	private JTable refererTbl;
	private JPanel panel_2;
	private JButton btnReadFile;
	private Reader reader;
	private DataStore dataStore;
	private JButton btnViewKnownIps;

	public LogData() {
		dataStore = new DataStore();
		reader = new Reader(dataStore);
		makeui();

	}

	/**
	 * @param dataStore
	 */
	public LogData(DataStore dataStore) {
		this.dataStore = dataStore;
		makeui();
		reader = new Reader(dataStore);
		updaateGUI();
	}

	public void makeui() {
		frmLogFileReader = new JFrame();
		frmLogFileReader.setTitle("Log file reader");

		frmLogFileReader.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmLogFileReader.setBounds(100, 100, 1169, 686);
		frmLogFileReader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 476, 0, 558, 0, 400, 10,
				0 };
		gridBagLayout.rowHeights = new int[] { 10, 5, 127, 501, 44, 60, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		frmLogFileReader.getContentPane().setLayout(gridBagLayout);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lbIPs = new JLabel("IPs on site");
		lbIPs.setHorizontalAlignment(SwingConstants.CENTER);
		lbIPs.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lbIPs = new GridBagConstraints();
		gbc_lbIPs.insets = new Insets(0, 0, 5, 5);
		gbc_lbIPs.gridx = 1;
		gbc_lbIPs.gridy = 0;
		frmLogFileReader.getContentPane().add(lbIPs, gbc_lbIPs);

		lblTopPages = new JLabel("Top pages");
		lblTopPages.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopPages.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblTimeOfDay = new GridBagConstraints();
		gbc_lblTimeOfDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeOfDay.gridx = 3;
		gbc_lblTimeOfDay.gridy = 0;
		frmLogFileReader.getContentPane().add(lblTopPages, gbc_lblTimeOfDay);

		lblTimeOfDay = new JLabel("Time of day");
		GridBagConstraints gbc_lblTimeOfDayLabel = new GridBagConstraints();
		gbc_lblTimeOfDayLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeOfDayLabel.gridx = 5;
		gbc_lblTimeOfDayLabel.gridy = 0;
		frmLogFileReader.getContentPane().add(lblTimeOfDay,
				gbc_lblTimeOfDayLabel);

		botsScrollPane = new JScrollPane();
		GridBagConstraints gbc_botsScrollPane = new GridBagConstraints();
		gbc_botsScrollPane.gridheight = 2;
		gbc_botsScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_botsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_botsScrollPane.gridx = 1;
		gbc_botsScrollPane.gridy = 2;
		frmLogFileReader.getContentPane().add(botsScrollPane,
				gbc_botsScrollPane);

		tbTopIps = new JTable();
		String ipHeader[] = new String[] { "ip", "Number" };
		topIPsMd = new DefaultTableModel(null, ipHeader);
		tbTopIps.setModel(topIPsMd);
		lbIPs.setLabelFor(tbTopIps);
		tbTopIps.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				String ip = (String) tbTopIps.getValueAt(row, 0);
				ipDetails details = new ipDetails(dataStore, ip);
				details.setVisible(true);
				frmLogFileReader.dispose();
			}
		});

		tbTopIps.setAutoCreateRowSorter(true);

		botsScrollPane.setViewportView(tbTopIps);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.WEST);

		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 3;
		gbc_scrollPane_1.gridy = 2;
		frmLogFileReader.getContentPane().add(scrollPane_1, gbc_scrollPane_1);

		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridheight = 2;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 5;
		gbc_scrollPane_2.gridy = 2;
		frmLogFileReader.getContentPane().add(scrollPane_2, gbc_scrollPane_2);

		refererTbl = new JTable();
		String timeHeadder[] = new String[] { "Time", "num" };
		refererlMd = new DefaultTableModel(null, timeHeadder);
		refererTbl.setModel(refererlMd);

		scrollPane_2.setViewportView(refererTbl);

		serchIPPL = new JPanel();
		GridBagConstraints gbc_serchIPPL = new GridBagConstraints();
		gbc_serchIPPL.insets = new Insets(0, 0, 5, 5);
		gbc_serchIPPL.fill = GridBagConstraints.BOTH;
		gbc_serchIPPL.gridx = 1;
		gbc_serchIPPL.gridy = 4;
		frmLogFileReader.getContentPane().add(serchIPPL, gbc_serchIPPL);

		txtFilter = new JTextField();
		txtFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String search = txtFilter.getText();
				try {
					setTimesHitIp(Integer.parseInt(search));
					updaateGUI();
				} catch (Exception e) {
					setTimesHitIp(0);
					updaateGUI();
				}
			}
		});

		lblSearchByTimes = new JLabel("Search by times hit");
		lblSearchByTimes.setLabelFor(txtFilter);
		serchIPPL.add(lblSearchByTimes);
		txtFilter.setText("Filter");
		serchIPPL.add(txtFilter);
		txtFilter.setColumns(10);

		tblPages = new JTable();

		String pageHeader[] = new String[] { "page", "num times" };
		topPagesMd = new DefaultTableModel(null, pageHeader);
		tblPages.setModel(topPagesMd);
		tblPages.setAutoCreateRowSorter(true);
		tblPages.setEnabled(false);
		scrollPane_1.setViewportView(tblPages);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 3;
		gbc_panel_1.gridy = 4;
		frmLogFileReader.getContentPane().add(panel_1, gbc_panel_1);

		lblSerch = new JLabel("Serch");
		panel_1.add(lblSerch);

		pageFilter = new JTextField();
		pageFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String search = pageFilter.getText();
				try {
					setTimesHitPages(Integer.parseInt(search));
					updaateGUI();
				} catch (Exception e) {
					setTimesHitPages(0);
					updaateGUI();
				}
			}
		});
		panel_1.add(pageFilter);
		pageFilter.setColumns(10);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 5;
		frmLogFileReader.getContentPane().add(panel_2, gbc_panel_2);

		btnReadFile = new JButton("Read file");
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView
						.getFileSystemView().getDefaultDirectory());
				jfc.setDialogTitle("Select a log file");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Text files", "txt");
				jfc.addChoosableFileFilter(filter);
				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.err.println(jfc.getSelectedFile().getPath());
					reader.setFile(jfc.getSelectedFile().getPath());
					reader.readFile();

				}
				updaateGUI();

			}
		});
		panel_2.add(btnReadFile);

		btnViewKnownIps = new JButton("View known IPs");
		btnViewKnownIps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// known UI here
			}
		});
		panel_2.add(btnViewKnownIps);
		JScrollPane scrollPane2 = new JScrollPane();
		getContentPane().add(scrollPane2, BorderLayout.WEST);

		this.frmLogFileReader.setVisible(true);

	}

	private void updaateGUI() {
		String ipHeader[] = new String[] { "ip", "Number", "Risk"};
		topIPsMd = new DefaultTableModel(null, ipHeader) {
			@Override
			public boolean isCellEditable(int row, int columm){
				return false;
			}
		};
		tbTopIps.setModel(topIPsMd);

		String pageHeader[] = new String[] { "page", "num times" };
		topPagesMd = new DefaultTableModel(null, pageHeader);
		tblPages.setModel(topPagesMd);

		String timeHeadder[] = new String[] { "Time", "num" };
		refererlMd = new DefaultTableModel(null, timeHeadder);
		refererTbl.setModel(refererlMd);
		
		
		for (Entry<String, Integer> val : dataStore.getOrrcancesOfip()
				.entrySet()) {
			Integer value = val.getValue();
			String vs = value.toString();
			if (val.getValue() >= timesHitIp) {
				topIPsMd.addRow(new String[] { val.getKey(), vs});
			}
		}
		for (Entry<String, Integer> val : dataStore.getPages().entrySet()) {
			Integer value = val.getValue();
			String vs = value.toString();
			if (val.getValue() >= timesHitPages) {
				topPagesMd.addRow(new String[] { val.getKey(), vs });
			}
		}
		for (Entry<String, Integer> val : dataStore.getReferers().entrySet()) {
			Integer value = val.getValue();
			String vs = value.toString();
			if (val.getValue() >= timesHitPages) {
				if (val.getKey().equals("\"-\"")) {
					refererlMd.addRow(new String[] { "direct", vs });
				} else {
					refererlMd.addRow(new String[] { val.getKey(), vs });
				}
			}
		}

	}

	/**
	 * @return the timesHitIp
	 */
	public int getTimesHitIp() {
		return timesHitIp;
	}

	/**
	 * @return the timesHitPages
	 */
	public int getTimesHitPages() {
		return timesHitPages;
	}

	/**
	 * @param timesHitIp
	 *            the timesHitIp to set
	 */
	public void setTimesHitIp(int timesHitIp) {
		this.timesHitIp = timesHitIp;
	}

	/**
	 * @param timesHitPages
	 *            the timesHitPages to set
	 */
	public void setTimesHitPages(int timesHitPages) {
		this.timesHitPages = timesHitPages;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					LogData frame = new LogData();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
