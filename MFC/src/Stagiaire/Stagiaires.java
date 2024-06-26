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

public class Stagiaires extends javax.swing.JFrame {

	private JFrame frame;
	private JTextField txtNoms;
	private JTextField txtPrenomS;
	private JTextField txtTelS;
	private JTextField txtMailS;
	private JTextField txtEntrepriseS;
	private JTextField txtAdresseS;
	private JTextField txtIdS;
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
					Stagiaires window = new Stagiaires();
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
	public Stagiaires() {
		initialize();
		Table();
	}

	public void Table() {
		String []stagiaire = {"IdS " , "Noms", "PrenomS", "TelS", "MailS","EntrepriseS","AdresseS"};
		String []Montrer = new String[7];
		
		model = new DefaultTableModel(null,stagiaire);
		
		String sql = "select * from stagiaires";
		try {
			Connect();
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Montrer[0] = rs.getString("IdS");
				Montrer[1] = rs.getString("Noms");
				Montrer[2] = rs.getString("PrenomS");
				Montrer[3] = rs.getString("TelS");
				Montrer[4] = rs.getString("MailS");
				Montrer[5] = rs.getString("EntrepriseS");
				Montrer[6] = rs.getString("AdresseS");
				model.addRow(Montrer);
			}
			table.setModel(model);
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Méthode pour récupérer le JFrame associé à la classe Stagiaires
    public JFrame getStagiaires() {
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
		frame.setBounds(100, 100, 1139, 686);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(128, 255, 128));
		frame.getContentPane().setLayout(null);
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			// Méthode pour enregistrer une formation
			public void actionPerformed(ActionEvent e) {
				try {
					
		            Connect();
		            pst = con.prepareStatement("INSERT INTO stagiaires (IdS , Noms, PrenomS, TelS, MailS,EntrepriseS,AdresseS) VALUES (?, ?, ?, ?, ?,?,?)");
		            pst.setString(1, txtIdS .getText());
		            pst.setString(2, txtNoms.getText());
		            pst.setString(3, txtPrenomS.getText());
		            pst.setString(4, txtTelS.getText());
		            pst.setString(5, txtMailS.getText());
		            pst.setString(6, txtEntrepriseS.getText());
		            pst.setString(7, txtAdresseS.getText());
		            pst.executeUpdate();
		            con.close();
		            JOptionPane.showMessageDialog(null, "Stagiaire enregistrée");
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
		            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un stagiaire à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Récupère l'identifiant de la formation sélectionnée
		        String selectedId = (String) table.getValueAt(selectedRow, 0);

		        // Vérifie que les champs obligatoires sont remplis
		        
		        if (txtNoms.getText().isEmpty() || txtPrenomS.getText().isEmpty() || txtTelS.getText().isEmpty() || txtMailS.getText().isEmpty() || txtEntrepriseS.getText().isEmpty() || txtAdresseS.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        try {
		            Connect();
		            pst = con.prepareStatement("UPDATE stagiaires SET Noms=?, PrenomS=?, TelS=?, MailS=?, EntrepriseS=?, AdresseS=? WHERE IdS=?");
		            pst.setString(1, txtNoms.getText());
		            pst.setString(2, txtPrenomS.getText());
		            // Convertit le telephone en un entier avant de l'insérer dans la base de données
		            int txtTelSValue = Integer.parseInt(txtTelS.getText());
		            pst.setInt(3, txtTelSValue);
		            pst.setString(4, txtMailS.getText());
		            pst.setString(5, txtEntrepriseS.getText());
		            pst.setString(6, txtAdresseS.getText());
		            pst.setString(7, selectedId);
		            pst.executeUpdate();
		            con.close();
		            JOptionPane.showMessageDialog(null, "Stagiaire modifié avec succès.");
		            Table(); // Met à jour le tableau après la modification
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Veuillez saisir un numéro de téléphone valide (nombre entier).", "Erreur", JOptionPane.ERROR_MESSAGE);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erreur lors de la modification du stagiaire : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnModifier.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnModifier.setBackground(new Color(0, 128, 128));
		btnModifier.setBounds(443, 526, 223, 48);
		frame.getContentPane().add(btnModifier);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			// Méthode pour supprimer un stagiaire
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // Ferme la fenêtre de l'application
			}
		});
		btnQuitter.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnQuitter.setBackground(new Color(0, 128, 128));
		btnQuitter.setBounds(443, 584, 223, 48);
		frame.getContentPane().add(btnQuitter);
		
		JLabel lblNewLabel_1 = new JLabel("Noms");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1.setBounds(0, 132, 129, 48);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("PrenomS");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(10, 190, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		txtPrenomS = new JTextField();
		txtPrenomS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtPrenomS.setColumns(10);
		txtPrenomS.setBounds(139, 190, 255, 48);
		frame.getContentPane().add(txtPrenomS);
		
		JLabel lblNewLabel_1_6 = new JLabel("MailS");
		lblNewLabel_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_6.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_6.setBounds(0, 248, 129, 48);
		frame.getContentPane().add(lblNewLabel_1_6);
		
		txtMailS = new JTextField();
		txtMailS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMailS.setColumns(10);
		txtMailS.setBounds(139, 248, 255, 48);
		frame.getContentPane().add(txtMailS);
		
		JLabel lblNewLabel_1_7 = new JLabel("EntrepriseS");
		lblNewLabel_1_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_7.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_7.setBounds(10, 306, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_7);
		
		txtEntrepriseS = new JTextField();
		txtEntrepriseS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEntrepriseS.setColumns(10);
		txtEntrepriseS.setBounds(139, 306, 255, 48);
		frame.getContentPane().add(txtEntrepriseS);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("TéléphoneS");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_1_1.setBounds(0, 422, 129, 48);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		txtTelS = new JTextField();
		txtTelS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTelS.setColumns(10);
		txtTelS.setBounds(139, 422, 255, 48);
		frame.getContentPane().add(txtTelS);
		
		JLabel lblNewLabel_1_2 = new JLabel("AdresseS");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(0, 364, 129, 48);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		txtNoms = new JTextField();
		txtNoms.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNoms.setColumns(10);
		txtNoms.setBounds(139, 132, 255, 48);
		frame.getContentPane().add(txtNoms);
		
		JLabel labelidf = new JLabel("Id S");
		labelidf.setHorizontalAlignment(SwingConstants.CENTER);
		labelidf.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		labelidf.setBounds(10, 74, 119, 48);
		frame.getContentPane().add(labelidf);
		
		txtIdS = new JTextField();
		txtIdS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtIdS.setColumns(10);
		txtIdS.setBounds(139, 74, 255, 48);
		frame.getContentPane().add(txtIdS);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Stagiaires");
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
		        "IdS", "Noms", "PrenomS", "TelS", "MailS","EntrepriseS","AdresseS"
		    }
		));
		scrollPane.setViewportView(table);
		
		txtAdresseS = new JTextField();
		txtAdresseS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAdresseS.setColumns(10);
		txtAdresseS.setBounds(139, 364, 255, 48);
		frame.getContentPane().add(txtAdresseS);
		
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

		        int dialogResult = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce stagiaire ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
		        if (dialogResult == JOptionPane.YES_OPTION) {
		            try {
		                Connect();
		                pst = con.prepareStatement("DELETE FROM stagiaires WHERE IdS=?");
		                pst.setString(1, selectedId);
		                pst.executeUpdate();
		                con.close();
		                JOptionPane.showMessageDialog(null, "Stagiare supprimée avec succès.");
		                Table(); // Met à jour le tableau après la suppression
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du stagiaire : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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
