package client;

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
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	GUIMenu myMenu = null;

	public GUI() {
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResource("/health.png"));
			this.setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		myMenu = new GUIMenu(this);
		this.setTitle("Regional Health Authority");
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String btnLabel = evt.getActionCommand();
		if (btnLabel.equals("Change Password")) {
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

		} else if (btnLabel.equals("Add New Patient")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(patientForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Patient")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchPatientForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Patient")) {
			this.getContentPane().removeAll();
			ResultSet rs = SADB.printPatients();
			this.getContentPane().add(patientsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Patients")) {
			try {
				JLabel message = new JLabel("PATIENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				ResultSet rs = SADB.printPatients();
				this.getContentPane().add(message);
				JScrollPane r = resultsForm(rs);
				r.setBounds(50, 150, 900, 600);
				this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add New Relative")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(relativeForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Relative")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchRelativeForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Relative")) {
			this.getContentPane().removeAll();
			ResultSet rs = SADB.printRelatives();
			this.getContentPane().add(relativesForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Relatives")) {
			try {
				JLabel message = new JLabel("RELATIVES");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				ResultSet rs = SADB.printRelatives();
				this.getContentPane().add(message);
				JScrollPane r = resultsForm(rs);
				r.setBounds(50, 150, 900, 600);
				this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add New Treatment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(treatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Treatment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchTreatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Treatment")) {
			this.getContentPane().removeAll();
			ResultSet rs = SADB.printTreatments();
			this.getContentPane().add(treatmentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Treatments")) {
			try {
				JLabel message = new JLabel("TREATMENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				ResultSet rs = SADB.printTreatments();
				this.getContentPane().add(message);
				JScrollPane r = resultsForm(rs);
				r.setBounds(50, 150, 900, 600);
				this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add New Medication")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(medicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Medication")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchMedicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Medication")) {
			this.getContentPane().removeAll();
			ResultSet rs = SADB.printMedications();
			this.getContentPane().add(medicationsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Medications")) {
			try {
				JLabel message = new JLabel("MEDICATIONS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				ResultSet rs = SADB.printMedications();
				this.getContentPane().add(message);
				JScrollPane r = resultsForm(rs);
				r.setBounds(50, 150, 900, 600);
				this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add New Incident")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(incidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Incident")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchIncidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Incident")) {
			this.getContentPane().removeAll();
			ResultSet rs = SADB.printIncidents();
			this.getContentPane().add(incidentsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Incidents")) {
			try {
				JLabel message = new JLabel("INCIDENTS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				ResultSet rs = SADB.printIncidents();
				this.getContentPane().add(message);
				JScrollPane r = resultsForm(rs);
				r.setBounds(50, 150, 900, 600);
				this.getContentPane().add(r);
				this.revalidate();
				this.repaint();
				this.pack();
			} catch (Exception er) {
				// Ignore the error and continues
			}
		} else if (btnLabel.equals("Add New Consultation") || btnLabel.equals("Add New Appointment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(consultationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Consultation") || btnLabel.equals("View/Search Appointment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchConsultationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Edit/Delete Consultation")) {
			this.getContentPane().removeAll();
			ResultSet rs = SADB.printConsultations();
			this.getContentPane().add(consultationsForm(rs, 1));
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View All Consultations")) {
			try {
				JLabel message = new JLabel("CONSULTATIONS");
				message.setFont(new Font("Arial", Font.BOLD, 14));
				message.setBounds(450, 80, 100, 100);
				this.getContentPane().removeAll();
				ResultSet rs = SADB.printConsultations();
				this.getContentPane().add(message);
				JScrollPane r = resultsForm(rs);
				r.setBounds(50, 150, 900, 600);
				this.getContentPane().add(r);
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
						if (messageFromServer.equals("Doctor")) {// user is doctor
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForDoctor());
						} else if (messageFromServer.equals("Nurse-HealthVisitor")) {// user is
																	// nurse or health visitor
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForClinicalStaff());
						} else if (messageFromServer.equals("Receptionist")) {// user is receptionist
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForReceptionist());
						} else if (messageFromServer.equals("MedicalRecords")) {// user is medical records
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForMedicalRecords());
						} else if (messageFromServer.equals("Management")) {// user is management
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForManagement());
						} else if (messageFromServer.equals("6")) {// user is management
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForPatient());
						}
						else if (messageFromServer.equals("wrong")) {// incorrect user
							JLabel message = new JLabel("Username or Password incorrect!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(380, 180, 250, 150);
							getContentPane().add(loginForm());
							getContentPane().add(message);
							getContentPane().add(signupForm());
						} else
							System.out.println("nothing");
						revalidate();
						repaint();
						pack();
					}
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
						if (messageFromServer.equals("passwordChanged")) {// password changed
							System.out.println(messageFromServer + "allagi");
							JLabel message = new JLabel("You have successfully changed your password!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(320, 180, 350, 150);
							getContentPane().add(changePasswordForm());
							getContentPane().add(message);
						}
						if (messageFromServer.equals("wrongPassword")) {// wrong password
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
		String[] roles = { "Doctor", "Nurse", "Health Visitor", "Receptionist", "Medical Records", "Management" };
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
						if (messageFromServer.equals("success")) {// successful sign
																// up
							JLabel message = new JLabel("You have successfully signed up!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(320, 470, 350, 160);
							getContentPane().add(loginForm());
							getContentPane().add(signupForm());
							getContentPane().add(message);
						}
						if (messageFromServer.equals("alreadyExists")) {// username already exists
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
					out.println("patient");
					out.println(name.getText());
					out.println(surname.getText());
					out.println(username.getText());
					out.println(password.getText());
					out.println(Integer.parseInt(phone.getText()));
					out.println(email.getText());
					out.println(address.getText());
					if ((messageFromServer = in.readLine()) != null) {
						getContentPane().removeAll();
						if(messageFromServer.equals("patientAdded")){
							JLabel message = new JLabel("You have successfully added the patient!");
							message.setFont(new Font("Arial", Font.BOLD, 14));
							message.setBounds(340, 470, 350, 50);
							getContentPane().add(patientForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}					
				}  catch (Exception er) {
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
			JPanel patientpanel = new JPanel();
			if (rs.next() == false && rs.previous() == false)
				return patientpanel;
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
						SADB.updatePatient(username.getText(), name.getText(), surname.getText(),
								Integer.parseInt(phone.getText()), email.getText(), address.getText());
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

	private JPanel relativesForm(ResultSet rs, int x) {
		try {
			JPanel relativepanel = new JPanel();
			if (rs.next() == false && rs.previous() == false)
				return relativepanel;
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
			JLabel lblid = new JLabel("    ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
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
			JLabel lblrelationship = new JLabel("Relationship");
			lblrelationship.setFont(new Font("Arial", Font.PLAIN, 14));
			JTextField id = new JTextField(rs.getString("RelativeID"));
			id.setEditable(false);
			JTextField patientid = new JTextField(rs.getString("PatientID"));
			JTextField name = new JTextField(rs.getString("Name"));
			JTextField surname = new JTextField(rs.getString("Surname"));
			JTextField phone = new JTextField(rs.getString("Phone"));
			JTextField email = new JTextField(rs.getString("Email"));
			JTextField address = new JTextField(rs.getString("Address"));
			JTextField relationship = new JTextField(rs.getString("Relationship"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updateRelative(Integer.parseInt(id.getText()), patientid.getText(), name.getText(),
								surname.getText(), Integer.parseInt(phone.getText()), email.getText(),
								address.getText(), relationship.getText());
						getContentPane().removeAll();
						getContentPane().add(relativesForm(SADB.printRelatives(), 1));
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
						SADB.deleteRelative(Integer.parseInt(id.getText()));
						getContentPane().removeAll();
						getContentPane().add(relativesForm(SADB.printRelatives(), 1));
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
						getContentPane().add(relativesForm(rs, -1));
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
						getContentPane().add(relativesForm(rs, 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			relativepanel.add(lblspace1);
			relativepanel.add(previous);
			relativepanel.add(next);
			relativepanel.add(lblspace2);
			relativepanel.add(lblid);
			relativepanel.add(id);
			relativepanel.add(lblpatientid);
			relativepanel.add(patientid);
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
			relativepanel.add(lblrelationship);
			relativepanel.add(relationship);
			relativepanel.add(update);
			relativepanel.add(delete);
			relativepanel.setBounds(350, 150, 250, 250);
			relativepanel.setOpaque(false);
			return relativepanel;
		} catch (Exception er) {
			// Ignore the error and continues
			return null;
		}
	}

	private JPanel searchRelativeForm() {
		JPanel relativepanel = new JPanel();
		JLabel message = new JLabel(
				"Search Relatives by ID, Patient's Username, Name, Surname, Phone, Email, Address, Relationship or all of them:");
		message.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblspace1 = new JLabel("                       ");
		JLabel lblspace2 = new JLabel("                       ");
		JLabel lblid = new JLabel("     ID");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
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
		JLabel lblrelationship = new JLabel("Relationship");
		lblrelationship.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField id = new JTextField(15);
		JTextField patientid = new JTextField(15);
		JTextField name = new JTextField(15);
		JTextField surname = new JTextField(15);
		JTextField username = new JTextField(15);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		JTextField address = new JTextField(15);
		JTextField relationship = new JTextField(15);
		JButton searchRelative = new JButton("Search");

		relativepanel.add(lblspace1);
		relativepanel.add(message);
		relativepanel.add(lblspace2);
		relativepanel.add(lblid);
		relativepanel.add(id);
		relativepanel.add(lblpatientid);
		relativepanel.add(patientid);
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
		relativepanel.add(lblrelationship);
		relativepanel.add(relationship);
		relativepanel.add(searchRelative);
		searchRelative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = SADB.searchRelative(id.getText(), patientid.getText(), name.getText(),
							surname.getText(), phone.getText(), email.getText(), address.getText(),
							relationship.getText());
					getContentPane().removeAll();
					getContentPane().add(searchRelativeForm());
					getContentPane().add(resultsForm(rs));
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		relativepanel.setBounds(50, 150, 900, 150);
		relativepanel.setOpaque(false);
		return relativepanel;
	}

	private JPanel incidentForm() {
		String[] types = { "Accidental Treatment Incident", "Deliberate Incident", "Threat" };
		JPanel incidentpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltype = new JLabel("Incident Type");
		lbltype.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblshortDescription = new JLabel("Short Description");
		lblshortDescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField patientid = new JTextField(15);
		JComboBox type = new JComboBox(types);
		JTextField shortDescription = new JTextField(15);
		JTextField description = new JTextField(15);
		JTextField date = new JTextField(15);
		JButton addIncident = new JButton("Add");

		incidentpanel.add(lblpatientid);
		incidentpanel.add(patientid);
		incidentpanel.add(lbltype);
		incidentpanel.add(type);
		incidentpanel.add(lblshortDescription);
		incidentpanel.add(shortDescription);
		incidentpanel.add(lbldescription);
		incidentpanel.add(description);
		incidentpanel.add(lbldate);
		incidentpanel.add(date);
		incidentpanel.add(addIncident);
		addIncident.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SADB.addIncident(patientid.getText(), type.getSelectedItem().toString(), shortDescription.getText(),
							description.getText(), date.getText());
					getContentPane().removeAll();
					JLabel message = new JLabel("You have successfully added the incident!");
					message.setFont(new Font("Arial", Font.BOLD, 14));
					message.setBounds(340, 470, 350, 50);
					getContentPane().add(incidentForm());
					getContentPane().add(message);
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		incidentpanel.setBounds(350, 150, 250, 250);
		incidentpanel.setOpaque(false);
		return incidentpanel;
	}

	private JPanel incidentsForm(ResultSet rs, int x) {
		try {
			JPanel incidentpanel = new JPanel();
			if (rs.next() == false && rs.previous() == false)
				return incidentpanel;
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
			JLabel lblid = new JLabel("    ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbltype = new JLabel("Incident Type");
			lbltype.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblshortDescription = new JLabel("Short Description");
			lblshortDescription.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldescription = new JLabel("Description");
			lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldate = new JLabel("Date");
			lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
			JTextField id = new JTextField(rs.getString("IncidentID"));
			id.setEditable(false);
			JTextField patientid = new JTextField(rs.getString("PatientID"));
			JTextField type = new JTextField(rs.getString("IncidentType"));
			JTextField shortDescription = new JTextField(rs.getString("ShortDescription"));
			JTextField description = new JTextField(rs.getString("Description"));
			JTextField date = new JTextField(rs.getString("Date"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updateIncident(Integer.parseInt(id.getText()), patientid.getText(), type.getText(),
								shortDescription.getText(), description.getText(), date.getText());
						getContentPane().removeAll();
						getContentPane().add(incidentsForm(SADB.printIncidents(), 1));
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
						SADB.deleteIncident(Integer.parseInt(id.getText()));
						getContentPane().removeAll();
						getContentPane().add(incidentsForm(SADB.printIncidents(), 1));
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
						getContentPane().add(incidentsForm(rs, -1));
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
						getContentPane().add(incidentsForm(rs, 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			incidentpanel.add(lblspace1);
			incidentpanel.add(previous);
			incidentpanel.add(next);
			incidentpanel.add(lblspace2);
			incidentpanel.add(lblid);
			incidentpanel.add(id);
			incidentpanel.add(lblpatientid);
			incidentpanel.add(patientid);
			incidentpanel.add(lbltype);
			incidentpanel.add(type);
			incidentpanel.add(lblshortDescription);
			incidentpanel.add(shortDescription);
			incidentpanel.add(lbldescription);
			incidentpanel.add(description);
			incidentpanel.add(lbldate);
			incidentpanel.add(date);
			incidentpanel.add(update);
			incidentpanel.add(delete);
			incidentpanel.setBounds(350, 150, 250, 250);
			incidentpanel.setOpaque(false);
			return incidentpanel;
		} catch (Exception er) {
			// Ignore the error and continues
			return null;
		}
	}

	private JPanel searchIncidentForm() {
		JPanel incidentpanel = new JPanel();
		JLabel message = new JLabel("Search Incidents by ID, Patient's Username, Type, Date or all of them:");
		message.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblspace1 = new JLabel("                       ");
		JLabel lblspace2 = new JLabel("                       ");
		JLabel lblid = new JLabel("     ID");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltype = new JLabel("Type");
		JLabel lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField id = new JTextField(15);
		JTextField patientid = new JTextField(15);
		JTextField type = new JTextField(15);
		JTextField date = new JTextField(15);
		JButton searchIncident = new JButton("Search");

		incidentpanel.add(lblspace1);
		incidentpanel.add(message);
		incidentpanel.add(lblspace2);
		incidentpanel.add(lblid);
		incidentpanel.add(id);
		incidentpanel.add(lblpatientid);
		incidentpanel.add(patientid);
		incidentpanel.add(lbltype);
		incidentpanel.add(type);
		incidentpanel.add(lbldate);
		incidentpanel.add(date);
		incidentpanel.add(searchIncident);
		searchIncident.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = SADB.searchIncident(id.getText(), patientid.getText(), type.getText(),
							date.getText());
					getContentPane().removeAll();
					getContentPane().add(searchIncidentForm());
					getContentPane().add(resultsForm(rs));
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		incidentpanel.setBounds(50, 150, 900, 150);
		incidentpanel.setOpaque(false);
		return incidentpanel;
	}

	private JPanel treatmentForm() {
		JPanel treatmentpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstartDate = new JLabel("Start Date");
		lblstartDate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblendDate = new JLabel("End Date");
		lblendDate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldiagnosis = new JLabel("Diagnosis");
		lbldiagnosis.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("Staff ID");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField patientid = new JTextField(15);
		JTextField startDate = new JTextField(15);
		JTextField endDate = new JTextField(15);
		JTextField diagnosis = new JTextField(15);
		JTextField description = new JTextField(15);
		JTextField staffid = new JTextField(15);
		JButton addTreatment = new JButton("Add");

		treatmentpanel.add(lblpatientid);
		treatmentpanel.add(patientid);
		treatmentpanel.add(lblstartDate);
		treatmentpanel.add(startDate);
		treatmentpanel.add(lblendDate);
		treatmentpanel.add(endDate);
		treatmentpanel.add(lbldiagnosis);
		treatmentpanel.add(diagnosis);
		treatmentpanel.add(lbldescription);
		treatmentpanel.add(description);
		treatmentpanel.add(lblstaffid);
		treatmentpanel.add(staffid);
		treatmentpanel.add(addTreatment);
		addTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SADB.addTreatment(patientid.getText(), startDate.getText(), endDate.getText(), diagnosis.getText(),
							description.getText(), staffid.getText());
					getContentPane().removeAll();
					JLabel message = new JLabel("You have successfully added the treatment!");
					message.setFont(new Font("Arial", Font.BOLD, 14));
					message.setBounds(340, 470, 350, 50);
					getContentPane().add(treatmentForm());
					getContentPane().add(message);
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		treatmentpanel.setBounds(350, 150, 250, 250);
		treatmentpanel.setOpaque(false);
		return treatmentpanel;
	}

	private JPanel treatmentsForm(ResultSet rs, int x) {
		try {
			JPanel treatmentpanel = new JPanel();
			if (rs.next() == false && rs.previous() == false)
				return treatmentpanel;
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
			JLabel lblid = new JLabel("    ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstartDate = new JLabel("Start Date");
			lblstartDate.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblendDate = new JLabel("End Date");
			lblendDate.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldiagnosis = new JLabel("Diagnosis");
			lbldiagnosis.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldescription = new JLabel("Description");
			lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstaffid = new JLabel("Staff ID");
			lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
			JTextField id = new JTextField(rs.getString("TreatmentID"));
			id.setEditable(false);
			JTextField patientid = new JTextField(rs.getString("PatientID"));
			JTextField startDate = new JTextField(rs.getString("StartDate"));
			JTextField endDate = new JTextField(rs.getString("EndDate"));
			JTextField diagnosis = new JTextField(rs.getString("Diagnosis"));
			JTextField description = new JTextField(rs.getString("Description"));
			JTextField staffid = new JTextField(rs.getString("StaffID"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updateTreatment(Integer.parseInt(id.getText()), patientid.getText(), startDate.getText(),
								endDate.getText(), diagnosis.getText(), description.getText(), staffid.getText());
						getContentPane().removeAll();
						getContentPane().add(treatmentsForm(SADB.printTreatments(), 1));
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
						SADB.deleteTreatment(Integer.parseInt(id.getText()));
						getContentPane().removeAll();
						getContentPane().add(treatmentsForm(SADB.printTreatments(), 1));
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
						getContentPane().add(treatmentsForm(rs, -1));
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
						getContentPane().add(treatmentsForm(rs, 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			treatmentpanel.add(lblspace1);
			treatmentpanel.add(previous);
			treatmentpanel.add(next);
			treatmentpanel.add(lblspace2);
			treatmentpanel.add(lblid);
			treatmentpanel.add(id);
			treatmentpanel.add(lblpatientid);
			treatmentpanel.add(patientid);
			treatmentpanel.add(lblstartDate);
			treatmentpanel.add(startDate);
			treatmentpanel.add(lblendDate);
			treatmentpanel.add(endDate);
			treatmentpanel.add(lbldiagnosis);
			treatmentpanel.add(diagnosis);
			treatmentpanel.add(lbldescription);
			treatmentpanel.add(description);
			treatmentpanel.add(lblstaffid);
			treatmentpanel.add(staffid);
			treatmentpanel.add(update);
			treatmentpanel.add(delete);
			treatmentpanel.setBounds(350, 150, 250, 250);
			treatmentpanel.setOpaque(false);
			return treatmentpanel;
		} catch (Exception er) {
			// Ignore the error and continues
			return null;
		}
	}

	private JPanel searchTreatmentForm() {
		JPanel treatmentpanel = new JPanel();
		JLabel message = new JLabel(
				"Search Treatments by ID, Patient's Username, Start Date, End Date, Staff ID or all of them:");
		message.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblspace1 = new JLabel("                       ");
		JLabel lblspace2 = new JLabel("                       ");
		JLabel lblid = new JLabel("     ID");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstartDate = new JLabel("Start Date");
		lblstartDate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblendDate = new JLabel("End Date");
		lblendDate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("Staff ID");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField id = new JTextField(15);
		JTextField patientid = new JTextField(15);
		JTextField startDate = new JTextField(15);
		JTextField endDate = new JTextField(15);
		JTextField staffid = new JTextField(15);
		JButton searchTreatment = new JButton("Search");

		treatmentpanel.add(lblspace1);
		treatmentpanel.add(message);
		treatmentpanel.add(lblspace2);
		treatmentpanel.add(lblid);
		treatmentpanel.add(id);
		treatmentpanel.add(lblpatientid);
		treatmentpanel.add(patientid);
		treatmentpanel.add(lblstartDate);
		treatmentpanel.add(startDate);
		treatmentpanel.add(lblendDate);
		treatmentpanel.add(endDate);
		treatmentpanel.add(lblstaffid);
		treatmentpanel.add(staffid);
		treatmentpanel.add(searchTreatment);
		searchTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = SADB.searchTreatment(id.getText(), patientid.getText(), startDate.getText(),
							endDate.getText(), staffid.getText());
					getContentPane().removeAll();
					getContentPane().add(searchTreatmentForm());
					getContentPane().add(resultsForm(rs));
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		treatmentpanel.setBounds(50, 150, 900, 150);
		treatmentpanel.setOpaque(false);
		return treatmentpanel;
	}

	private JPanel medicationForm() {
		JPanel medicationpanel = new JPanel();
		JLabel lblbrand = new JLabel("Brand");
		lblbrand.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblname = new JLabel("Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbleffects = new JLabel("Known Side Effects");
		lbleffects.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField brand = new JTextField(15);
		JTextField name = new JTextField(15);
		JTextField description = new JTextField(15);
		JTextField effects = new JTextField(15);
		JButton addMedication = new JButton("Add");

		medicationpanel.add(lblbrand);
		medicationpanel.add(brand);
		medicationpanel.add(lblname);
		medicationpanel.add(name);
		medicationpanel.add(lbldescription);
		medicationpanel.add(description);
		medicationpanel.add(lbleffects);
		medicationpanel.add(effects);
		medicationpanel.add(addMedication);
		addMedication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SADB.addMedication(brand.getText(), name.getText(), description.getText(), effects.getText());
					getContentPane().removeAll();
					JLabel message = new JLabel("You have successfully added the medication!");
					message.setFont(new Font("Arial", Font.BOLD, 14));
					message.setBounds(340, 470, 350, 50);
					getContentPane().add(medicationForm());
					getContentPane().add(message);
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		medicationpanel.setBounds(350, 150, 250, 250);
		medicationpanel.setOpaque(false);
		return medicationpanel;
	}

	private JPanel medicationsForm(ResultSet rs, int x) {
		try {
			JPanel medicationpanel = new JPanel();
			if (rs.next() == false && rs.previous() == false)
				return medicationpanel;
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
			JLabel lblid = new JLabel("    ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblbrand = new JLabel("Brand");
			lblbrand.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblname = new JLabel("Name");
			lblname.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldescription = new JLabel("Description");
			lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbleffects = new JLabel("Known Side Effects");
			lbleffects.setFont(new Font("Arial", Font.PLAIN, 14));
			JTextField id = new JTextField(rs.getString("MedicationID"));
			id.setEditable(false);
			JTextField brand = new JTextField(rs.getString("Brand"));
			JTextField name = new JTextField(rs.getString("Name"));
			JTextField description = new JTextField(rs.getString("Description"));
			JTextField effects = new JTextField(rs.getString("KnownSideEffects"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updateMedication(Integer.parseInt(id.getText()), brand.getText(), name.getText(),
								description.getText(), effects.getText());
						getContentPane().removeAll();
						getContentPane().add(medicationsForm(SADB.printMedications(), 1));
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
						SADB.deleteMedication(Integer.parseInt(id.getText()));
						getContentPane().removeAll();
						getContentPane().add(medicationsForm(SADB.printMedications(), 1));
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
						getContentPane().add(medicationsForm(rs, -1));
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
						getContentPane().add(medicationsForm(rs, 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			medicationpanel.add(lblspace1);
			medicationpanel.add(previous);
			medicationpanel.add(next);
			medicationpanel.add(lblspace2);
			medicationpanel.add(lblid);
			medicationpanel.add(id);
			medicationpanel.add(lblbrand);
			medicationpanel.add(brand);
			medicationpanel.add(lblname);
			medicationpanel.add(name);
			medicationpanel.add(lbldescription);
			medicationpanel.add(description);
			medicationpanel.add(lbleffects);
			medicationpanel.add(effects);
			medicationpanel.add(update);
			medicationpanel.add(delete);
			medicationpanel.setBounds(350, 150, 250, 250);
			medicationpanel.setOpaque(false);
			return medicationpanel;
		} catch (Exception er) {
			// Ignore the error and continues
			return null;
		}
	}

	private JPanel searchMedicationForm() {
		JPanel medicationpanel = new JPanel();
		JLabel message = new JLabel("Search Medications by ID, Brand, Name or all of them:");
		message.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblspace1 = new JLabel("                       ");
		JLabel lblspace2 = new JLabel("                       ");
		JLabel lblid = new JLabel("     ID");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblbrand = new JLabel("Brand");
		lblbrand.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblname = new JLabel("Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField id = new JTextField(15);
		JTextField brand = new JTextField(15);
		JTextField name = new JTextField(15);
		JButton searchMedication = new JButton("Search");

		medicationpanel.add(lblspace1);
		medicationpanel.add(message);
		medicationpanel.add(lblspace2);
		medicationpanel.add(lblid);
		medicationpanel.add(id);
		medicationpanel.add(lblbrand);
		medicationpanel.add(brand);
		medicationpanel.add(lblname);
		medicationpanel.add(name);
		medicationpanel.add(searchMedication);
		searchMedication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = SADB.searchMedication(id.getText(), brand.getText(), name.getText());
					getContentPane().removeAll();
					getContentPane().add(searchMedicationForm());
					getContentPane().add(resultsForm(rs));
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		medicationpanel.setBounds(50, 150, 900, 150);
		medicationpanel.setOpaque(false);
		return medicationpanel;
	}

	private JPanel consultationForm() {
		JPanel consultationpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("Staff ID");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsubject = new JLabel("Subject");
		lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldateBooked = new JLabel("Date Booked");
		lbldateBooked.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltime = new JLabel("Time");
		lbltime.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltreatmentid = new JLabel("Treatment ID");
		lbltreatmentid.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField patientid = new JTextField(15);
		JTextField staffid = new JTextField(15);
		JTextField subject = new JTextField(15);
		JTextField dateBooked = new JTextField(15);
		JTextField date = new JTextField(15);
		JTextField time = new JTextField(15);
		JTextField treatmentid = new JTextField(15);
		JButton addConsultation = new JButton("Add");

		consultationpanel.add(lblpatientid);
		consultationpanel.add(patientid);
		consultationpanel.add(lblstaffid);
		consultationpanel.add(staffid);
		consultationpanel.add(lblsubject);
		consultationpanel.add(subject);
		consultationpanel.add(lbldateBooked);
		consultationpanel.add(dateBooked);
		consultationpanel.add(lbldate);
		consultationpanel.add(date);
		consultationpanel.add(lbltime);
		consultationpanel.add(time);
		consultationpanel.add(lbltreatmentid);
		consultationpanel.add(treatmentid);
		consultationpanel.add(addConsultation);
		addConsultation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SADB.addConsultation(patientid.getText(), staffid.getText(), subject.getText(),
							dateBooked.getText(), date.getText(), time.getText(), treatmentid.getText());
					getContentPane().removeAll();
					JLabel message = new JLabel("You have successfully added the consultation!");
					message.setFont(new Font("Arial", Font.BOLD, 14));
					message.setBounds(340, 470, 350, 50);
					getContentPane().add(consultationForm());
					getContentPane().add(message);
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		consultationpanel.setBounds(350, 150, 250, 250);
		consultationpanel.setOpaque(false);
		return consultationpanel;
	}

	private JPanel consultationsForm(ResultSet rs, int x) {
		try {
			JPanel consultationpanel = new JPanel();
			if (rs.next() == false && rs.previous() == false)
				return consultationpanel;
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
			JLabel lblid = new JLabel("    ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstaffid = new JLabel("Staff ID");
			lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblsubject = new JLabel("Subject");
			lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldateBooked = new JLabel("Date Booked");
			lbldateBooked.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldate = new JLabel("Date");
			lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbltime = new JLabel("Time");
			lbltime.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbltreatmentid = new JLabel("Treatment ID");
			lbltreatmentid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblattended = new JLabel("Attended");
			lblattended.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblupdated = new JLabel("Medical Record Updated");
			lblupdated.setFont(new Font("Arial", Font.PLAIN, 14));
			JTextField id = new JTextField(rs.getString("ConsultationID"));
			id.setEditable(false);
			JTextField patientid = new JTextField(rs.getString("PatientID"));
			JTextField staffid = new JTextField(rs.getString("StaffID"));
			JTextField subject = new JTextField(rs.getString("Subject"));
			JTextField dateBooked = new JTextField(rs.getString("DateBooked"));
			JTextField date = new JTextField(rs.getString("Date"));
			JTextField time = new JTextField(rs.getString("Time"));
			JTextField attended = new JTextField(rs.getString("Attended"));
			JTextField updated = new JTextField(rs.getString("MedicalRecordUpdated"));
			JTextField treatmentid = new JTextField(rs.getString("TreatmentID"));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SADB.updateConsultation(Integer.parseInt(id.getText()), patientid.getText(), staffid.getText(),
								subject.getText(), dateBooked.getText(), date.getText(), time.getText(),
								Integer.parseInt(attended.getText()), Integer.parseInt(updated.getText()),
								Integer.parseInt(treatmentid.getText()));
						getContentPane().removeAll();
						getContentPane().add(consultationsForm(SADB.printConsultations(), 1));
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
						SADB.deleteConsultation(Integer.parseInt(id.getText()));
						getContentPane().removeAll();
						getContentPane().add(consultationsForm(SADB.printConsultations(), 1));
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
						getContentPane().add(consultationsForm(rs, -1));
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
						getContentPane().add(consultationsForm(rs, 1));
						revalidate();
						repaint();
						pack();
					} catch (Exception er) {
						// Ignore the error and continues
					}
				}
			});
			consultationpanel.add(lblspace1);
			consultationpanel.add(previous);
			consultationpanel.add(next);
			consultationpanel.add(lblspace2);
			consultationpanel.add(lblid);
			consultationpanel.add(id);
			consultationpanel.add(lblpatientid);
			consultationpanel.add(patientid);
			consultationpanel.add(lblstaffid);
			consultationpanel.add(staffid);
			consultationpanel.add(lblsubject);
			consultationpanel.add(subject);
			consultationpanel.add(lbldateBooked);
			consultationpanel.add(dateBooked);
			consultationpanel.add(lbldate);
			consultationpanel.add(date);
			consultationpanel.add(lbltime);
			consultationpanel.add(time);
			consultationpanel.add(lblattended);
			consultationpanel.add(attended);
			consultationpanel.add(lblupdated);
			consultationpanel.add(updated);
			consultationpanel.add(lbltreatmentid);
			consultationpanel.add(treatmentid);
			consultationpanel.add(update);
			consultationpanel.add(delete);
			consultationpanel.setBounds(350, 150, 250, 250);
			consultationpanel.setOpaque(false);
			return consultationpanel;
		} catch (Exception er) {
			// Ignore the error and continues
			return null;
		}
	}

	private JPanel searchConsultationForm() {
		JPanel consultationpanel = new JPanel();
		JLabel message = new JLabel(
				"Search Consultations by ID, Patient's Username, Staff ID, Subject, Date Booked, Date, Time, Attended, Medical Record Updated, Treatment ID or all of them:");
		message.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel lblspace1 = new JLabel("                       ");
		JLabel lblspace2 = new JLabel("                       ");
		JLabel lblid = new JLabel("    ID");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("Staff ID");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsubject = new JLabel("Subject");
		lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldateBooked = new JLabel("Date Booked");
		lbldateBooked.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltime = new JLabel("Time");
		lbltime.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltreatmentid = new JLabel("Treatment ID");
		lbltreatmentid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblattended = new JLabel("Attended");
		lblattended.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblupdated = new JLabel("Medical Record Updated");
		lblupdated.setFont(new Font("Arial", Font.PLAIN, 14));
		JTextField id = new JTextField(15);
		JTextField patientid = new JTextField(15);
		JTextField staffid = new JTextField(15);
		JTextField subject = new JTextField(15);
		JTextField dateBooked = new JTextField(15);
		JTextField date = new JTextField(15);
		JTextField time = new JTextField(15);
		JTextField attended = new JTextField(15);
		JTextField updated = new JTextField(15);
		JTextField treatmentid = new JTextField(15);
		JButton searchConsultation = new JButton("Search");

		consultationpanel.add(lblspace1);
		consultationpanel.add(message);
		consultationpanel.add(lblspace2);
		consultationpanel.add(lblid);
		consultationpanel.add(lblid);
		consultationpanel.add(id);
		consultationpanel.add(lblpatientid);
		consultationpanel.add(patientid);
		consultationpanel.add(lblstaffid);
		consultationpanel.add(staffid);
		consultationpanel.add(lblsubject);
		consultationpanel.add(subject);
		consultationpanel.add(lbldateBooked);
		consultationpanel.add(dateBooked);
		consultationpanel.add(lbldate);
		consultationpanel.add(date);
		consultationpanel.add(lbltime);
		consultationpanel.add(time);
		consultationpanel.add(lblattended);
		consultationpanel.add(attended);
		consultationpanel.add(lblupdated);
		consultationpanel.add(updated);
		consultationpanel.add(lbltreatmentid);
		consultationpanel.add(treatmentid);
		consultationpanel.add(searchConsultation);
		searchConsultation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = SADB.searchConsultation(id.getText(), patientid.getText(), staffid.getText(),
							subject.getText(), dateBooked.getText(), date.getText(), time.getText(), attended.getText(),
							updated.getText(), treatmentid.getText());
					getContentPane().removeAll();
					getContentPane().add(searchConsultationForm());
					getContentPane().add(resultsForm(rs));
					revalidate();
					repaint();
					pack();
				} catch (Exception er) {
					// Ignore the error and continues
				}
			}
		});
		consultationpanel.setBounds(50, 150, 900, 150);
		consultationpanel.setOpaque(false);
		return consultationpanel;
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
