package com.redhat.qe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="host_nic")
public class Nic extends Model{

	@XmlAttribute
	String id;
	Host host;
	Network network;
	Mac mac;
	@XmlElement(name="boot_protocol")
	String bootProtocol;
	String speed;
	Status satus;
	String mtu;
	Boolean bridged;
	@XmlElement(name="custom_configuration")
	String customConfiguration;

	
	
	public Host getHost() {
		return host;
	}



	public void setHost(Host host) {
		this.host = host;
	}



	public Network getNetwork() {
		return network;
	}



	public void setNetwork(Network network) {
		this.network = network;
	}



	public Mac getMac() {
		return mac;
	}



	public void setMac(Mac mac) {
		this.mac = mac;
	}



	public String getBootProtocol() {
		return bootProtocol;
	}



	public void setBootProtocol(String bootProtocol) {
		this.bootProtocol = bootProtocol;
	}



	public String getSpeed() {
		return speed;
	}



	public void setSpeed(String speed) {
		this.speed = speed;
	}



	public Status getSatus() {
		return satus;
	}



	public void setSatus(Status satus) {
		this.satus = satus;
	}



	public String getMtu() {
		return mtu;
	}



	public void setMtu(String mtu) {
		this.mtu = mtu;
	}



	public Boolean getBridged() {
		return bridged;
	}



	public void setBridged(Boolean bridged) {
		this.bridged = bridged;
	}



	public String getCustomConfiguration() {
		return customConfiguration;
	}



	public void setCustomConfiguration(String customConfiguration) {
		this.customConfiguration = customConfiguration;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getId() {
		return id;
	}




	
}
