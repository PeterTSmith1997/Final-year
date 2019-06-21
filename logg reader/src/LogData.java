import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogData extends JFrame {
	private Menu menu;
	private JFrame frame;
	private JScrollPane botsScrollPane;
	private JTable tbTopIps;
	private JLabel lblTopPages;
	private JScrollPane scrollPane_1;
	private JTable tblPages;
	private DefaultTableModel topIPsMd;
	private JPanel panel;
	private JTextField txtFilter;
	public LogData(Menu menu) {
		this.menu = menu;
		makeui();

	}

	public void makeui() {
		frame = new JFrame();

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1169, 686);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 476, 0, 358, 0, 400, 10,
				0 };
		gridBagLayout.rowHeights = new int[] { 10, 5, 127, 501, 60, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lbIPs = new JLabel("IPs on site");
		lbIPs.setHorizontalAlignment(SwingConstants.CENTER);
		lbIPs.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lbIPs = new GridBagConstraints();
		gbc_lbIPs.insets = new Insets(0, 0, 5, 5);
		gbc_lbIPs.gridx = 1;
		gbc_lbIPs.gridy = 0;
		frame.getContentPane().add(lbIPs, gbc_lbIPs);

		lblTopPages = new JLabel("Top pages");
		lblTopPages.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopPages.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblTimeOfDay = new GridBagConstraints();
		gbc_lblTimeOfDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeOfDay.gridx = 3;
		gbc_lblTimeOfDay.gridy = 0;
		frame.getContentPane().add(lblTopPages, gbc_lblTimeOfDay);

		botsScrollPane = new JScrollPane();
		GridBagConstraints gbc_botsScrollPane = new GridBagConstraints();
		gbc_botsScrollPane.gridheight = 2;
		gbc_botsScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_botsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_botsScrollPane.gridx = 1;
		gbc_botsScrollPane.gridy = 2;
		frame.getContentPane().add(botsScrollPane, gbc_botsScrollPane);

		tbTopIps = new JTable();
		String ipHeader[] = new String[] {"ip", "Number"};
		topIPsMd = new DefaultTableModel(null,ipHeader);
		tbTopIps.setModel(topIPsMd);
		lbIPs.setLabelFor(tbTopIps); 
		tbTopIps.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				String ip = (String) tbTopIps.getValueAt(row, 0);
				// TODO ui to get all data on a IP
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
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		frame.getContentPane().add(panel, gbc_panel);
		
		txtFilter = new JTextField();
		txtFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String search = txtFilter.getText();
				try {
					int s = Integer.parseInt(search);
					updaateGUI(s);
					System.out.println(s);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		txtFilter.setText("Filter");
		panel.add(txtFilter);
		txtFilter.setColumns(10);

	/**	tblPages = new JTable(tables(menu.getPages(), "page", "num times"));
		lblTopPages.setLabelFor(tblPages);
		tblPages.setAutoCreateRowSorter(true);
		tblPages.setEnabled(false);
		scrollPane_1.setViewportView(tblPages);
		JScrollPane scrollPane2 = new JScrollPane();
		getContentPane().add(scrollPane2, BorderLayout.WEST);
		*/
		updaateGUI(0);

		this.frame.setVisible(true);

	}
	private void updaateGUI(int s) {
		String ipHeader[] = new String[] {"ip", "Number"};
		topIPsMd = new DefaultTableModel(null, ipHeader);
		tbTopIps.setModel(topIPsMd);

		for (Entry<String, Integer> val : menu.getOrrcancesOfip().entrySet()) {
			Integer value = val.getValue();
			String vs = value.toString();
			if (val.getValue() >= s) 
				topIPsMd.addRow(new String[] {val.getKey(), vs});
			}
			
		}
	}

