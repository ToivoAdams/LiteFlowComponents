package lite.flow.example.flow;

import static lite.flow.api.flow.define.FlowBuilder.*;

import lite.flow.api.flow.define.Flow;
import lite.flow.component.SqlReader;

public class ExampleFlow {
	public static Flow flowSql = defineFlow("flowSql")
			.component("CustomerReader", 		SqlReader.class, 			 10, 180)
			.parameter("sqlSelect", "select * from Customer where personId= :personId")
			.parameter("queryTimeout", 15)
			
			.component("ProductsReader", 		SqlReader.class, 			 10, 180)
			.parameter("sqlSelect", "select * from Product")
			.parameter("queryTimeout", 15)
			

			.get();

}
