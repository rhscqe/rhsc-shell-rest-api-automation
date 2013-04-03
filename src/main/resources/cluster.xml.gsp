<cluster>
    <name>$cluster.name</name>
    <description>$cluster.description</description>
    <cpu id="$cluster.cpu.id"/>
    <data_center>
        <name><% def name = (cluster.datacenter) ? cluster.datacenter.name : ""; print name; %></name>
    </data_center>
    <version major="$cluster.version.major" minor="$cluster.version.minor"/>
    <virt_service>$cluster.virtService</virt_service>
    <gluster_service>$cluster.glusterService</gluster_service>
</cluster>
