package com.cmcc.omc.configcenter.dao.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.alibaba.fastjson.JSON;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"linkName"})})
public class SctpLink {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private OperateType type;
	
	private String linkName;
	
	@ElementCollection(targetClass=String.class)
	private List<String> localIp;
	
	private Integer localPort;
	
	private ModeType mode;
	
	private Integer ratio;
	
	@ElementCollection(targetClass=String.class)
	private List<String> remoteIp;
	
	private Integer remotePort;
	
	private TransportType transport;
	
	public static enum OperateType{
		add,
		del,
		modify;
	}
	
	public static enum ModeType{
		client,
		server;
	}
	
	private static enum TransportType{
		SCTP,
		TCP;
	}
	
	public static void main(String[] args){
		SctpLink link = new SctpLink();
		link.id = 1;
		link.type=  OperateType.add;
		link.setLinkName("linkName1");
		
		List<String> lIps = new ArrayList<String>();
		lIps.add("127.0.0.1");
		lIps.add("192.168.2.1");
		link.localIp = lIps;
		
		link.setLocalPort(2222);
		link.setMode(ModeType.client);
		link.setRatio(11);
		
		List<String> rs = new ArrayList<String>();
		rs.add("127.0.0.1");
		rs.add("192.168.2.1");
		link.remoteIp = rs;
		
		link.setRemotePort(3333);
		
		link.setTransport(TransportType.SCTP);
		System.out.println(JSON.toJSONString(link));
	}
}
