package Stagiaire;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

public class Formateurs extends javax.swing.JFrame{

	private JFrame frame;
	private JTextField adresseInter;
	private JTextField mailInter;
	private JTextField specialiteInter;
	private JTextField telInter;
	private JTextField prenomInter;
	private JTextField txtNomInter;
	private JTextField txtIdFOR;
	private JTable table;
	
	// Déclarer la connexion
    Connection con;
    
    // Déclarer la déclaration préparée
    PreparedStatement pst;
    
    // Déclarer le résultat de la requête
    ResultSet rs;
    
    // Déclarer le modèle de tableau
    DefaultTableModel model;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Formateurs window = new Formateurs();
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
	public Formateurs() {
		initialize();
		Table();
	}
	
	public void Table() {
		String []formateur = {"IdFOR" , "NomInter", "PrenomInter", "TelInter", "AdresseInter","MailInter","SpecialiteInter"};
		String []Montrer = new String[7];
		
		model = new DefaultTableModel(null,formateur);
		
		String sql = "select * from intervenantsformateurs";
		try {
			Connect();
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Montrer[0] = rs.getString("IdFOR");
				Montrer[1] = rs.getString("NomInter");
				Montrer[2] = rs.getString("PrenomInter");
				Montrer[3] = rs.getString("TelInter");
				Montrer[4] = rs.getString("AdresseInter");
				Montrer[5] = rs.getString("MailInter");
				Montrer[6] = rs.getString("SpecialiteInter");
				model.addRow(Montrer);
			}
			table.setModel(model);
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Méthode pour récupérer le JFrame associé à la classe Formateurs
    public JFrame getFormateurs() {
        return frame;
    }
	
	public void Connect () {
		try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Établir la connexion à la base de données
            con = DriverManager.getConnection("jdbc:mysql://localhost/mfc", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1138, 681);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(128, 255, 128));
		frame.getContentPane().setLayout(null);
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			// Méthode pour enregistrer une formation
			public void actionPerformed(ActionEvent e) {
				try {
					
		            Connect();
		            pst = con.prepareStatement("INSERT INTO intervenantsformateurs (IdFOR , NomInter, PrenomInter, TelInter, AdresseInter,MailInter,SpecialiteInter) VALUES (?, ?, ?, ?, ?,?,?)");
		            pst.setString(1, txtIdFOR.getText());
		            pst.setString(2, txtNomInter.getText());
		            pst.setString(3, prenomInter.getText());
		            pst.setString(4, telInter.getText());
		            pst.setString(5, specialiteInter.getText());
		            pst.setString(6, mailInter.getText());
		            pst.setString(7, adresseInter.getText());
		            pst.executeUpdate();
		            con.close();
		            JOptionPane.showMessageDialog(null, "Formateur enregistrée");
		            Table();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage());
		        }
			}
		});
		btnEnregistrer.setBackground(new Color(0, 128, 128));
		btnEnregistrer.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnEnregistrer.setBounds(139, 526, 223, 48);
		frame.getContentPane().add(btnEnregistrer);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			// Méthode pour modifier une formation
			public void actionPerformed(ActionEvent e) {
				// Vérifie si une ligne est sélectionnée dans le tableau
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un formateur à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Récupère l'identifiant de la formation sélectionnée
		        String selectedId = (String) table.getValueAt(selectedRow, 0);

		        // Vérifie que les champs obligatoires sont remplis
		        
		        if (txtNomInter.getText().isEmpty() || prenomInter.getText().isEmpty() || telInter.getText().isEmpty() || specialiteInter.getText().isEmpty() || mailInter.getText().isEmpty() || adresseInter.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        try {
		            Connect();
		            pst = con.prepareStatement("UPDATE intervenantsformateurs SET NomInter=?, PrenomInter=?, TelInter=?, AdresseInter=?, MailInter=?, SpecialiteInter=? WHERE IdFOR=?");
		            pst.setString(1, txtNomInter.getText());
		            pst.setString(2, prenomInter.getText());
		            // Convertit le telephone en un entier avant de l'insérer dans la base de données
		            int telInterValue = Integer.parseInt(telInter.getText());
		            pst.setInt(3, telInterValue);
		            pst.setString(4, specialiteInter.getText());
		            pst.setString(5, mailInter.getText());
		            pst.setString(6, adresseInter.getText());
		            pst.setString(7, selectedId);
		            pst.executeUpdate();
		            con.close();
		            JOptionPane.showMessageDialog(null, "Formateur modifié avec succès.");
		            Table(); // Met à jour le tableau après la modification
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Veuillez saisir un numéro de téléphone valide (nombre entier).", "Erreur", JOptionPane.ERROR_MESSAGE);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erreur lors de la modification du formateur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnModifier.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnModifier.setBackground(new Color(0, 128, 128));
		btnModifier.setBounds(443, 526, 223, 48);
		frame.getContentPane().add(btnModifier);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			// Méthode pour supprimer une formation
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // Ferme la fenêtre de l'application
			}
		});
		btnQuitter.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnQuitter.setBackground(new Color(0, 128, 128));
		btnQuitter.setBounds(443, 584, 223, 48);
		frame.getContentPane().add(btnQuitter);
		
		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1.setBounds(0, 132, 129, 48);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Prenom");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(10, 190, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		prenomInter = new JTextField();
		prenomInter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		prenomInter.setColumns(10);
		prenomInter.setBounds(139, 190, 255, 48);
		frame.getContentPane().add(prenomInter);
		
		JLabel lblNewLabel_1_6 = new JLabel("Mail");
		lblNewLabel_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_6.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_6.setBounds(0, 248, 129, 48);
		frame.getContentPane().add(lblNewLabel_1_6);
		
		mailInter = new JTextField();
		mailInter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mailInter.setColumns(10);
		mailInter.setBounds(139, 248, 255, 48);
		frame.getContentPane().add(mailInter);
		
		JLabel lblNewLabel_1_7 = new JLabel("spécialité");
		lblNewLabel_1_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_7.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_7.setBounds(10, 306, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_7);
		
		specialiteInter = new JTextField();
		specialiteInter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		specialiteInter.setColumns(10);
		specialiteInter.setBounds(139, 306, 255, 48);
		frame.getContentPane().add(specialiteInter);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Téléphone");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_1_1.setBounds(0, 422, 129, 48);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		telInter = new JTextField();
		telInter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		telInter.setColumns(10);
		telInter.setBounds(139, 422, 255, 48);
		frame.getContentPane().add(telInter);
		
		JLabel lblNewLabel_1_2 = new JLabel("Adresse");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(0, 364, 129, 48);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		txtNomInter = new JTextField();
		txtNomInter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNomInter.setColumns(10);
		txtNomInter.setBounds(139, 132, 255, 48);
		frame.getContentPane().add(txtNomInter);
		
		JLabel labelidf = new JLabel("Id FOR");
		labelidf.setHorizontalAlignment(SwingConstants.CENTER);
		labelidf.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		labelidf.setBounds(10, 74, 119, 48);
		frame.getContentPane().add(labelidf);
		
		txtIdFOR = new JTextField();
		txtIdFOR.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtIdFOR.setColumns(10);
		txtIdFOR.setBounds(139, 74, 255, 48);
		frame.getContentPane().add(txtIdFOR);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Formateurs");
		lblNewLabel_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3_1.setFont(new Font("Wide Latin", Font.BOLD, 30));
		lblNewLabel_1_3_1.setBounds(357, 10, 386, 71);
		frame.getContentPane().add(lblNewLabel_1_3_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(404, 74, 699, 396);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setToolTipText("");
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
		    new Object[][] {},
		    new String[] {
		        "IdFOR", "NomInter", "PrenomInter", "TelInter", "AdresseInter","MailInter","SpecialiteInter"
		    }
		));
		scrollPane.setViewportView(table);
		
		adresseInter = new JTextField();
		adresseInter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		adresseInter.setColumns(10);
		adresseInter.setBounds(139, 364, 255, 48);
		frame.getContentPane().add(adresseInter);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un stagiaire à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Récupère l'identifiant de la formation sélectionnée
		        String selectedId = (String) table.getValueAt(selectedRow, 0);

		        int dialogResult = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce formateur ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
		        if (dialogResult == JOptionPane.YES_OPTION) {
		            try {
		                Connect();
		                pst = con.prepareStatement("DELETE FROM intervenantsformateurs WHERE IdFOR=?");
		                pst.setString(1, selectedId);
		                pst.executeUpdate();
		                con.close();
		                JOptionPane.showMessageDialog(null, "Formateur supprimée avec succès.");
		                Table(); // Met à jour le tableau après la suppression
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du formateur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});
		btnSupprimer.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnSupprimer.setBackground(new Color(0, 128, 128));
		btnSupprimer.setBounds(754, 526, 223, 48);
		frame.getContentPane().add(btnSupprimer);
	}

}
