# Mule Email Utility Module Extension #

## What? ###
This project contains utilities to generate email content that can be used in Mule application

## Why? ###
The main reason for developing this project is to generate encoded RFC 2822 Email content that is acceptable in Google Email API. This extension can be used in Mule application without requiring custom java code.

### Changes ###
### 1.0.0
* Initial implementation
 - Accepts below Email attributes to generate encoded RFC 2822 Email content that can be passed to Google Email API
 	- From 
	- To
	- CC 
	- BCC
	- Message Subject
	- Message Content Type
	- Message Content

## Dependency ##
Add below dependency to your application pom.xml to use this module
```xml
<dependency>
	<groupId>com.avioconsulting.mule</groupId>
	<artifactId>mule-email-utility</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<classifier>mule-plugin</classifier>
</dependency>
```