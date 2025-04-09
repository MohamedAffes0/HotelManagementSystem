import DatabaseManagement.Connect;
import java.util.ArrayList;
public class Room
{

//Private Attributes
private int id_chambre;
private int etage;
private int nb_personnes;
private int prix_nuit;
private String etat;
private String type_chambre;

//Constructors
//1-with parameters
public Room(int id_chambre, int etage, int nb_personnes,int prix_nuit,String etat,String type_chambre)
{
    this.id_chambre=id_chambre;
    this.etage=etage;
    this.nb_personnes=nb_personnes;
    this.prix_nuit=prix_nuit;
    this.etat=etat;
    this.type_chambre=type_chambre;

}

//2-without parameters
public Room()
{

}

//Getters and Setters
public int getIdChambre()
{
    return this.id_chambre;
}
public void setIdChambre(int etage)
{
    this.id_chambre=id_chambre;
}

public int getEtage()
{
    return this.etage;
}
public void setEtage(int etage)
{
    this.etage=etage;
}


public int getNbPersonnes()
{
    return this.etage;
}
public void setNbPersonnes(int nb_personnes)
{
    this.nb_personnes=nb_personnes;
}


public int getPrixNuit()
{
    return this.etage;
}
public void setPrixNuit(int prix_nuit)
{
    this.prix_nuit=prix_nuit;
}

public int getEtat()
{
    return this.etage;
}
public void setEtat(String etat)
{
    this.etat=etat;
}

public int getTypeChambre()
{
    return this.etage;
}
public void setTypeChambre(String type_chambre)
{
    this.type_chambre=type_chambre;

}

//Add a room to the database

public void Add_Room(Room new_room) {
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String insert_query = "INSERT INTO Chambre VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStmt = con.prepareStatement(insert_query);
        preparedStmt.setInt(1,room.getIdChambre());
        preparedStmt.setInt(2, room.getEtage());
        preparedStmt.setInt(3,room.getNbPersonnes());
        preparedStmt.setDouble(4,room.getPrixNuit());
        preparedStmt.setString(5,room.getEtat());
        preparedStmt.setString(6,room.getTypeChambre());
        preparedStmt.executeUpdate();
        con.close();
    }
    catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
}

//Delete a room from the database
public void DeleteChambre(int id) {
    try {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String delete_query = "DELETE FROM CHAMBRE WHERE ID_CHAMBRE=?;";
        PreparedStatement preparedStmt = con.prepareStatement(delete_query);
        preparedStmt.setInt(1,id);
        preparedStmt.executeUpdate();
        con.close();
        }
        catch(SQLException err)
        {
                    System.out.println(err.getMessage());
        }
    }


//Modify a specific room in the database

//1-update the status of the room
public void update_etat(String etat_chambre,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET etat=? WHERE id_chambre=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setString(1,etat_chambre);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}

//2-modify one of the properties of a specific room

//a-etage
public void update_etage(int c_etage,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET etage=? WHERE id_chambre=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setInt(1,c_etage);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}

//b-nb_personnes
public void update_nb_personnes(int nb,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET nb_personnes=? WHERE id_chambre=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setInt(1,nb);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}

//c-type_chambre
public void update_type_chambre(String type,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET type_chambre=? WHERE id_chambre=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setString(1,type);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}

//d-prix_nuit
public void update_prix(int prix,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET prix=? WHERE id_chambre=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setInt(1,prix);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}
    
//Interrogation de la base de données
//Afficher les chambres et leurs états
public ArrayList<Room> afficher_chambres_états()
{
    try
    {
        
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        Statement stmt=con.createStatement();
        String query="select * from chambre";
        ResultSet rs=stmt.executeQuery(query);
        ArrayList<Room> listRooms = new ArrayList<Room>();
        while(rs.next())
        {
            Room room = new Room();
            room.setIdChambre(rs.getInt(1));
            room.setEtage(rs.getInt(2));
            room.setNbPersonnes(rs.getInt(3));
            room.setPrixNuit(rs.getInt(4));
            room.setEtat(rs.getString(5));
            room.setTypeChambre(rs.getString(6));
            listRooms.add(room);
        }
        con.close();
        return listRooms;


    }
    
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }
}
}
