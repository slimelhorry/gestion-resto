package resto_ges_java;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Aliment_table extends JFrame {
	Statement st;
	Conneccion con=new Conneccion();
	ResultSet rst;
	JTable table,table2;
	JScrollPane scroll,scroll2;
	JLabel lbtitre1,lbtitre2,lbnum_tab,lbnombre_chaise,lbcode_aliment,lbnom_aliment,lbprix_aliment;
	JTextField tfnum_tab,tfcode_aliment,tfprix_aliment,tfnom_aliment;
	JComboBox combonombre_chaise;
	JButton btenrg,btsupp,btenrg2,btsupp2,btcommande,btactu;
	public Aliment_table(){
		this.setTitle("chcode_appli");
		this.setSize(1000,600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel pn=new JPanel();
		pn.setLayout(null);
		add(pn);
		pn.setBackground(new Color(200,250,150));
		
		lbtitre1=new JLabel("Formulaire d'enregistrement des tables");
		lbtitre1.setBounds(50,10,800,30);
		lbtitre1.setFont(new Font("Arial",Font.BOLD,18));
		pn.add(lbtitre1);
		
		lbtitre2=new JLabel("Formulaire d'enregistrement des aliments");
		lbtitre2.setBounds(50,200,800,30);
		lbtitre2.setFont(new Font("Arial",Font.BOLD,18));
		pn.add(lbtitre2);
		
		//numero de table
		lbnum_tab=new JLabel("Numéro de table");
		lbnum_tab.setBounds(63,50,170,25);
		lbnum_tab.setFont(new Font("Arial",Font.BOLD,16));
				pn.add(lbnum_tab);
								
				tfnum_tab=new JTextField();
				tfnum_tab.setBounds(200,50,100,25);
				pn.add(tfnum_tab);
		//nombre chaise
				lbnombre_chaise=new JLabel("Nombre chaises");
				lbnombre_chaise.setBounds(63,80,170,25);
				lbnombre_chaise.setFont(new Font("Arial",Font.BOLD,16));
						pn.add(lbnombre_chaise);
										
			combonombre_chaise=new JComboBox();
			combonombre_chaise.addItem("");
			combonombre_chaise.addItem("1");
			combonombre_chaise.addItem("2");
			combonombre_chaise.addItem("4");
			combonombre_chaise.addItem("6");
			combonombre_chaise.setBounds(200,80,100,25);
			pn.add(combonombre_chaise);
	//code aliment
			lbcode_aliment=new JLabel("Code aliment");
			lbcode_aliment.setBounds(63,240,170,25);
			lbcode_aliment.setFont(new Font("Arial",Font.BOLD,16));
					pn.add(lbcode_aliment);
									
					tfcode_aliment=new JTextField();
					tfcode_aliment.setBounds(200,240,100,25);
					pn.add(tfcode_aliment);
	//nom aliment
					lbnom_aliment=new JLabel("Nom aliment");
					lbnom_aliment.setBounds(63,270,170,25);
					lbnom_aliment.setFont(new Font("Arial",Font.BOLD,16));
							pn.add(lbnom_aliment);
											
							tfnom_aliment=new JTextField();
							tfnom_aliment.setBounds(200,270,100,25);
							pn.add(tfnom_aliment);
	//prix aliment
							//nom aliment
							lbprix_aliment=new JLabel("Prix aliment ");
							lbprix_aliment.setBounds(63,300,170,25);
							lbprix_aliment.setFont(new Font("Arial",Font.BOLD,16));
									pn.add(lbprix_aliment);
													
									tfprix_aliment=new JTextField();
									tfprix_aliment.setBounds(200,300,100,25);
									pn.add(tfprix_aliment);
					
			//enregistrement des tables
			btenrg=new JButton("ENREGISTRER");
			btenrg.setBounds(30,130,120,25);
			btenrg.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					String numero,nbchaises;
					numero=tfnum_tab.getText();
					nbchaises=combonombre_chaise.getSelectedItem().toString();
	String rq="insert into tb_table(num_tab,nb_place,etat) values('"+numero+"','"+nbchaises+"','libre')";
	try{
		st=con.getConnection().createStatement();
		if(!numero.equals("")&&!nbchaises.equals("")){
		st.executeUpdate(rq);
		JOptionPane.showMessageDialog(null,"Enregistrement éffectué avec succès !",null,JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null,"Completez le formulaire!",null,JOptionPane.ERROR_MESSAGE);
		}
	}
	catch(SQLException ex){
    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
    }
	dispose();
	Aliment_table rg=new Aliment_table();
	rg.setVisible(true);			
				}
			});
			pn.add(btenrg);
		
		//bouton pour supprimer l'enregistrement d'une table
			btsupp=new JButton("SUPPRIMER");
			btsupp.setBounds(180,130,120,25);
			btsupp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					//vérifier le numéro dans la base de données
					String num=tfnum_tab.getText(),nb="";
					String req="select count(*) as nb from tb_table where num_tab='"+num+"'";
					try{
						st=con.getConnection().createStatement();
						rst=st.executeQuery(req);
						if(rst.next()){
							nb=rst.getString("nb");
						}
					}
					catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
				    }
					//
					String rq="delete from tb_table where num_tab='"+num+"'";
					try{
						st=con.getConnection().createStatement();
						if(!num.equals("")){
							if(!nb.equals("0")){
						st.executeUpdate(rq);
						JOptionPane.showMessageDialog(null,"Suppréssion éffectuée avec succès !",null,JOptionPane.INFORMATION_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null,"Cet enregistrement n'existe pas dans la base de données !",null,JOptionPane.ERROR_MESSAGE);
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Indiquez le numéro de table!",null,JOptionPane.ERROR_MESSAGE);
						}
					}
					catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
				    }
					dispose();
					Aliment_table rg=new Aliment_table();
					rg.setVisible(true);
				}
				
			});
			pn.add(btsupp);
			//ALIMENTS
			//bouton pour enregistrer les aliments
			btenrg2=new JButton("ENREGISTRER");
			btenrg2.setBounds(50,350,120,25);
			btenrg2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					String code,nom,prix;
					code=tfcode_aliment.getText();
					nom=tfnom_aliment.getText();
					prix=tfprix_aliment.getText();
					String rq="insert into tb_aliment(code_aliment,nom_aliment,prix_aliment) values('"+code+"','"+nom+"','"+prix+"')";
					try{
						st=con.getConnection().createStatement();
						if(!code.equals("")&&!nom.equals("")&&!prix.equals("")){
						st.executeUpdate(rq);
						JOptionPane.showMessageDialog(null,"Enregistrement éffectué avec succès !",null,JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(null,"Completez le formulaire!",null,JOptionPane.ERROR_MESSAGE);
						}
					}
					catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
				    }
					dispose();
					Aliment_table rg=new Aliment_table();
					rg.setVisible(true);			
								
				}
			});
			pn.add(btenrg2);
			//bouton pour supprimer les aliments
			btsupp2=new JButton("SUPPRIMER");
			btsupp2.setBounds(180,350,120,25);
			btsupp2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					//vérifier le numéro dans la base de données
					String code=tfcode_aliment.getText(),nb="";
					String req="select count(*) as nb from tb_aliment where code_aliment='"+code+"'";
					try{
						st=con.getConnection().createStatement();
						rst=st.executeQuery(req);
						if(rst.next()){
							nb=rst.getString("nb");
						}
					}
					catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
				    }
					//
					String rq="delete from tb_aliment where code_aliment='"+code+"'";
					try{
						st=con.getConnection().createStatement();
						if(!code.equals("")){
							if(!nb.equals("0")){
						st.executeUpdate(rq);
						JOptionPane.showMessageDialog(null,"Suppréssion éffectuée avec succès !",null,JOptionPane.INFORMATION_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null,"Cet enregistrement n'existe pas dans la base de données !",null,JOptionPane.ERROR_MESSAGE);
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Indiquez le code aliment!",null,JOptionPane.ERROR_MESSAGE);
						}
					}
					catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
				    }
					dispose();
					Aliment_table rg=new Aliment_table();
					rg.setVisible(true);
				}
				
			});
			pn.add(btsupp2);
			//bouton pour aller dans l'interface graphique des commandes
			btcommande=new JButton("COMMANDE");
			btcommande.setBounds(180,400,120,25);
			btcommande.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					Commande cm=new Commande();
					cm.setVisible(true);
					
				}
			});
			pn.add(btcommande);
			//bouton pour actualiser la fenetre
			btactu=new JButton("ACTUALISER");
			btactu.setBounds(50,400,120,25);
			btactu.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					dispose();
					Aliment_table tb=new Aliment_table();
					tb.setVisible(true);
					
				}
			});
			pn.add(btactu);
			
		//afficher la liste des tables
			 DefaultTableModel df=new  DefaultTableModel();
			  init();
			  pn.add(scroll);
			 df.addColumn("Numéro table");
			 df.addColumn("Nombre chaises");
			 df.addColumn("Etat");
			 table.setModel(df);
			 String req="select * from tb_table ";
			 try{
				 st=con.getConnection().createStatement();
				 rst=st.executeQuery(req);
				 while(rst.next()){
					 df.addRow(new Object[]{
	rst.getString("num_tab"),rst.getString("nb_place"),rst.getString("etat")
							 });
					 
				 }
				 
					 
				 }
				 
			 catch(SQLException ex){
			    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
			    }
			 //afficher la liste des aliments en vente
			 DefaultTableModel df2=new  DefaultTableModel();
			  init2();
			  pn.add(scroll2);
			 df2.addColumn("Code aliment");
			 df2.addColumn("Nom aliment");
			 df2.addColumn("Prix unitaire (fcfa)");
	
			 table2.setModel(df2);
			 String req2="select * from tb_aliment  ";
			 try{
				 st=con.getConnection().createStatement();
				 rst=st.executeQuery(req2);
				 while(rst.next()){
					 df2.addRow(new Object[]{
	rst.getString("code_aliment"),rst.getString("nom_aliment"),rst.getString("prix_aliment")
	
							 });
					 
				 }
				 
					 
				 }
				 
			 catch(SQLException ex){
			    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
			    }
			 //
	}
	private void init(){
		table=new JTable();
		scroll=new JScrollPane();
		scroll.setBounds(400,20,570,160);
		scroll.setViewportView(table);
		
	}
	private void init2(){
		table2=new JTable();
		scroll2=new JScrollPane();
		scroll2.setBounds(360,230,610,270);
		scroll2.setViewportView(table2);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Aliment_table alt=new  Aliment_table();
		 alt.setVisible(true);
		 
	}

}
