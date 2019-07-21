package dataModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author peter
 * @version 21 Jul 2019
 */
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
		return (getRisk30Days(ip) * 0.75) + (getRiskAlltime(ip) * 0.25);
	}

	public void updateRiskIP(String ip, DataStore dataStore, double risk) {

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String today = dateFormat.format(date);
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO IPLog (IP, Risk, DateReported, Type) VALUES (?,?,?,?)");
			stmt.setString(1, ip);
			stmt.setDouble(2, risk);
			stmt.setDate(3, convertStringToSQLDate(today));
			stmt.setInt(4, 4);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getOcourances(String ip) {
		int occurances = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT count(IP) AS count from IPLog Where IP=?");
			stmt.setString(1, ip);
			ResultSet rs = stmt.executeQuery();
			Boolean moreRecords = rs.next();
			if (!moreRecords) {
				System.err.println("no R");
				return 0;
			}
			occurances = Integer.parseInt(rs.getString("count"));
			System.out.println("count=" + occurances);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return occurances;

	}

	public int getOcourancesLast30days(String ip) {
		int occurances = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT count(IP) AS count from IPLog Where IP=? and DateReported>?");
			stmt.setString(1, ip);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			Date date = new Date();
			cal.setTime(date);
			cal.add(Calendar.DATE, -30);
			stmt.setDate(2,
					convertStringToSQLDate(dateFormat.format(cal.getTime())));
			ResultSet rs = stmt.executeQuery();
			Boolean moreRecords = rs.next();
			if (!moreRecords) {
				System.err.println("no R");
				return 0;
			}
			occurances = Integer.parseInt(rs.getString("count"));
			System.out.println("count=" + occurances);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return occurances;

	}

	public java.sql.Date convertStringToSQLDate(String date)
			throws ParseException {
		Date convertedDate;
		java.sql.Date sConvertedDate;

		if (date != null) {
			convertedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			sConvertedDate = new java.sql.Date(convertedDate.getTime());
		} else {
			sConvertedDate = null;
		}
		return sConvertedDate;
	}

	private double getRisk30Days(String ip) {
		int risk = 0;
		try {
			System.out.println(ip);
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT Avg(risk) AS Risk FROM IPLog where IP=? and DateReported>?");
			stmt.setString(1, ip);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			Date date = new Date();
			cal.setTime(date);
			cal.add(Calendar.DATE, -30);
			stmt.setDate(2,
					convertStringToSQLDate(dateFormat.format(cal.getTime())));
			ResultSet rs = stmt.executeQuery();
			Boolean moreRecords = rs.next();
			if (!moreRecords) {
				System.err.println("no R");
				return 0;
			}
			risk = rs.getInt("Risk");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return risk;
	}

	public DefaultTableModel getRisk30DaysAdmin() {
		String ipHeader[] = new String[] { "ip", "Number", "Risk" };
		DefaultTableModel last30Days = new DefaultTableModel(null, ipHeader);
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT ip, Count(*) AS num, Avg(risk) AS Risk FROM IPLog Where DateReported>? GROUP BY ip");

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			Date date = new Date();
			cal.setTime(date);
			cal.add(Calendar.DATE, -30);
			stmt.setDate(1,
					convertStringToSQLDate(dateFormat.format(cal.getTime())));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				last30Days.addRow(new String[] { rs.getString("ip"),
						Integer.toString(rs.getInt("num")),
						Integer.toString(rs.getInt("Risk")) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return last30Days;
	}

	private double getRiskAlltime(String ip) {
		int risk = 0;
		try {
			System.out.println(ip);
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT Avg(risk) AS Risk FROM IPLog where IP=? and DateReported>?");
			stmt.setString(1, ip);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			Date date = new Date();
			cal.setTime(date);
			cal.add(Calendar.DATE, -30);
			stmt.setDate(2,
					convertStringToSQLDate(dateFormat.format(cal.getTime())));
			ResultSet rs = stmt.executeQuery();
			Boolean moreRecords = rs.next();
			if (!moreRecords) {
				System.err.println("no R");
				return 0;
			}
			risk = rs.getInt("Risk");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return risk;
	}

	public boolean validateLogin(String user, String password) {
		boolean validLogin = false;
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT PasswordHash FROM User WHERE Username = '" + user
							+ "'");
			ResultSet rs = stmt.executeQuery();
			boolean moreRecords = rs.next();
			// If there are no records to show validLogin is set to false
			if (!moreRecords) {
				System.out.println("ResultSet contained no records");
				return false;
			}
			// If the entered password matches the one stored in the database
			// validLogin is set to true
			if ((password.equals(rs.getString("PasswordHash")))) {
				validLogin = true;
				System.out.println("Sucess");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Return validLogin to check if login was successful
		System.out.println(validLogin);
		return validLogin;
	}

	public DefaultTableModel getTotalReports() {
		String ipHeader[] = new String[] { "ip", "Number", "Risk" };
		DefaultTableModel allTime = new DefaultTableModel(null, ipHeader);
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT ip, Count(*) AS num, Avg(risk) AS Risk FROM IPLog GROUP BY ip;");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				allTime.addRow(new String[] { rs.getString("ip"),
						Integer.toString(rs.getInt("num")),
						Integer.toString(rs.getInt("Risk")) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allTime;

	}
}
