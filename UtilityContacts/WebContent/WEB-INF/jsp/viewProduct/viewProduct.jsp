<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary JavaScript plugins) -->
<!-- Custom Theme files -->
<link href="css/style.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--webfont-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<link rel="stylesheet" href="css/etalage.css">
<script>
			jQuery(document).ready(function($){

				$('#etalage').etalage({
					thumb_image_width: 300,
					thumb_image_height: 400,
					source_image_width: 900,
					source_image_height: 1200,
					show_hint: true,
					click_callback: function(image_anchor, instance_id){
						alert('Callback example:\nYou clicked on an image with the anchor: "'+image_anchor+'"\n(in Etalage instance: "'+instance_id+'")');
					}
				});

			});
		</script>
<!-- the jScrollPane script -->
		<script type="text/javascript" id="sourcecode">
			$(function()
			{
				$('.scroll-pane').jScrollPane();
				$('.rentOffer').removeClass('bg-primary').addClass('bg-danger');
				$('.rentOffer').click(function(){
					$('.rentOffer').removeClass('bg-danger').addClass('bg-primary');
					$(this).removeClass('bg-primary').addClass('bg-danger');
				})
			});
		</script> 
</head>
<body>
<div class="container">
<div class="women_main">
	<!-- start content -->
	<div id="productId" style="visibility:hidden;">$(context.ViewProductProjectorOB.productsDBBean.productid)</div>
			<div class="row single">
				<div class="col-md-12">
				  <div class="single_left">
					<div class="grid images_3_of_2">
						<ul id="etalage">
						<s:iterator value="context.ViewProductProjectorOB.productImagesList">
							<li>
								<a href="optionallink.html">
									<img class="etalage_thumb_image" src="<s:property />" class="img-responsive" />
									<img class="etalage_source_image" src="<s:property />" class="img-responsive" title="" />
								</a>
							</li>
							</s:iterator>
						</ul>
						 <div class="clearfix"></div>		
				  </div>
				  <div class="desc1 span_3_of_2">
					<h3><s:property value="context.ViewProductProjectorOB.productsDBBean.productname"/></h3>
					<p class="money">Security Money: Rs. <s:property value="context.ViewProductProjectorOB.productsDBBean.securitymoney"/></p>
					<h4>Select Quantity 
						<select id="quantity" style="width:50px">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
						</select>
					</h4>
					<div class="det_nav">
						<h4>Rent offers</h4>
						<ul>
							<s:iterator value="context.ViewProductProjectorOB.rentOffersDBBeans">
							<li>
								<div id="rentOffer<s:property value="rentid"/>" class="rentOffer bg-primary">
									<p>Rs. <s:property value="amount"/></p>
									<p>For <s:property value="periodvalue"/> <s:property value="periodunit"/></p>
								</div>
							</li>
							
							</s:iterator>
						</ul>
					</div>
					<div class="btn_form">
						<a href="buy.html">buy</a>
					</div>
					<a href="#"><span>login to save in wishlist </span></a>
					
			   	 </div>
          	    <div class="clearfix"></div>
          	   </div>
          	    <div class="single-bottom1">
					<h6>Details</h6>
					<p class="prod-desc">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option</p>
				</div>
				
	       </div>	
	       			
		   <div class="clearfix"></div>		
	  </div>
	<!-- end content -->
</div>
</div>

</body>
</html>