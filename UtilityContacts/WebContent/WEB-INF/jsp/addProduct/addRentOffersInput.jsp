<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Rent Offer</title>

</head>
<body>
<table>
<!-- <tr>
<th>abc</th>
<th>tgb</th>
</tr> -->
<s:iterator value="context.addProductProjectorOB.confirmDisplayMap">

<tr>
	<td><s:property value="key"/></td>
	<td><s:property value="value"/></td>
	</tr> 

</s:iterator>
</table>
</body>
</html>