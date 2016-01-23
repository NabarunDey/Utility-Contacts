package addProduct.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import addProduct.AddProductAppContext;
import addProduct.appService.AddProductAppService;
import addProduct.appService.inputBeans.AddProductAppServiceIB;
import addProduct.appService.inputBeans.FileBean;
import addProduct.projector.outputBeans.AddProductProjectorOB;

public class AddProductAction {


	private AddProductAppService addProductAppService;
	private AddProductAppContext context;
	private File image;
	private String imageContentType;
	private String imageFileName;
	
	String text ;

	public String addProductInput()
	{
		text = "abhisek";
		AddProductAppServiceIB addProductAppServiceIB = new AddProductAppServiceIB();
		AddProductProjectorOB addProductProjectorOB = addProductAppService.getInputDetails(addProductAppServiceIB);
		context.setAddProductProjectorOB(addProductProjectorOB);
		return "success";
	}

	public String addProductRecap()
	{
		Map<String, String> recapDisplayMap = new HashMap<String, String>();
		recapDisplayMap.put("ProductName", "Godrej Fridje");
		recapDisplayMap.put("ProductType", "Refrijerator");
		recapDisplayMap.put("Price", "20000");
		context.getAddProductProjectorOB().setRecapDisplayMap(recapDisplayMap);
		
		List<FileBean> fileBeans = new ArrayList<FileBean>();
		FileBean fileBean = new FileBean();
		fileBean.setFile(image);
		fileBean.setFileType(imageContentType);
		fileBeans.add(fileBean);

		AddProductAppServiceIB addProductAppServiceIB =new AddProductAppServiceIB();
		addProductAppServiceIB.setFileBeans(fileBeans);
		AddProductProjectorOB addProductProjectorOB = addProductAppService.getRecapDetails(addProductAppServiceIB);
		context.setAddProductProjectorOB(addProductProjectorOB);
		return "success";
	}

	public String addProductConfirmation()
	{
		AddProductAppServiceIB addProductAppServiceIB = new AddProductAppServiceIB();
		AddProductProjectorOB addProductProjectorOB = addProductAppService.addProduct(addProductAppServiceIB);
		return "SUCCESS";
	}

	public AddProductAppService getAddProductAppService() {
		return addProductAppService;
	}

	public void setAddProductAppService(AddProductAppService addProductAppService) {
		this.addProductAppService = addProductAppService;
	}

	public AddProductAppContext getContext() {
		return context;
	}

	public void setContext(AddProductAppContext context) {
		this.context = context;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}




}
