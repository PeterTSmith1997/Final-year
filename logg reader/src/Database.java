import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.*;

public class Database {
	private String driver = "jdbc:ucanaccess://";
	private String Db = "database//Main.accdb";
	private Connection conn = null;
	private String url = driver + Db;

	public Database() {
		connect();
	}

	public boolean connect() {
		try {
			conn = DriverManager.getConnection(url);
			System.err.println("Connected");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Could not establish databse connection. Contact database administrator",
					"Database error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<String> knownGoodIPs() {
		try {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT IP address FROM knownip");
			ResultSet rs = stmt.executeQuery();

			ArrayList<String> ips = new ArrayList<String>();
			while (rs.next()) {
				ips.add(rs.getString("IP"));
			}
			System.out.println(ips);
			return ips;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<String> getCategories() {

		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT CatName FROM IPType";
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<String> categories = new ArrayList<String>();
			while (rs.next()) {
				categories.add(rs.getString("CatName"));
			}

			return categories;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/**
	 * Takes an insert SQL statement and executes it
	 * 
	 * @param A
	 *            formatted SQL statement
	 * @return If the statement executed correctly
	 */
	public Boolean executeSQL(String insertSQL) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(insertSQL);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public DefaultTableModel bots() {
		DefaultTableModel bots = new DefaultTableModel();
		Object[] row = new Object[3];
		bots.addColumn("IP");
		bots.addColumn("Name");
		bots.addColumn("dis");
		PreparedStatement stmt = null;
		try {
			stmt = conn
					.prepareStatement("SELECT IP address FROM knownip");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				row[0] = rs.getString("IP");
				row[1]= "n.a";
				row[2]= "n.a";
				bots.addRow(row);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bots;
	}

}
