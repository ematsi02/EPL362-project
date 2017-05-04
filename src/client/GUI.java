package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import server.JDBC;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GUI extends JFrame implements ActionListener {
	BufferedImage image;
	public MyPanel contentPane = new MyPanel();
	public JDBC SADB;
	String usernameGUI = "";
	String roleGUI = "";
	private BufferedReader in;
	private PrintWriter out;
	private static Socket socket;
	String messageFromServer = null;

	public GUI() {
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResource("/health.png"));
			this.setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("Regional Health Authority");
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String btnLabel = evt.getActionCommand();
		if (btnLabel.equals("Change My Password")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(changePasswordForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Log Out")) {

			this.getContentPane().removeAll();
			this.setJMenuBar(null);
			this.getContentPane().add(loginForm());
			this.getContentPane().add(signupForm());
			this.revalidate();
			this.repaint();
			this.pack();

			out.println("logout");
			out.flush();
			out.println(usernameGUI);
			out.flush();
			System.out.println(usernameGUI);
			usernameGUI = "";
			roleGUI = "";

		} else if (btnLabel.equals("Add Patient")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(patientForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Patient")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchPatientForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Patient")) {
			this.getContentPane().removeAll();
			// ResultSet rs = SADB.printPatients();
			// this.getContentPane().add(patientsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Patients")) {
			try {
				JLabel message = new JLabel("PATIENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				// ResultSet rs = SADB.printPatients();
				// this.getContentPane().add(message);
				// JScrollPane r = resultsForm(rs);
				// r.setBounds(50, 150, 900, 600);
				// this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Relative")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(relativeForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Relative")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(searchRelativeForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Relative")) {
			this.getContentPane().removeAll();
			// ResultSet rs = SADB.printRelatives();
			// this.getContentPane().add(relativesForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Relatives")) {
			try {
				JLabel message = new JLabel("RELATIVES");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				// ResultSet rs = SADB.printRelatives();
				this.getContentPane().add(message);
				// JScrollPane r = resultsForm(rs);
				// r.setBounds(50, 150, 900, 600);
				// this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Treatment")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(treatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Treatment")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(searchTreatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Treatment")) {
			this.getContentPane().removeAll();
			// ResultSet rs = SADB.printTreatments();
			// this.getContentPane().add(treatmentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Treatments")) {
			try {
				JLabel message = new JLabel("TREATMENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				// ResultSet rs = SADB.printTreatments();
				this.getContentPane().add(message);
				// JScrollPane r = resultsForm(rs);
				// r.setBounds(50, 150, 900, 600);
				// this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Medication")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(medicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Medication")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(searchMedicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Medication")) {
			this.getContentPane().removeAll();
			// ResultSet rs = SADB.printMedications();
			// this.getContentPane().add(medicationsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Medications")) {
			try {
				JLabel message = new JLabel("MEDICATIONS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				// ResultSet rs = SADB.printMedications();
				this.getContentPane().add(message);
				// JScrollPane r = resultsForm(rs);
				// r.setBounds(50, 150, 900, 600);
				// this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Incident")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(incidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Incident")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(searchIncidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Incident")) {
			this.getContentPane().removeAll();
			// ResultSet rs = SADB.printIncidents();
			// this.getContentPane().add(incidentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Incidents")) {
			try {
				JLabel message = new JLabel("INCIDENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				// ResultSet rs = SADB.printIncidents();
				this.getContentPane().add(message);
				// JScrollPane r = resultsForm(rs);
				// r.setBounds(50, 150, 900, 600);
				// this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Appointment")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(appointmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Appointment")) {
			this.getContentPane().removeAll();
			// this.getContentPane().add(searchAppointmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Appointment")) {
			this.getContentPane().removeAll();
			// ResultSet rs = SADB.printAppointments();
			// this.getContentPane().add(appointmentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Appointments")) {
			try {
				JLabel message = new JLabel("APPOINTMENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				// ResultSet rs = SADB.printAppointments();
				this.getContentPane().add(message);
				// JScrollPane r = resultsForm(rs);
				// r.setBounds(50, 150, 900, 600);
				// this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		}
	}

	class MyPanel extends JPanel {
		private BufferedImage image;

		public MyPanel() {
			try {
				image = ImageIO.read(MyPanel.class.getResource("/background.jpg"));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return image == null ? new Dimension(400, 300) : new Dimension(image.getWidth(), image.getHeight());
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}

	public void connectToServer() throws IOException {

		// Make connection and initialize streams
		socket = new Socket("localhost", 8080);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	private JPanel loginForm() {
		String[] roles = { "Staff", "Patient" };
		JPanel loginpanel = new JPanel();
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpassword = new JLabel(" Password");
		lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblrole = new JLabel("     Role");
		lblrole.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField username = new JTextField(15);
		JTextField password = new JPasswordField(15);
		JComboBox role = new JComboBox(roles);
		JButton login = new JButton("Log In");
		login.setFont(new Font("Arial", Font.PLAIN, 14));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					out.flush();
					out.println("login");
					out.flush();
					System.out.println("I am getText = " + username.getText());
					out.println(username.getText());
					out.flush();
					out.println(password.getText());
					out.flush();
					out.println(role.getSelectedItem().toString());
					out.flush();

					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						if (messageFromServer.equals("1")) {// user is staff
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(menuForClinicalStaff());
							// TODO menu selection needs to change, based on the specific StaffType
							/*
							roleGUI = rs.getString("StaffType");
							getContentPane().removeAll();
							GUIMenu myMenu = new GUIMenu(this);
							if (roleGUI.equals("Doctor"))
								setJMenuBar(myMenu.menuForDoctor());
							else if (roleGUI.equals("Nurse") || roleGUI.equals("Health Visitor"))
								setJMenuBar(myMenu.menuForClinicalStaff());
							else if (roleGUI.equals("Receptionist"))
								setJMenuBar(myMenu.menuForReceptionist());
							else if (roleGUI.equals("Medical Records"))
								setJMenuBar(myMenu.menuForMedicalRecords());
							else if (roleGUI.equals("Management"))
								setJMenuBar(myMenu.menuForManagement());
							*/
						}
						else if (messageFromServer.equals("2")) {// user is receptionist
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(menuForReceptionists());
							// TODO menu selection needs to change, based on the specific StaffType
						}
						else if (messageFromServer.equals("3")) {// incorrect user
							JLabel message = new JLabel("Username or Password incorrect!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(380, 180, 250, 150);
							getContentPane().add(loginForm());
							getContentPane().add(message);
							getContentPane().add(signupForm());
						} else System.out.println("not 1 2 3");
						revalidate();
						repaint();
						pack();
					} else System.out.println("else of big if");
				} catch (Exception er) {
					// Ignore the error and continues
					System.out.println("exception in logn");
				}
			}
		});
		loginpanel.add(lblusername);
		loginpanel.add(username);
		loginpanel.add(lblpassword);
		loginpanel.add(password);
		loginpanel.add(lblrole);
		loginpanel.add(role);
		loginpanel.add(login);
		loginpanel.setBounds(350, 130, 250, 130);
		loginpanel.setOpaque(false);
		return loginpanel;
	}

	private JPanel changePasswordForm() {
		JPanel m = new JPanel();
		JLabel lbloldpassword = new JLabel(" Old Password");
		lbloldpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblnewpassword = new JLabel("New Password");
		lblnewpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField oldpassword = new JPasswordField(15);
		JTextField newpassword = new JPasswordField(15);
		JButton changepassword = new JButton("Change Password");
		changepassword.setFont(new Font("Arial", Font.PLAIN, 14));
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					out.println("changePassword");
					out.flush();
					out.println(usernameGUI);
					out.flush();
					out.println(oldpassword.getText());
					out.flush();
					out.println(newpassword.getText());
					out.flush();
					out.println(roleGUI);
					out.flush();

					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						if (messageFromServer.equals("1")) {// password changed
							System.out.println(messageFromServer + "allagi");
							JLabel message = new JLabel("You have successfully changed your password!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(320, 180, 350, 150);
							getContentPane().add(changePasswordForm());
							getContentPane().add(message);
						}
						if (messageFromServer.equals("2")) {// wrong password
							JLabel message = new JLabel("Wrong password! Give again!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(400, 180, 350, 150);
							getContentPane().add(changePasswordForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
					out.flush();
				} catch (Exception er) { // Ignore the error and continues
				}
			}
		});
		m.add(lbloldpassword);
		m.add(oldpassword);
		m.add(lblnewpassword);
		m.add(newpassword);
		m.add(changepassword);
		m.setBounds(350, 150, 300, 100);
		m.setOpaque(false);
		return m;
	}

	JPanel signupForm() {
		String[] roles = { "Doctor", "Nurse", "Health Visitor", "Receptionist", "Medical Records Staff" };
		JPanel signuppanel = new JPanel();
		JLabel lblmessage = new JLabel("Don't have an account? Sign up now!");
		lblmessage.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblname = new JLabel("       Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsurname = new JLabel("  Surname");
		lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpassword = new JLabel(" Password");
		lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblrole = new JLabel("        Role  ");
		lblrole.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("      Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("       Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("  Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField username = new JTextField(15);
		JTextField password = new JPasswordField(15);
		JTextField name = new JTextField(15);
		JTextField surname = new JTextField(15);
		JComboBox role = new JComboBox(roles);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		JTextField address = new JTextField(15);
		JButton signup = new JButton("Sign Up");
		signup.setFont(new Font("Arial", Font.PLAIN, 14));
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					out.println("signup");
					out.println(username.getText());
					out.println(password.getText());
					out.println(name.getText());
					out.println(surname.getText());
					out.println(role.getSelectedItem().toString());
					out.println(Integer.parseInt(phone.getText()));
					out.println(email.getText());
					out.println(address.getText());
					out.flush();
					if ((messageFromServer = in.readLine()) != null) {
						getContentPane().removeAll();
						if (messageFromServer.equals("1\n")) {// successful sign
																// up
							JLabel message = new JLabel("You have successfully signed up!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(320, 470, 350, 160);
							getContentPane().add(loginForm());
							getContentPane().add(signupForm());
							getContentPane().add(message);
						}
						if (messageFromServer.equals("2\n")) {// username
																// already
																// exists
							JLabel message = new JLabel("Username already exists! Give a different one!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(320, 470, 350, 160);
							getContentPane().add(loginForm());
							getContentPane().add(signupForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		signuppanel.add(lblmessage);
		signuppanel.add(lblname);
		signuppanel.add(name);
		signuppanel.add(lblsurname);
		signuppanel.add(surname);
		signuppanel.add(lblusername);
		signuppanel.add(username);
		signuppanel.add(lblpassword);
		signuppanel.add(password);
		signuppanel.add(lblrole);
		signuppanel.add(role);
		signuppanel.add(lblphone);
		signuppanel.add(phone);
		signuppanel.add(lblemail);
		signuppanel.add(email);
		signuppanel.add(lbladdress);
		signuppanel.add(address);
		signuppanel.add(signup);
		signuppanel.setBounds(320, 280, 300, 270);
		signuppanel.setOpaque(false);
		return signuppanel;
	}

	private JPanel patientForm() {
		JPanel patientpanel = new JPanel();
		JLabel lblname = new JLabel("     Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsurname = new JLabel("Surname");
		lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpassword = new JLabel("Password");
		lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField name = new JTextField(15);
		JTextField surname = new JTextField(15);
		JTextField username = new JTextField(15);
		JTextField password = new JPasswordField(15);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		JTextField address = new JTextField(15);
		JButton addPatient = new JButton("Add");

		patientpanel.add(lblname);
		patientpanel.add(name);
		patientpanel.add(lblsurname);
		patientpanel.add(surname);
		patientpanel.add(lblusername);
		patientpanel.add(username);
		patientpanel.add(lblpassword);
		patientpanel.add(password);
		patientpanel.add(lblphone);
		patientpanel.add(phone);
		patientpanel.add(lblemail);
		patientpanel.add(email);
		patientpanel.add(lbladdress);
		patientpanel.add(address);
		patientpanel.add(addPatient);
		addPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SADB.addPatient(name.getText(), surname.getText(), username.getText(), password.getText(),
							Integer.parseInt(phone.getText()), email.getText(), address.getText());
					getContentPane().removeAll();
					JLabel message = new JLabel("You have successfully added the patient!");
					message.setFont(new Font("Arial", Font.BOLD, 14));
					message.setBounds(340, 470, 350, 50);
					getContentPane().add(patientForm());
					getContentPane().add(message);
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		patientpanel.setBounds(350, 150, 250, 250);
		patientpanel.setOpaque(false);
		return patientpanel;
	}

	private JPanel patientsForm(ResultSet rs, int x) {
		try {
			if (x == 1) {
				if (rs.next() == false) {
					rs.previous();
				}
			} else if (x == -1) {
				if (rs.previous() == false) {
					rs.next();
				}
			}
			JLabel lblspace1 = new JLabel("                      ");
			JLabel lblspace2 = new JLabel("                      ");
			JPanel patientpanel = new JPanel();
			JLabel lblname = new JLabel("     Name");
			lblname.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblsurname = new JLabel("Surname");
			lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblusername = new JLabel("Username");
			lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpassword = new JLabel("Password");
			lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblphone = new JLabel("Phone");
			lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblemail = new JLabel("Email");
			lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbladdress = new JLabel("Address");
			lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
			JTextField name = new JTextField(rs.getString("Name"));
			JTextField surname = new JTextField(rs.getString("Surname"));
			JTextField username = new JTextField(rs.getString("PatientID"));
			username.setEditable(false);
			JTextField password = new JPasswordField(rs.getString("Password"));
			password.setEditable(false);
			JTextField phone = new JTextField(rs.getString("Phone"));
			JTextField email = new JTextField(rs.getString("Email"));
			JTextField address = new JTextField(rs.getString("Address"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updatePatient(name.getText(), surname.getText(), Integer.parseInt(phone.getText()),
								email.getText(), address.getText());
						getContentPane().removeAll();
						getContentPane().add(patientsForm(SADB.printPatients(), 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.deletePatient(username.getText());
						getContentPane().removeAll();
						getContentPane().add(patientsForm(SADB.printPatients(), 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			JButton previous = new JButton("<");
			previous.setFont(new Font("Arial", Font.PLAIN, 14));
			previous.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getContentPane().removeAll();
						getContentPane().add(patientsForm(rs, -1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			JButton next = new JButton(">");
			next.setFont(new Font("Arial", Font.PLAIN, 14));
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getContentPane().removeAll();
						getContentPane().add(patientsForm(rs, 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			patientpanel.add(lblspace1);
			patientpanel.add(previous);
			patientpanel.add(next);
			patientpanel.add(lblspace2);
			patientpanel.add(lblname);
			patientpanel.add(name);
			patientpanel.add(lblsurname);
			patientpanel.add(surname);
			patientpanel.add(lblusername);
			patientpanel.add(username);
			patientpanel.add(lblpassword);
			patientpanel.add(password);
			patientpanel.add(lblphone);
			patientpanel.add(phone);
			patientpanel.add(lblemail);
			patientpanel.add(email);
			patientpanel.add(lbladdress);
			patientpanel.add(address);
			patientpanel.add(update);
			patientpanel.add(delete);
			patientpanel.setBounds(350, 150, 250, 250);
			patientpanel.setOpaque(false);
			return patientpanel;
		} catch (Exception er) {
			// Ignore the error and continues
			return null;
		}
	}

	private JPanel searchPatientForm() {
		JPanel patientpanel = new JPanel();
		JLabel message = new JLabel(
				"Search Patients by Username, Name, Surname, Phone, Email, Address, Number of incidents, or all of them:");
		message.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblspace1 = new JLabel("                       ");
		JLabel lblspace2 = new JLabel("                       ");
		JLabel lblname = new JLabel("     Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsurname = new JLabel("Surname");
		lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblincidents = new JLabel("Number of incidents");
		lblincidents.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField name = new JTextField(15);
		JTextField surname = new JTextField(15);
		JTextField username = new JTextField(15);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		JTextField address = new JTextField(15);
		JTextField incidents = new JTextField(15);
		JButton searchPatient = new JButton("Search");

		patientpanel.add(lblspace1);
		patientpanel.add(message);
		patientpanel.add(lblspace2);
		patientpanel.add(lblname);
		patientpanel.add(name);
		patientpanel.add(lblsurname);
		patientpanel.add(surname);
		patientpanel.add(lblusername);
		patientpanel.add(username);
		patientpanel.add(lblphone);
		patientpanel.add(phone);
		patientpanel.add(lblemail);
		patientpanel.add(email);
		patientpanel.add(lbladdress);
		patientpanel.add(address);
		patientpanel.add(lblincidents);
		patientpanel.add(incidents);
		patientpanel.add(searchPatient);
		searchPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = SADB.searchPatient(name.getText(), surname.getText(), username.getText(),
							phone.getText(), email.getText(), address.getText(), incidents.getText());
					getContentPane().removeAll();
					getContentPane().add(searchPatientForm());
					getContentPane().add(resultsForm(rs));
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		patientpanel.setBounds(50, 150, 900, 150);
		patientpanel.setOpaque(false);
		return patientpanel;
	}

	private JPanel relativeForm() {
		JPanel relativepanel = new JPanel();
		JLabel lblpatientusername = new JLabel("Patient's Username");
		lblpatientusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblrelationship = new JLabel("Relationship");
		lblrelationship.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblname = new JLabel("     Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsurname = new JLabel("Surname");
		lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField patientusername = new JTextField(15);
		JTextField relationship = new JTextField(15);
		JTextField name = new JTextField(15);
		JTextField surname = new JTextField(15);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		JTextField address = new JTextField(15);
		JButton addRelative = new JButton("Add");

		relativepanel.add(lblpatientusername);
		relativepanel.add(patientusername);
		relativepanel.add(lblrelationship);
		relativepanel.add(relationship);
		relativepanel.add(lblname);
		relativepanel.add(name);
		relativepanel.add(lblsurname);
		relativepanel.add(surname);
		relativepanel.add(lblphone);
		relativepanel.add(phone);
		relativepanel.add(lblemail);
		relativepanel.add(email);
		relativepanel.add(lbladdress);
		relativepanel.add(address);
		relativepanel.add(addRelative);
		addRelative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SADB.addRelative(patientusername.getText(), name.getText(), surname.getText(),
							Integer.parseInt(phone.getText()), email.getText(), address.getText(),
							relationship.getText());
					getContentPane().removeAll();
					JLabel message = new JLabel("You have successfully added the relative!");
					message.setFont(new Font("Arial", Font.BOLD, 14));
					message.setBounds(340, 470, 350, 50);
					getContentPane().add(relativeForm());
					getContentPane().add(message);
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		relativepanel.setBounds(350, 150, 250, 250);
		relativepanel.setOpaque(false);
		return relativepanel;
	}

	private JScrollPane resultsForm(ResultSet rs) throws Exception {
		JTable results = new JTable(JDBC.buildTableModel(rs));
		JScrollPane resultspanel = new JScrollPane(results);
		resultspanel.setBounds(50, 400, 900, 300);
		return resultspanel;
	}

	public static void main(String[] args) throws IOException {
		GUI frame = new GUI();
		frame.connectToServer();
		frame.contentPane.setLayout(null);
		frame.setLocationByPlatform(true);
		frame.setContentPane(frame.contentPane);
		frame.getContentPane().add(frame.loginForm());
		frame.getContentPane().add(frame.signupForm());
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					socket.close();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		System.out.println("WELCOME TO Regional Health Authority JDBC program ! \n\n");

	} // end of Main

}

/*
 * try { if (!SADB.conn.isClosed()) { System.out.print(
 * "\nDisconnecting from database..."); SADB.conn.close(); System.out.println(
 * "Done\n\nBye !"); } } catch (Exception e) { // Ignore the error and continues
 * }
 */
