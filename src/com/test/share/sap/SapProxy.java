package com.test.share.sap;

public class SapProxy {
	
	public Object parsePostData(Object soapTemplate) {
		
		System.out.println(soapTemplate);
		return soapTemplate;
	}
	public Object parseRtnData(Object data) {

		System.out.println(data);
		return data;
	}

	public void doBefore() {
		System.out.println(" doBefore~ ");
	};

	public void doAfter() {
		System.out.println(" doAfter~ ");
	};

	public void doError(Exception e) {
		System.out.println(e.getMessage());
	};

}
