package com.ruoyi.vuln.domain.vo;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ExecutionObjectFactory {

	/*public Scan createScan() {
	    return new Scan();
	}*/
	
	public ScriptHelp createScriptHelp() {
	    return new ScriptHelp();
	}
	public Cnnvd createCnnvd() {
		return new Cnnvd();
	}
}
