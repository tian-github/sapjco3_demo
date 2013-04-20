package com.test.share.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;

public class Test {
	
	/**
	 * Test Git update
	 * 
	 * 测试的 数据格式
	 * ---------------------------------------------
	 * |		(BAPI 名称)BAPI NAME :BAPI_AR_ACC_GETOPENITEMS
	 * |--------------------------------------------
	 * |IMPORT(传入的数据)
	 * |----------------------------------------------------
	 * |COMPANYCODE:8880    |    CUSTOMER:0099000032  | KEYDATE:20070830  
	 * |----------------------------------------------------
	 * |EXPORT TABLES:LINEITEMS(返回的表格数据)
	 * |-------------------------------------------------------------------------------------------
	 * |COMP_CODE(公司代码) |  CUSTOMER(客户编号1)|SP_GL_IND(特别总/分类帐指示符)|ALLOC_NMBR(分配编号).....
	 * |-------------------------------------------------------------------------------------------
	 * |                    |					 |                             | 	
	 * |-------------------------------------------------------------------------------------------
	 */
	
	public static void main(String[] args) throws Exception {
		
		
//		初始化，创建连接池
		SapJcoStore.initStore("testSap");
//		获取传输对象 JCoFunction
		JCoFunction  func=SapHelper.getRfcFunction("BAPI_AR_ACC_GETOPENITEMS", "testSap");
//		向JCoFunction 填充数据
		func= fillData(func);
		SapProxy proxy =new SapProxy();
		SapExecutor exec=new SapExecutor(proxy);
//		执行交互
		JCoFunction result =(JCoFunction)exec.processSap("testSap", func);
//		返回结果处理
		resultPrint(result, "LINEITEMS");
	}
	
	/** 
	 * 填充数据,根据上面表格
	 * 在SAP中间件其实也就是解析在填充数据这里加工很多配置,实现同理
	 * 
	 */
	public static JCoFunction fillData(JCoFunction jcoFunc){
		
		JCoRecord  record =jcoFunc.getImportParameterList();
		
//		构造业务数据
		Map<String, Object> importData=new HashMap<String, Object>();
		importData.put("COMPANYCODE", "8880");
		importData.put("CUSTOMER", "0099000032");
		importData.put("KEYDATE", "20070830");
//		填充传入值
		for(String key:importData.keySet()){
			Object value=importData.get(key);
			record.setValue(key, value);
		}
		
//		上面构造数据只有传入的参数数据,下面是传入table的数据
		
		/*设置值
		 
		JCoTable jcoTable= jcoFunc.getTableParameterList().getTable("");
		
		for(){
		
//		增加一行数据，同时游标也只想了当前行
		jcoTable.appendRow();
//		设置当前行的某个列的值
		jcoTable.setValue("", "");
		jcoTable.setValue("", "");
		....
		
		}
		
		*/
		return jcoFunc;
	}
	
	/**
	 * 
	 * @param jcoFunction
	 * @param tableName
	 */
	public static void resultPrint(JCoFunction jcoFunction,String tableName){
		
//		需要打印的列名称
		List<String> printKey=new ArrayList<String>();
		printKey.add("COMP_CODE");
		printKey.add("CUSTOMER");
		printKey.add("SP_GL_IND");
		printKey.add("ITEM_TEXT");
		
//		得到table
		JCoTable table =jcoFunction.getTableParameterList().getTable(tableName);
//		取得行
		int rows=table.getNumRows();
		for(int i=0,len=rows;i<len;i++){
//			游标移动到row
			table.setRow(i);
			JCoFieldIterator iterator = table.getFieldIterator();
			while (iterator.hasNextField()) {
				// System.out.println(parameterField.getName()+"JCoFieldIterator");
				JCoField recordField = iterator.nextField();
				
				String fieldName=recordField.getName();
//				过滤不需要的字段
				if(!printKey.contains(fieldName))
				{
					continue;
				}
				
//				取值打印
//				Object  value=recordField.getValue();
				String val_str=recordField.getString();
				System.out.print(val_str+" 	");
			}
			System.out.print("\n====================================\n");
		}
		
	}
	
	
	

}
