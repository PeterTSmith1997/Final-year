import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Menu extends JFrame {
	private ArrayList<Hits> hits = new ArrayList<>();
	private Map<String, Integer> orrcancesOfip = new HashMap<String, Integer>();
	private Map<String, Integer> referers = new HashMap<String, Integer>();
	private Map<String, Integer> protcals = new HashMap<String, Integer>();
	private Map<String, Integer> pages = new HashMap<String, Integer>();
	
	Reader reader = new Reader(this);
	Database database = new Database();
	LogData logUI = new LogData(this);
	public Menu() {
		setSize(new Dimension(100, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblWebLogReader = new JLabel("Web log reader");
		panel.add(lblWebLogReader);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);

		JButton openButton = new JButton("Open File");
		openButton.addActionListener(new ActionListener() {
			//https://www.mkyong.com/swing/java-swing-jfilechooser-example/
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(
						FileSystemView.getFileSystemView().getDefaultDirectory());
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
			}
		});
		panel_1.add(openButton);

		JButton btnViewIpsknown = new JButton("View IPs (known)");
		btnViewIpsknown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IpInfo ipUi = new IpInfo();
//				ipUi.setVisible(true);
			}
		});
		panel_1.add(btnViewIpsknown);

		JButton btnAddKnownIps = new JButton("Add known IPs");
		btnAddKnownIps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_1.add(btnAddKnownIps);
		
		JButton btnViewData = new JButton("View data");
		btnViewData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logUI.makeui();
			
			}
		});
		panel_1.add(btnViewData);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @return the hits
	 */
	public ArrayList<Hits> getHits() {
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setHits(ArrayList<Hits> hits) {
		this.hits = hits;
	}
	public void addHit(Hits h) {
		hits.add(h);
	}

	/**
	 * @return the orrcancesOfip
	 */
	public Map<String, Integer> getOrrcancesOfip() {
		return orrcancesOfip;
	}

	/**
	 * @param orrcancesOfip the orrcancesOfip to set
	 */
	public void setOrrcancesOfip(Map<String, Integer> orrcancesOfip) {
		this.orrcancesOfip = orrcancesOfip;
	}

	/**
	 * @return the pages
	 */
	public Map<String, Integer> getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(Map<String, Integer> pages) {
		this.pages = pages;
	}

	/**
	 * @return the referers
	 */
	public Map<String, Integer> getReferers() {
		return referers;
	}

	/**
	 * @param referers the referers to set
	 */
	public void setReferers(Map<String, Integer> referers) {
		this.referers = referers;
	}

	/**
	 * @return the protcals
	 */
	public Map<String, Integer> getProtcals() {
		return protcals;
	}

	/**
	 * @param protcals the protcals to set
	 */
	public void setProtcals(Map<String, Integer> protcals) {
		this.protcals = protcals;
	}

}
