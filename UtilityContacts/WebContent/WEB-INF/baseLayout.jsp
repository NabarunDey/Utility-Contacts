<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="jsp/error/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.util.FacebookHandler"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta name="google-site-verification"
	content="rrZ1Dwm3Iete-AgAMdhQCTm4nnuVVjnSOlQxxANfv4k" />
<meta name="author" content="Nabarun Dey (nabarundeysit@gmail.com)">
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.jpg" />
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary JavaScript plugins) -->
<script type='text/javascript' src="js/jquery-1.11.1.min.js"></script>
<!-- Custom Theme files -->
<link href="css/style.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<!--webfont-->
<!-- <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
 -->
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script type='text/javascript' src='js/jquery.LoadingBox.js'></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
		var lb = new $.LoadingBox({
			loadingImageSrc : "images/default.gif",
		});
		setTimeout(function() {
			lb.close();
		}, 500);
	});
</script>
<!-- start slider -->
<link rel="stylesheet" href="css/fwslider.css" media="all">
<link rel="stylesheet" href="css/jasny-bootstrap.min.css">

<script src="js/jquery-ui.min.js"></script>
<script src="js/fwslider.js"></script>

<script src="js/menu_jquery.js"></script>
<script src="js/login/login.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jasny-bootstrap.min.js"></script>
<script src="js/jquery-func.js"></script>
<script src="js/jquery.etalage.min.js"></script>
<script src="js/jquery.jcarousel.pack.js"></script>
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body >	
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.8&appId=841162669353192";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.8&appId=841162669353192";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="menu" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />
</body>
</html>