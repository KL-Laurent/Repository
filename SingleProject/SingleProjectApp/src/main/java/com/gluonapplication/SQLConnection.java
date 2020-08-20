package com.gluonapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLConnection {
	private static Connection connection ; 
	
	private static String connectionUrl;
    
			
	//"jdbc:sqlserver://192.168.0.41:1433;databaseName=n4testdb;user=klaurent;password=Init2020";
	//"jdbc:sqlserver://127.0.0.1:1434;databaseName=Test;user=KL;password=kl141294"; 

	//192.168.0.41
	//1433
	//n4testdb
	//klaurent
	//Init2020
		
	
	private String address ; 
	private String dataBase ;
	private String user  ;
	private String password ; 
	private String port ; 

	
	public static Connection Connector() throws ClassNotFoundException, SQLException {
		Connection conn  = DriverManager.getConnection(connectionUrl);
		return  conn ; 
		
	}
	
	public SQLConnection() {
			try {
				connectionDb();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	public SQLConnection(String address ,String port,  String database , String user , String password) throws ClassNotFoundException, SQLException{
		//CONSTRUCTION DU LIEN 
		this.address = address ; 
		this.dataBase = database ;
		this.user = user ; 
		this.password =password ; 
		String lien  =  "jdbc:sqlserver://"+address+":"+port+"; databaseName="+database+";user="+user+";password="+password;
		connectionUrl = lien ;
		
		//";instanceName=KLSERVER;"
		
		//LANCEMENT DE LA CONNECTION 
		connectionDb();
	}
	
	//FERME LA CONNECTION DB 
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//OUVRE LA CONNECTION DB 
	public void connectionDb() throws ClassNotFoundException, SQLException {
		connection = SQLConnection.Connector();
		if(connection == null ) 
			System.exit(1);
		
		if(connection.isClosed()) {
			System.out.print("Pas connecter");
		}
		else
			System.out.print("Connecter");
	}

	public void insert_type_engin(Base_view_type_engin engin){
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[type_engin]" + 
				"           ([libelle]" + 
				"           ,[dateCreation]" + 
				"           ,[dateModification]" + 
				"           ,[createur] " + 
				"           ,[modificateur])" + 
				"     VALUES (?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,engin.libelle.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(2,dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(3,null,Types.BIGINT);
			preparedStatement.setObject(4,CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(5,null,Types.BIGINT);

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert_engin(Base_view_engin engin){
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[engin] " + 
				"           ( [libelle]" + 
				"           ,[dateCreation] " + 
				"           ,[dateModification] " + 
				"           ,[createur] " + 
				"           ,[modificateur] " + 
				"           ,[gkey_type_engin]"+
				"           ,[gkey_responsable] )" + 
				"     VALUES (?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,engin.libelle.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(2,getObjetTimeStamp(engin.dateCreation),Types.TIMESTAMP);
			preparedStatement.setObject(3,null,Types.TIMESTAMP);
			preparedStatement.setObject(4,engin.createur,Types.BIGINT);
			preparedStatement.setObject(5,null,Types.BIGINT);
			preparedStatement.setObject(6,engin.getGkeyTypeEngin(),Types.BIGINT);
			preparedStatement.setObject(7,engin.getGkeyResponsable(),Types.BIGINT);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_equipement(Base_view_equipment equipement){
		PreparedStatement  preparedStatement = null ; 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		String query  ="INSERT INTO [dbo].[equipement_test_fonction] " + 
				"           ([libelle]" + 
				"           ,[critique]" + 
				"           ,[type]" + 
				"           ,[dateCreation]" + 
				"           ,[dateModification]" + 
				"           ,[createur]" + 
				"           ,[modificateur]" + 
				"           ,[gkey_type_engin]"
				+ "         ,[gkey_responsable] )" + 
				"     VALUES (?,?,?,?,?,?,?, "
				+ " (SELECT TOP(1) [gkey] FROM type_engin ORDER BY [gkey] DESC) "
				+ " ,?) " ;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,equipement.libelle.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(2,equipement.isCritique(),Types.BIT);
			preparedStatement.setObject(3,equipement.type,Types.VARCHAR);
			preparedStatement.setObject(4,dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(5,null,Types.TIMESTAMP);
			preparedStatement.setObject(6,CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(7,null,Types.BIGINT);
			preparedStatement.setObject(8,equipement.getIdResponsable(),Types.BIGINT);

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_equipement(Base_view_equipment equipement,int gkey_engin){
		PreparedStatement  preparedStatement = null ; 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		String query  ="INSERT INTO [dbo].[equipement_test_fonction] " + 
				"           ([libelle]" + 
				"           ,[critique]" + 
				"           ,[type]" + 
				"           ,[dateCreation]" + 
				"           ,[dateModification]" + 
				"           ,[createur]" + 
				"           ,[modificateur]" + 
				"           ,[gkey_type_engin]"
				+ "         ,[gkey_responsable] )" + 
				"     VALUES (?,?,?,?,?,?,?,?,?) " ;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,equipement.libelle.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(2,equipement.isCritique(),Types.BIT);
			preparedStatement.setObject(3,equipement.type,Types.VARCHAR);
			preparedStatement.setObject(4,dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(5,null,Types.TIMESTAMP);
			preparedStatement.setObject(6,CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(7,null,Types.BIGINT);
			preparedStatement.setObject(8,gkey_engin,Types.BIGINT);
			preparedStatement.setObject(9,equipement.getIdResponsable(),Types.BIGINT);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insert_fiche_verification(Base_view_fiche fiche){
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[fiche_verification]" + 
				"           ([observation]" + 
				"           ,[heuredebut]" + 
				"           ,[heurefin]" + 
				"           ,[date]" + 
				"           ,[operateur]"
				+ "			,[gkey_engin]"
				+ "			,[shift_jour])" + 
				"     VALUES (?,?,?,?,?,?,?)";
		try {
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,getObjetString(fiche.observation),Types.VARCHAR);
			preparedStatement.setObject(2,getObjetTime(fiche.heuredebut),Types.TIME);
			preparedStatement.setObject(3,getObjetTime(fiche.heurefin),Types.TIME);
			preparedStatement.setObject(4,getObjetDate(fiche.date),Types.DATE);
			preparedStatement.setObject(5,fiche.gkey_operateur,Types.BIGINT);
			preparedStatement.setObject(6,fiche.gkey_engin,Types.BIGINT);
			preparedStatement.setObject(7,fiche.shift_jour,Types.BIT);

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_user(Base_view_user user){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[user] (" + 
				"            [nom] " + 
				"           ,[email] " + 
				"           ,[adresse] " + 
				"           ,[numtel] " + 
				"           ,[privilege] " + 
				"           ,[dateCreation] " + 
				"           ,[dateModification] " + 
				"           ,[createur] " + 
				"           ,[modificateur] "
				+ "			,[id] "
				+ "			,[pass] "

				+ " )" + 
				"     VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,user.fieldNom.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(2,user.fieldMail.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(3,user.fieldAddress.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(4,user.fieldNumtel.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(5,user.fieldType.getValue().getSelectionModel().getSelectedItem(),Types.VARCHAR);
			preparedStatement.setObject(6,dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(7,null,Types.TIMESTAMP);
			preparedStatement.setObject(8,CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(9,null,Types.BIGINT);
			preparedStatement.setObject(10,user.fieldId.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(11,user.fieldPass.getValue().getText(),Types.NVARCHAR);


			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_contenu(Base_view_contenu contenu){

		
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[contenu] ( " + 
				"           [commentaire]" + 
				"           ,[status]" + 
				"           ,[dateModification]" + 
				"           ,[gkey_fiche_verification]" + 
				"           ,[gkey_equipement]" + 
				"           ,[modificateur])" + 
				"     VALUES (?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,contenu.commentaire.getValue().getText(),Types.VARCHAR);
			preparedStatement.setObject(2,contenu.status.getValue().isSelected(),Types.BIT);
			preparedStatement.setObject(3,getObjetTimeStamp(contenu.dateModification),Types.TIMESTAMP);
			preparedStatement.setObject(4,getObjetInteger(contenu.gkey_fiche_verification),Types.BIGINT);
			preparedStatement.setObject(5,getObjetInteger(contenu.gkey_equipement),Types.BIGINT);
			preparedStatement.setObject(6,getObjetInteger(contenu.gkey_modificateur),Types.BIGINT);

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_mail(int gkey_destinataire ,int gkey_expediteure , int gkey_contenu){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[mail] "+
		           		"([gkey_destinataire] "+
		                ",[gkey_expeditaire] "+
		                ",[gkey_contenu ]"+
		                ",[date] "+
		                 " ) "+
		             "VALUES (?,?,?,?) ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,gkey_destinataire,Types.BIGINT);
			preparedStatement.setObject(2,gkey_expediteure,Types.BIGINT);
			preparedStatement.setObject(3,gkey_contenu,Types.BIGINT);
			preparedStatement.setObject(4,dtf.format(now).toString(),Types.TIMESTAMP);

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_responsable(int user){
		PreparedStatement  preparedStatement = null ; 
		String query  ="INSERT INTO [dbo].[responsable]" + 
				"           ( [gkey_equipement]  " + 
				"           ,[gkey_user])" + 
				"     VALUES "
				+ "( (SELECT TOP(1) [gkey] FROM equipement_test_fonction ORDER BY [gkey] DESC ) ,"
				+ " ?"
				+ ")";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1,user,Types.BIGINT);
			preparedStatement.executeUpdate();
	 		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Base_view_type_engin> select_type_engin(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_type_engin> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[libelle] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"  FROM [dbo].[type_engin]";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_type_engin(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	/*public ObservableList<Base_langue> select_langue(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_langue> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] , [fr] ,[mlg],[eng] FROM [dbo].[langue]";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_langue(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	*/
	public ObservableList<Base_view_type_engin> select_type_engin_by_gkey(int gkey){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_type_engin> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[libelle] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"  FROM [dbo].[type_engin] "+
				"  WHERE [gkey] = ? ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, gkey);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_type_engin(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_engin> select_engin(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_engin> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[libelle] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"      ,[gkey_type_engin] " + 
				"      ,[gkey_responsable] " + 
				"  FROM [dbo].[engin]";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_engin(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6),
							result.getInt(7),
							result.getInt(8)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	

	public Base_view_engin select_engin(String libelle){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_engin> liste = FXCollections.observableArrayList();
		String query  ="SELECT TOP(1) [gkey] " + 
				"      ,[libelle] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"      ,[gkey_type_engin] " + 
				"      ,[gkey_responsable] " + 
				"  FROM [dbo].[engin] "+
				"WHERE libelle = ? " +
				"ORDER BY [gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, libelle);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_engin(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6),
							result.getInt(7),
							result.getInt(8)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(!liste.isEmpty())
			return liste.get(0) ; 
		
		return null; 
	}
	
	
	
	public int getLastFiche(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		int id = 0 ;
		String query  ="SELECT TOP(1) [gkey]  " + 
				"  FROM [dbo].[fiche_verification] "
				+ " ORDER BY [gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			

			while(result.next()) {
				id = result.getInt(1);
			}
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  id ; 
	}
	
	public ObservableList<Base_view_equipment> select_equipement_fonction(int gkey_type_engin , String type , boolean isCritique  ){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_equipment> liste = FXCollections.observableArrayList();
		String query  ="SELECT equipement_test_fonction.[gkey] " + 
				"      ,equipement_test_fonction.[libelle] " + 
				"      ,equipement_test_fonction.[critique] " + 
				"      ,equipement_test_fonction.[type] " + 
				"      ,equipement_test_fonction.[dateCreation] " + 
				"      ,equipement_test_fonction.[dateModification] " + 
				"      ,equipement_test_fonction.[createur] " + 
				"      ,equipement_test_fonction.[modificateur] " + 
				"      ,equipement_test_fonction.[gkey_type_engin] " + 
				"      ,equipement_test_fonction.[gkey_responsable] " + 
				"      ,R1.[nom] " + 
				"  FROM [dbo].[equipement_test_fonction] " + 
				"  INNER JOIN [user] R1 ON R1.gkey = [gkey_responsable] " + 
				"  WHERE [gkey_type_engin] = ? " + 
				"  AND [type] =  ? " + 
				"  AND [critique] =  ? "+
				 "ORDER BY [gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, gkey_type_engin);
			preparedStatement.setObject(2, type);
			preparedStatement.setObject(3, isCritique);

			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_equipment(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getInt(7),
							result.getInt(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_equipment> select_equipement_fonction(int gkey_type_engin , String type ){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_equipment> liste = FXCollections.observableArrayList();
		String query  ="SELECT equipement_test_fonction.[gkey] " + 
				"      ,equipement_test_fonction.[libelle] " + 
				"      ,equipement_test_fonction.[critique] " + 
				"      ,equipement_test_fonction.[type] " + 
				"      ,equipement_test_fonction.[dateCreation] " + 
				"      ,equipement_test_fonction.[dateModification] " + 
				"      ,equipement_test_fonction.[createur] " + 
				"      ,equipement_test_fonction.[modificateur] " + 
				"      ,equipement_test_fonction.[gkey_type_engin] " + 
				"      ,equipement_test_fonction.[gkey_responsable] " + 
				"      ,R1.[nom] " + 
				"  FROM [dbo].[equipement_test_fonction] " + 
				"  INNER JOIN [user] R1 ON R1.gkey = [gkey_responsable] " + 
				"  WHERE [gkey_type_engin] = ? " + 
				"  AND [type] =  ? " + 
				"ORDER BY [gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, gkey_type_engin);
			preparedStatement.setObject(2, type);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_equipment(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getInt(7),
							result.getInt(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_equipment> select_equipement_fonction( String type ){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_equipment> liste = FXCollections.observableArrayList();
		String query  ="SELECT equipement_test_fonction.[gkey] " + 
				"      ,equipement_test_fonction.[libelle] " + 
				"      ,equipement_test_fonction.[critique] " + 
				"      ,equipement_test_fonction.[type] " + 
				"      ,equipement_test_fonction.[dateCreation] " + 
				"      ,equipement_test_fonction.[dateModification] " + 
				"      ,equipement_test_fonction.[createur] " + 
				"      ,equipement_test_fonction.[modificateur] " + 
				"      ,equipement_test_fonction.[gkey_type_engin] " + 
				"      ,equipement_test_fonction.[gkey_responsable] " + 
				"      ,R1.[nom] " + 
				"  FROM [dbo].[equipement_test_fonction] " + 
				"  INNER JOIN [user] R1 ON R1.gkey = [gkey_responsable] " + 
				"  WHERE [type] =  ? " + 
			    "ORDER BY [gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, type);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(new Base_view_equipment(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getInt(7),
							result.getInt(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_contenu> select_contenu(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_contenu> liste = FXCollections.observableArrayList();
		String query  ="SELECT contenu.[gkey] " + 
				"      , contenu.[commentaire] " + 
				"      , contenu.[status] " + 
				"      , contenu.[dateModification] " + 
				"      , contenu.[gkey_fiche_verification] " + 
				"      , contenu.[gkey_equipement] " + 
				"      , contenu.[modificateur] " + 
				"	  ,R1.[date] " + 
				"	  ,R2.[libelle] " + 
				"	  ,R4.libelle"+
				"	  ,R3.[nom] " + 
				"      , contenu.[isClosed] " + 
				"      , contenu.[reparer] " + 
				"      , contenu.[commentaire_reparation] " +
				"	  ,R5.[nom] " +
				"  FROM [dbo].[contenu] " + 
				"   INNER JOIN [fiche_verification] R1 ON R1.gkey = [gkey_fiche_verification] " + 
				"   INNER JOIN [equipement_test_fonction] R2 ON R2.gkey = [gkey_equipement] " + 
				"   FULL OUTER JOIN [user] R3 ON R3.gkey = contenu.[modificateur] " + 
				"	FULL OUTER JOIN [engin] R4 ON R4.gkey = (SELECT engin.[gkey] FROM [engin] WHERE engin.[gkey] = R1.gkey_engin)"+
				"   FULL OUTER JOIN [user] R5 ON R5.gkey = R1.[operateur] " +
				"  WHERE contenu.[gkey] IS NOT NULL "+
				"  AND (contenu.[status] = 0  OR contenu.[status] IS NULL) "+
				"  AND (contenu.[isClosed] = 0 OR contenu.[isClosed] IS NULL  )  "+
				"  ORDER BY contenu.[gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_contenu(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6),
							result.getInt(7),
							result.getString(8),
							result.getString(9),
							result.getString(10),
							result.getString(11),
							result.getBoolean(12),
							result.getBoolean(13),
							result.getString(14),
							result.getString(15)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_contenu> select_contenu_closed(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_contenu> liste = FXCollections.observableArrayList();
		String query  ="SELECT contenu.[gkey] " + 
				"      , contenu.[commentaire] " + 
				"      , contenu.[status] " + 
				"      , contenu.[dateModification] " + 
				"      , contenu.[gkey_fiche_verification] " + 
				"      , contenu.[gkey_equipement] " + 
				"      , contenu.[modificateur] " + 
				"	  ,R1.[date] " + 
				"	  ,R2.[libelle] " + 
				"	  ,R4.libelle"+
				"	  ,R3.[nom] " + 
				"      , contenu.[isClosed] " + 
				"      , contenu.[reparer] " + 
				"      , contenu.[commentaire_reparation] " +
				"	  ,R5.[nom] " +
				"  FROM [dbo].[contenu] " + 
				"   INNER JOIN [fiche_verification] R1 ON R1.gkey = [gkey_fiche_verification] " + 
				"   INNER JOIN [equipement_test_fonction] R2 ON R2.gkey = [gkey_equipement] " + 
				"   FULL OUTER JOIN [user] R3 ON R3.gkey = contenu.[modificateur] " + 
				"	FULL OUTER JOIN [engin] R4 ON R4.gkey = (SELECT engin.[gkey] FROM [engin] WHERE engin.[gkey] = R1.gkey_engin)"+
				"   FULL OUTER JOIN [user] R5 ON R5.gkey = R1.[operateur] " +
				"  WHERE contenu.[gkey] IS NOT NULL "+
				"  AND (contenu.[status] = 0  OR contenu.[status] IS NULL) "+
				"  ORDER BY contenu.[gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_contenu(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6),
							result.getInt(7),
							result.getString(8),
							result.getString(9),
							result.getString(10),
							result.getString(11),
							result.getBoolean(12),
							result.getBoolean(13),
							result.getString(14),
							result.getString(15)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_contenu> select_contenu_technicien(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_contenu> liste = FXCollections.observableArrayList();
		String query  ="SELECT contenu.[gkey] " + 
				"      , contenu.[commentaire] " + 
				"      , contenu.[status] " + 
				"      , contenu.[dateModification] " + 
				"      , contenu.[gkey_fiche_verification] " + 
				"      , contenu.[gkey_equipement] " + 
				"      , contenu.[modificateur] " + 
				"	  ,R1.[date] " + 
				"	  ,R2.[libelle] " + 
				"	  ,R4.libelle"+
				"	  ,R3.[nom] " + 
				"      , contenu.[isClosed] " + 
				"      , contenu.[reparer] " + 
				"      , contenu.[commentaire_reparation] " +
				"	  ,R5.[nom] " +

				"  FROM [dbo].[contenu] " + 
				"   INNER JOIN [fiche_verification] R1 ON R1.gkey = [gkey_fiche_verification] " + 
				"   INNER JOIN [equipement_test_fonction] R2 ON R2.gkey = [gkey_equipement] " + 
				"   FULL OUTER JOIN [user] R3 ON R3.gkey = contenu.[modificateur] " + 
				"	FULL OUTER JOIN [engin] R4 ON R4.gkey = (SELECT engin.[gkey] FROM [engin] WHERE engin.[gkey] = R1.gkey_engin)"+
				"   FULL OUTER JOIN [user] R5 ON R5.gkey = R1.[operateur] " +

				"  WHERE contenu.[gkey] IS NOT NULL "+
				"  AND (contenu.[status] = 0  OR contenu.[status] IS NULL) "+
				"  AND (contenu.[reparer] = 0  OR contenu.[reparer] IS NULL) "+
				"  AND R2.[gkey_responsable]  = ? "+
				"  AND (contenu.[isClosed] = 0 OR contenu.[isClosed] IS NULL  )  "+
				"  ORDER BY contenu.[gkey] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			System.out.println("REPONSABLE " + CONFIG.user.gkey);
			preparedStatement.setInt(1,CONFIG.user.gkey);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_contenu(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6),
							result.getInt(7),
							result.getString(8),
							result.getString(9),
							result.getString(10),
							result.getString(11),
							result.getBoolean(12),
							result.getBoolean(13),
							result.getString(14),
							result.getString(15)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<Base_view_contenu> select_contenu_technicien_closed(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_contenu> liste = FXCollections.observableArrayList();
		String query  ="SELECT contenu.[gkey] " + 
				"      , contenu.[commentaire] " + 
				"      , contenu.[status] " + 
				"      , contenu.[dateModification] " + 
				"      , contenu.[gkey_fiche_verification] " + 
				"      , contenu.[gkey_equipement] " + 
				"      , contenu.[modificateur] " + 
				"	  ,R1.[date] " + 
				"	  ,R2.[libelle] " + 
				"	  ,R4.libelle"+
				"	  ,R3.[nom] " + 
				"      , contenu.[isClosed] " + 
				"      , contenu.[reparer] " + 
				"      , contenu.[commentaire_reparation] " +
				"	  ,R5.[nom] " +

				"  FROM [dbo].[contenu] " + 
				"   INNER JOIN [fiche_verification] R1 ON R1.gkey = [gkey_fiche_verification] " + 
				"   INNER JOIN [equipement_test_fonction] R2 ON R2.gkey = [gkey_equipement] " + 
				"   FULL OUTER JOIN [user] R3 ON R3.gkey = contenu.[modificateur] " + 
				"	FULL OUTER JOIN [engin] R4 ON R4.gkey = (SELECT engin.[gkey] FROM [engin] WHERE engin.[gkey] = R1.gkey_engin)"+
				"   FULL OUTER JOIN [user] R5 ON R5.gkey = R1.[operateur] " +

				"  WHERE contenu.[gkey] IS NOT NULL "+
				"  AND (contenu.[status] = 0  OR contenu.[status] IS NULL) "+
				"  AND R2.[gkey_responsable]  = ? "+
				"  ORDER BY contenu.[gkey] DESC ";
		try {
			
			preparedStatement = connection.prepareStatement(query);
			System.out.println("REPONSABLE " + CONFIG.user.gkey);
			preparedStatement.setInt(1,CONFIG.user.gkey);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_contenu(
							result.getInt(1),
							result.getString(2),
							result.getBoolean(3),
							result.getString(4),
							result.getInt(5),
							result.getInt(6),
							result.getInt(7),
							result.getString(8),
							result.getString(9),
							result.getString(10),
							result.getString(11),
							result.getBoolean(12),
							result.getBoolean(13),
							result.getString(14),
							result.getString(15)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	public ObservableList<Base_view_user> select_user(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[nom] " + 
				"      ,[email] " + 
				"      ,[adresse] " + 
				"      ,[numtel] " + 
				"      ,[privilege] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " +
				"      ,[id] " + 
				"      ,[pass] " + 
				"  FROM [dbo].[user] " +
			    " ORDER BY [nom] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_user(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getString(7),
							result.getString(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11),
							result.getString(12)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	
	public Base_view_user select_user(String id){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[nom] " + 
				"      ,[email] " + 
				"      ,[adresse] " + 
				"      ,[numtel] " + 
				"      ,[privilege] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"      ,[id] " + 
				"      ,[pass] " + 
				"  FROM [dbo].[user] "+
				" WHERE [id] = ?" +
			    " ORDER BY [nom] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_user(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getString(7),
							result.getString(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11),
							result.getString(12)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(!liste.isEmpty())
			return liste.get(0) ; 
		else 
			return null;
	}
	
	public Base_view_user select_user(String id , String pass) throws SQLException{
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[nom] " + 
				"      ,[email] " + 
				"      ,[adresse] " + 
				"      ,[numtel] " + 
				"      ,[privilege] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"      ,[id] " + 
				"      ,[pass] " + 
				"  FROM [dbo].[user] "+
				" WHERE [id] = ?" +
				" AND [pass] = ? "+
			    " ORDER BY [nom] DESC ";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pass);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_user(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getString(7),
							result.getString(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11),
							result.getString(12)
							));
			}
	
		if(!liste.isEmpty())
			return liste.get(0) ;
		return null ;
	}
	
	
	public void update_user(Base_view_user user){
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  =
				"UPDATE [dbo].[user] SET "+
				"  [nom] = ? "+
		        " ,[email] = ? " +
				" ,[adresse] = ? "+
				" ,[numtel] = ? "+
				" ,[privilege] = ? "+
				" ,[dateCreation] = ? "+
				" ,[dateModification] = ? "+
				" ,[createur] = ? "+
		        " ,[modificateur] = ? "+
		        " ,[id] = ? "+
		        " ,[pass] = ? "+
	      		" WHERE [gkey] = ? ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, user.fieldNom.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(2, user.fieldMail.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(3, user.fieldAddress.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(4, user.fieldNumtel.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(5, user.fieldType.getValue().getSelectionModel().getSelectedItem(),Types.NVARCHAR);
			preparedStatement.setObject(6, getObjetString(user.dateCreation),Types.TIMESTAMP);
			preparedStatement.setObject(7, dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(8, user.createur,Types.BIGINT);
			preparedStatement.setObject(9, CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(10, user.fieldId.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(11, user.fieldPass.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(12, user.gkey,Types.BIGINT);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update_engin(Base_view_engin engin){
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  =
				"UPDATE [dbo].[engin] "+ 
						   "SET [libelle] = ? "+
						      ",[dateCreation] = ? "+
						      ",[dateModification] = ? " +
						      ",[createur] = ? " +
						      ",[modificateur] = ? " +
						      ",[gkey_type_engin] = ? " +
						      ",[gkey_responsable] = ? " +
						" WHERE [gkey] = ? " ;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, engin.libelle.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(2, engin.dateCreation.getValue(),Types.TIMESTAMP);
			preparedStatement.setObject(3, dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(4, engin.createur,Types.BIGINT);
			preparedStatement.setObject(5, CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(6, engin.getGkeyTypeEngin(),Types.BIGINT);
			preparedStatement.setObject(7, engin.getGkeyResponsable(),Types.BIGINT);
			preparedStatement.setObject(8, engin.gkey,Types.BIGINT);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update_contenu(Base_view_contenu contenu){
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  ="UPDATE [dbo].[contenu] "+
				   	 "SET [dateModification] = ? "+  
					      ",[modificateur] = ? "+
					      ",[isClosed] = ? " +
					      ",[reparer] = ? " +
					      ",[commentaire_reparation] = ? " +
					 "WHERE [gkey] = ? ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(2, CONFIG.user.gkey,Types.BIGINT);
			preparedStatement.setObject(3, contenu.isClosed.getValue().isSelected(),Types.BIT);
			preparedStatement.setObject(4, contenu.reparer.getValue().isSelected(),Types.BIT);
			preparedStatement.setObject(5, contenu.commentaireRP.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(6, contenu.gkey,Types.BIGINT);
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update_type_engin(Base_view_type_engin typeEngin) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		String query  = " UPDATE [dbo].[type_engin] "+
				   		" SET [libelle] = ? " +
				   			 ",[dateModification] = ? "+
				   			 ",[modificateur] = ? "+
				   		  "WHERE [gkey] = ? " ;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, typeEngin.libelle.getValue().getText(),Types.NVARCHAR);
			preparedStatement.setObject(2, dtf.format(now).toString(),Types.TIMESTAMP);
			preparedStatement.setObject(3, CONFIG.user.gkey,Types.BIT);
			preparedStatement.setInt(4, typeEngin.gkey);
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update_equipement(Base_view_equipment equipement) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(dtf.format(now).toString());
		
		PreparedStatement  preparedStatement = null ; 
		String query  = "UPDATE [dbo].[equipement_test_fonction] "+
				   		"SET [libelle] = ? " +
				   		",[critique] = ? "+
				   		",[dateModification] = ? "+
				   		",[modificateur] = ? " +
				   		",[gkey_responsable] = ? "+
				   		"WHERE [gkey] = ? "  ;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, equipement.libelle.getValue().getText() ,Types.NVARCHAR);
			preparedStatement.setObject(2, equipement.critique.getValue().isSelected(),Types.BIT);
			preparedStatement.setObject(3,dtf.format(now).toString() ,Types.TIMESTAMP);
			preparedStatement.setObject(4, CONFIG.user.gkey,Types.BIT);
			preparedStatement.setObject(5, equipement.getIdResponsable(),Types.BIGINT);
			preparedStatement.setInt(6, equipement.gkey);
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void deleteUser(Base_view_user user) throws SQLException {
		PreparedStatement  preparedStatement = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  ="DELETE FROM [dbo].[user] "+
			      "WHERE gkey = ? ";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, user.gkey);
		preparedStatement.executeUpdate();
	}

	
	public void delete_engin(Base_view_engin engin) throws SQLException{
		
		
		PreparedStatement  preparedStatement = null ; 
		String query  = " DELETE FROM [dbo].[engin] WHERE [gkey] = ? " ;
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, engin.gkey,Types.BIGINT);
		preparedStatement.executeUpdate();
	}
	
	public void delete_type_engin(Base_view_type_engin engin) throws SQLException{
		
		
		PreparedStatement  preparedStatement = null ; 
		String query  = " DELETE FROM [dbo].[type_engin] WHERE [gkey] = ? " ;

		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, engin.gkey,Types.BIGINT);
		preparedStatement.executeUpdate();

	}
		
	public void delete_equipement(Base_view_equipment equip) throws SQLException{
		
		
		PreparedStatement  preparedStatement = null ; 
		String query  = " DELETE FROM [dbo].[equipement_test_fonction]  WHERE [gkey] = ? " ;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setObject(1, equip.gkey,Types.BIGINT);
			preparedStatement.executeUpdate();
	
	}
	
	public ObservableList<Base_view_user> select_user_by_gkey(int gkey){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<Base_view_user> liste = FXCollections.observableArrayList();
		String query  ="SELECT [gkey] " + 
				"      ,[nom] " + 
				"      ,[email] " + 
				"      ,[adresse] " + 
				"      ,[numtel] " + 
				"      ,[privilege] " + 
				"      ,[dateCreation] " + 
				"      ,[dateModification] " + 
				"      ,[createur] " + 
				"      ,[modificateur] " + 
				"      ,[id] " + 
				"      ,[pass] " + 
				"  FROM [dbo].[user] "+
				" WHERE [gkey] = ?" +
			    " ORDER BY [nom] DESC ";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, gkey);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
					liste.add(new Base_view_user(
							result.getInt(1),
							result.getString(2),
							result.getString(3),
							result.getString(4),
							result.getString(5),
							result.getString(6),
							result.getString(7),
							result.getString(8),
							result.getInt(9),
							result.getInt(10),
							result.getString(11),
							result.getString(12)
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<String> select_distinct_engin(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<String> liste = FXCollections.observableArrayList();
		String query  ="SELECT DISTINCT [libelle] FROM [Claims].[dbo].[engin] ORDER BY [libelle]";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<String> select_distinct_equipement(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<String> liste = FXCollections.observableArrayList();
		String query  ="SELECT DISTINCT [libelle] FROM [Claims].[dbo].[equipement_test_fonction] ORDER BY [libelle]";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	
	public ObservableList<String> select_distinct_user(){
		PreparedStatement  preparedStatement = null ; 
		ResultSet result = null ; 
		ObservableList<String> liste = FXCollections.observableArrayList();
		String query  ="SELECT DISTINCT [nom] FROM [Claims].[dbo].[user] ORDER BY [nom]";
		try {
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()) {

					liste.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste ; 
	}
	

	
	public Float getObjetFloat(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				return Float.valueOf(str.getValue());
			}
		}
		return null;
	}
	
	public Integer getObjetInt(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				return Integer.valueOf(str.getValue());
			}
		}
		return null;
	}
	
	public Integer getObjetBit(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				return Integer.valueOf(str.getValue());
			}
		}
		return null;
	}
	
	public String getObjetString(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				return str.getValue();
			}
		}
		return null;
	}
	
	public Object getObjetInteger(IntegerProperty str) {
		if(str!=null)
		 if(str.getValue() != null) {
			if(str.getValue().equals(null)) {
				return  null;
			}
			else {
				return str.getValue();
			}
		}
		return  null;
	}
	
	public java.sql.Timestamp getObjetTimeStamp(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				System.out.println("DATE  : "+ str.getValue());
				return  java.sql.Timestamp.valueOf(str.getValue());
			}
		}
		return null;
	}
	
	public java.sql.Date getObjetDate(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				System.out.println("DATE  : "+ str.getValue());
				return  java.sql.Date.valueOf(str.getValue());
			}
		}
		return null;
	}
	
	public String getObjetTime(StringProperty str) {
		if(str!=null)
		if(str.getValue() != null) {
			if(str.getValue().equals("null")) {
				return null;
			}
			else {
				System.out.println("Le temp est assassin et emporte avec lui les rire des enfants  "+ str.getValue());
				return  str.getValue();
			}
		}
		return null;
	}
	
}
