package addProduct.appService.inputBeans;

import java.util.List;

public class AddProductAppServiceIB {

	private String productname;
	private String producttype;
	private String subproducttype;
	private String username;
	private String quantity;
	private List<FileBean> fileBeans;
	private String status;
	private String securitymoney;
	private List<String> imageIdsList;
	private AddRentOfferAppServiceIB addRentOfferAppServiceIB;
	
	

	public AddRentOfferAppServiceIB getAddRentOfferAppServiceIB() {
		return addRentOfferAppServiceIB;
	}
	public void setAddRentOfferAppServiceIB(
			AddRentOfferAppServiceIB addRentOfferAppServiceIB) {
		this.addRentOfferAppServiceIB = addRentOfferAppServiceIB;
	}
	public String getSubproducttype() {
		return subproducttype;
	}
	public void setSubproducttype(String subproducttype) {
		this.subproducttype = subproducttype;
	}
	public List<String> getImageIdsList() {
		return imageIdsList;
	}
	public void setImageIdsList(List<String> imageIdsList) {
		this.imageIdsList = imageIdsList;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSecuritymoney() {
		return securitymoney;
	}
	public void setSecuritymoney(String securitymoney) {
		this.securitymoney = securitymoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<FileBean> getFileBeans() {
		return fileBeans;
	}
	public void setFileBeans(List<FileBean> fileBeans) {
		this.fileBeans = fileBeans;
	}
}
