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

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.google.common.collect.ImmutableList;

public class DataRow {

	public final TableDescriptor tableDescriptor;
	public final ImmutableList<Object> columnValues;

	public DataRow(TableDescriptor tableDescriptor, ImmutableList<Object> columnValues) {
		super();
		this.tableDescriptor = tableDescriptor;
		this.columnValues = columnValues;
	}

	public final Object get(int index) {
		tableDescriptor.requireInRange(index);
		return columnValues.get(index);
	}

	public Object[] getColumnValues() {
		int count = tableDescriptor.getColumnCount();
		Object[] values = new Object[count];
		for (int i = 0; i < values.length; i++)
			values[i] = columnValues.get(i);

		return values;
	}

	/**
	 *  Format some values, to be better readable.
	 * 
	 * @return
	 */
	public Object[] getColumnValues2() {
		
		SimpleDateFormat fmte = new SimpleDateFormat("dd.MM.yyyy");

		Object[] orderedValues = getColumnValues();
		for (int i = 0; i < orderedValues.length; i++) {
			Object object = orderedValues[i];
			if (object instanceof Date) {
				Date date = (Date) object;
				orderedValues[i] = fmte.format(date);
			}
		}

		return orderedValues;
	}

}
