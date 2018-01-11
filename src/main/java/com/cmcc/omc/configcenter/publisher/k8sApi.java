package com.cmcc.omc.configcenter.publisher;

public interface k8sApi {

	
	public boolean restartResources(String resources,String nameSpace);
	
	public boolean updateConfigMap(String configMapName,String folderPath,String namePath);
}
