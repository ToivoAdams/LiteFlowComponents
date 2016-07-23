/**
 * Copyright 2016 ToivoAdams
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lite.flow.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import lite.data.table.Table;
import lite.data.table.TableBuilder;
import lite.flow.api.activity.Parameter;

/**
 * 	Reads data from SQL database using select statement.
 * Any JDBC compatible database can be used. 
 * 
 * @author ToivoAdams
 *
 */
public class SqlReader {
	
	protected final DataSource database;
	
	@Parameter
	String sqlSelect;
	
	/**
	 * Maximum number of rows can be read.
	 */
	@Parameter
	int maxRows = 1000;
	
	/**
	 * The maximum amount of time allowed for a running SQL select query in seconds,
	 * zero means there is no limit. Max time less than 1 second will be equal to zero.
	 */
    @Parameter
    int queryTimeout = 0;

	/**
	 *  database is resource which will be provided by framework.
	 * @param database
	 */
	public SqlReader(DataSource database) {
		super();
		this.database = database;
	}

	public Table read(Map<String,Object> sqlParams) throws SQLException {
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(database);
		
		try (Connection con = database.getConnection()) {
			try (PreparedStatement stmt = con.prepareStatement(sqlSelect)) {
				stmt.setQueryTimeout(queryTimeout);
		//		stmt.set
		        ResultSet rs = stmt.executeQuery(sqlSelect);
		        Table data = TableBuilder.toTable(rs, 5000);
		        return data;
			}
		}
		
	}

}
