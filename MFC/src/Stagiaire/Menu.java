package Stagiaire;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 128, 0));
		frame.setBounds(0, -25, 1132, 679);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\delpierro\\Documents\\NetBeans_image\\entreprise-reduce.jpg"));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(407, 83, 674, 472);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btndeconnexion = new JButton("Se déconnecter");
		btndeconnexion.setBackground(new Color(255, 0, 0));
		btndeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login login = new Login();
				login.getLogin().setVisible(true);
				
			}
		});
		btndeconnexion.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		btndeconnexion.setBounds(105, 458, 292, 64);
		frame.getContentPane().add(btndeconnexion);
		
		JButton btnStagiaires = new JButton("Formateurs");
		btnStagiaires.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Formateurs formateurs = new Formateurs();
				formateurs.getFormateurs().setVisible(true);
			}
		});
		btnStagiaires.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		btnStagiaires.setBackground(new Color(128, 255, 0));
		btnStagiaires.setBounds(105, 189, 292, 64);
		frame.getContentPane().add(btnStagiaires);
		
		JButton btnStagiaires_1 = new JButton("Stagiaires");
		btnStagiaires_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Stagiaires stagiaires = new Stagiaires();
				stagiaires.getStagiaires().setVisible(true);
			}
		});
		btnStagiaires_1.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		btnStagiaires_1.setBackground(new Color(128, 255, 0));
		btnStagiaires_1.setBounds(105, 263, 292, 64);
		frame.getContentPane().add(btnStagiaires_1);
		
		JButton btnFormations = new JButton("Formations");
		btnFormations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Formations formations = new Formations();
				formations.getFormations().setVisible(true);
			}
		});
		btnFormations.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		btnFormations.setBackground(new Color(128, 255, 0));
		btnFormations.setBounds(105, 115, 292, 64);
		frame.getContentPane().add(btnFormations);
	}
	
	// Méthode pour récupérer le JFrame associé à la classe Menu
    public JFrame getFrame() {
        return frame;
    }

}
