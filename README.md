# Adding custom commands to SQLcl

This is a sample repo of how to add a custom java written command into sqlcl versions 18.3+

This repository is a sample of doing this by adding a `banner` command.


## Adding a New Command
 
### Javascript 

There is a simple way to add some commands by using Javascript. These are on my blog as well in :

-	[Reserved Words](http://krisrice.io/2016-11-14-adding-reserved-command-in-sqlcl/)
-	[DIY Commands](http://krisrice.io/2016-03-04-diy-sqcl-commands/)
-	[snark.js](https://gist.github.com/krisrice/d8c7be3c7632dbee2aef)
-	[Auto Correct](https://gist.github.com/krisrice/ce9f5eb290a203ef1c12)
-	[Repeat Command and Spool](https://gist.github.com/krisrice/6066ca2a57fed8c1224af6b835662930)
	


### Java 
SQLcl leverages [Java Service Loader](https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html) to install commands.

The jar file that this repository produces contains the following file >>
`./src/main/resources/META-INF/services/oracle.dbtools.extension.SQLCLService`


This file contains 1 line per command being installed. This repository is adding  `io.krisrice.FigletCmd` The java follows the same approach as the javascript examples in that the code must extend `CommandListener` This example goes one more step and  implements other interfaces such as IHelp so `SQL> help banner` works

```
SQL>help banner
something helpful

```


## Setup

Run the `configure.sh` script included which find `sql` in the path and populates the SQLCL\_HOME,SQLCL\_BIN, and SQLCL\_VERSION to be used by the maven build process

### Maven Repository
The script will add 2 files to the local maven repository. These contain the required java APIs to interact with sqlcl.

	${SQLCL_BIN}/../lib/dbtools-common.jar
	${SQLCL_BIN}/../lib/sqlcl.jar

### sqlcl.properties
The configure script also creates a `sqlcl.properties` file which contains the following used later by maven to deploy the custom command to 

	sqlcl.bin=../sqlcl/bin
	sqlcl.home=../sqlcl
	sqlcl.version=18.4.0.0
	
	

## Build
The build is simply `mvn install`


## Try it out

```
kriss-MacBook-Pro:rest-data-services klrice$ sql /nolog

SQLcl: Release 18.4 Production on Mon Dec 17 11:45:09 2018

Copyright (c) 1982, 2018, Oracle.  All rights reserved.


SQL> banner Hello World!
  _   _          _   _            __        __                 _       _   _
 | | | |   ___  | | | |   ___     \ \      / /   ___    _ __  | |   __| | | |
 | |_| |  / _ \ | | | |  / _ \     \ \ /\ / /   / _ \  | '__| | |  / _` | | |
 |  _  | |  __/ | | | | | (_) |     \ V  V /   | (_) | | |    | | | (_| | |_|
 |_| |_|  \___| |_| |_|  \___/       \_/\_/     \___/  |_|    |_|  \__,_| (_)


SQL> help banner
something helpful
SQL>
``
