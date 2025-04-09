public class Employe extends Personne
{
    private String mdp;
    private int salaire;
    private String is_admin;
    public Employe()
    {

    }
    public Employe(int cin,String nom,String prenom,String mail,int tel,String mdp,int salaire,String is_admin)
    {
        super(cin,nom,prenom,mail,tel);
        this.mdp=mdp;
        this.salaire=salaire;
        this.is_admin=is_admin;

    }
public String getMdp()
{
    return this.mdp;
}
public void setMdp(String mdp)
{
    this.mdp=mdp;
}

public int getSalaire()
{
    return this.cin;
}
public void setSalaire(int salaire)
{
    this.salaire=salaire;
}

public String getIsAdmin()
{
    return this.is_admin;
}
public void setIsAdmin(int is_admin)
{
    this.is_admin=is_admin;
}
}
