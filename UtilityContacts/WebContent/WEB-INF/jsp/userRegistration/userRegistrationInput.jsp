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
<%-- 
<s:form action="userRegistrationConfirm">

<table >
<tr><s:textfield key="Email Id"  name="username"/></tr>
<tr><s:textfield key="First Name"  name="firstname"/></tr>
<tr><s:textfield key="Last Name" name="lastname" /></tr>
<tr><s:password key="Password" name="password" /></tr>
<tr><s:password key="Re-enter Password" name="rePassword" /></tr>
<tr><s:select label="User Type" headerKey="-1"  list="#{'CUSTOMER':'Customer', 'VENDOR':'Vendor'}" name="usertype" value = "#{'usertype'}"/></tr>     
<tr><s:select label="Gender" headerKey="-1"  list="#{'Male':'Male', 'Female':'Female'}" name="sex" value = "#{'Gender'}"/></tr>
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
 --%>


<div class="container">
<div class="main">
	<!-- start registration -->
	<div class="registration">
		<div class="registration_left">
		<h2>new user? <span> create a shoppe account </span></h2>
		<a href="#"><div class="reg_fb"><img src="images/facebook.png" alt=""><i>register using Facebook</i><div class="clearfix"></div></div></a>
		<!-- [if IE] 
		    < link rel='stylesheet' type='text/css' href='ie.css'/>  
		 [endif] -->  
		  
		<!-- [if lt IE 7]>  
		    < link rel='stylesheet' type='text/css' href='ie6.css'/>  
		<! [endif] -->  
		<script>
			(function() {
		
			// Create input element for testing
			var inputs = document.createElement('input');
			
			// Create the supports object
			var supports = {};
			
			supports.autofocus   = 'autofocus' in inputs;
			supports.required    = 'required' in inputs;
			supports.placeholder = 'placeholder' in inputs;
		
			// Fallback for autofocus attribute
			if(!supports.autofocus) {
				
			}
			
			// Fallback for required attribute
			if(!supports.required) {
				
			}
		
			// Fallback for placeholder attribute
			if(!supports.placeholder) {
				
			}
			
			// Change text inside send button on submit
			var send = document.getElementById('register-submit');
			if(send) {
				send.onclick = function () {
					this.innerHTML = '...Sending';
				}
			}
		
		})();
		</script>
		 <div class="registration_form">
		 <!-- Form -->
			<s:form id="registration_form" action="userRegistrationConfirm" method="post">
				<div>
					<label>
						<input name = "firstname" placeholder="first name:" type="text" tabindex="1" required autofocus>
					</label>
				</div>
				<div>
					<label>
						<input name= "lastname" placeholder="last name:" type="text" tabindex="2" required autofocus>
					</label>
				</div>
				<div>
					<label>
						<input name ="username" placeholder="email address:" type="email" tabindex="3" required>
					</label>
				</div>
				<div class="sky-form">
					<div class="sky_form1">
						<ul>
							<li><label class="radio left"><input type="radio" name="sex" value = "Male" checked=""><i></i>Male</label></li>
							<li><label class="radio"><input type="radio" name="sex" value = "Female"><i></i>Female</label></li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</div>
				<div>
					<label>
						<input name = "password" placeholder="password" type="password" tabindex="4" required>
					</label>
				</div>						
				<div>
					<label>
						<input placeholder="retype password" type="password" tabindex="4" required>
					</label>
				</div>	
				<div>
					<input type="submit" value="create an account" id="register-submit">
				</div>
				<div class="sky-form">
					<label class="checkbox"><input type="checkbox" name="checkbox" ><i></i>i agree to shoppe.com &nbsp;<a class="terms" href="#"> terms of service</a> </label>
				</div>
			</s:form>
			<!-- /Form -->
		</div>
	</div>
	<div class="registration_left">
		<h2>existing user</h2>
		<a href="#"><div class="reg_fb"><img src="images/facebook.png" alt=""><i>sign in using Facebook</i><div class="clear"></div></div></a>
		 <div class="registration_form">
		 <!-- Form -->
			<form id="registration_form" action="contact.php" method="post">
				<div>
					<label>
						<input placeholder="email:" type="email" tabindex="3" required>
					</label>
				</div>
				<div>
					<label>
						<input placeholder="password" type="password" tabindex="4" required>
					</label>
				</div>						
				<div>
					<input type="submit" value="sign in" id="register-submit">
				</div>
				<div class="forget">
					<a href="#">forgot your password</a>
				</div>
			</form>
			<!-- /Form -->
			</div>
	</div>
	<div class="clearfix"></div>
	</div>
	<!-- end registration -->
</div>
</div>




</body>
</html>