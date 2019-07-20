package main;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

/**
 * @author peter
 * @version 20 Jul 2019
 */
public class LoginUI extends JFrame{
	private JTextField textField;
	private JPasswordField passwordField;
	private DataStore dataStore;
	public LoginUI(DataStore dataStore) {
		this.dataStore = dataStore;
		setTitle("Login");
		setLocationRelativeTo(null);
		setSize(298, 123);
		getContentPane().setLayout(new MigLayout("", "[95.00][190.00px][161.00px][46px]", "[20px][][]"));
		
		JLabel lbUser = new JLabel("User Name");
		lbUser.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lbUser, "cell 1 0,alignx center,aligny center");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 2 0,alignx left,aligny top");
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		getContentPane().add(lblPassword, "cell 1 1,alignx center,aligny center");
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		getContentPane().add(passwordField, "cell 2 1,alignx left,aligny top");
		
		JButton btnLogin = new JButton("Login");
		getContentPane().add(btnLogin, "cell 1 2,alignx center");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogData logData = new LogData(dataStore);
				dispose();
			}
		});
		getContentPane().add(btnCancel, "cell 2 2,alignx center");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				LogData logData = new LogData(dataStore);
				dispose();
			}
		});
	}

}
