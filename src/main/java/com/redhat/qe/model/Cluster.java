package com.redhat.qe.model;
import java.io.StringWriter;
import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.ssh.IResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.redhat.qe.ssh.Response;


@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="cluster")
public class Cluster extends Model{
	
	
	@XmlAttribute
	private String id;
	
	private String name;
	private String description;
	
	@XmlElement(name="data_center")
	private Datacenter datacenter;
	
	@XmlElement(name="version")
	private Version version;
	
	@XmlElement(name="virt_service")
	private boolean virtService = false;
	
	@XmlElement(name="gluster_service")
	private boolean glusterService = true;
	
	@XmlElement(name="cpu")
	private Cpu cpu;
	
	
	public static Cluster fromResponse(IResponse response){
		return fromKeyValue(response.toString());
	}

	public static Cluster fromKeyValue(String keyValue){
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(keyValue);
		Cluster cluster = new Cluster();
		cluster.setId(attributes.get("id"));
		cluster.setName(attributes.get("name"));
		cluster.setDescription(attributes.get("description"));
		String majorVersion = attributes.get("version-major");
		String minorVersion = attributes.get("version-minor");
		if(majorVersion != null)
			cluster.setMajorVersion(Integer.parseInt(majorVersion));
		if(minorVersion != null)
			cluster.setMinorVersion(Integer.parseInt(minorVersion));
		return cluster;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

	/**
	 * @return the datacenter
	 */
	public Datacenter getDatacenter() {
		return datacenter;
	}

	/**
	 * @param datacenter the datacenter to set
	 */
	public void setDatacenter(Datacenter datacenter) {
		this.datacenter = datacenter;
	}

	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the majorVersion
	 */
	public int getMajorVersion() {
		return (getVersion() != null) ? getVersion().getMajor() : null ;
	}

	/**
	 * @param majorVersion the majorVersion to set
	 */
	public void setMajorVersion(int majorVersion) {
		if(getVersion() == null)
			setVersion(new Version());
		getVersion().setMajor(majorVersion);
	}

	/**
	 * @return the minorVersion
	 */
	public int getMinorVersion() {
		return (getVersion() != null) ? getVersion().getMinor() : null ;
	}

	/**
	 * @param minorVersion the minorVersion to set
	 */
	public void setMinorVersion(int minorVersion) {
		if(getVersion() == null)
			setVersion(new Version());
		getVersion().setMinor(minorVersion);
	}
//
//	@Override
//	public boolean equals(Object o){
//		return (o instanceof Cluster) 
//				&& (getId()==null || ((Cluster)o).getId().equals(getId()))
//				&& (getName() == null || ((Cluster)o).getName().equals(getName()))
//				&& (getDescription() == null || ((Cluster)o).getDescription().equals(getDescription()));
//	}
	
	

	public boolean isVirtService() {
		return virtService;
	}

	/**
	 * @param virtService the virtService to set
	 */
	public void setVirtService(boolean virtService) {
		this.virtService = virtService;
	}

	/**
	 * @return the glusterService
	 */
	public boolean isGlusterService() {
		return glusterService;
	}

	/**
	 * @param glusterService the glusterService to set
	 */
	public void setGlusterService(boolean glusterService) {
		this.glusterService = glusterService;
	}

	/**
	 * @return the cpu
	 */
	public Cpu getCpu() {
		return cpu;
	}

	/**
	 * @param cpu the cpu to set
	 */
	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	
	
	public int hashCode() {
        return new HashCodeBuilder(3, 31). // two randomly chosen prime numbers
            append(name).
            toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        Cluster rhs = (Cluster) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(name, rhs.name).
            isEquals();
    }

	public static void main(String[] args) throws JAXBException{
		Cluster c = new Cluster();
		c.setName("hi");
		c.setDatacenter(new Datacenter(){{setId("id");}});
		c.setGlusterService(true);
		c.setVirtService(false);
		c.setVersion(new Version(){{ setMajor(3); setMinor(2);}});
		JAXBContext ctx = JAXBContext.newInstance(Cluster.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(c, System.out);
		StringWriter writer = new StringWriter();
		m.marshal(c, writer);
		System.out.println(writer.toString());
		
		
	}

}
