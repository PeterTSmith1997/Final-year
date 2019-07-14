package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

	public double getRiskIP(String ip) {
		int risk=0;
		try {
			System.out.println(ip);
			PreparedStatement stmt = conn
					.prepareStatement("SELECT Risk FROM "
							+ "knownip WHERE IP = ?");
			stmt.setString(1, ip);
			ResultSet rs =stmt.executeQuery();
			Boolean moreRecords = rs.next();
			if(!moreRecords) {
				System.err.println("no R");
				return 0;
			}
			risk = rs.getInt("Risk");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return risk;

	}
	
	public void updateRiskIP(String ip, DataStore dataStore) {
		Analise analise = new Analise();
		double risk = analise.risk(ip, dataStore);
		int occurances = getOcourances(ip);
		double oldDbRisk = getRiskIP(ip);
		double newRisk;
		if  (occurances == 0) {
			newRisk = risk;
			try {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO knownip (IP, Type, Risk, occurances)"
								+ "VALUES (?,?,?,?)");
				stmt.setString(1, ip);
				stmt.setInt(2, 9);
				stmt.setDouble(3, newRisk);
				stmt.setInt(4, 1);
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			occurances++;
			newRisk = (risk+oldDbRisk) /occurances;
			try {
				PreparedStatement stmt = conn.prepareStatement("UPDATE knownip SET Risk=?,occurances=? WHERE IP=?");
				stmt.setDouble(1, newRisk);
				stmt.setInt(2, occurances);
				stmt.setString(3, ip);
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int getOcourances(String ip) {
		int occurances = 0;
		try {
		PreparedStatement stmt = conn
				.prepareStatement("SELECT occurances FROM "
						+ "knownip WHERE IP = ?");
		stmt.setString(1, ip);
		ResultSet rs =stmt.executeQuery();
		Boolean moreRecords = rs.next();
		if(!moreRecords) {
			System.err.println("no R");
			return 0;
		}
		occurances = Integer.parseInt(rs.getString("occurances"));
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return occurances;
	
	}
}
