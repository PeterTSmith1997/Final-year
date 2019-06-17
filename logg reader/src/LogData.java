import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class LogData extends JFrame{
	Menu menu;
	private JFrame frame;
	private JPanel panel_1;
	private JScrollPane botsScrollPane;
	private JTable tbTopIps;
	public LogData(Menu menu) {
		this.menu = menu;
		makeui();
	}
	private void makeui() {
		frame = new JFrame();

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1169, 686);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 476, 0, 358, 0, 400, 10,
				0 };
		gridBagLayout.rowHeights = new int[] { 10, 5, 5, 501, 60, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblToDo = new JLabel("Known bots");
		lblToDo.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblToDo = new GridBagConstraints();
		gbc_lblToDo.insets = new Insets(0, 0, 5, 5);
		gbc_lblToDo.gridx = 1;
		gbc_lblToDo.gridy = 1;
		frame.getContentPane().add(lblToDo, gbc_lblToDo);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 1;
		frame.getContentPane().add(panel_1, gbc_panel_1);

		botsScrollPane = new JScrollPane();
		GridBagConstraints gbc_botsScrollPane = new GridBagConstraints();
		gbc_botsScrollPane.gridheight = 2;
		gbc_botsScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_botsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_botsScrollPane.gridx = 1;
		gbc_botsScrollPane.gridy = 2;
		frame.getContentPane().add(botsScrollPane, gbc_botsScrollPane);

		tbTopIps = new JTable(tables(menu.get, c1, c2));
		tbTopIps.setAutoCreateRowSorter(true);

		botsScrollPane.setViewportView(tbTopIps);


	}
	private DefaultTableModel tables (Map<String, Integer> data, String c1, String c2) {
		DefaultTableModel tbl = new DefaultTableModel();
		Object[] row = new Object[2];
		tbl.addColumn(c1);
		tbl.addColumn(c2);

		for (Entry<String, Integer> val : data.entrySet()) {
			row[0] = val.getKey();
			row[1] = val.getKey();
			tbl.addRow(row);
		}
		
		return tbl;
	}

}
