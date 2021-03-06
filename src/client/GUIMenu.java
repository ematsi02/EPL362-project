/**
 * This class is a utilities class for creating the Menu. This class is used by
 * the GUI class, which calls specific methods for creating the menu according
 * to the user type.
 * 
 * These are the different menus that can be created: menuForPatient,
 * menuForDoctor, menuForClinicalStaff, menuForReceptionist,
 * menuForMedicalRecords, menuForManagement.
 * 
 * @author Sotia Gregoriou, Elena Matsi, Erasmia Shimitra
 *
 */

package client;

// Libraries
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUIMenu {

	private ActionListener al = null;

	/**
	 * Constructor of GUI Menu class
	 * 
	 * @param ActionListener
	 *            : the GUI object, from GUI class
	 * 
	 * @return JMenu : a menu element about General Reports
	 */
	public GUIMenu(ActionListener listener) {
		this.al = listener;
	}

	/**
	 * Account Menu Element. This menu is included for all the users of the
	 * system. It offers options that have to do with the user's profile
	 * information.
	 * 
	 * @return JMenu : a menu element about Account
	 */
	private JMenu AccountMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Account");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("View/Edit Profile");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Change Password");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Log Out");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Consultation/Treatment Menu Element. Allows the user to add, view,
	 * search, edit or delete consultation or treatment information about a
	 * patient.
	 * 
	 * @return JMenu : a menu element about Consultation/Treatment
	 */
	private JMenu ConsultationTreatmentMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Consultation/Treatment");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add New Consultation");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Consultation");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Add New Treatment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Treatment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Comments, Incidents, Harm Risk and Warning Letters Menu Element. Allows
	 * the user to add, view, search, edit or delete incidents and comments
	 * about a patient, as well as edit a patient's harm risk record.
	 * 
	 * @return JMenu : a menu element about Comments, Incidents, Harm Risk and
	 *         Warning Letters
	 */
	private JMenu CommentsIncidentsMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Comments/Harm Risk");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add New Comment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Comment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Add New Incident");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Incident");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Update Harm Risk Record");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View Warning Letters");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Patients Menu Element. Allows the user to add, view, search, edit or
	 * delete patient records.
	 * 
	 * @return JMenu : a menu element about Patients
	 */
	private JMenu PatientsMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Patient");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add New Patient");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Patient");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		// menuItem = new JMenuItem("Import/Export Patient Record");
		// menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		// menuItem.addActionListener(al);
		// menu.add(menuItem);
		return menu;
	}

	/**
	 * Relatives Menu Element. Allows the user to add, view, search, edit or
	 * delete relatives of a patient.
	 * 
	 * @return JMenu : a menu element about Relatives
	 */
	private JMenu RelativesMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Relatives");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add New Relative");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Relative");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Inform Relative");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Medication Menu Element. Allows the user to add, view, search, edit or
	 * delete information about different medication records.
	 * 
	 * @return JMenu : a menu element about Medication
	 */
	private JMenu MedicationMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Medication");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add New Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Add Medication Reaction");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Medication Reaction");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Appointments Menu Element. Allows the user to add, view, search, edit or
	 * delete information about different appointments.
	 * 
	 * @return JMenu : a menu element about Appointments
	 */
	private JMenu AppointmentsMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Appointments");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Add New Appointment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View/Search Appointment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("View Today's Appointments");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Repeat Treatment Menu Element. Allows the user to repeat a treatment of a
	 * patient, after request.
	 * 
	 * @return JMenu : a menu element about Repeat Treatment
	 */
	private JMenu RepeatTreatmentMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Repeat Prescription");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Search Treatment");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Consultation Reports Menu Element. Allows the medical records user to see
	 * reports (daily or general) about consultation, appointments and
	 * treatments.
	 * 
	 * @return JMenu : a menu element about Consultation Reports
	 */
	private JMenu ConsultationReportsMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Consultation Reports");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Attended Daily Consultations");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Attended General Consultations");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Non Attended Daily Consultations");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Non Attended General Consultations");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Non Updated Daily Medical Records");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Non Updated General Medical Records");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Patient Reports Menu Element. Allows the medical records user to see
	 * reports (daily or general) about patient records and information.
	 * 
	 * @return JMenu : a menu element about Patient Reports
	 */
	private JMenu PatientReportsMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Patient Reports");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Changes of Patient Profiles");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Patients with specific Condition");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Patients with specific Treatment/Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * General Reports Menu Element. Allows the health service management user
	 * to see general reports.
	 * 
	 * @return JMenu : a menu element about General Reports
	 */
	private JMenu GeneralReportsMenu() {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("General Reports");
		menu.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem = new JMenuItem("Number of Patients Attended");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Number of Patients by Condition");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Number of Patients by Treatment/Medication");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem = new JMenuItem("Medication Prescriptions Summary");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		menuItem.addActionListener(al);
		menu.add(menuItem);
		return menu;
	}

	/**
	 * Menu for the Patient. This method generates the menu that a patient can
	 * see. The patient can only see the user profile and change some data.
	 * 
	 * @return JMenuBar : the Menu for Patient users
	 */
	public JMenuBar menuForPatient() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = AccountMenu();
		JMenuItem menuItem;
		// menuItem = new JMenuItem("View My Appointments");
		// menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
		// menuItem.addActionListener(al);
		// menu.add(menuItem);
		menuBar.add(menu);
		return menuBar;
	}

	/**
	 * Menu for Doctor. This method generates the menu that a user with the role
	 * of "Doctor" can see, when logged in.
	 * 
	 * @return JMenuBar : the Menu for Doctor users
	 */
	public JMenuBar menuForDoctor() {
		JMenuBar menuBar = new JMenuBar();
		// Options for Clinical Staff, except the MedicationMenu (only Doctors)
		menuBar.add(AccountMenu());
		menuBar.add(ConsultationTreatmentMenu());
		menuBar.add(CommentsIncidentsMenu());
		menuBar.add(PatientsMenu());
		menuBar.add(RelativesMenu());
		menuBar.add(MedicationMenu());
		return menuBar;
	}

	/**
	 * Menu for the rest Clinical Staff. This method generates the menu that a
	 * user with the roles of "Nurse" or "Health Visitor" can see, when logged
	 * in. The menu concerns all clinical staff, except doctors.
	 * 
	 * @return JMenuBar : the Menu for Clinical Staff users
	 */
	public JMenuBar menuForClinicalStaff() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(AccountMenu());
		menuBar.add(ConsultationTreatmentMenu());
		menuBar.add(CommentsIncidentsMenu());
		menuBar.add(PatientsMenu());
		menuBar.add(RelativesMenu());
		return menuBar;
	}

	/**
	 * Menu for Receptionist. This method generates the menu that a user with
	 * the role of "Receptionist" can see, when logged in.
	 * 
	 * @return JMenuBar : the Menu for Receptionist users
	 */
	public JMenuBar menuForReceptionist() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(AccountMenu());
		menuBar.add(AppointmentsMenu());
		menuBar.add(RepeatTreatmentMenu());
		return menuBar;
	}

	/**
	 * Menu for Medical Records. This method generates the menu that a user with
	 * the role of "Medical Records" can see, when logged in.
	 * 
	 * @return JMenuBar : the Menu for Medical Records users
	 */
	public JMenuBar menuForMedicalRecords() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(AccountMenu());
		menuBar.add(ConsultationReportsMenu());
		menuBar.add(PatientReportsMenu());
		menuBar.add(GeneralReportsMenu());
		return menuBar;
	}

	/**
	 * Menu for Management. This method generates the menu that a user with the
	 * role of "Management" can see, when logged in.
	 * 
	 * @return JMenuBar : the Menu for Management users
	 */
	public JMenuBar menuForManagement() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(AccountMenu());
		menuBar.add(GeneralReportsMenu());
		return menuBar;
	}
}
