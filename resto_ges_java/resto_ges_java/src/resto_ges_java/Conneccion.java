package resto_ges_java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conneccion {
	Connection con=null;
	Statement st = null;
	public Connection getConnection(){
	try {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver chargé...");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("erreur chargement driver .."+e.getMessage());
	}
	//Conexion a la base
	
			String url="jdbc:mysql://localhost:3308/resto_db";
			String login="root";
			String mp="";
			
			try {
				con=DriverManager.getConnection(url,login,mp);
				st = con.createStatement();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur de connexion"+e.getMessage());
			}
			System.out.println("Connected...");
			return con;
}
	public  void Deconnect() {
	    if (con != null) {
	        try {
	            //Déconnexion de la connexion DB
	            con.close();
	            System.out.println("Deconnected...");
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	        }
	  
	    }
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}