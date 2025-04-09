public class Personne 
{
    //Private Attributes
    private int cin;
    private String nom;
    private String prenom;
    private String mail;
    private int tel;
    
    //Constructors
    //1-with parameters
    public Personne(int cin,String nom,String prenom,String mail,int tel)
    {
        this.cin=cin;
        this.nom=nom;
        this.prenom=prenom;
        this.mail=mail;
        this.tel=tel;
    }

    //2-without parameters
    public Personne()
    {
        
    }

    //Getters and Setters
public int getCin()
{
    return this.cin;
}
public void setCin(int cin)
{
    this.cin=cin;
}

public String getNom()
{
    return this.nom;
}
public void setNom(String nom)
{
    this.nom=nom;
}

public String getPrenom()
{
    return this.prenom;
}
public void setPrenom(String prenom)
{
    this.prenom=prenom;
}

public String getMail()
{
    return this.mail;
}
public void setMail(String mail)
{
    this.mail=mail;
}

public int getTel()
{
    return this.tel;
}
public void setTel(int tel)
{
    this.tel=tel;
}
    
//Add a person to the database
public void AddPerson(Personne pers) {
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String insert_query = "INSERT INTO PERSONNE VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStmt = con.prepareStatement(insert_query);
        preparedStmt.setInt(1,pers.getCin()());
        preparedStmt.setString(2, pers.getNom());
        preparedStmt.setString(3,room.getPrenom());
        preparedStmt.setString(4,room.getMail());
        preparedStmt.setString(5,room.getTel());
        preparedStmt.executeUpdate();
        con.close();
    }
    catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
}

//Delete a person from the database
public void DeletePerson(int id) {
    try {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String delete_query = "DELETE FROM PERSONNE WHERE CIN=?;";
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

//Modify properties
//a-nom
public void update_nom(String name,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET nom=? WHERE cin=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setString(1,name);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}
//b-prenom
public void update_prenom(String pre,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET prenom=? WHERE cin=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setString(1,pre);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}
//c-mail
public void update_tel(int num,int id) 
{
    try 
    {
        String name="root";
        String pass="root";
        String host="jdbc:derby://localhost:1527/test";
        Connection con = DriverManager.getConnection(host,name,pass);
        String update_query = "UPDATE chambre SET tel=? WHERE cin=?";
        PreparedStatement preparedStmt1 = con.prepareStatement(update_query);
        preparedStmt1.setString(1,num);
        preparedStmt1.setInt(2,id);
        preparedStmt1.executeUpdate();
        con.close();
    }
    catch(SQLException err)
    {
                System.out.println(err.getMessage());
    }

}
//d-tel
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
}
