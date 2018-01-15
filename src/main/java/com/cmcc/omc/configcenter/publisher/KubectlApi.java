package com.cmcc.omc.configcenter.publisher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmcc.omc.configcenter.Config;

@Service
public class KubectlApi implements k8sApi {

	@Autowired
	Config config;

	@Override
	public boolean restartResources(String resources, String nameSpace) {
		
		String delePodCommand = config.getKubectl() + "delete po -l app="+resources + " -n " + nameSpace;
		return runCommand(delePodCommand);
	}

	@Override
	public boolean updateConfigMap(String configMapName, String folderPath, String nameSpace) {

		String deleCommand = config.getKubectl() + " delete cm " + configMapName + " -n " + nameSpace;

		if (runCommand(deleCommand)) {

			String createCommand = config.getKubectl() + " create cm " + configMapName + " -n " + nameSpace
					+ " --from-file=" + folderPath;
			if (runCommand(createCommand)) {
				return true;
			}
		}
		return false;
	}

	private static Boolean runCommand(String command) {

		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<error></error>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
			return exitVal == 0;
		} catch (Throwable t) {
			t.printStackTrace();

			return false;
		}

	}

	public static void main(String[] args) {

		String command = "/usr/local/bin/kubectl create cm hjh --from-file=/Users/hejiahuan/develop/dockerBuild/ipsmgw/config";
		System.out.println(runCommand(command));
	}
}
