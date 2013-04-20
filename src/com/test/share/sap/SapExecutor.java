package com.test.share.sap;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;

/**
 * SAP 执行工具
 * @author fat_tian
 *
 */
public class SapExecutor {

	private SapProxy proxy;
	
	public SapExecutor(SapProxy proxy){
		this.proxy=proxy;
	}

	/**
	 * 
	 * @param poolName 连接池名称
	 * @param function 传输的带数据JCoFunction对象
	 * @return
	 * @throws Exception
	 */
	public Object processSap(String poolName, JCoFunction function)
			throws Exception {
		doBefore();
		try {
			parsePostData(function);
			JCoFunction func = execFunc(function, poolName);
			Object rtn = parseRtnData(func);
			return rtn;
		} catch (Exception e) {
			doError(e);
		} finally {
			doAfter();
		}
		return null;
	}

	public JCoFunction execFunc(JCoFunction func, String poolName)
			throws Exception {
//		取得	 destination 文件执行 
		JCoDestination destination = SapJcoStore.getDestination(poolName);
		func.execute(destination);
		return func;
	}

	public Object parsePostData(Object soapTemplate) {

		// 这里可以对这个xml进行操作~从而达到赋值的效果，这里需要下手写多点代码了做数据绑定
		// 让代理去搞
		return proxy.parsePostData(soapTemplate);
	}

	public Object parseRtnData(Object data) {

		// 这里可以对返回值进行处理,组装符合业务需求的xml
		// 让代理去搞
		return proxy.parseRtnData(data);
	}

	public void doBefore() {
		// 让代理去搞
		proxy.doBefore();
	};

	public void doAfter() {
		// ..
		proxy.doAfter();
	}

	public void doError(Exception e) {
		// ...
		proxy.doError(e);
	}

}
