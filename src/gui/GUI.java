import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
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

public class GUI extends JFrame implements ActionListener {
	BufferedImage image;
	MyPanel contentPane = new MyPanel();
	JDBC SADB;
	String usernameGUI = "";
	String roleGUI = "";

	GUI() {
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
			usernameGUI = "";
			roleGUI = "";
			this.getContentPane().removeAll();
			this.setJMenuBar(null);
			this.getContentPane().add(loginForm());
			this.getContentPane().add(signupForm());
			this.revalidate();
			this.repaint();
			this.pack();
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
	//		ResultSet rs = SADB.printPatients();
		//	this.getContentPane().add(patientsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Patients")) {
			try {
				JLabel message = new JLabel("PATIENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
		//		ResultSet rs = SADB.printPatients();
		//		this.getContentPane().add(message);
		//		JScrollPane r = resultsForm(rs);
		//		r.setBounds(50, 150, 900, 600);
		//		this.getContentPane().add(r);
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
			this.getContentPane().add(searchRelativeForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Relative")) {
			this.getContentPane().removeAll();
		//	ResultSet rs = SADB.printRelatives();
		//	this.getContentPane().add(relativesForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Relatives")) {
			try {
				JLabel message = new JLabel("RELATIVES");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
		//		ResultSet rs = SADB.printRelatives();
				this.getContentPane().add(message);
			//	JScrollPane r = resultsForm(rs);
	//			r.setBounds(50, 150, 900, 600);
	//			this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Treatment")) {
			this.getContentPane().removeAll();
	//		this.getContentPane().add(treatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Treatment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchTreatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Treatment")) {
			this.getContentPane().removeAll();
	//		ResultSet rs = SADB.printTreatments();
	//		this.getContentPane().add(treatmentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Treatments")) {
			try {
				JLabel message = new JLabel("TREATMENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
		//		ResultSet rs = SADB.printTreatments();
				this.getContentPane().add(message);
		//		JScrollPane r = resultsForm(rs);
			//	r.setBounds(50, 150, 900, 600);
		//		this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Medication")) {
			this.getContentPane().removeAll();
//			this.getContentPane().add(medicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Medication")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchMedicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Medication")) {
			this.getContentPane().removeAll();
	//		ResultSet rs = SADB.printMedications();
		//	this.getContentPane().add(medicationsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Medications")) {
			try {
				JLabel message = new JLabel("MEDICATIONS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
	//			ResultSet rs = SADB.printMedications();
				this.getContentPane().add(message);
			//	JScrollPane r = resultsForm(rs);
		//	r.setBounds(50, 150, 900, 600);
	//			this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Incident")) {
			this.getContentPane().removeAll();
	//		this.getContentPane().add(incidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Incident")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchIncidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Incident")) {
			this.getContentPane().removeAll();
	//		ResultSet rs = SADB.printIncidents();
		//	this.getContentPane().add(incidentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Incidents")) {
			try {
				JLabel message = new JLabel("INCIDENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
			//	ResultSet rs = SADB.printIncidents();
				this.getContentPane().add(message);
			//	JScrollPane r = resultsForm(rs);
			//	r.setBounds(50, 150, 900, 600);
		//		this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add Appointment")) {
			this.getContentPane().removeAll();
	//		this.getContentPane().add(appointmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Appointment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchAppointmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Appointment")) {
			this.getContentPane().removeAll();
	//		ResultSet rs = SADB.printAppointments();
	//		this.getContentPane().add(appointmentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Appointments")) {
			try {
				JLabel message = new JLabel("APPOINTMENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
			this.getContentPane().removeAll();
		//		ResultSet rs = SADB.printAppointments();
				this.getContentPane().add(message);
			//	JScrollPane r = resultsForm(rs);
		//		r.setBounds(50, 150, 900, 600);
			//	this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} 
	}

	private class MyPanel extends JPanel {
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

	private JPanel loginForm() {
		String[] roles = { "Doctor", "Nurse", "Health Visitor", "Receptionist", "Medical Records Staff", "Patient" };
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
					ResultSet rs = SADB.login(username.getText(), password.getText(),
							role.getSelectedItem().toString());
					if (rs.next()) {
						usernameGUI = username.getText();
						roleGUI = role.getSelectedItem().toString();
						getContentPane().removeAll();
						if (roleGUI.equals("Doctor") || roleGUI.equals("Nurse") || roleGUI.equals("Health Visitor"))
							setJMenuBar(menuForClinicalStaff());
						else
							setJMenuBar(menuForReceptionists());
						revalidate();
						repaint();
						pack();
					} else {
						getContentPane().removeAll();
						JLabel message = new JLabel("Username or Password incorrect!");
						message.setFont(new Font("Arial", Font.BOLD, 14));
						message.setBounds(380, 180, 250, 150);
						getContentPane().add(loginForm());
						getContentPane().add(message);
						getContentPane().add(signupForm());
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					// Ignore the error and continues
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
					ResultSet rs = SADB.login(usernameGUI, oldpassword.getText(), roleGUI);
					if (rs.next()) {
						SADB.changepassword(usernameGUI, newpassword.getText(), roleGUI);
						getContentPane().removeAll();
						JLabel message = new JLabel("You have successfully changed your password!");
						message.setFont(new Font("Arial", Font.BOLD, 14));
						message.setBounds(320, 180, 350, 150);
						getContentPane().add(changePasswordForm());
						getContentPane().add(message);
						revalidate();
						repaint();
						pack();
					} else {
						getContentPane().removeAll();
						JLabel message = new JLabel("Wrong password! Give again!");
						message.setFont(new Font("Arial", Font.BOLD, 14));
						message.setBounds(400, 180, 350, 150);
						getContentPane().add(changePasswordForm());
						getContentPane().add(message);
						revalidate();
						repaint();
						pack();
					}
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

	private JPanel signupForm() {
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
					ResultSet rs = SADB.checksignup(username.getText(), role.getSelectedItem().toString());
					if (!rs.next()) {
						SADB.signup(username.getText(), password.getText(), name.getText(), surname.getText(),
								role.getSelectedItem().toString(), Integer.parseInt(phone.getText()), email.getText(),
								address.getText());
						getContentPane().removeAll();
						JLabel message = new JLabel("You have successfully signed up!");
						message.setFont(new Font("Arial", Font.BOLD, 14));
						message.setBounds(360, 470, 250, 160);
						getContentPane().add(loginForm());
						getContentPane().add(signupForm());
						getContentPane().add(message);
						revalidate();
						repaint();
						pack();
					} else {
						getContentPane().removeAll();
						JLabel message = new JLabel("Username already exists! Give a different one!");
						message.setFont(new Font("Arial", Font.BOLD, 14));
						message.setBounds(320, 470, 350, 160);
						getContentPane().add(loginForm());
						getContentPane().add(signupForm());
						getContentPane().add(message);
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

	private JPanel searchPatientForm() {
		JPanel patientpanel = new JPanel();
		return patientpanel;
	}

	private JPanel searchRelativeForm() {
		JPanel relativepanel = new JPanel();
		return relativepanel;
	}

	private JPanel searchTreatmentForm() {
		JPanel treatmentpanel = new JPanel();
		return treatmentpanel;
	}

	private JPanel searchMedicationForm() {
		JPanel medicationpanel = new JPanel();
		return medicationpanel;
	}

	private JPanel searchIncidentForm() {
		JPanel incidentpanel = new JPanel();
		return incidentpanel;
	}

	private JPanel searchAppointmentForm() {
	JPanel appointmentpanel = new JPanel();
	return appointmentpanel;
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
					SADB.addPatient(name.getText(), surname.getText(), username.getText(), password.getText(), Integer.parseInt(phone.getText()), email.getText(), address.getText());
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
					SADB.addRelative(patientusername.getText(), name.getText(), surname.getText(), Integer.parseInt(phone.getText()), email.getText(), address.getText(), relationship.getText());
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

	

/*	private JPanel patientsForm(ResultSet rs, int x) {
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
			JTextField password = new JPasswordField(rs.getString("Password"));
			JTextField phone = new JTextField(rs.getString("Phone"));
			JTextField email = new JTextField(rs.getString("Email"));
			JTextField address = new JTextField(rs.getString("Address"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updatePatient(username.getText(), name.getText(), surname.getText(), password.getText(), Integer.parseInt(phone.getText()), email.getText(), address.getText());
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
*/
	private JScrollPane resultsForm(ResultSet rs) throws Exception {
		JTable results = new JTable(JDBC.buildTableModel(rs));
		JScrollPane resultspanel = new JScrollPane(results);
		resultspanel.setBounds(50, 400, 900, 300);
		return resultspanel;
	}

	private JMenuBar menuForClinicalStaff() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("Account");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Change My Password");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Log Out");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Patients");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add Patient");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Search Patient");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit/Delete Patient");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("View All Patients");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Relatives");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add Relative");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Search Relative");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit/Delete Relative");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("View All Relatives");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Incidents");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add Incident");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Search Incident");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit/Delete Incident");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("View All Incidents");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Treatments");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add Treatment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Search Treatment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit/Delete Treatment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("View All Treatments");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Medications");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Search Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit/Delete Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("View All Medications");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		return menuBar;
	}

	private JMenuBar menuForReceptionists() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("Account");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Change My Password");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Log Out");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Appointments");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add Appointment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Search Appointment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit/Delete Appointment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("View All Appointments");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		return menuBar;
	}

	public static void main(String[] args) {
		GUI frame = new GUI();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.contentPane.setLayout(null);
		frame.setLocationByPlatform(true);
		frame.setContentPane(frame.contentPane);
		frame.getContentPane().add(frame.loginForm());
		frame.getContentPane().add(frame.signupForm());
		frame.pack();
		frame.setVisible(true);
		frame.SADB = new JDBC();
		frame.SADB.conn = frame.SADB.getDBConnection();
		if (frame.SADB.conn == null) {
			return;
		}
		System.out.println("WELCOME TO Regional Health Authority JDBC program ! \n\n");
	}
}

/*
 * try { if (!SADB.conn.isClosed()) { System.out.print(
 * "\nDisconnecting from database..."); SADB.conn.close(); System.out.println(
 * "Done\n\nBye !"); } } catch (Exception e) { // Ignore the error and continues
 * }
 */
