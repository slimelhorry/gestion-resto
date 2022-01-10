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

public class Commande extends JFrame {
	Statement st;
	Conneccion con=new Conneccion();
	ResultSet rst;
	JTable table,table2;
	JScrollPane scroll,scroll2;
	JLabel lbtitre1,lbtitre2,lbaliment,lbquantite,lbtab_num;
	JTextField tfquantite,tftab_num,tfid;
	JComboBox combo_aliment;
	JButton btenrg,btsupp,btliberer,bthistorique,btactu;
	public Commande(){
		this.setTitle("chcode_appli");
		this.setSize(900,500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel pn=new JPanel();
		pn.setLayout(null);
		add(pn);
		pn.setBackground(new Color(200,250,150));
		
		lbtitre1=new JLabel("Formulaire d'enregistrement des commandes");
		lbtitre1.setBounds(50,10,800,30);
		lbtitre1.setFont(new Font("Arial",Font.BOLD,18));
		pn.add(lbtitre1);
		
		lbtitre2=new JLabel("Liste des montants totaux par table");
		lbtitre2.setBounds(20,280,800,30);
		lbtitre2.setFont(new Font("Arial",Font.BOLD,18));
		pn.add(lbtitre2);
		//aliment
		lbaliment=new JLabel("Aliment");
		lbaliment.setBounds(85,60,100,30);
		lbaliment.setFont(new Font("Arial",Font.BOLD,16));
		pn.add(lbaliment);
		
		combo_aliment=new JComboBox();
		combo_aliment.addItem("");
		combo_aliment.setBounds(170,60,130,25);
		pn.add(combo_aliment);
		
	////remplissage du combo_aliment
		 String sql="select code_aliment from tb_aliment";
		 try{
			 st=con.getConnection().createStatement();
			 rst=st.executeQuery(sql);
			 while(rst.next()){
				 combo_aliment.addItem(rst.getString("code_aliment")); 
			 }
		 }
		 catch(SQLException ex){
		    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
		    }
		 //quantite
		 lbquantite=new JLabel("Quantité");
		 lbquantite.setBounds(80,90,100,25);
		 lbquantite.setFont(new Font("Arial",Font.BOLD,16));
		 pn.add(lbquantite);
		 
		 tfquantite=new JTextField();
		 tfquantite.setBounds(170,90,100,25);
			pn.add(tfquantite);
			//numéro de table
			 lbtab_num=new JLabel("Numéro de table");
			 lbtab_num.setBounds(20,120,150,25);
			 lbtab_num.setFont(new Font("Arial",Font.BOLD,16));
			 pn.add(lbtab_num);
			 
			 tftab_num=new JTextField();
			 tftab_num.setBounds(170,120,100,25);
				pn.add(tftab_num);
				//id commande
				 tfid=new JTextField("ID commande");
				 tfid.setBounds(50,200,100,25);
					pn.add( tfid);
			//bouton pour ouvrir la liste de l'historique des commandes 
					bthistorique=new JButton("HISTORIQUE DES COMMANDES");
					bthistorique.setBounds(400,430,230,25);
					bthistorique.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ev){
							//afficher la liste des commandes
							 DefaultTableModel df=new  DefaultTableModel();
							  init();
							  pn.add(scroll);
							 df.addColumn("Numéro de table");
							 df.addColumn("Commande");
							 df.addColumn("Quantité");
							 df.addColumn("Date commande");
							 table.setModel(df);
							 String req="select * from tb_historique order by date_commande desc ";
							 try{
								 st=con.getConnection().createStatement();
								 rst=st.executeQuery(req);
								 while(rst.next()){
									 df.addRow(new Object[]{
					rst.getString("tab_num"),rst.getString("aliment"),rst.getString("qte_aliment"),rst.getString("date_commande")
											 });
									 
								 }
								 
									 
								 }
								 
							 catch(SQLException ex){
							    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
							    }
						}
						
					});
					pn.add(bthistorique);
		//bouton pour actualiser la fenetre
					btactu=new JButton("ACTUALISER");
					btactu.setBounds(660,430,120,25);
					btactu.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ev){
							dispose();
							Commande cd=new Commande();
							cd.setVisible(true);
							
						}
						
					});
					pn.add(btactu);
				//bouton pour enregistrer une commande
				btenrg=new JButton("ENREGISTRER");
				btenrg.setBounds(50,160,120,25);
				btenrg.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						String aliment,quantite,num_tab,etat="";
						aliment=combo_aliment.getSelectedItem().toString();
						quantite=tfquantite.getText();
						num_tab=tftab_num.getText();
									
		String rq="insert into tb_commande(aliment,qte_aliment,tab_num) values('"+aliment+"','"+quantite+"','"+num_tab+"')";
		String rq2="update tb_table set etat='occupee' where num_tab='"+num_tab+"'";
		String rq3="insert into tb_historique(aliment,qte_aliment,tab_num,date_commande) values('"+aliment+"','"+quantite+"','"+num_tab+"',now())";
	
		try{
			st=con.getConnection().createStatement();
			if(!aliment.equals("")&&!quantite.equals("")&&!num_tab.equals("")){
			st.executeUpdate(rq);
			st.executeUpdate(rq2);
			st.executeUpdate(rq3);
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
		Commande cm=new Commande();
		cm.setVisible(true);	
					}
				});
				pn.add(btenrg);
				//bouton pour annuler une commande
				btsupp=new JButton("ANNULER");
				btsupp.setBounds(190,200,100,25);
				btsupp.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						//vérifier le numéro dans la base de données
						String id=tfid.getText(),nb="";
						String req="select count(*) as nb from tb_commande where id_commande='"+id+"'";
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
						String rq2="delete from tb_historique where id_commande='"+id+"'";
						String rq="delete from tb_commande where id_commande='"+id+"'";
						
						try{
							st=con.getConnection().createStatement();
							if(!id.equals("")){
								if(!nb.equals("0")){
							st.executeUpdate(rq);
							
							JOptionPane.showMessageDialog(null,"Suppréssion éffectuée avec succès !",null,JOptionPane.INFORMATION_MESSAGE);
								}
								else{
									JOptionPane.showMessageDialog(null,"Cet enregistrement n'existe pas dans la base de données !",null,JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
								JOptionPane.showMessageDialog(null,"Indiquez l'identifiant (ID) de la commande!",null,JOptionPane.ERROR_MESSAGE);
							}
						}
						catch(SQLException ex){
					    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
					    }
						try{
							st=con.getConnection().createStatement();
							st.executeUpdate(rq2);
						}
						catch(SQLException ex){
					    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
					    }
						dispose();
						Commande rg=new Commande();
						rg.setVisible(true);
					}
					
				});
				pn.add(btsupp);
				//bouton pour liberer une table
				btliberer=new JButton("LIBERER UNE TABLE");
				btliberer.setBounds(50,240,240,25);
				btliberer.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						//vérifier le numéro dans la base de données
						String num=tftab_num.getText(),nb="";
						String req="select count(*) as nb from tb_commande where tab_num='"+num+"'";
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
						String rq="delete from tb_commande where tab_num='"+num+"'";
						String rq2="update tb_table set etat='libre' where num_tab='"+num+"'";
						try{
							st=con.getConnection().createStatement();
							if(!num.equals("")){
								if(!nb.equals("0")){
							st.executeUpdate(rq2);
							st.executeUpdate(rq);
							JOptionPane.showMessageDialog(null,"Libération de table confirmée!",null,JOptionPane.INFORMATION_MESSAGE);
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
						Commande rg=new Commande();
						rg.setVisible(true);
					}
					
				});
				pn.add(btliberer);
				//afficher la liste des commandes
				 DefaultTableModel df=new  DefaultTableModel();
				  init();
				  pn.add(scroll);
				 df.addColumn("Numéro de table");
				 df.addColumn("Commande");
				 df.addColumn("Quantité");
				 df.addColumn("ID commande");
				 table.setModel(df);
				 String req="select * from tb_commande ";
				 try{
					 st=con.getConnection().createStatement();
					 rst=st.executeQuery(req);
					 while(rst.next()){
						 df.addRow(new Object[]{
		rst.getString("tab_num"),rst.getString("aliment"),rst.getString("qte_aliment"),rst.getString("id_commande")
								 });
						 
					 }
					 
						 
					 }
					 
				 catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
				    }
				 //afficher les montants totaux des commandes
				 DefaultTableModel df2=new  DefaultTableModel();
				  init2();
				  pn.add(scroll2);
				 df2.addColumn("Numéro table");
				 df2.addColumn("Montant total (fcfa)");
				 table2.setModel(df2);
				 String req2="select tab_num,sum(qte_aliment*prix_aliment) as montant from tb_aliment inner join tb_commande on tb_aliment.code_aliment=tb_commande.aliment group by tab_num  ";
				 try{
					 st=con.getConnection().createStatement();
					 rst=st.executeQuery(req2);
					 while(rst.next()){
						 df2.addRow(new Object[]{
		rst.getString("tab_num"),rst.getString("montant")
		
								 });
						 
					 }
					 
						 
					 }
					 
				 catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
				    }
	}
	private void init(){
		table=new JTable();
		scroll=new JScrollPane();
		scroll.setBounds(360,40,520,380);
		scroll.setViewportView(table);
	}
	
	private void init2(){
		table2=new JTable();
		scroll2=new JScrollPane();
		scroll2.setBounds(10,310,340,150);
		scroll2.setViewportView(table2);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
  Commande cmd=new Commande();
  cmd.setVisible(true);

	}

}
