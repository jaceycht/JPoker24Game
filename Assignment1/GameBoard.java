import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import java.rmi.*;
import java.rmi.registry.*;


public class GameBoard implements Runnable {

	private Count counter;
	public GameBoard(String host) {
	    try {
	        Registry registry = LocateRegistry.getRegistry(host);
	        counter = (Count)registry.lookup("Counter");
	} catch(Exception e) {
	        System.err.println("Failed accessing RMI: "+e);
	    }
	}

	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new GameBoard(args[0]));
	}
	
	private String count;
	private JTextField loginName, password, registerName, registerPassword, registerConfirm;
	private JButton loginBtn, registerBtn, registerButton, cancelButton, btn1, btn2, btn3, btn4;
	private String currentUser, currentPw;
	
	public void loginCount() {
		if(counter != null) {
	        try {
	            count = counter.count(currentUser,currentPw);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	    }
	}
	public void registerCount() {
		if(counter != null) {
	        try {
	            count = counter.count(currentUser,currentPw,currentPw);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	    }
	}
	public void logoutCount() {
		if(counter != null) {
	        try {
	            count = counter.count(currentUser);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	    }
	}
	public void run() {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginName = new JTextField();
		loginName.setPreferredSize( new Dimension(250, 30));
		password = new JTextField();
		password.setPreferredSize( new Dimension(250, 30));
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
					String name = loginName.getText();
					String pw = password.getText(); 
					if (name.equals("")) {
						JOptionPane.showMessageDialog(null, "Login name should not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						currentUser=name;
						currentPw = pw;
						loginCount();
						if (count.equals("invalid name")) {
							JOptionPane.showMessageDialog(null, "Invalid user. Please register.", "Error", JOptionPane.ERROR_MESSAGE);
						} else if (count.equals("invalid pw")) {
							JOptionPane.showMessageDialog(null, "Invalid password.", "Error", JOptionPane.ERROR_MESSAGE);
						} else if (count.equals("multiple")) {
							JOptionPane.showMessageDialog(null, "Multiple login is not allowed.", "Error", JOptionPane.ERROR_MESSAGE);
						} else {	
							frame.dispose();
							board();
						} 
					}
				} 
			});
		registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
					frame.dispose();
					register();
				} 
			});
		
		JPanel panel = new JPanel(new GridLayout(4,1));
		panel.add(new JLabel("Login Name"));
		panel.add(loginName);
		panel.add(new JLabel("Password"));
		panel.add(password);
		
		JPanel btnPanel = new JPanel(new GridLayout(1,2));
		btnPanel.add(loginBtn);
		btnPanel.add(registerBtn);
		
		frame.add(panel, BorderLayout.PAGE_START);
		frame.add(btnPanel, BorderLayout.PAGE_END);
		frame.getRootPane().setBorder(new TitledBorder("Login"));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void register() {
		JFrame frame = new JFrame("Register");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		registerName = new JTextField();
		registerName.setPreferredSize( new Dimension(250, 30));
		registerPassword = new JTextField();
		registerPassword.setPreferredSize( new Dimension(250, 30));
		registerConfirm = new JTextField();
		registerConfirm.setPreferredSize( new Dimension(250, 30));
		registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
					String name = registerName.getText();
					String pw = registerPassword.getText(); 
					String confirm = registerConfirm.getText();
					if (name.equals("")) {
						JOptionPane.showMessageDialog(null, "Login name should not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (!pw.equals(confirm)) {
						JOptionPane.showMessageDialog(null, "Password and Confirm Password don't match.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						currentUser=name;
						currentPw=pw;
						registerCount();
						if (count.equals("duplicated")) {
							JOptionPane.showMessageDialog(null, "Duplicate user name.", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							frame.dispose();
							board();
						}
					}
				} 
			});
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
					frame.dispose();
					run();
				} 
			});
		
		JPanel panel = new JPanel(new GridLayout(6,1));
		panel.add(new JLabel("Login Name"));
		panel.add(registerName);
		panel.add(new JLabel("Password"));
		panel.add(registerPassword);
		panel.add(new JLabel("Confirm Password"));
		panel.add(registerConfirm);
		
		JPanel btnPanel = new JPanel(new GridLayout(1,2));
		btnPanel.add(registerButton);
		btnPanel.add(cancelButton);
		
		frame.add(panel, BorderLayout.PAGE_START);
		frame.add(btnPanel, BorderLayout.PAGE_END);
		frame.getRootPane().setBorder(new TitledBorder("Register"));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void board() {
		JFrame frame = new JFrame("JPoker 24-Game");
		frame.setPreferredSize(new Dimension (500,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel1 = new JPanel(new GridLayout(10,1));
		panel1.add(new JLabel(currentUser));
		panel1.add(new JLabel("Number of wins: 10"));
		panel1.add(new JLabel("Number of games: 20"));
		panel1.add(new JLabel("Average time to win: 12.5s"));
		panel1.add(new JLabel("Rank: #10"));
		
		JPanel panel2 = new JPanel(new GridLayout(5,1));
		JLabel tbi = new JLabel("To be implemented...");
		tbi.setBackground(Color.white);
		tbi.setOpaque(true);
		panel2.add(tbi);
		
		JPanel panel3 = new JPanel();
		String data[][] = { {"1","Player 4","20","35","10.4s"},    
				{"2","Player 2","18","25","13.2s"},
				{"3","Player 6","18","31","15.1s"},
				{"4","Player 8","16","30","12.8s"},
				{"5","Player 7","10","25","10.2s"},
				{"6","Player 3","5","7","17.1s"},
				{"7","Player 5","4","10","15.4s"},
				{"8","Player 10","1","2","16.2s"},
				{"9","Player 9","1","4","14.1s"},
				{"10","Player 1","1","4","18.4s"}};    
		String column[] = {"Rank","Player","Games Won","Games Played","Avg. winning time"};         
		JTable jt = new JTable(data,column);
		JScrollPane sp = new JScrollPane(jt);
		sp.getViewport().add(jt);
		sp.setPreferredSize(new Dimension(500,200));
		panel3.add(sp);		
		
		btn1 = new JButton("User Profile");
		btn1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
			    try{frame.remove(panel2);}catch(Exception e1){}
			    try{frame.remove(panel3);}catch(Exception e2){}
				frame.add(panel1, BorderLayout.CENTER);
				frame.repaint();
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
				frame.pack();
				frame.setVisible(true);
			}
		});
		btn2 = new JButton("Play Game");
		btn2.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				try{frame.remove(panel1);}catch(Exception e1){}
			    try{frame.remove(panel3);}catch(Exception e2){}
				frame.add(panel2, BorderLayout.CENTER);
				frame.repaint();
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
				frame.pack();
				frame.setVisible(true);
			}
		});
		btn3 = new JButton("Leader Board");
		btn3.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				try{frame.remove(panel1);}catch(Exception e1){}
			    try{frame.remove(panel2);}catch(Exception e2){}
				frame.add(panel3, BorderLayout.CENTER);
				frame.repaint();
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
				frame.pack();
				frame.setVisible(true);
			}
		});
		btn4 = new JButton("Logout");
		btn4.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				logoutCount();
				if (count.equals("ok")) {
					frame.dispose();
					run();
				} else {
					JOptionPane.showMessageDialog(null, "Logout error.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JPanel btnPanel = new JPanel(new GridLayout(1,4));
		btnPanel.add(btn1);
		btnPanel.add(btn2);
		btnPanel.add(btn3);
		btnPanel.add(btn4);
		
		frame.add(btnPanel, BorderLayout.PAGE_START);
		frame.add(panel1, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
}
