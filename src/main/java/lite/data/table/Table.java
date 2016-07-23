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

import java.util.Formatter;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

/**
 *	Simple tabular data structure.
 * Data is hold in rows and columns.
 * Columns have names.
 *  
 * @author ToivoAdams
 *
 */
public class Table {

	public final TableDescriptor tableDescriptor;
	public final ImmutableList<DataRow> dataRows;
	
	public Table(TableDescriptor tableDescriptor, ImmutableList<DataRow> dataRows) {
		super();
		this.tableDescriptor = tableDescriptor;
		this.dataRows = dataRows;
	}
	
	public String toString(int level) {

		String indent = createIndent(level);

		int width = 20;
		StringBuilder formatBuilder = new StringBuilder(indent);
		for (String name : tableDescriptor.columnNames)
			formatBuilder.append("%" + width + "s");
		formatBuilder.append("\n");
		String format = formatBuilder.toString();

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.US);
		
		createHeader(indent, format, formatter);

		for (DataRow row : dataRows) {
			
			formatter.format(format, row.getColumnValues2());
		}

		return sb.toString();
	}

	private void createHeader(String indent, String format, Formatter formatter) {
		formatter.format( indent + "%-60s\n", tableDescriptor.name);
		formatter.format( indent + "====================================================================================================\n");
		formatter.format( format, (Object[]) tableDescriptor.getColumnNames());
		formatter.format( indent + "----------------------------------------------------------------------------------------------------\n");
	}

	public String createIndent(int level) {
		return "                                                ".substring(0, level * 2);
	}

	public String toString() {
		return "\n" + toString(2);
	}

}
