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

public class Formations extends javax.swing.JFrame{

	private JFrame frame;
	private JTextField tarif;
	private JTextField txtduree;
	private JTextField descriptif;
	private JTextField txtintitule;
	private JTextField txtid;
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
					Formations window = new Formations();
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
	public Formations() {
		initialize();
		Table();
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
	
	public void Table() {
		String []formation = {"IdF" , "IntuleF", "Tarif", "Duree", "DescriptionF"};
		String []Montrer = new String[5];
		
		model = new DefaultTableModel(null,formation);
		
		String sql = "select * from formation";
		try {
			Connect();
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Montrer[0] = rs.getString("IdF");
				Montrer[1] = rs.getString("IntuleF");
				Montrer[2] = rs.getString("Tarif");
				Montrer[3] = rs.getString("Duree");
				Montrer[4] = rs.getString("DescriptionF");
				model.addRow(Montrer);
			}
			table.setModel(model);
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Méthode pour récupérer le JFrame associé à la classe Formations
    public JFrame getFormations() {
        return frame;
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(128, 255, 128));
		frame.setBounds(100, 100, 1134, 759);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			// Méthode pour enregistrer une formation
			public void actionPerformed(ActionEvent e) {
				try {
		            Connect();
		            pst = con.prepareStatement("INSERT INTO formation (IdF , IntuleF, Tarif, Duree, DescriptionF) VALUES (?, ?, ?, ?, ?)");
		            pst.setString(1, txtid.getText());
		            pst.setString(2, txtintitule.getText());
		            pst.setString(3, tarif.getText());
		            pst.setString(4, txtduree.getText());
		            pst.setString(5, descriptif.getText());
		            pst.executeUpdate();
		            con.close();
		            JOptionPane.showMessageDialog(null, "Formation enregistrée");
		            Table();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage());
		        }
			}
		});
		btnEnregistrer.setBackground(new Color(0, 128, 128));
		btnEnregistrer.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnEnregistrer.setBounds(138, 504, 223, 48);
		frame.getContentPane().add(btnEnregistrer);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			// Méthode pour modifier une formation
			public void actionPerformed(ActionEvent e) {
				// Vérifie si une ligne est sélectionnée dans le tableau
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une formation à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Récupère l'identifiant de la formation sélectionnée
		        String selectedId = (String) table.getValueAt(selectedRow, 0);

		        // Vérifie que les champs obligatoires sont remplis
		        if (txtintitule.getText().isEmpty() || tarif.getText().isEmpty() || txtduree.getText().isEmpty() || descriptif.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        try {
		            Connect();
		            pst = con.prepareStatement("UPDATE formation SET IntuleF=?, Tarif=?, Duree=?, DescriptionF=? WHERE IdF=?");
		            pst.setString(1, txtintitule.getText());
		            // Convertit le tarif en un entier avant de l'insérer dans la base de données
		            int tarifValue = Integer.parseInt(tarif.getText());
		            pst.setInt(2, tarifValue);
		            pst.setString(3, txtduree.getText());
		            pst.setString(4, descriptif.getText());
		            pst.setString(5, selectedId);
		            pst.executeUpdate();
		            con.close();
		            JOptionPane.showMessageDialog(null, "Formation modifiée avec succès.");
		            Table(); // Met à jour le tableau après la modification
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Veuillez saisir un tarif valide (nombre entier).", "Erreur", JOptionPane.ERROR_MESSAGE);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erreur lors de la modification de la formation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnModifier.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnModifier.setBackground(new Color(0, 128, 128));
		btnModifier.setBounds(443, 504, 223, 48);
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
		btnQuitter.setBounds(443, 574, 223, 48);
		frame.getContentPane().add(btnQuitter);
		
		JLabel lblNewLabel_1 = new JLabel("Intitulé F");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1.setBounds(0, 185, 129, 48);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("tarif");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(0, 325, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		tarif = new JTextField();
		tarif.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tarif.setColumns(10);
		tarif.setBounds(139, 326, 255, 48);
		frame.getContentPane().add(tarif);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Durée");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_1_1.setBounds(0, 395, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		txtduree = new JTextField();
		txtduree.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtduree.setColumns(10);
		txtduree.setBounds(139, 396, 255, 48);
		frame.getContentPane().add(txtduree);
		
		JLabel lblNewLabel_1_2 = new JLabel("Descriptif");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(0, 253, 119, 48);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		txtintitule = new JTextField();
		txtintitule.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtintitule.setColumns(10);
		txtintitule.setBounds(139, 186, 255, 48);
		frame.getContentPane().add(txtintitule);
		
		JLabel labelidf = new JLabel("Id F");
		labelidf.setHorizontalAlignment(SwingConstants.CENTER);
		labelidf.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		labelidf.setBounds(10, 115, 119, 48);
		frame.getContentPane().add(labelidf);
		
		txtid = new JTextField();
		txtid.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtid.setColumns(10);
		txtid.setBounds(139, 116, 255, 48);
		frame.getContentPane().add(txtid);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Formations");
		lblNewLabel_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3_1.setFont(new Font("Wide Latin", Font.BOLD, 30));
		lblNewLabel_1_3_1.setBounds(294, 10, 386, 71);
		frame.getContentPane().add(lblNewLabel_1_3_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(436, 115, 557, 331);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int i = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				txtid.setText(model.getValueAt(i, 0).toString());
	            txtintitule.setText(model.getValueAt(i, 1).toString());
	            tarif.setText(model.getValueAt(i, 2).toString());
	            txtduree.setText(model.getValueAt(i, 3).toString());
	            descriptif.setText(model.getValueAt(i, 4).toString());
			}
		});
		table.setToolTipText("");
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
		    new Object[][] {},
		    new String[] {
		        "IdF", "IntuleF", "Tarif", "Duree", "DescriptionF"
		    }
		));
		scrollPane.setViewportView(table);
		
		descriptif = new JTextField();
		descriptif.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descriptif.setColumns(10);
		descriptif.setBounds(139, 253, 255, 48);
		frame.getContentPane().add(descriptif);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une formation à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Récupère l'identifiant de la formation sélectionnée
		        String selectedId = (String) table.getValueAt(selectedRow, 0);

		        int dialogResult = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cette formation ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
		        if (dialogResult == JOptionPane.YES_OPTION) {
		            try {
		                Connect();
		                pst = con.prepareStatement("DELETE FROM formation WHERE IdF=?");
		                pst.setString(1, selectedId);
		                pst.executeUpdate();
		                con.close();
		                JOptionPane.showMessageDialog(null, "Formation supprimée avec succès.");
		                Table(); // Met à jour le tableau après la suppression
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la formation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});
		btnSupprimer.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		btnSupprimer.setBackground(new Color(0, 128, 128));
		btnSupprimer.setBounds(753, 504, 223, 48);
		frame.getContentPane().add(btnSupprimer);
	}
}