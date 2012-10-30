import de.bezier.data.sql.*;


void setup()
{
  IMDB imdb=new IMDB(this);
  imdb.query();
}


public class IMDB{
MySQL msql;
PApplet context;

public IMDB(PApplet context){
  this.context=context;
}

public void query()
{

    String user     = "root";
    String pass     = "root";
    String database = "cs424imdb";

    msql = new MySQL( context, "localhost", database, user, pass );
    
    if ( msql.connect() )
    {
        msql.query( "SELECT COUNT(*) FROM title" );
        msql.next();
        println( "number of rows: " + msql.getInt(1) );
    }
    else
    {
      
    }
}
}

