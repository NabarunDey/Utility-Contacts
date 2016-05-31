$(document).ready(function(){
	var dropdown = document.getElementById("productType");
	dropdown.onchange = function(event){
		alert("Your message"+  $('#productType').val());
		//$('div#subProductTypePlaceholder div').not('#subProductType'+$('#productType').val()).hide();
        $('#subProductTypePlaceholder').show();
        $("#subProductTypePlaceholder").children().hide(); 
        $('#subProductTypeLabel').show();
        $('#subProductTypeDropdown').show();
        $("#subProductTypeDropdown").children().hide(); 
		$('#subProductType'+$('#productType').val()).show();
		
	}
	
	var images = $('input[id^="image"]');
	images.each(function(i){
		$(this).change(function(event){
			
			if (this.files && this.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$("#previewImage"+(i+1)).attr("src", e.target.result).css({height:'100px',margin:'5px'});
				}
				reader.readAsDataURL(this.files[0]);
			}
		});
	});
});

