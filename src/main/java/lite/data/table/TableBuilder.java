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
package lite.data.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;


public class TableBuilder {

	public static Table toTable(ResultSet rs, int max) throws SQLException {
		Objects.requireNonNull(rs, "TableBuilder.toTable resultset argument cannot be null");
		
		TableDescriptor tableDescriptor = createTableDescriptor(rs);
		ArrayList<DataRow> rows = new ArrayList<>();
		ArrayList<Object> row = new ArrayList<>(tableDescriptor.getColumnCount());
		
		int nrOfRows = 0;
		while (rs.next()) {
			if (nrOfRows++ > max)
				throw new IllegalArgumentException("Can't read from ResultSet more than max=" + max + " rows");

			for (int i = 1; i <= tableDescriptor.getColumnCount(); i++) {
				Object value = rs.getObject(i);
				row.add(i-1, value);
			}
			rows.add(new DataRow(tableDescriptor, ImmutableList.copyOf(row)));
		}
		
		return new Table(tableDescriptor, ImmutableList.copyOf(rows));
	}

	public static TableDescriptor createTableDescriptor(ResultSet rs) throws SQLException {
		
		List<String> columnNames = new ArrayList<>();
		
		ResultSetMetaData meta = rs.getMetaData();
		//Note, some databases and queries may not return table name!
		String name = meta.getTableName(1);
		
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			String columnName = meta.getColumnName(i);
	//		int columnType = meta.getColumnType(i);
			columnNames.add(columnName);
		}

		return new TableDescriptor(name, ImmutableList.copyOf(columnNames));
	}
}
