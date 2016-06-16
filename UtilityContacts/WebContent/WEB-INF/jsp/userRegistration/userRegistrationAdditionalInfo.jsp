<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="/Struts2Example/struts/xhtml/styles.css" type="text/css"/> 
<script type="text/javascript" src="js/jquery-1.4.1.min.js"></script>
</head>
<body>


<div class="container">
<div class="main">
</div>
</div>

<s:form action="userRegistrationConfirm">

<table >
<tr><s:textfield key="Phone Number" name="phoneno" /></tr>
<tr><s:textfield key="Mobile 1" name="mobileno1" /></tr>
<tr><s:textfield key="Mobile 2" name="mobileno2" /></tr>
<s:textarea label="Address" name="address" />
<tr><s:select label="City" headerKey="-1" list="#{'Kolkata':'Kolkata', 'Siliguri':'Siliguri'}" name="city" value = "#{'city'}"/></tr>  
<tr><s:select label="State" headerKey="-1"  list="#{'West Bengal':'West Bengal', 'Assam':'Assam'}" name="state" value = "#{'state'}"/></tr>
<tr><s:textfield key="Pincode" name = "pinno" /></tr>
<tr>
<td><s:submit type="button" name="submit" theme="simple" />
<s:reset type="button" name="reset" theme="simple" /></td>

</tr>
<tr>
</tr>


</table>
</s:form>


</body>
</html>