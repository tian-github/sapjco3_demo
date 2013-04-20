package com.test.share.sap;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;

public class SapHelper {

	/**
	 * 根据连接池，rfc 名称获取rfc函数模板
	 * 
	 * @param rfcName
	 * @param poolName
	 * @return
	 * @throws Exception
	 */
	public static JCoFunctionTemplate getRfcTemplate(String rfcName,
			String fdPoolName) throws Exception {
		JCoDestination destination = SapJcoStore.getDestination(fdPoolName);
		JCoRepository repository = null;
		try {
			repository = destination.getRepository();
		} catch (Exception e) {
			destination = JCoDestinationManager.getDestination(fdPoolName);
			SapJcoStore.destinations.remove(fdPoolName);
			SapJcoStore.destinations.put(fdPoolName, destination);
			repository = destination.getRepository();
		}
		JCoFunctionTemplate template = repository.getFunctionTemplate(rfcName);
		return template;
	}

	/**
	 * 根据RFC 函数名称,连接池获取函数
	 * @param rfcName
	 * @param fdPoolName
	 * @param store
	 * @return
	 * @throws Exception
	 */
	public static JCoFunction getRfcFunction(String rfcName, String fdPoolName) throws Exception {
		JCoFunctionTemplate template = getRfcTemplate(rfcName, fdPoolName);
		return template.getFunction();
	}
	
	

}
