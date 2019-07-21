package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
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

import admin.LoginUI;
import dataModel.DataStore;
import dataModel.Reader;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

/**
 * @author peter
 * @version 19 Jul 2019
 */
public class LogData extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -5672269806381056292L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LogData();
			}
		});
	}

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
	private Font deflautFont;
	private JPanel panel;
	private JButton btnAdmin;

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

	public void makeui() {
		frmLogFileReader = new JFrame();
		frmLogFileReader.setResizable(false);
		frmLogFileReader.setTitle("Log file reader");

		frmLogFileReader.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmLogFileReader.setBounds(100, 100, 1169, 686);
		frmLogFileReader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		frmLogFileReader.getContentPane().setLayout(new MigLayout("", "[654px][654px][653px,grow]", "[17px][715px][85px][106px,grow]"));
        deflautFont = new Font("Tahoma", Font.BOLD, 14);
		JLabel lbIPs = new JLabel("IPs on site");
		lbIPs.setHorizontalAlignment(SwingConstants.CENTER);
		lbIPs.setFont(deflautFont);
		frmLogFileReader.getContentPane().add(lbIPs,
				"cell 0 0,alignx center,aligny center");
        
		lblTopPages = new JLabel("Top pages");
		lblTopPages.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopPages.setFont(deflautFont);
		frmLogFileReader.getContentPane().add(lblTopPages,
				"cell 1 0,alignx center,aligny center");

		lblTimeOfDay = new JLabel("Time of day");
		lblTimeOfDay.setFont(deflautFont);
		frmLogFileReader.getContentPane().add(lblTimeOfDay,
				"cell 2 0,alignx center,aligny center");

		botsScrollPane = new JScrollPane();
		frmLogFileReader.getContentPane().add(botsScrollPane, "cell 0 1,grow");

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
				IPDetails details = new IPDetails(dataStore, ip);
				details.setVisible(true);
				frmLogFileReader.dispose();
			}
		});

		tbTopIps.setAutoCreateRowSorter(true);

		botsScrollPane.setViewportView(tbTopIps);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.WEST);

		scrollPane_1 = new JScrollPane();
		frmLogFileReader.getContentPane().add(scrollPane_1, "cell 1 1,grow");

		scrollPane_2 = new JScrollPane();
		frmLogFileReader.getContentPane().add(scrollPane_2, "cell 2 1,grow");

		refererTbl = new JTable();
		String timeHeadder[] = new String[] { "Time", "num" };
		refererlMd = new DefaultTableModel(null, timeHeadder);
		refererTbl.setModel(refererlMd);

		scrollPane_2.setViewportView(refererTbl);

		serchIPPL = new JPanel();
		frmLogFileReader.getContentPane().add(serchIPPL, "cell 0 2,grow");

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
		frmLogFileReader.getContentPane().add(panel_1, "cell 1 2,grow");

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
				frmLogFileReader.getContentPane().add(panel_2, "cell 1 3,grow");
				
						btnReadFile = new JButton("Read file");
						btnReadFile.addActionListener(new ActionListener() {
							@Override
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
						
						panel = new JPanel();
						frmLogFileReader.getContentPane().add(panel, "cell 2 3,grow");
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
						btnAdmin = new JButton("Admin login");
						btnAdmin.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								LoginUI loginUI = new LoginUI(dataStore);
								loginUI.setVisible(true);
								frmLogFileReader.dispose();
							}
						});
						panel.add(btnAdmin);
		JScrollPane scrollPane2 = new JScrollPane();
		getContentPane().add(scrollPane2, BorderLayout.WEST);

		this.frmLogFileReader.setVisible(true);

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

	private void updaateGUI() {
		String ipHeader[] = new String[] { "ip", "Number" };
		topIPsMd = new DefaultTableModel(null, ipHeader) {
			/**
			 *
			 */
			private static final long serialVersionUID = 4585202425202280069L;

			@Override
			public boolean isCellEditable(int row, int columm) {
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
				topIPsMd.addRow(new String[] { val.getKey(), vs });
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
}
