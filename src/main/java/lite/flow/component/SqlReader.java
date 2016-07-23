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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

	public Table read(Map<String,?> sqlParams) throws SQLException {

		JdbcTemplate template = new JdbcTemplate(database);
		template.setQueryTimeout(queryTimeout);
		NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(template);
		
		Table data = named.query(sqlSelect, sqlParams, new TableExtractor());
		return data;
	}

	class TableExtractor implements ResultSetExtractor<Table> {

		@Override
		public Table extractData(ResultSet rs) throws SQLException, DataAccessException {
	        Table data = TableBuilder.toTable(rs, maxRows);
	        return data;
		}
	}
}
