Prereqs
-------
* Maven 3
* Java 7

Environment and Configuration
------------
Tests make use of:
* RHS-C instance
* 2 RHS nodes with each node having an xfs volume mounted on /bricks 

rename src/test/resources/rhsc-config.xml.sample to src/test/resources/rhsc-config.xml
and replace all of hostname and credentials for the rhscApi, shellHost, com.redhat.qe.model.Hosts elements.
The config file has marked the areas that need changing by TODO comments.

Run Tests
---------
`mvn clean test` to run all tests.

