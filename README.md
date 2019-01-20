# flat-file-retructure
The source code is written with the below technology stack
  1) Spring BOOT 2.0
  2) Spring Web
  3) Junit 3.0
  4) Java 8.0
  
  
# How to run and install the application
 
  The first step to run the project is to install Eclipse, Maven and Java 8.0 or above.
  After installation of Eclipse, Maven and Java 8.0, the project source code has to be imported from Gitlab. 
  Once all the project source code are imported from GitLab, the below configuration file must be updated as detailed.
  
  1) /flat-file-restructure-web/src/main/resources/acceptable_ids.properties 
    This file must contain all the new column and old column mapping. The mapping detail can be found in the requirements document
    
  2) /flat-file-restructure-web/src/main/resources/column_converter.properties 
    This file will contain the IDs, which are acceptable and should be parsed from the input file.
    
  3) The third step is to mention the configuration file location and other properties in the application.properties file.
  Even if the application.properties file is self explanatory, as brief description of the properties is given below.

input.file.location=/root/input /* This is the input file directory */

output.file.location=/root/output /* This is the output target file directory */

input.file.name=input_data.txt /* This is the name of the input file */

output.file.name=output_data.txt /* This is the name of the output file */

column.name.converter.file=column_converter.properties /* Name of the file, given in 2) . This is configurable */

acceptable.id.file=acceptable_ids.properties  /* Name of the file, given in 1) . This is configurable */


If the specification is followed and the configuration is done, then the program, DataFileRestructureApp should be run from the
command prompt or java application runner. It can also be deployed in a TomCat container and the web application intializer will
do the file conversion.

# Deployment and running the application
The application can also be run with the help of a web application container. The /target/flat-file-restructure-web.war can be deployed in a web application container ( like tomcat or jBoss or WebSphere ). When the application starts, the properties file of the application must be given the above mentioned properties. If the properties are given vorrectly, then the web application will process the data.



# Testing the application

The whole flow of the application is well tested with Spring Boot test cases. The Spring boot jUnit test cases copy the sample data
inside the java temporary directory. Then the main program formats and generates the output. At last the juNit testing unit tests the generated output with the java source code.


