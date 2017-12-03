package positronix.lantern;

import java.util.*;
import java.sql.*;

public class Lantern {
	
    public static void main(String[] args) throws Exception {
	    Class.forName("org.h2.Driver");
	    Connection conn = DriverManager.getConnection("jdbc:h2:~/Databases/PathfinderSpells", "sa", "");
	    Statement s = conn.createStatement();
	    String q = "select * from Master where Name = 'Magic Missile'";
	    ResultSet rs = s.executeQuery(q);
	    while (rs.next()) {
	    	System.out.println(rs.getString("Name") + "\n" + rs.getString("Description"));
	    }
	    conn.close();
    }
    
    
}
