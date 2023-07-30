package db;

public class DBConnection {
    private static DBConnection instance;
    private DBConnection(){

    }
    public static DBConnection getInstance(){
        if(null==instance){
            instance=new DBConnection();
        }
        return instance;
    }
}
