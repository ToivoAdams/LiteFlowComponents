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

import static java.util.Objects.*;
import com.google.common.collect.ImmutableList;

public class TableDescriptor {
	
	public final String name;			// table name
	public final ImmutableList<String> columnNames;

	public TableDescriptor(String name, ImmutableList<String> columnNames) {
		super();
		requireNonNull(name, "TableDescriptor name argument cannot be null");
		requireNonNull(columnNames, "TableDescriptor columnNames argument cannot be null");
		this.name = name;
		this.columnNames = columnNames;
	}
	
	public String[] getColumnNames() {
		String[] columnNames2 = new String[columnNames.size()];
		int i = 0;
		for (String name : columnNames) {
			columnNames2[i++] = name;
		}
		return columnNames2;
	}

	public void requireInRange(int index) { 
		
		if (index >= columnNames.size())
			throw new IllegalArgumentException("TableDescriptor.requireInRange: index cannot be more than number of columns, index=" + index + " nrOfColumns=" + columnNames.size());
		
		if (index < 0)
			throw new IllegalArgumentException("TableDescriptor.requireInRange: index cannot negative, index=" + index);
	}

	public int getColumnCount() {
		return columnNames.size();
	}
}
