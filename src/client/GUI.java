/**
 * This class implements the graphical user interface (GUI) that the users will be able
 * to see and use to interact with the system. The users of the system are:
 * Clinical Staff (Doctors, Nurses, Health Visitors), Receptionists, Medical Records
 * Staff, Health Service Management.
 * 
 * @author Sotia Gregoriou, Elena Matsi, Erasmia Shimitra
 */

package client;

// Libraries
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import server.JDBC;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import entities.*;

public class GUI extends JFrame implements ActionListener, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	public MyPanel contentPane = new MyPanel();
	public JDBC SADB;
	String usernameGUI = "";
	String roleGUI = "";
	private BufferedReader in;
	private PrintWriter out;
	private ObjectInputStream inObject;
	static Socket socket;
	String messageFromServer = null;
	GUIMenu myMenu = null;

	// Constructor
	public GUI() {
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResource("/health.png")); // Icon
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
		} else if (btnLabel.equals("View/Edit Profile")) {
			this.getContentPane().removeAll();
			out.println("printProfile");
			out.flush();
			out.println(usernameGUI);
			out.flush();
			out.println(roleGUI);
			out.flush();
			if (roleGUI.equals("Patient")) {
				List<Patient> ls = new ArrayList<Patient>();
				try {
					ls = (List<Patient>) inObject.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.getContentPane().add(profilePatientForm(ls));
			} else {
				List<Staff> ls = new ArrayList<Staff>();
				try {
					ls = (List<Staff>) inObject.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.getContentPane().add(profileStaffForm(ls));
			}
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
			try {
				this.getContentPane().add(printAllPatients());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add New Relative")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(relativeForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Relative")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllRelatives());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Inform Relative")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(informRelativesForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add New Treatment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchPreviousTreatmentForm());
			this.getContentPane().add(treatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Treatment")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllTreatments());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Search Treatment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchRenewTreatmentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add New Medication")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(medicationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Medication")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllMedications());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add Medication Reaction")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(medicationReactionForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Medication Reaction")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllMedicationReactions());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add New Incident")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(incidentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Incident")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllIncidents());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add New Consultation") || btnLabel.equals("Add New Appointment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(consultationForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Consultation") || btnLabel.equals("View/Search Appointment")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllConsultations());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Add New Comment")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(commentForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View/Search Comment")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(printAllComments());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Update Harm Risk Record")) {
			this.getContentPane().removeAll();
			this.getContentPane().add(searchPatientForHarmRiskForm());
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Attended Daily Consultations")) {
			this.getContentPane().removeAll();
			JPanel datePanel = new JPanel();
			JLabel lbldate = new JLabel("Date");
			lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
			final JDateChooser date = new JDateChooser();
			date.setDateFormatString("yyyy-MM-dd");
			JButton search = new JButton("Search");
			search.setFont(new Font("Arial", Font.PLAIN, 14));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						getContentPane().add(consultationReports(1, dateFormat.format(date.getDate())));
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					revalidate();
					repaint();
					pack();
				}
			});
			datePanel.add(lbldate);
			datePanel.add(date);
			datePanel.add(search);
			datePanel.setBounds(350, 130, 250, 100);
			datePanel.setOpaque(false);
			datePanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.getContentPane().add(datePanel);
			this.revalidate();
			this.repaint();
			this.pack();

		} else if (btnLabel.equals("Attended General Consultations")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(consultationReports(1, "null"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		}

		else if (btnLabel.equals("Non Attended Daily Consultations")) {
			this.getContentPane().removeAll();
			JPanel datePanel = new JPanel();
			JLabel lbldate = new JLabel("Date");
			lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
			final JDateChooser date = new JDateChooser();
			date.setDateFormatString("yyyy-MM-dd");
			JButton search = new JButton("Search");
			search.setFont(new Font("Arial", Font.PLAIN, 14));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						getContentPane().add(consultationReports(0, dateFormat.format(date.getDate())));
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					revalidate();
					repaint();
					pack();
				}
			});
			datePanel.add(lbldate);
			datePanel.add(date);
			datePanel.add(search);
			datePanel.setBounds(350, 130, 250, 100);
			datePanel.setOpaque(false);
			datePanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.getContentPane().add(datePanel);
			this.revalidate();
			this.repaint();
			this.pack();

		} else if (btnLabel.equals("Non Attended General Consultations")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(consultationReports(0, "null"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Non Updated Daily Medical Records")) {
			this.getContentPane().removeAll();
			JPanel datePanel = new JPanel();
			JLabel lbldate = new JLabel("Date");
			lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
			final JDateChooser date = new JDateChooser();
			date.setDateFormatString("yyyy-MM-dd");
			JButton search = new JButton("Search");
			search.setFont(new Font("Arial", Font.PLAIN, 14));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						getContentPane().add(consultationReportMedical(dateFormat.format(date.getDate())));
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					revalidate();
					repaint();
					pack();
				}
			});
			datePanel.add(lbldate);
			datePanel.add(date);
			datePanel.add(search);
			datePanel.setBounds(350, 130, 250, 100);
			datePanel.setOpaque(false);
			datePanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.getContentPane().add(datePanel);
			this.revalidate();
			this.repaint();
			this.pack();

		} else if (btnLabel.equals("Non Updated General Medical Records")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(consultationReportMedical("null"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Changes of Patient Profiles")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(patientReport(1, "null"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Patients with specific Condition")) {
			this.getContentPane().removeAll();
			JPanel namePanel = new JPanel();
			JLabel lblname = new JLabel("Condition");
			lblname.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField value = new JTextField(15);
			JButton search = new JButton("Search");
			search.setFont(new Font("Arial", Font.PLAIN, 14));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getContentPane().add(patientReport(2, value.getText()));
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					revalidate();
					repaint();
					pack();
				}
			});
			namePanel.add(lblname);
			namePanel.add(value);
			namePanel.add(search);
			namePanel.setBounds(350, 130, 250, 100);
			namePanel.setOpaque(false);
			namePanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.getContentPane().add(namePanel);
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Patients with specific Treatment/Medication")) {
			this.getContentPane().removeAll();
			JPanel namePanel = new JPanel();
			JLabel lblname = new JLabel("Treatment/Medication");
			lblname.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField value = new JTextField(15);
			JButton search = new JButton("Search");
			search.setFont(new Font("Arial", Font.PLAIN, 14));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getContentPane().add(patientReport(3, value.getText()));
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					revalidate();
					repaint();
					pack();
				}
			});
			namePanel.add(lblname);
			namePanel.add(value);
			namePanel.add(search);
			namePanel.setBounds(350, 130, 250, 100);
			namePanel.setOpaque(false);
			namePanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.getContentPane().add(namePanel);
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Number of Patients Attended")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(AttendanceReport());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Number of Patients by Condition")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(ConditionReport());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Number of Patients by Treatment/Medication")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(MedicationReport());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("Medication Prescriptions Summary")) {
			this.getContentPane().removeAll();
			try {
				this.getContentPane().add(medicationPrescription());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View Warning Letters")) {
			try {
				getContentPane().add(warningLetters("null"));
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		} else if (btnLabel.equals("View Today's Appointments")) {
			try {
				getContentPane().add(todaysAppointments("null"));
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			this.revalidate();
			this.repaint();
			this.pack();
		}

	}

	/**
	 * This class is used to insert a background image.
	 */
	class MyPanel extends JPanel implements java.io.Serializable {
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
		inObject = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * This function implements the action of the log in. Only users can log in
	 * to the system (Staff: Clinical, Medical Records, Receptionists, Health
	 * Service and Patients)
	 * 
	 * @return JPanel the panel for the log in
	 */
	private JPanel loginForm() {
		String[] roles = { "Staff", "Patient" };
		JPanel loginpanel = new JPanel();
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpassword = new JLabel(" Password");
		lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblrole = new JLabel("     Role");
		lblrole.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField username = new JTextField(15);
		final JTextField password = new JPasswordField(15);
		final JComboBox role = new JComboBox(roles);
		JButton login = new JButton("Log In");
		login.setFont(new Font("Arial", Font.PLAIN, 14));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
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
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// User is doctor
						if (messageFromServer.equals("Doctor")) {
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForDoctor());
							// User is nurse or health visitor
						} else if (messageFromServer.equals("Nurse-HealthVisitor")) {
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForClinicalStaff());
							// User is receptionist
						} else if (messageFromServer.equals("Receptionist")) {
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForReceptionist());
							// User is medical records
						} else if (messageFromServer.equals("MedicalRecords")) {
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForMedicalRecords());
							// User is management
						} else if (messageFromServer.equals("Management")) {
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForManagement());
							// User is patient
						} else if (messageFromServer.equals("Patient")) {
							usernameGUI = username.getText();
							roleGUI = role.getSelectedItem().toString();
							setJMenuBar(myMenu.menuForPatient());
							// Not authenticated user
						} else if (messageFromServer.equals("wrong")) {
							JLabel message = new JLabel("* Username or Password incorrect!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.red);
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
					System.out.println("Exception: login");
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
		loginpanel.setBounds(350, 130, 250, 100);
		loginpanel.setOpaque(false);
		loginpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return loginpanel;
	}

	/**
	 * This function shows the profile of the patient that is logged in and
	 * gives the opportunity to change his/her personal information.
	 * 
	 * @return JPanel the panel for profile if the user is patient
	 */
	private JPanel profilePatientForm(List<Patient> patients) {
		try {
			JPanel profilepanel = new JPanel();
			JLabel lblname = new JLabel("Name");
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
			final JTextField name = new JTextField(patients.get(0).Name);
			final JTextField surname = new JTextField(patients.get(0).Surname);
			final JTextField username = new JTextField(patients.get(0).PatientID);
			username.setEditable(false);
			JTextField password = new JPasswordField(patients.get(0).Password);
			password.setEditable(false);
			final JTextField phone = new JTextField(Integer.toString(patients.get(0).Phone));
			final JTextField email = new JTextField(patients.get(0).Email);
			final JTextField address = new JTextField(patients.get(0).Address);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateProfile");
						out.println(name.getText());
						out.println(surname.getText());
						out.println(username.getText());
						out.println(Integer.parseInt(phone.getText()));
						out.println(email.getText());
						out.println(address.getText());
						out.println(roleGUI);
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("profileUpdated")) {
								JLabel message = new JLabel("You have successfully updated your profile!");
								message.setFont(new Font("Arial", Font.PLAIN, 14));
								message.setBounds(340, 350, 350, 50);
								message.setForeground(Color.blue);
								// Get data from server
								List<Patient> ls = new ArrayList<Patient>();
								ls = (List<Patient>) inObject.readObject();
								getContentPane().add(profilePatientForm(ls));
								getContentPane().add(message);
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: profilePatientForm");
					}
				}
			});
			profilepanel.add(lblname);
			profilepanel.add(name);
			profilepanel.add(lblsurname);
			profilepanel.add(surname);
			profilepanel.add(lblusername);
			profilepanel.add(username);
			profilepanel.add(lblpassword);
			profilepanel.add(password);
			profilepanel.add(lblphone);
			profilepanel.add(phone);
			profilepanel.add(lblemail);
			profilepanel.add(email);
			profilepanel.add(lbladdress);
			profilepanel.add(address);
			profilepanel.add(update);
			profilepanel.setBounds(350, 150, 250, 250);
			profilepanel.setOpaque(false);
			return profilepanel;
		} catch (Exception er) {
			System.out.println("Exception: profilePatientForm");
			return null;
		}
	}

	/**
	 * This function shows the profile of the staff that is logged in and gives
	 * the opportunity to change his/her personal information.
	 * 
	 * @return JPanel the panel for profile if the user is staff
	 */
	private JPanel profileStaffForm(List<Staff> staff) {
		try {
			JPanel profilepanel = new JPanel();
			JLabel lblname = new JLabel("Name");
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
			final JTextField name = new JTextField(staff.get(0).Name);
			final JTextField surname = new JTextField(staff.get(0).Surname);
			final JTextField username = new JTextField(staff.get(0).StaffID);
			username.setEditable(false);
			final JTextField password = new JPasswordField(staff.get(0).Password);
			password.setEditable(false);
			final JTextField phone = new JTextField(Integer.toString(staff.get(0).Phone));
			final JTextField email = new JTextField(staff.get(0).Email);
			final JTextField address = new JTextField(staff.get(0).Address);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateProfile");
						out.println(name.getText());
						out.println(surname.getText());
						out.println(username.getText());
						out.println(Integer.parseInt(phone.getText()));
						out.println(email.getText());
						out.println(address.getText());
						out.println(roleGUI);
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("profileUpdated")) {
								JLabel message = new JLabel("You have successfully updated your profile!");
								message.setFont(new Font("Arial", Font.PLAIN, 14));
								message.setBounds(340, 350, 350, 50);
								message.setForeground(Color.blue);
								// Get data from server
								List<Staff> ls = new ArrayList<Staff>();
								ls = (List<Staff>) inObject.readObject();
								getContentPane().add(profileStaffForm(ls));
								getContentPane().add(message);
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: profileStaffForm");
					}
				}
			});
			profilepanel.add(lblname);
			profilepanel.add(name);
			profilepanel.add(lblsurname);
			profilepanel.add(surname);
			profilepanel.add(lblusername);
			profilepanel.add(username);
			profilepanel.add(lblpassword);
			profilepanel.add(password);
			profilepanel.add(lblphone);
			profilepanel.add(phone);
			profilepanel.add(lblemail);
			profilepanel.add(email);
			profilepanel.add(lbladdress);
			profilepanel.add(address);
			profilepanel.add(update);
			profilepanel.setBounds(350, 150, 250, 250);
			profilepanel.setOpaque(false);
			return profilepanel;
		} catch (Exception er) {
			System.out.println("Exception: profileStaffForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to the user that is logged in to
	 * change his/her password.
	 * 
	 * @return JPanel the panel for changing password form
	 */
	private JPanel changePasswordForm() {
		JPanel m = new JPanel();
		JLabel lbloldpassword = new JLabel(" Old Password");
		lbloldpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblnewpassword = new JLabel("New Password");
		lblnewpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField oldpassword = new JPasswordField(15);
		final JTextField newpassword = new JPasswordField(15);
		JButton changepassword = new JButton("Change Password");
		changepassword.setFont(new Font("Arial", Font.PLAIN, 14));
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
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
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful update
						if (messageFromServer.equals("passwordChanged")) {
							System.out.println(messageFromServer + "allagi");
							JLabel message = new JLabel("You have successfully changed your password!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(350, 180, 350, 150);
							getContentPane().add(changePasswordForm());
							getContentPane().add(message);
						}
						// Unsuccessful update
						if (messageFromServer.equals("wrongPassword")) {
							JLabel message = new JLabel("* Wrong password! Give again!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.red);
							message.setBounds(400, 180, 350, 150);
							getContentPane().add(changePasswordForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
					out.flush();
				} catch (Exception er) {
					System.out.println("Exception: changePasswordForm");
				}
			}
		});
		m.add(lbloldpassword);
		m.add(oldpassword);
		m.add(lblnewpassword);
		m.add(newpassword);
		m.add(changepassword);
		m.setBounds(350, 150, 300, 90);
		m.setOpaque(false);
		m.setBorder(BorderFactory.createLineBorder(Color.black));
		return m;
	}

	/**
	 * This function gives the opportunity to new staff of the system to sign up
	 * (as a Doctor, Nurse, Health Visitor, Receptionist, Medical Records or
	 * Management).
	 * 
	 * @return JPanel the panel for signing up form
	 */
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
		JLabel lblrole = new JLabel("        Role          ");
		lblrole.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("      Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("       Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("  Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField username = new JTextField(15);
		final JTextField password = new JPasswordField(15);
		final JTextField name = new JTextField(15);
		final JTextField surname = new JTextField(15);
		final JComboBox role = new JComboBox(roles);
		final JTextField phone = new JTextField(15);
		final JTextField email = new JTextField(15);
		final JTextField address = new JTextField(15);
		JButton signup = new JButton("Sign Up");
		signup.setFont(new Font("Arial", Font.PLAIN, 14));
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
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
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						getContentPane().removeAll();
						// Successful sign up
						if (messageFromServer.equals("success")) {
							JLabel message = new JLabel("You have successfully signed up!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(350, 490, 350, 160);
							getContentPane().add(loginForm());
							getContentPane().add(signupForm());
							getContentPane().add(message);
						}
						// Unsuccessful sign up
						if (messageFromServer.equals("alreadyExists")) {
							JLabel message = new JLabel("* Username already exists! Give a different one!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.red);
							message.setBounds(320, 490, 350, 160);
							getContentPane().add(loginForm());
							getContentPane().add(signupForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: signupForm");
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
		signuppanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return signuppanel;
	}

	/**
	 * This function gives the opportunity to add new patients.
	 * 
	 * @return JPanel the panel for adding patients form
	 */
	private JPanel patientForm() {
		JPanel patientpanel = new JPanel();
		JLabel lblname = new JLabel("        Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsurname = new JLabel("  Surname");
		lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblpassword = new JLabel(" Password");
		lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("      Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("       Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("  Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField name = new JTextField(15);
		final JTextField surname = new JTextField(15);
		final JTextField username = new JTextField(15);
		final JTextField password = new JPasswordField(15);
		final JTextField phone = new JTextField(15);
		final JTextField email = new JTextField(15);
		final JTextField address = new JTextField(15);
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
					// Send data to server
					out.println("addPatient");
					out.println(name.getText());
					out.println(surname.getText());
					out.println(username.getText());
					out.println(password.getText());
					out.println(Integer.parseInt(phone.getText()));
					out.println(email.getText());
					out.println(address.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("patientAdded")) {
							JLabel message = new JLabel("You have successfully added the patient!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setBounds(340, 350, 350, 50);
							message.setForeground(Color.blue);
							getContentPane().add(patientForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: patientForm");
				}
			}
		});
		patientpanel.setBounds(350, 150, 250, 210);
		patientpanel.setOpaque(false);
		patientpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return patientpanel;
	}

	/**
	 * This function gives the opportunity to view and edit patients.
	 * 
	 * @return JPanel the panel for editing patients form
	 */
	private JPanel patientsForm(List<Patient> patients) {
		try {
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
			final JTextField name = new JTextField(patients.get(0).Name);
			final JTextField surname = new JTextField(patients.get(0).Surname);
			final JTextField username = new JTextField(patients.get(0).PatientID);
			username.setEditable(false);
			final JTextField password = new JPasswordField(patients.get(0).Password);
			password.setEditable(false);
			final JTextField phone = new JTextField(Integer.toString(patients.get(0).Phone));
			System.out.println(phone.getText());
			final JTextField email = new JTextField(patients.get(0).Email);
			final JTextField address = new JTextField(patients.get(0).Address);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updatePatient");
						out.println(username.getText());
						out.println(name.getText());
						out.println(surname.getText());
						out.println(Integer.parseInt(phone.getText()));
						out.println(email.getText());
						out.println(address.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("patientUpdated")) {
								getContentPane().add(searchPatientForm());
								// Get data from server
								List<Patient> ls = new ArrayList<Patient>();
								ls = (List<Patient>) inObject.readObject();
								getContentPane().add(patientsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: patientsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deletePatient");
						out.println(username.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("patientDeleted")) {
								getContentPane().add(searchPatientForm());
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: patientsForm");
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
			patientpanel.setBounds(350, 300, 250, 250);
			patientpanel.setOpaque(false);
			return patientpanel;
		} catch (Exception er) {
			System.out.println("Exception: patientsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity update harm risk records for
	 * patients.
	 * 
	 * @return JPanel the panel for editing harm risk records form
	 */
	private JPanel harmRiskForm(List<Patient> patients) {
		try {
			JPanel patientpanel = new JPanel();
			JLabel lblusername = new JLabel("Patient's Username");
			lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblname = new JLabel("Name");
			lblname.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblsurname = new JLabel("Surname");
			lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblincidents = new JLabel("Number Of Incidents");
			lblincidents.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblself = new JLabel("Self Harm Risk");
			lblself.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblothers = new JLabel("Others Harm Risk");
			lblothers.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstatus = new JLabel("Risk Status");
			lblstatus.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldead = new JLabel("Dead Read Only");
			lbldead.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField username = new JTextField(patients.get(0).PatientID);
			username.setEditable(false);
			final JTextField name = new JTextField(patients.get(0).Name);
			name.setEditable(false);
			final JTextField surname = new JTextField(patients.get(0).Surname);
			surname.setEditable(false);
			final JTextField incidents = new JTextField(Integer.toString(patients.get(0).NumOfIncidents));
			incidents.setEditable(false);
			final JTextField self = new JTextField(Integer.toString(patients.get(0).SelfHarmRisk));
			final JTextField others = new JTextField(Integer.toString(patients.get(0).OthersHarmRisk));
			final JTextField status = new JTextField(patients.get(0).RiskStatus);
			final JTextField dead = new JTextField(Integer.toString(patients.get(0).DeadReadOnly));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateHarmRisk");
						out.println(username.getText());
						out.println(Integer.parseInt(self.getText()));
						out.println(Integer.parseInt(others.getText()));
						out.println(status.getText());
						out.println(Integer.parseInt(dead.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("harmRiskUpdated")) {
								getContentPane().add(searchPatientForHarmRiskForm());
								// Get data from server
								List<Patient> ls = new ArrayList<Patient>();
								ls = (List<Patient>) inObject.readObject();
								getContentPane().add(harmRiskForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: harmRiskForm");
					}
				}
			});
			patientpanel.add(lblusername);
			patientpanel.add(username);
			patientpanel.add(lblname);
			patientpanel.add(name);
			patientpanel.add(lblsurname);
			patientpanel.add(surname);
			patientpanel.add(lblincidents);
			patientpanel.add(incidents);
			patientpanel.add(lblself);
			patientpanel.add(self);
			patientpanel.add(lblothers);
			patientpanel.add(others);
			patientpanel.add(lblstatus);
			patientpanel.add(status);
			patientpanel.add(lbldead);
			patientpanel.add(dead);
			patientpanel.add(update);
			patientpanel.setBounds(350, 300, 250, 250);
			patientpanel.setOpaque(false);
			return patientpanel;
		} catch (Exception er) {
			System.out.println("Exception: harmRiskForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search harm risk records.
	 * 
	 * @return JPanel the panel for searching harm risk records form
	 */
	private JPanel searchPatientForHarmRiskForm() {
		JPanel patientpanel = new JPanel();
		JLabel lblid = new JLabel("Search Patient with Username: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchHarmRisk");
					out.println(id.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("harmRiskSearched")) {
							getContentPane().add(searchPatientForHarmRiskForm());
							// Get data from server
							List<Patient> ls = (List<Patient>) inObject.readObject();
							getContentPane().add(harmRiskForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchPatientForHarmRiskForm");
				}
			}
		});
		patientpanel.add(lblid);
		patientpanel.add(id);
		patientpanel.add(search);
		patientpanel.setBounds(50, 150, 900, 150);
		patientpanel.setOpaque(false);
		return patientpanel;
	}

	/**
	 * This function gives the opportunity to search patients.
	 * 
	 * @return JPanel the panel for searching patients form
	 */
	private JPanel searchPatientForm() {
		JPanel patientpanel = new JPanel();
		JLabel lblid = new JLabel("Search Patient with Username: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchPatient");
					out.println(id.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("patientSearched")) {
							getContentPane().add(searchPatientForm());
							// Get data from server
							List<Patient> ls = (List<Patient>) inObject.readObject();
							getContentPane().add(patientsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchPatientForm");
				}
			}
		});
		patientpanel.add(lblid);
		patientpanel.add(id);
		patientpanel.add(search);
		patientpanel.setBounds(50, 150, 900, 150);
		patientpanel.setOpaque(false);
		return patientpanel;
	}

	/**
	 * This function gives the opportunity to add new relatives.
	 * 
	 * @return JPanel the panel for adding relatives form
	 */
	private JPanel relativeForm() {
		JPanel relativepanel = new JPanel();
		JLabel lblpatientusername = new JLabel("Patient's Username");
		lblpatientusername.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblrelationship = new JLabel("           Relationship");
		lblrelationship.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblname = new JLabel("                     Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsurname = new JLabel("                Surname");
		lblsurname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblphone = new JLabel("                    Phone");
		lblphone.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblemail = new JLabel("                     Email");
		lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbladdress = new JLabel("                 Address");
		lbladdress.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientusername = new JTextField(15);
		final JTextField relationship = new JTextField(15);
		final JTextField name = new JTextField(15);
		final JTextField surname = new JTextField(15);
		final JTextField phone = new JTextField(15);
		final JTextField email = new JTextField(15);
		final JTextField address = new JTextField(15);
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
					// Send data to server
					out.println("relativeForm");
					out.println(patientusername.getText());
					out.println(name.getText());
					out.println(surname.getText());
					out.println(Integer.parseInt(phone.getText()));
					out.println(email.getText());
					out.println(address.getText());
					out.println(relationship.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("success")) {
							JLabel message = new JLabel("You have successfully added the relative!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setBounds(340, 470, 350, 50);
							message.setForeground(Color.blue);
							getContentPane().add(relativeForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: relativeForm");
				}
			}
		});
		relativepanel.setBounds(350, 150, 350, 210);
		relativepanel.setOpaque(false);
		relativepanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return relativepanel;
	}

	/**
	 * This function gives the opportunity to view and edit relatives.
	 * 
	 * @return JPanel the panel for editing relatives form
	 */
	private JPanel relativesForm(List<Relative> relatives) {
		try {
			JPanel relativepanel = new JPanel();
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
			final JTextField id = new JTextField(Integer.toString(relatives.get(0).RelativeID));
			id.setEditable(false);
			final JTextField patientid = new JTextField(relatives.get(0).PatientID);
			final JTextField name = new JTextField(relatives.get(0).Name);
			final JTextField surname = new JTextField(relatives.get(0).Surname);
			final JTextField phone = new JTextField(Integer.toString(relatives.get(0).Phone));
			final JTextField email = new JTextField(relatives.get(0).Email);
			final JTextField address = new JTextField(relatives.get(0).Address);
			final JTextField relationship = new JTextField(relatives.get(0).Relationship);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateRelative");
						out.println(Integer.parseInt(id.getText()));
						out.println(patientid.getText());
						out.println(name.getText());
						out.println(surname.getText());
						out.println(Integer.parseInt(phone.getText()));
						out.println(email.getText());
						out.println(address.getText());
						out.println(relationship.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Succesful update
							if (messageFromServer.equals("relativeUpdated")) {
								getContentPane().add(searchRelativeForm());
								// Get data from server
								List<Relative> ls = new ArrayList<Relative>();
								ls = (List<Relative>) inObject.readObject();
								getContentPane().add(relativesForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: relativesForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteRelative");
						out.println(Integer.parseInt(id.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("relativeDeleted")) {
								getContentPane().add(searchRelativeForm());
							}
							revalidate();
							repaint();
							pack();
						}

					} catch (Exception er) {
						System.out.println("Exception: relativesForm");
					}
				}
			});
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
			relativepanel.setBounds(350, 300, 250, 250);
			relativepanel.setOpaque(false);
			return relativepanel;
		} catch (Exception er) {
			System.out.println("Exception: relativesForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search relatives.
	 * 
	 * @return JPanel the panel for searching relatives form
	 */
	private JPanel searchRelativeForm() {
		JPanel relativepanel = new JPanel();
		JLabel lblid = new JLabel("Search Relative with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.flush();
					out.println("searchRelative");
					out.flush();
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("relativeSearched")) {
							getContentPane().add(searchRelativeForm());
							// Get data from server
							List<Relative> ls = (List<Relative>) inObject.readObject();
							getContentPane().add(relativesForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchRelativeForm");
				}
			}
		});
		relativepanel.add(lblid);
		relativepanel.add(id);
		relativepanel.add(search);
		relativepanel.setBounds(50, 150, 900, 150);
		relativepanel.setOpaque(false);
		return relativepanel;
	}

	/**
	 * This function gives the opportunity to add new incidents.
	 * 
	 * @return JPanel the panel for adding incidents form
	 */
	private JPanel incidentForm() {
		String[] types = { "Accidental Treatment Incident", "Deliberate Incident", "Threat" };
		JPanel incidentpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltype = new JLabel("    Incident Type");
		lbltype.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblshortDescription = new JLabel("   Short Description");
		lblshortDescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldate = new JLabel("                       Date");
		JLabel lblspace = new JLabel("                          ");
		JLabel lblspace2 = new JLabel("               ");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientid = new JTextField(15);
		final JComboBox type = new JComboBox(types);
		final JTextField shortDescription = new JTextField(15);
		final JTextArea description = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(description);
		final JDateChooser date = new JDateChooser();
		date.setDateFormatString("yyyy-MM-dd");
		JButton addIncident = new JButton("Add");
		incidentpanel.add(lblpatientid);
		incidentpanel.add(patientid);
		incidentpanel.add(lbltype);
		incidentpanel.add(type);
		incidentpanel.add(lblshortDescription);
		incidentpanel.add(shortDescription);
		incidentpanel.add(lbldescription);
		incidentpanel.add(scrollPane);
		incidentpanel.add(lbldate);
		incidentpanel.add(date);
		incidentpanel.add(lblspace);
		incidentpanel.add(lblspace2);
		incidentpanel.add(addIncident);
		addIncident.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("incidentForm");
					out.println(patientid.getText());
					out.println(type.getSelectedItem().toString());
					out.println(shortDescription.getText());
					out.println(description.getText());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					out.println(dateFormat.format(date.getDate()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("success")) {
							JLabel message = new JLabel("You have successfully added the incident!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(380, 380, 350, 50);
							getContentPane().add(incidentForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}

				} catch (Exception er) {
					System.out.println("Exception: incidentForm");
				}
			}
		});
		incidentpanel.setBounds(350, 150, 350, 230);
		incidentpanel.setOpaque(false);
		incidentpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return incidentpanel;
	}

	/**
	 * This function gives the opportunity to view and edit incidents.
	 * 
	 * @return JPanel the panel for editing incidents form
	 */
	private JPanel incidentsForm(List<Incident> incident) {
		try {
			JPanel incidentpanel = new JPanel();
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
			final JTextField id = new JTextField(Integer.toString(incident.get(0).IncidentID));
			id.setEditable(false);
			final JTextField patientid = new JTextField(incident.get(0).PatientID);
			final JTextField type = new JTextField(incident.get(0).IncidentType);
			final JTextField shortDescription = new JTextField(incident.get(0).ShortDescription);
			final JTextField description = new JTextField(incident.get(0).Description);
			final JTextField date = new JTextField(incident.get(0).Date);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateIncident");
						out.println(Integer.parseInt(id.getText()));
						out.println(patientid.getText());
						out.println(type.getText());
						out.println(shortDescription.getText());
						out.println(description.getText());
						out.println(date.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("incidentUpdated")) {
								getContentPane().add(searchIncidentForm());
								// Get data from server
								List<Incident> ls = new ArrayList<Incident>();
								ls = (List<Incident>) inObject.readObject();
								getContentPane().add(incidentsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}

					} catch (Exception er) {
						System.out.println("Exception: incidentsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteIncident");
						out.println(Integer.parseInt(id.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("incidentDeleted")) {
								getContentPane().add(searchIncidentForm());
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: incidentsForm");
					}
				}
			});
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
			incidentpanel.setBounds(350, 250, 250, 250);
			incidentpanel.setOpaque(false);
			return incidentpanel;
		} catch (Exception er) {
			System.out.println("Exception: incidentsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search incidents.
	 * 
	 * @return JPanel the panel for searching incidents form
	 */
	private JPanel searchIncidentForm() {
		JPanel incidentpanel = new JPanel();
		JLabel lblid = new JLabel("Search Incident with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchIncident");
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("incidentSearched")) {
							getContentPane().add(searchIncidentForm());
							// Get data from server
							List<Incident> ls = (List<Incident>) inObject.readObject();
							getContentPane().add(incidentsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}

				} catch (Exception er) {
					System.out.println("Exception: searchIncidentForm");
				}
			}
		});
		incidentpanel.add(lblid);
		incidentpanel.add(id);
		incidentpanel.add(search);
		incidentpanel.setBounds(50, 150, 900, 150);
		incidentpanel.setOpaque(false);
		return incidentpanel;
	}

	/**
	 * This function gives the opportunity to add new treatments.
	 * 
	 * @return JPanel the panel for adding treatments form
	 */
	private JPanel treatmentForm() {
		JPanel treatmentpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstartDate = new JLabel("                 Start Date");
		lblstartDate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblendDate = new JLabel("                  End Date");
		lblendDate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldiagnosis = new JLabel("              Diagnosis");
		lbldiagnosis.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("   Staff's Username");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientid = new JTextField(15);
		final JDateChooser startDate = new JDateChooser();
		startDate.setDateFormatString("yyyy-MM-dd");
		final JDateChooser endDate = new JDateChooser();
		endDate.setDateFormatString("yyyy-MM-dd");
		final JTextField diagnosis = new JTextField(15);
		final JTextArea description = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(description);
		final JTextField staffid = new JTextField(15);
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
		treatmentpanel.add(scrollPane);
		treatmentpanel.add(lblstaffid);
		treatmentpanel.add(staffid);
		treatmentpanel.add(addTreatment);
		addTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("addTreatment");
					out.println(patientid.getText());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					out.println(dateFormat.format(startDate.getDate()));
					out.println(dateFormat.format(endDate.getDate()));
					out.println(diagnosis.getText());
					out.println(description.getText());
					out.println(staffid.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("treatmentAdded")) {
							JLabel message = new JLabel("You have successfully added the treatment!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(380, 380, 350, 50);
							// Get data from server
							List<Treatment> ls = new ArrayList<Treatment>();
							ls = (List<Treatment>) inObject.readObject();
							getContentPane().add(treatmentMedicationForm(ls));
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: treatmentForm");
				}
			}
		});
		treatmentpanel.setBounds(450, 150, 350, 250);
		treatmentpanel.setOpaque(false);
		treatmentpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return treatmentpanel;
	}

	/**
	 * This function gives the opportunity to add medications to a treatment.
	 * 
	 * @return JPanel the panel for adding medications form
	 */
	private JPanel treatmentMedicationForm(final List<Treatment> treatment) {
		JPanel treatmentpanel = new JPanel();
		JLabel lbltreatmentid = new JLabel("               Treatment's ID          ");
		lbltreatmentid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblmedicationid = new JLabel("             Medication's ID");
		lblmedicationid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldose = new JLabel("                            Dose");
		lbldose.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Dose Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField treatmentid = new JTextField(Integer.toString(treatment.get(0).TreatmentID));
		treatmentid.setEditable(false);
		final JTextField medicationid = new JTextField(15);
		final JTextField dose = new JTextField(15);
		final JTextArea description = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(description);
		JButton addTreatment = new JButton("Add");
		treatmentpanel.add(lbltreatmentid);
		treatmentpanel.add(treatmentid);
		treatmentpanel.add(lblmedicationid);
		treatmentpanel.add(medicationid);
		treatmentpanel.add(lbldose);
		treatmentpanel.add(dose);
		treatmentpanel.add(lbldescription);
		treatmentpanel.add(scrollPane);
		treatmentpanel.add(addTreatment);
		addTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("addTreatmentMedication");
					out.println(treatmentid.getText());
					out.println(medicationid.getText());
					out.println(dose.getText());
					out.println(description.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("treatmentMedicationAdded")) {
							// Get data from server
							String warning = messageFromServer = in.readLine();
							JLabel message = new JLabel("You have successfully added the medication to the treatment!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(360, 380, 400, 50);
							getContentPane().add(treatmentMedicationForm(treatment));
							getContentPane().add(message);
							// Warning issued
							if (!messageFromServer.equals("None")) {
								int dialogButton = JOptionPane.showConfirmDialog(null,
										warning + " Do you want to continue?", "WARNING", JOptionPane.YES_NO_OPTION);
								// Overruled Warning
								if (dialogButton == JOptionPane.YES_OPTION) {
									out.println("overruleWarning");
									out.println(Integer.parseInt(treatmentid.getText()));
									out.println(Integer.parseInt(medicationid.getText()));
								}
								// Accepted Warning
								if (dialogButton == JOptionPane.NO_OPTION) {
									out.println("deleteTreatmentMedication");
									out.println(Integer.parseInt(treatmentid.getText()));
									out.println(Integer.parseInt(medicationid.getText()));
								}
								// Get data from server
								messageFromServer = in.readLine();
							}
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: treatmentMedicationForm");
				}
			}
		});
		treatmentpanel.setBounds(350, 150, 350, 200);
		treatmentpanel.setOpaque(false);
		treatmentpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return treatmentpanel;
	}

	/**
	 * This function gives the opportunity to view and edit treatments.
	 * 
	 * @return JPanel the panel for editing treatments form
	 */
	private JPanel treatmentsForm(List<Treatment> treatment) {
		try {
			JPanel treatmentpanel = new JPanel();
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
			final JTextField id = new JTextField(Integer.toString(treatment.get(0).TreatmentID));
			id.setEditable(false);
			final JTextField patientid = new JTextField(treatment.get(0).PatientID);
			final JTextField startDate = new JTextField(treatment.get(0).StartDate);
			final JTextField endDate = new JTextField(treatment.get(0).EndDate);
			final JTextField diagnosis = new JTextField(treatment.get(0).Diagnosis);
			final JTextField description = new JTextField(treatment.get(0).Description);
			final JTextField staffid = new JTextField(treatment.get(0).StaffID);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateTreatment");
						out.println(Integer.parseInt(id.getText()));
						out.println(patientid.getText());
						out.println(startDate.getText());
						out.println(endDate.getText());
						out.println(diagnosis.getText());
						out.println(description.getText());
						out.println(staffid.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("treatmentUpdated")) {
								getContentPane().add(searchTreatmentForm());
								// Get data from server
								List<Treatment> ls = new ArrayList<Treatment>();
								ls = (List<Treatment>) inObject.readObject();
								getContentPane().add(treatmentsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: treatmentsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteTreatment");
						out.println(Integer.parseInt(id.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("treatmentDeleted")) {
								getContentPane().add(searchTreatmentForm());
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: treatmentsForm");
					}
				}
			});
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
			treatmentpanel.setBounds(350, 250, 250, 250);
			treatmentpanel.setOpaque(false);
			return treatmentpanel;
		} catch (Exception er) {
			System.out.println("Exception: treatmentsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to view a previous treatment.
	 * 
	 * @return JPanel the panel for viewing previous treatment form
	 */
	private JPanel previousTreatmentsForm(List<Treatment> treatment) {
		try {
			JPanel treatmentpanel = new JPanel();
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
			JTextField id = new JTextField(Integer.toString(treatment.get(0).TreatmentID));
			id.setEditable(false);
			JTextField patientid = new JTextField(treatment.get(0).PatientID);
			patientid.setEditable(false);
			JTextField startDate = new JTextField(treatment.get(0).StartDate);
			startDate.setEditable(false);
			JTextField endDate = new JTextField(treatment.get(0).EndDate);
			endDate.setEditable(false);
			JTextField diagnosis = new JTextField(treatment.get(0).Diagnosis);
			diagnosis.setEditable(false);
			JTextField description = new JTextField(treatment.get(0).Description);
			description.setEditable(false);
			JTextField staffid = new JTextField(treatment.get(0).StaffID);
			staffid.setEditable(false);
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
			treatmentpanel.setBounds(200, 200, 250, 250);
			treatmentpanel.setOpaque(false);
			return treatmentpanel;
		} catch (Exception er) {
			System.out.println("Exception: previousTreatmentsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search treatments.
	 * 
	 * @return JPanel the panel for searching treatments form
	 */
	private JPanel searchTreatmentForm() {
		JPanel treatmentpanel = new JPanel();
		JLabel lblid = new JLabel("Search Treatment with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchTreatment");
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("treatmentSearched")) {
							getContentPane().add(searchTreatmentForm());
							// Get data from server
							List<Treatment> ls = (List<Treatment>) inObject.readObject();
							getContentPane().add(treatmentsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchTreatmentForm");
				}
			}
		});
		treatmentpanel.add(lblid);
		treatmentpanel.add(id);
		treatmentpanel.add(search);
		treatmentpanel.setBounds(50, 150, 900, 150);
		treatmentpanel.setOpaque(false);
		return treatmentpanel;
	}

	/**
	 * This function gives the opportunity to search a previous treatment.
	 * 
	 * @return JPanel the panel for searching previous treatment form
	 */
	private JPanel searchPreviousTreatmentForm() {
		JPanel treatmentpanel = new JPanel();
		JLabel lblid = new JLabel("Search Previous Treatment with Patient's Username: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchPreviousTreatment");
					out.println(id.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Succesful search
						if (messageFromServer.equals("previousTreatmentSearched")) {
							getContentPane().add(searchPreviousTreatmentForm());
							// Get data from server
							List<Treatment> ls = (List<Treatment>) inObject.readObject();
							getContentPane().add(treatmentForm());
							getContentPane().add(previousTreatmentsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchPreviousTreatmentForm");
				}
			}
		});
		treatmentpanel.add(lblid);
		treatmentpanel.add(id);
		treatmentpanel.add(search);
		treatmentpanel.setBounds(50, 100, 900, 150);
		treatmentpanel.setOpaque(false);
		return treatmentpanel;
	}

	/**
	 * This function gives the opportunity to renew a treatment.
	 * 
	 * @return JPanel the panel for renewing treatment form
	 */
	private JPanel renewTreatmentsForm(List<Treatment> treatment) {
		try {
			JPanel treatmentpanel = new JPanel();
			JLabel lblid = new JLabel("ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstartDate = new JLabel("Start Date");
			lblstartDate.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblendDate = new JLabel("End Date");
			lblendDate.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblnotes = new JLabel("Notes");
			lblnotes.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstaffid = new JLabel("Staff ID");
			lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField id = new JTextField(Integer.toString(treatment.get(0).TreatmentID));
			id.setEditable(false);
			final JTextField patientid = new JTextField(treatment.get(0).PatientID);
			patientid.setEditable(false);
			final JTextField startDate = new JTextField(treatment.get(0).StartDate);
			final JTextField endDate = new JTextField(treatment.get(0).EndDate);
			final JTextArea notes = new JTextArea(5, 20);
			JScrollPane scrollPane = new JScrollPane(notes);
			final JTextField staffid = new JTextField(treatment.get(0).StaffID);
			JButton renew = new JButton("Renew");
			renew.setFont(new Font("Arial", Font.PLAIN, 14));
			renew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("renewTreatment");
						out.println(Integer.parseInt(id.getText()));
						out.println(patientid.getText());
						out.println(startDate.getText());
						out.println(endDate.getText());
						out.println(notes.getText());
						out.println(staffid.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful addition
							if (messageFromServer.equals("treatmentRenewed")) {
								getContentPane().add(searchRenewTreatmentForm());
								// Get data from server
								List<Treatment> ls = new ArrayList<Treatment>();
								ls = (List<Treatment>) inObject.readObject();
								getContentPane().add(renewTreatmentsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: renewTreatmentsForm");
					}
				}
			});
			treatmentpanel.add(lblid);
			treatmentpanel.add(id);
			treatmentpanel.add(lblpatientid);
			treatmentpanel.add(patientid);
			treatmentpanel.add(lblstartDate);
			treatmentpanel.add(startDate);
			treatmentpanel.add(lblendDate);
			treatmentpanel.add(endDate);
			treatmentpanel.add(lblnotes);
			treatmentpanel.add(scrollPane);
			treatmentpanel.add(lblstaffid);
			treatmentpanel.add(staffid);
			treatmentpanel.add(renew);
			treatmentpanel.setBounds(350, 250, 250, 250);
			treatmentpanel.setOpaque(false);
			return treatmentpanel;
		} catch (Exception er) {
			System.out.println("Exception: renewTreatmentsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search renewed treatments.
	 * 
	 * @return JPanel the panel for searching renewed treatments form
	 */
	private JPanel searchRenewTreatmentForm() {
		JPanel treatmentpanel = new JPanel();
		JLabel lblid = new JLabel("Search Treatment with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchRenewTreatment");
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("renewTreatmentSearched")) {
							getContentPane().add(searchRenewTreatmentForm());
							// Get data from server
							List<Treatment> ls = (List<Treatment>) inObject.readObject();
							getContentPane().add(renewTreatmentsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchRenewTreatmentForm");
				}
			}
		});
		treatmentpanel.add(lblid);
		treatmentpanel.add(id);
		treatmentpanel.add(search);
		treatmentpanel.setBounds(50, 150, 900, 150);
		treatmentpanel.setOpaque(false);
		return treatmentpanel;
	}

	/**
	 * This function gives the opportunity to add new medications.
	 * 
	 * @return JPanel the panel for adding medications form
	 */
	private JPanel medicationForm() {
		JPanel medicationpanel = new JPanel();
		JLabel lblbrand = new JLabel("                     Brand");
		lblbrand.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblname = new JLabel("                     Name");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("            Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbleffects = new JLabel("Known Side Effects");
		lbleffects.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldose = new JLabel("                     Max Dose");
		lbldose.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField brand = new JTextField(15);
		final JTextField name = new JTextField(15);
		final JTextArea description = new JTextArea(5, 20);
		JScrollPane scrollPane1 = new JScrollPane(description);
		final JTextArea effects = new JTextArea(5, 20);
		JScrollPane scrollPane2 = new JScrollPane(effects);
		final JTextField dose = new JTextField(15);
		JButton addMedication = new JButton("Add");
		medicationpanel.add(lblbrand);
		medicationpanel.add(brand);
		medicationpanel.add(lblname);
		medicationpanel.add(name);
		medicationpanel.add(lbldescription);
		medicationpanel.add(scrollPane1);
		medicationpanel.add(lbleffects);
		medicationpanel.add(scrollPane2);
		medicationpanel.add(lbldose);
		medicationpanel.add(dose);
		medicationpanel.add(addMedication);
		addMedication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("addMedication");
					out.println(brand.getText());
					out.println(name.getText());
					out.println(description.getText());
					out.println(effects.getText());
					out.println(dose.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("medicationAdded")) {
							JLabel message = new JLabel("You have successfully added the medication!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(340, 470, 350, 50);
							getContentPane().add(medicationForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: medicationForm");
				}
			}
		});
		medicationpanel.setBounds(350, 150, 370, 290);
		medicationpanel.setOpaque(false);
		medicationpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return medicationpanel;
	}

	/**
	 * This function gives the opportunity to view and edit medications.
	 * 
	 * @return JPanel the panel for editing medications form
	 */
	private JPanel medicationsForm(List<Medication> medication) {
		try {
			JPanel medicationpanel = new JPanel();
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
			JLabel lbldose = new JLabel("                 Max Dose");
			lbldose.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField id = new JTextField(Integer.toString(medication.get(0).MedicationID));
			id.setEditable(false);
			final JTextField brand = new JTextField(medication.get(0).Brand);
			final JTextField name = new JTextField(medication.get(0).Name);
			final JTextField description = new JTextField(medication.get(0).Description);
			final JTextField effects = new JTextField(medication.get(0).KnownSideEffects);
			final JTextField dose = new JTextField(Integer.toString(medication.get(0).MaxDose));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateMedication");
						out.println(Integer.parseInt(id.getText()));
						out.println(brand.getText());
						out.println(name.getText());
						out.println(description.getText());
						out.println(effects.getText());
						out.println(Integer.parseInt(dose.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("medicationUpdated")) {
								getContentPane().add(searchMedicationForm());
								// Get data from server
								List<Medication> ls = new ArrayList<Medication>();
								ls = (List<Medication>) inObject.readObject();
								getContentPane().add(medicationsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: medicationsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteMedication");
						out.println(Integer.parseInt(id.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("medicationDeleted")) {
								getContentPane().add(searchMedicationForm());
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: medicationsForm");
					}
				}
			});
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
			medicationpanel.setBounds(350, 250, 250, 250);
			medicationpanel.setOpaque(false);
			return medicationpanel;
		} catch (Exception er) {
			System.out.println("Exception: medicationsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search medications.
	 * 
	 * @return JPanel the panel for searching medications form
	 */
	private JPanel searchMedicationForm() {
		JPanel medicationpanel = new JPanel();
		JLabel lblid = new JLabel("Search Medication with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchMedication");
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("medicationSearched")) {
							getContentPane().add(searchMedicationForm());
							@SuppressWarnings("unchecked")
							// Get data from server
							List<Medication> ls = (List<Medication>) inObject.readObject();
							getContentPane().add(medicationsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchMedicationForm");
				}
			}
		});
		medicationpanel.add(lblid);
		medicationpanel.add(id);
		medicationpanel.add(search);
		medicationpanel.setBounds(50, 150, 900, 150);
		medicationpanel.setOpaque(false);
		return medicationpanel;
	}

	/**
	 * This function gives the opportunity to add new medication reactions.
	 * 
	 * @return JPanel the panel for adding medication reactions form
	 */
	private JPanel medicationReactionForm() {
		JPanel reactionpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblmedicationid = new JLabel("         Medication ID");
		lblmedicationid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltype = new JLabel("        Reaction Type");
		lbltype.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldescription = new JLabel("Description");
		lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientid = new JTextField(15);
		final JTextField medicationid = new JTextField(15);
		final JTextField type = new JTextField(15);
		final JTextArea description = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(description);
		JButton addReaction = new JButton("Add");
		reactionpanel.add(lblpatientid);
		reactionpanel.add(patientid);
		reactionpanel.add(lblmedicationid);
		reactionpanel.add(medicationid);
		reactionpanel.add(lbltype);
		reactionpanel.add(type);
		reactionpanel.add(lbldescription);
		reactionpanel.add(scrollPane);
		reactionpanel.add(addReaction);
		addReaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("addReaction");
					out.println(patientid.getText());
					out.println(medicationid.getText());
					out.println(type.getText());
					out.println(description.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("reactionAdded")) {
							JLabel message = new JLabel("You have successfully added the reaction!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(340, 470, 370, 50);
							getContentPane().add(medicationReactionForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: medicationReactionForm");
				}
			}
		});
		reactionpanel.setBounds(350, 150, 350, 200);
		reactionpanel.setOpaque(false);
		reactionpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return reactionpanel;
	}

	/**
	 * This function gives the opportunity to view and edit medication
	 * reactions.
	 * 
	 * @return JPanel the panel for editing medication reactions form
	 */
	private JPanel medicationReactionsForm(List<MedicationReaction> reaction) {
		try {
			JPanel reactionpanel = new JPanel();
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblmedicationid = new JLabel("Medication ID");
			lblmedicationid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbltype = new JLabel("Reaction Type");
			lbltype.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lbldescription = new JLabel("Description");
			lbldescription.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField patientid = new JTextField(reaction.get(0).PatientID);
			final JTextField medicationid = new JTextField(Integer.toString(reaction.get(0).MedicationID));
			final JTextField type = new JTextField(reaction.get(0).ReactionType);
			final JTextField description = new JTextField(reaction.get(0).Description);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateReaction");
						out.println(patientid.getText());
						out.println(Integer.parseInt(medicationid.getText()));
						out.println(type.getText());
						out.println(description.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("reactionUpdated")) {
								getContentPane().add(searchMedicationReactionForm());
								// Get data from server
								List<MedicationReaction> ls = new ArrayList<MedicationReaction>();
								ls = (List<MedicationReaction>) inObject.readObject();
								getContentPane().add(medicationReactionsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: medicationReactionsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteReaction");
						out.println(patientid.getText());
						out.println(Integer.parseInt(medicationid.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("reactionDeleted")) {
								getContentPane().add(searchMedicationReactionForm());
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: medicationReactionsForm");
					}
				}
			});
			reactionpanel.add(lblpatientid);
			reactionpanel.add(patientid);
			reactionpanel.add(lblmedicationid);
			reactionpanel.add(medicationid);
			reactionpanel.add(lbltype);
			reactionpanel.add(type);
			reactionpanel.add(lbldescription);
			reactionpanel.add(description);
			reactionpanel.add(update);
			reactionpanel.add(delete);
			reactionpanel.setBounds(350, 250, 250, 250);
			reactionpanel.setOpaque(false);
			return reactionpanel;
		} catch (Exception er) {
			System.out.println("Exception: medicationReactionsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search medication reactions.
	 * 
	 * @return JPanel the panel for searching medication reactions form
	 */
	private JPanel searchMedicationReactionForm() {
		JPanel reactionpanel = new JPanel();
		JLabel lblpid = new JLabel("Search Medication Reaction with Patient's Username: ");
		lblpid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField pid = new JTextField(15);
		JLabel lblmid = new JLabel("and Medication ID: ");
		lblmid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField mid = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchReaction");
					out.println(pid.getText());
					out.println(Integer.parseInt(mid.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("reactionSearched")) {
							getContentPane().add(searchMedicationReactionForm());
							@SuppressWarnings("unchecked")
							// Get data from server
							List<MedicationReaction> ls = (List<MedicationReaction>) inObject.readObject();
							getContentPane().add(medicationReactionsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchMedicationReactionForm");
				}
			}
		});
		reactionpanel.add(lblpid);
		reactionpanel.add(pid);
		reactionpanel.add(lblmid);
		reactionpanel.add(mid);
		reactionpanel.add(search);
		reactionpanel.setBounds(50, 150, 900, 150);
		reactionpanel.setOpaque(false);
		return reactionpanel;
	}

	/**
	 * This function gives the opportunity to add new consultations.
	 * 
	 * @return JPanel the panel for adding consultations form
	 */
	private JPanel consultationForm() {
		JPanel consultationpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("    Staff's Username");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsubject = new JLabel("                   Subject");
		lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldateBooked = new JLabel("            Date Booked");
		lbldateBooked.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbldate = new JLabel("                         Date");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltime = new JLabel("                      Time");
		lbltime.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lbltreatmentid = new JLabel("         Treatment ID");
		lbltreatmentid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientid = new JTextField(15);
		final JTextField staffid = new JTextField(15);
		final JTextField subject = new JTextField(15);
		final JDateChooser dateBooked = new JDateChooser();
		dateBooked.setDateFormatString("yyyy-MM-dd");
		final JDateChooser date = new JDateChooser();
		date.setDateFormatString("yyyy-MM-dd");
		final JTextField time = new JTextField(15);
		final JTextField treatmentid = new JTextField(15);
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
					// Send data to server
					out.println("addConsultation");
					out.println(patientid.getText());
					out.println(staffid.getText());
					out.println(subject.getText());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					out.println(dateFormat.format(dateBooked.getDate()));
					out.println(dateFormat.format(date.getDate()));
					out.println(time.getText());
					out.println(treatmentid.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("consultationAdded")) {
							JLabel message = new JLabel("You have successfully added the consultation!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(380, 380, 350, 50);
							getContentPane().add(consultationForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: consultationForm");
				}
			}
		});
		consultationpanel.setBounds(350, 150, 350, 220);
		consultationpanel.setOpaque(false);
		consultationpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return consultationpanel;
	}

	/**
	 * This function gives the opportunity to view and edit consultations.
	 * 
	 * @return JPanel the panel for editing consultations form
	 */
	private JPanel consultationsForm(List<Consultation> consultation) {
		try {
			JPanel consultationpanel = new JPanel();
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
			final JTextField id = new JTextField(Integer.toString(consultation.get(0).ConsultationID));
			id.setEditable(false);
			final JTextField patientid = new JTextField(consultation.get(0).PatientID);
			final JTextField staffid = new JTextField(consultation.get(0).StaffID);
			final JTextField subject = new JTextField(consultation.get(0).Subject);
			final JTextField dateBooked = new JTextField(consultation.get(0).DateBooked);
			final JTextField date = new JTextField(consultation.get(0).Date);
			final JTextField time = new JTextField(consultation.get(0).Time);
			final JTextField attended = new JTextField(Integer.toString(consultation.get(0).Attended));
			final JTextField updated = new JTextField(Integer.toString(consultation.get(0).MedicalRecordUpdated));
			final JTextField treatmentid = new JTextField(Integer.toString(consultation.get(0).TreatmentID));
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateConsultation");
						out.println(Integer.parseInt(id.getText()));
						out.println(patientid.getText());
						out.println(staffid.getText());
						out.println(subject.getText());
						out.println(dateBooked.getText());
						out.println(date.getText());
						out.println(time.getText());
						out.println(Integer.parseInt(attended.getText()));
						out.println(Integer.parseInt(updated.getText()));
						out.println(Integer.parseInt(treatmentid.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("consultationUpdated")) {
								getContentPane().add(searchConsultationForm());
								// Get data from server
								List<Consultation> ls = new ArrayList<Consultation>();
								ls = (List<Consultation>) inObject.readObject();
								getContentPane().add(consultationsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: consultationsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteConsultation");
						out.println(Integer.parseInt(id.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("consultationDeleted")) {
								getContentPane().add(searchConsultationForm());
							}
							revalidate();
							repaint();
							pack();
						}

					} catch (Exception er) {
						System.out.println("Exception: consultationsForm");
					}
				}
			});
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
			consultationpanel.setBounds(350, 250, 250, 250);
			consultationpanel.setOpaque(false);
			return consultationpanel;
		} catch (Exception er) {
			System.out.println("Exception: consultationsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search consultations.
	 * 
	 * @return JPanel the panel for searching consultations form
	 */
	private JPanel searchConsultationForm() {
		JPanel consultationpanel = new JPanel();
		JLabel lblid = new JLabel("Search Consultation with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchConsultation");
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("consultationSearched")) {
							getContentPane().add(searchConsultationForm());
							@SuppressWarnings("unchecked")
							// Get data from server
							List<Consultation> ls = (List<Consultation>) inObject.readObject();
							getContentPane().add(consultationsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: searchConsultationForm");
				}
			}
		});
		consultationpanel.add(lblid);
		consultationpanel.add(id);
		consultationpanel.add(search);
		consultationpanel.setBounds(50, 150, 900, 150);
		consultationpanel.setOpaque(false);
		return consultationpanel;
	}

	/**
	 * This function gives the opportunity to add new comments.
	 * 
	 * @return JPanel the panel for adding comments form
	 */
	private JPanel commentForm() {
		JPanel commentpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("    Staff's Username");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsubject = new JLabel("                   Subject");
		lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblcomment = new JLabel("        Comment");
		lblcomment.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientid = new JTextField(15);
		final JTextField staffid = new JTextField(15);
		final JTextField subject = new JTextField(15);
		final JTextArea comment = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(comment);
		JButton addComment = new JButton("Add");
		commentpanel.add(lblpatientid);
		commentpanel.add(patientid);
		commentpanel.add(lblstaffid);
		commentpanel.add(staffid);
		commentpanel.add(lblsubject);
		commentpanel.add(subject);
		commentpanel.add(lblcomment);
		commentpanel.add(scrollPane);
		commentpanel.add(addComment);
		addComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("addComment");
					out.println(patientid.getText());
					out.println(staffid.getText());
					out.println(subject.getText());
					out.println(comment.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("commentAdded")) {
							JLabel message = new JLabel("You have successfully added the comment!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(380, 380, 350, 50);
							getContentPane().add(commentForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: commentForm");
				}
			}
		});
		commentpanel.setBounds(350, 150, 350, 210);
		commentpanel.setOpaque(false);
		commentpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return commentpanel;
	}

	/**
	 * This function gives the opportunity to view and edit comments.
	 * 
	 * @return JPanel the panel for editing comments form
	 */
	private JPanel commentsForm(List<Comment> comment) {
		try {
			JPanel commentpanel = new JPanel();
			JLabel lblid = new JLabel("    ID");
			lblid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblpatientid = new JLabel("Patient's Username");
			lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblstaffid = new JLabel("Staff ID");
			lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblsubject = new JLabel("Subject");
			lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel lblcomment = new JLabel("Comment");
			lblcomment.setFont(new Font("Arial", Font.PLAIN, 14));
			final JTextField id = new JTextField(Integer.toString(comment.get(0).CommentID));
			id.setEditable(false);
			final JTextField patientid = new JTextField(comment.get(0).PatientID);
			final JTextField staffid = new JTextField(comment.get(0).StaffID);
			final JTextField subject = new JTextField(comment.get(0).Subject);
			final JTextField comm = new JTextField(comment.get(0).Comment);
			JButton update = new JButton("Update");
			update.setFont(new Font("Arial", Font.PLAIN, 14));
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("updateComment");
						out.println(Integer.parseInt(id.getText()));
						out.println(patientid.getText());
						out.println(staffid.getText());
						out.println(subject.getText());
						out.println(comm.getText());
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful update
							if (messageFromServer.equals("commentUpdated")) {
								getContentPane().add(searchCommentForm());
								// Get data from server
								List<Comment> ls = new ArrayList<Comment>();
								ls = (List<Comment>) inObject.readObject();
								getContentPane().add(commentsForm(ls));
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: commentsForm");
					}
				}
			});
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Arial", Font.PLAIN, 14));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// Send data to server
						out.println("deleteComment");
						out.println(Integer.parseInt(id.getText()));
						// Get data from server
						if ((messageFromServer = in.readLine()) != null) {
							getContentPane().removeAll();
							// Successful deletion
							if (messageFromServer.equals("commentDeleted")) {
								getContentPane().add(searchCommentForm());
							}
							revalidate();
							repaint();
							pack();
						}
					} catch (Exception er) {
						System.out.println("Exception: commentsForm");
					}
				}
			});
			commentpanel.add(lblid);
			commentpanel.add(id);
			commentpanel.add(lblpatientid);
			commentpanel.add(patientid);
			commentpanel.add(lblstaffid);
			commentpanel.add(staffid);
			commentpanel.add(lblsubject);
			commentpanel.add(subject);
			commentpanel.add(lblcomment);
			commentpanel.add(comm);
			commentpanel.add(update);
			commentpanel.add(delete);
			commentpanel.setBounds(350, 250, 250, 250);
			commentpanel.setOpaque(false);
			return commentpanel;
		} catch (Exception er) {
			System.out.println("Exception: commentsForm");
			return null;
		}
	}

	/**
	 * This function gives the opportunity to search comments.
	 * 
	 * @return JPanel the panel for searching comments form
	 */
	private JPanel searchCommentForm() {
		JPanel commentpanel = new JPanel();
		JLabel lblid = new JLabel("Search Comment with ID: ");
		lblid.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField id = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("searchComment");
					out.println(Integer.parseInt(id.getText()));
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful search
						if (messageFromServer.equals("commentSearched")) {
							getContentPane().add(searchCommentForm());
							// Get data from server
							List<Comment> ls = (List<Comment>) inObject.readObject();
							getContentPane().add(commentsForm(ls));
						}
						revalidate();
						repaint();
						pack();
					}

				} catch (Exception er) {
					System.out.println("Exception: searchCommentForm");
				}
			}
		});
		commentpanel.add(lblid);
		commentpanel.add(id);
		commentpanel.add(search);
		commentpanel.setBounds(50, 150, 900, 150);
		commentpanel.setOpaque(false);
		return commentpanel;
	}

	/**
	 * This function gives the opportunity to inform relatives.
	 * 
	 * @return JPanel the panel for informing relatives form
	 */
	private JPanel informRelativesForm() {
		JPanel informpanel = new JPanel();
		JLabel lblpatientid = new JLabel("Patient's Username");
		lblpatientid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblstaffid = new JLabel("    Staff's Username");
		lblstaffid.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblsubject = new JLabel("                  Subject");
		lblsubject.setFont(new Font("Arial", Font.PLAIN, 14));
		JLabel lblmessage = new JLabel("Message");
		lblmessage.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField patientid = new JTextField(15);
		final JTextField staffid = new JTextField(15);
		final JTextField subject = new JTextField(15);
		final JTextArea msg = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(msg);
		JButton addInform = new JButton("Inform");
		informpanel.add(lblpatientid);
		informpanel.add(patientid);
		informpanel.add(lblstaffid);
		informpanel.add(staffid);
		informpanel.add(lblsubject);
		informpanel.add(subject);
		informpanel.add(lblmessage);
		informpanel.add(scrollPane);
		informpanel.add(addInform);
		addInform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Send data to server
					out.println("informRelatives");
					out.println(patientid.getText());
					out.println(staffid.getText());
					out.println(subject.getText());
					out.println(msg.getText());
					// Get data from server
					if ((messageFromServer = in.readLine()) != null) {
						System.out.println(messageFromServer);
						getContentPane().removeAll();
						// Successful addition
						if (messageFromServer.equals("relativesInformed")) {
							JLabel message = new JLabel("You have successfully informed the relatives!");
							message.setFont(new Font("Arial", Font.PLAIN, 14));
							message.setForeground(Color.blue);
							message.setBounds(380, 380, 350, 50);
							getContentPane().add(informRelativesForm());
							getContentPane().add(message);
						}
						revalidate();
						repaint();
						pack();
					}
				} catch (Exception er) {
					System.out.println("Exception: informRelativesForm");
				}
			}
		});
		informpanel.setBounds(350, 150, 320, 200);
		informpanel.setOpaque(false);
		informpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return informpanel;
	}

	/**
	 * This function presents consultation report for Attended Daily
	 * Consultations (attended=true and daily!=null) Attended General
	 * Consultations (attended=true and daily==null), Non Attended Daily
	 * Consultations (attended=false and daily!=null) and Non Attended General
	 * Consultations (attended=false and daily==null) according to parameters.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane consultationReports(int attent, String daily) throws ClassNotFoundException {
		out.println("consultationReport");
		out.println(attent);
		out.println(daily);
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("report")) {

					List<ConsultationReport> ls = (List<ConsultationReport>) inObject.readObject();

					ConsultationReport cr = new ConsultationReport();
					try {
						JTable results = new JTable(cr.buildTableModel(ls, cr.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * This function presents consultation report for Non Updated Daily Medical
	 * Records (daily!=null) and Non Updated General Medical Records
	 * (daily==null) according to parameters.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane consultationReportMedical(String daily) throws ClassNotFoundException {
		out.println("consultationReportMedical");
		out.println(daily);
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("medicalReport")) {
					List<ConsultationReport> ls = (List<ConsultationReport>) inObject.readObject();
					ConsultationReport cr = new ConsultationReport();
					try {
						JTable results = new JTable(cr.buildTableModel(ls, cr.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function presents patient report for Changes of Patient Profiles(if
	 * option==1 and name==null, Patients with specific Condition (if option==2
	 * and name!=null and Patients with specific Treatment/Medication (if
	 * option==3 and name!=null) according to parameters.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane patientReport(int option, String name) throws ClassNotFoundException {
		out.println("patientReport");
		out.println(option);
		out.println(name);
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("patientReport")) {
					List<Patient> ls = (List<Patient>) inObject.readObject();
					Patient pat = new Patient();
					try {
						JTable results = new JTable(pat.buildTableModel(ls, pat.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function presents Attendance report which shows the number of
	 * patients who attended the clinic for each day.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane AttendanceReport() throws ClassNotFoundException {
		out.println("attendanceReport");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("attendanceReport")) {
					List<AttendanceReport> ls = (List<AttendanceReport>) inObject.readObject();
					AttendanceReport ar = new AttendanceReport();
					try {
						JTable results = new JTable(ar.buildTableModel(ls, ar.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function presents Condition report which shows the number of
	 * patients who were marked with the same diagnosis/condition, for each one
	 * of them.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane ConditionReport() throws ClassNotFoundException {
		out.println("conditionReport");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("conditionReport")) {
					List<ConditionReport> ls = (List<ConditionReport>) inObject.readObject();
					ConditionReport conR = new ConditionReport();
					try {
						JTable results = new JTable(conR.buildTableModel(ls, conR.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function presents Medication report which shows the number of
	 * patients who were prescribed with the same medication (of the same
	 * brand), for each one of them.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane MedicationReport() throws ClassNotFoundException {
		out.println("medicationReport");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("medicationReport")) {
					List<MedicationReport> ls = (List<MedicationReport>) inObject.readObject();
					MedicationReport mr = new MedicationReport();
					try {
						JTable results = new JTable(mr.buildTableModel(ls, mr.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function presents Medication Prescription report which shows a
	 * summary of the medication prescriptions from all treatment records of the
	 * clinic.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane medicationPrescription() throws ClassNotFoundException {
		out.println("medicationPrescription");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("medicationPrescription")) {
					List<MedicationPrescription> ls = (List<MedicationPrescription>) inObject.readObject();
					MedicationPrescription mr = new MedicationPrescription();
					try {
						JTable results = new JTable(mr.buildTableModel(ls, mr.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 150, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function gives the opportunity to search warning letters.
	 * 
	 * @return JPanel the panel for searching warning letters form
	 */
	private JPanel searchWarningLetters() {
		JPanel namePanel = new JPanel();
		JLabel lblname = new JLabel("Search: ");
		lblname.setFont(new Font("Arial", Font.PLAIN, 14));
		final JTextField value = new JTextField(15);
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getContentPane().add(warningLetters(value.getText()));

				} catch (Exception er) {
					// Ignore the error and continues
				}
				revalidate();
				repaint();
				pack();
			}
		});
		namePanel.add(lblname);
		namePanel.add(value);
		namePanel.add(search);
		namePanel.setBounds(350, 130, 250, 100);
		namePanel.setOpaque(false);
		namePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return namePanel;
	}

	/**
	 * This function presents warning letters.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	private JScrollPane warningLetters(String id) throws ClassNotFoundException {
		out.println("warningLetters");
		out.println(id);
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("warningLetters")) {
					List<WarningLetter> ls = (List<WarningLetter>) inObject.readObject();
					WarningLetter wl = new WarningLetter();
					try {
						getContentPane().add(searchWarningLetters());
						JTable results = new JTable(wl.buildTableModel(ls, wl.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all patients with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllPatients() throws ClassNotFoundException {

		out.println("searchPatient");
		out.println("null");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();

				if (messageFromServer.equals("patientSearched")) {
					List<Patient> ls = (List<Patient>) inObject.readObject();
					Patient p = new Patient();
					try {
						getContentPane().add(searchPatientForm());
						JTable results = new JTable(p.buildTableModel(ls, p.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all relatives with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllRelatives() throws ClassNotFoundException {
		out.println("searchRelative");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("relativeSearched")) {
					List<Relative> ls = (List<Relative>) inObject.readObject();
					Relative r = new Relative();
					try {
						getContentPane().add(searchRelativeForm());
						JTable results = new JTable(r.buildTableModel(ls, r.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all incidents with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllIncidents() throws ClassNotFoundException {
		out.println("searchIncident");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("incidentSearched")) {
					List<Incident> ls = (List<Incident>) inObject.readObject();
					Incident i = new Incident();
					try {
						getContentPane().add(searchIncidentForm());
						JTable results = new JTable(i.buildTableModel(ls, i.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all treatments with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllTreatments() throws ClassNotFoundException {
		out.println("searchTreatment");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("treatmentSearched")) {
					List<Treatment> ls = (List<Treatment>) inObject.readObject();
					Treatment i = new Treatment();
					try {
						getContentPane().add(searchTreatmentForm());
						JTable results = new JTable(i.buildTableModel(ls, i.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all medications with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllMedications() throws ClassNotFoundException {
		out.println("searchMedication");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("medicationSearched")) {
					List<Medication> ls = (List<Medication>) inObject.readObject();
					Medication m = new Medication();
					try {
						getContentPane().add(searchMedicationForm());
						JTable results = new JTable(m.buildTableModel(ls, m.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all medications and reactions with the the selection
	 * of search specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllMedicationReactions() throws ClassNotFoundException {
		out.println("searchReaction");
		out.println("null");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("reactionSearched")) {
					List<MedicationReaction> ls = (List<MedicationReaction>) inObject.readObject();
					MedicationReaction mr = new MedicationReaction();
					try {
						getContentPane().add(searchMedicationReactionForm());
						JTable results = new JTable(mr.buildTableModel(ls, mr.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all consultations with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllConsultations() throws ClassNotFoundException {
		out.println("searchConsultation");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("consultationSearched")) {
					List<Consultation> ls = (List<Consultation>) inObject.readObject();
					Consultation con = new Consultation();
					try {
						getContentPane().add(searchConsultationForm());
						JTable results = new JTable(con.buildTableModel(ls, con.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function shows all comments with the the selection of search
	 * specific one.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane printAllComments() throws ClassNotFoundException {
		out.println("searchComment");
		out.println("-1");
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("commentSearched")) {
					List<Comment> ls = (List<Comment>) inObject.readObject();
					Comment com = new Comment();
					try {
						getContentPane().add(searchCommentForm());
						JTable results = new JTable(com.buildTableModel(ls, com.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function gives the opportunity to search appointments for a specific
	 * date.
	 * 
	 * @return JPanel the panel for searching appointments form
	 */
	private JPanel searchTodaysAppointment() {
		JPanel datePanel = new JPanel();
		JLabel lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Arial", Font.PLAIN, 14));
		final JDateChooser date = new JDateChooser();
		date.setDateFormatString("yyyy-MM-dd");
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.PLAIN, 14));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					getContentPane().add(todaysAppointments(dateFormat.format(date.getDate())));
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				revalidate();
				repaint();
				pack();
			}
		});
		datePanel.add(lbldate);
		datePanel.add(date);
		datePanel.add(search);
		datePanel.setBounds(350, 130, 250, 100);
		datePanel.setOpaque(false);
		return datePanel;

	}

	/**
	 * This function shows all appointments with the the selection of search
	 * specific date 's appointments.
	 * 
	 * @return JScrollPane the panel for JTable with results.
	 */
	@SuppressWarnings("unchecked")
	private JScrollPane todaysAppointments(String date) throws ClassNotFoundException {
		out.println("todaysAppointments");
		out.println(date);
		try {
			if ((messageFromServer = in.readLine()) != null) {
				System.out.println(messageFromServer);
				getContentPane().removeAll();
				if (messageFromServer.equals("todaysAppointments")) {
					List<Consultation> ls = (List<Consultation>) inObject.readObject();
					Consultation con = new Consultation();
					try {
						getContentPane().add(searchTodaysAppointment());
						JTable results = new JTable(con.buildTableModel(ls, con.columnNames));
						JScrollPane resultspanel = new JScrollPane(results);
						resultspanel.setBounds(50, 250, 900, 300);
						return resultspanel;

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		final GUI frame = new GUI();
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
