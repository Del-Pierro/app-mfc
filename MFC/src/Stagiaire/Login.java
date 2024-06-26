package Stagiaire;

import java.awt.EventQueue;


import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends javax.swing.JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtnom;
	private JPasswordField txtmdp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}
	
	// Méthode pour récupérer le JFrame associé à la classe Login
    public JFrame getLogin() {
        return frame;
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(new Color(128, 128, 0));
		frame.setBounds(0, -20, 1104, 652);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("centre de formation MFC");
		lblNewLabel.setBackground(new Color(0, 255, 0));
		lblNewLabel.setFont(new Font("Snap ITC", Font.BOLD, 30));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(334, 118, 468, 58);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Connectez-vous");
		lblNewLabel_1.setFont(new Font("Stencil", Font.BOLD, 25));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(253, 207, 279, 32);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\delpierro\\Documents\\NetBeans_image\\school-prime.jfif"));
		lblNewLabel_2.setBounds(223, 227, 351, 197);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Mot de passe");
		lblNewLabel_3.setFont(new Font("Agency FB", Font.BOLD, 23));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(636, 317, 215, 43);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtnom = new JTextField();
		txtnom.setHorizontalAlignment(SwingConstants.CENTER);
		txtnom.setBounds(636, 268, 215, 39);
		frame.getContentPane().add(txtnom);
		txtnom.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Nom d'utilisateur");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setFont(new Font("Agency FB", Font.BOLD, 23));
		lblNewLabel_3_1.setBounds(636, 227, 215, 43);
		frame.getContentPane().add(lblNewLabel_3_1);
		
		JButton btnconnexion = new JButton("Connexion");
		btnconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
			        
		            String nomUtilisateur = txtnom.getText();
		            String mdpUtilisateur = txtmdp.getText();
		            
		            if (nomUtilisateur.equals("") && mdpUtilisateur.equals("")){
		                JOptionPane.showMessageDialog(null,"veuiller remplir les champs","Message",JOptionPane.OK_OPTION);
		            }
		            else if (nomUtilisateur.equals("admin") && mdpUtilisateur.equals("1234")){
		            	frame.dispose();
		                Menu menu = new Menu();
		                menu.getFrame().setVisible(true);
		            }
		            else{
		                JOptionPane.showMessageDialog(null,"Mot de passe ou\nNom Utilisateur sont incorrect","Message", JOptionPane.OK_OPTION);
		            }
		        } catch(Exception ex){
		            ex.addSuppressed(ex);
		        }
			}
		});
		btnconnexion.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 24));
		btnconnexion.setBackground(new Color(192, 192, 192));
		btnconnexion.setBounds(636, 418, 215, 43);
		frame.getContentPane().add(btnconnexion);
		
		txtmdp = new JPasswordField();
		txtmdp.setHorizontalAlignment(SwingConstants.CENTER);
		txtmdp.setBounds(636, 356, 215, 43);
		frame.getContentPane().add(txtmdp);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnAnnuler.setForeground(new Color(255, 0, 0));
		btnAnnuler.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 24));
		btnAnnuler.setBackground(Color.LIGHT_GRAY);
		btnAnnuler.setBounds(292, 418, 215, 43);
		frame.getContentPane().add(btnAnnuler);
	}
}
