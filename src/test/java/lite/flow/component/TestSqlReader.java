package lite.flow.component;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import lite.data.table.Table;

public class TestSqlReader {

    final static String DB_LOCATION = "target/db";


	@Test
	public void testToTable() throws ClassNotFoundException, SQLException, IOException {
		
        final File dbLocation = new File(DB_LOCATION);
        FileUtils.deleteDirectory(dbLocation);

        DataSource database = createDataSource();
        
		try (Connection con = database.getConnection()) {
			try (Statement stmt = con.createStatement()) {
		        try {
		            stmt.execute("drop table Customer");
		        } catch (final SQLException sqle) {
		        }

		        stmt.execute("create table Customer (name varchar(40), email varchar(40), phone	varchar(40), address varchar(60), city varchar(40))");

		        stmt.execute("insert into Customer (name, email, phone, address, city) VALUES ('Bob Wollis', '', '54675 78969' , '639 Main St', 'Alabama')");
		        stmt.execute("insert into Customer (name, email, phone, address, city) VALUES ('James Oswald Jr', '', '674 5467 3783' , '3 Mcauley Dr', 'Paris')");
		        stmt.execute("insert into Customer (name, email, phone, address, city) VALUES ('May Hunberdad', '', '435 5468 38993' , '1134 Center St', 'Cholamabanga')");
			}
		}

        SqlReader sqlReader = new SqlReader(createDataSource());
        sqlReader.sqlSelect = "select * from Customer";
        
        Map<String,?> sqlParams = new HashMap<>();
        
        Table customers = sqlReader.read(sqlParams);
        assertNotNull("select result data sould not be null", customers);
        System.out.println(customers);
        
	}

    public DataSource createDataSource() throws ClassNotFoundException, SQLException {
    	
    	BasicDataSource basicDataSource = new BasicDataSource();
    	basicDataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
    	basicDataSource.setUrl("jdbc:derby:" + DB_LOCATION + ";create=true");
    	return basicDataSource;
    }
}
