package lite.data.table;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestTableBuilder {

    final static String DB_LOCATION = "target/db";


	@Test
	public void testToTable() throws ClassNotFoundException, SQLException, IOException {
		
        final File dbLocation = new File(DB_LOCATION);
        FileUtils.deleteDirectory(dbLocation);

        final Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("create table Customer (name varchar(40), email varchar(40), phone	varchar(40), address varchar(60), city varchar(40))");

        stmt.execute("insert into Customer (name, email, phone, address, city) VALUES ('Bob Wollis', '', '54675 78969' , '639 Main St', 'Alabama')");
        stmt.execute("insert into Customer (name, email, phone, address, city) VALUES ('James Oswald Jr', '', '674 5467 3783' , '3 Mcauley Dr', 'Paris')");
        stmt.execute("insert into Customer (name, email, phone, address, city) VALUES ('May Hunberdad', '', '435 5468 38993' , '1134 Center St', 'Cholamabanga')");

        ResultSet rs = stmt.executeQuery("select * from Customer");
        Table customers = TableBuilder.toTable(rs, 5000);
        System.out.println(customers);
        
	}

    public Connection getConnection() throws ClassNotFoundException, SQLException {
    	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    	final Connection con = DriverManager.getConnection("jdbc:derby:" + DB_LOCATION + ";create=true");
    	return con;
    }

}
