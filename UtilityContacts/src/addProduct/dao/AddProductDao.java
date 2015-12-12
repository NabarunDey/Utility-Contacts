package addProduct.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import userRegistration.dao.outputBeans.UserRegistrationDaoOB;

import com.databaseBeans.UsersDBBean;
import com.util.CommonUtility;

public class AddProductDao {
	
	HibernateTemplate template;  
	public void setTemplate(HibernateTemplate template) {  
	    this.template = template;  
	}  
	public void saveContacts(UsersDBBean usersDBBean){  
	    template.save(usersDBBean);  
	}  
	public void updateContacts(UsersDBBean usersDBBean){  
	    template.update(usersDBBean);  
	}  
	public void deleteContacts(UsersDBBean usersDBBean){  
	    template.delete(usersDBBean);  
	}  
		
	
	public UserRegistrationDaoOB addUser(
			UserRegistrationDaoIB userRegistrationDaoIB) {

		UsersDBBean usersDBBean = new  UsersDBBean();
		CommonUtility.copyBean(userRegistrationDaoIB, usersDBBean);
		
		
		saveContacts(usersDBBean);
		UserRegistrationDaoOB userRegistrationDaoOB =new UserRegistrationDaoOB();
		userRegistrationDaoOB.setContactInserted(true);
		return userRegistrationDaoOB;
	}

}
