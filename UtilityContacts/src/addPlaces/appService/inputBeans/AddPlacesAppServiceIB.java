package addPlaces.appService.inputBeans;

import java.io.File;


public class AddPlacesAppServiceIB {

	private int placeid;
	private String placename;
	private String placetype;
	private String username;
	private String description;
	private String profileImage;
	private int imagesGalleryId;
	private int facilitiesId;
	private String status;
	private String joiningFees;
	private String city;
	private String pin;
	private String address;
	private String approvalStatus;
	private File imageFile;
	
	public File getImageFile() {
		return imageFile;
	}
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}
	public int getPlaceid() {
		return placeid;
	}
	public void setPlaceid(int placeid) {
		this.placeid = placeid;
	}
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	public String getPlacetype() {
		return placetype;
	}
	public void setPlacetype(String placetype) {
		this.placetype = placetype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public int getImagesGalleryId() {
		return imagesGalleryId;
	}
	public void setImagesGalleryId(int imagesGalleryId) {
		this.imagesGalleryId = imagesGalleryId;
	}
	public int getFacilitiesId() {
		return facilitiesId;
	}
	public void setFacilitiesId(int facilitiesId) {
		this.facilitiesId = facilitiesId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJoiningFees() {
		return joiningFees;
	}
	public void setJoiningFees(String joiningFees) {
		this.joiningFees = joiningFees;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	
}
