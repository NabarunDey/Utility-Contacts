package currentHoldings.appService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import payment.appService.inputBeans.PaymentAppServiceIB;

import com.dao.CurrentHoldingsDao;
import com.dao.ImagesDao;
import com.dao.OrdersDao;
import com.dao.PaymentsDao;
import com.dao.ProductsDao;
import com.dao.RentOffersDao;
import com.dao.UsersDao;
import com.databaseBeans.CurrentHoldingsDBBean;
import com.databaseBeans.ImagesDBBean;
import com.databaseBeans.OrdersDBBean;
import com.databaseBeans.PaymentsDBBean;
import com.databaseBeans.ProductsDBBean;
import com.databaseBeans.RentOffersDBBean;
import com.databaseBeans.UsersDBBean;
import com.sessionBeans.UserProfile;
import com.structures.status.CurrentHoldingStatus;
import com.structures.status.PaymentStatus;
import com.structures.userTypes.UserType;
import com.util.CommonUtility;
import com.util.MailHandler;

import currentHoldings.appService.inputBeans.CurrentHoldingsAppServiceIB;
import currentHoldings.projector.CurrentHoldingsProjector;
import currentHoldings.projector.input.CurrentHoldingsProjectorIB;
import currentHoldings.projector.outputBeans.CurrentHoldingsProjectorOB;


/**
 * @author nd29794
 *
 */
public class CurrentHoldingsAppService {

	RentOffersDao rentOffersDao;
	CurrentHoldingsDao currentHoldingsDao;
	UserProfile userProfile;
	PaymentsDao paymentsDao;
	OrdersDao ordersDao;
	ProductsDao productsDao;
	CurrentHoldingsProjector currentHoldingsProjector;
	ImagesDao imagesDao;
	UsersDao usersDao;

	public void addCurrentHolding(OrdersDBBean ordersDBBean)
	{
		CurrentHoldingsAppServiceIB currentHoldingsAppServiceIB = new CurrentHoldingsAppServiceIB();
		currentHoldingsAppServiceIB.setOrderid(ordersDBBean.getOrderid());
		currentHoldingsAppServiceIB.setProductid(ordersDBBean.getProductid());
		currentHoldingsAppServiceIB.setRentOfferid(ordersDBBean.getRentid());
		currentHoldingsAppServiceIB.setStatus(CurrentHoldingStatus.ONGOING.toString());
		currentHoldingsAppServiceIB.setItemreceiveddate(Calendar.getInstance().getTime().toString());
		currentHoldingsAppServiceIB.setUsername(ordersDBBean.getUsername());
		RentOffersDBBean rentOffersDBBean = rentOffersDao.getRentOffersById(ordersDBBean.getRentid());
		Date todayDate= Calendar.getInstance().getTime();
		Date expiryDate = CommonUtility.addDate(todayDate, rentOffersDBBean.getPeriodunit(), rentOffersDBBean.getPeriodvalue());
		currentHoldingsAppServiceIB.setRentexpirydate(expiryDate.toString());

		currentHoldingsDao.addCurrentHolding(currentHoldingsAppServiceIB);
	}

	public void endCurrentHolding(String currentHoldingId, boolean systemCall)
	{
		CurrentHoldingsAppServiceIB currentHoldingsAppServiceIB = new CurrentHoldingsAppServiceIB();
		currentHoldingsAppServiceIB.setCurrentHoldinsId(currentHoldingId);
		currentHoldingsAppServiceIB.setStatus(CurrentHoldingStatus.ENDREQUESTED.toString());
		CurrentHoldingsDBBean currentHoldingsDBBean =  currentHoldingsDao.modiFyCurrentHoldingStatus(currentHoldingsAppServiceIB,userProfile,systemCall);

		OrdersDBBean ordersDBBean =  ordersDao.getOrder(currentHoldingsDBBean.getOrderid());
		PaymentAppServiceIB paymentAppServiceIB = new PaymentAppServiceIB();
		paymentAppServiceIB.setOrderid(ordersDBBean.getOrderid());
		List<PaymentsDBBean> paymentsDBBeans = paymentsDao.getPaymentsForOrder(paymentAppServiceIB);

		for(PaymentsDBBean paymentsDBBean : paymentsDBBeans)
		{
			if(StringUtils.isNotEmpty(paymentsDBBean.getSecuritymoney())&& !paymentsDBBean.getSecuritymoney().equals("0"))
			{
				PaymentAppServiceIB paymentAppServiceIB2 = new PaymentAppServiceIB();
				paymentAppServiceIB2.setFromusername(paymentsDBBean.getTousername());
				paymentAppServiceIB2.setOrderid(currentHoldingsDBBean.getOrderid());
				paymentAppServiceIB2.setPaymentStatus(PaymentStatus.PENDING.toString());
				paymentAppServiceIB2.setSecuritymoney(paymentsDBBean.getSecuritymoney());
				paymentAppServiceIB2.setTousername(paymentsDBBean.getFromusername());

				List<PaymentAppServiceIB> paymentAppServiceIBs = new ArrayList<PaymentAppServiceIB>();
				paymentAppServiceIBs.add(paymentAppServiceIB2);
				List<PaymentsDBBean> paymentsDBBeansForMail= paymentsDao.addPayment(paymentAppServiceIBs);
				sendPaymentNotifications(paymentsDBBeansForMail);
			}
		}

	}


	public List<CurrentHoldingsProjectorOB> viewMyCurrentHoldingsCustomer()
	{
		List<CurrentHoldingsProjectorOB> currentHoldingsProjectorOBs= null;
		if(null!= userProfile && userProfile.getUserType().equals(UserType.CUSTOMER))
		{
			List<Integer> productIds = new ArrayList<Integer>();
			List<Integer> rentIds = new ArrayList<Integer>();
			List<Integer> orderIds = new ArrayList<Integer>();
			List<CurrentHoldingsDBBean> currentHoldingsDBBeans = currentHoldingsDao.getMyCurrentHoldingsCustomer(userProfile.getUserName());

			if(null!=currentHoldingsDBBeans && currentHoldingsDBBeans.size()>0)
			{
				for(CurrentHoldingsDBBean currentHoldingsDBBean : currentHoldingsDBBeans)
				{
					productIds.add(currentHoldingsDBBean.getProductid());
					rentIds.add(currentHoldingsDBBean.getRentOfferid());
					orderIds.add(currentHoldingsDBBean.getOrderid());
				}
			}
			List<ProductsDBBean> productsDBBeans = productsDao.getProductListByIdsInteger(productIds);
			List<RentOffersDBBean> rentOffersDBBeans = rentOffersDao.getRentOffersByIdsInteger(rentIds);
			List<OrdersDBBean> ordersDBBeans = ordersDao.getOrders(orderIds);

			Map<String, ProductsDBBean> productsMap = CommonUtility.getProductMap(productsDBBeans);
			Map<String, RentOffersDBBean> rentMap = CommonUtility.getRentMap(rentOffersDBBeans);
			Map<String, OrdersDBBean> orderMap = CommonUtility.getOrdersMap(ordersDBBeans);
			Map<String, ImagesDBBean> productImageMap = imagesDao.getPrimaryImageOfProduct(productsDBBeans);

			CurrentHoldingsProjectorIB currentHoldingsProjectorIB = new CurrentHoldingsProjectorIB();
			currentHoldingsProjectorIB.setCurrentHoldingsDBBeans(currentHoldingsDBBeans);
			currentHoldingsProjectorIB.setOrdersMap(orderMap);
			currentHoldingsProjectorIB.setProductsMap(productsMap);
			currentHoldingsProjectorIB.setRentMap(rentMap);
			currentHoldingsProjectorIB.setProductImagesMap(productImageMap);


			currentHoldingsProjectorOBs= currentHoldingsProjector.getMyCurrentHoldingsProjector(currentHoldingsProjectorIB);
		}
		return currentHoldingsProjectorOBs;
	}

	public List<CurrentHoldingsProjectorOB> viewMyCurrentHoldingsVendor()
	{
		List<CurrentHoldingsProjectorOB> currentHoldingsProjectorOBs = null;

		List<ProductsDBBean> productsDBBeans = productsDao.searchByVendor(userProfile.getUserName());
		if(null!= productsDBBeans && productsDBBeans.size()>0)
		{
			Map<String, ProductsDBBean> productsMap = CommonUtility.getProductMap(productsDBBeans);

			Set<String> productIdSet = productsMap.keySet();
			List<Integer> productIdListInt = new ArrayList<Integer>();
			for(String productIdStr : productIdSet)
			{
				productIdListInt.add(Integer.parseInt(productIdStr));
			}
			List<CurrentHoldingsDBBean> currentHoldingsDBBeans = currentHoldingsDao.getMyCurrentHoldingsVendor(productIdListInt);
			List<Integer> rentIds = new ArrayList<Integer>();
			List<Integer> orderIds = new ArrayList<Integer>();
			if(null!=currentHoldingsDBBeans && currentHoldingsDBBeans.size()>0)
			{
				for(CurrentHoldingsDBBean currentHoldingsDBBean : currentHoldingsDBBeans)
				{
					rentIds.add(currentHoldingsDBBean.getRentOfferid());
					orderIds.add(currentHoldingsDBBean.getOrderid());
				}
			}
			List<RentOffersDBBean> rentOffersDBBeans = rentOffersDao.getRentOffersByIdsInteger(rentIds);
			List<OrdersDBBean> ordersDBBeans = ordersDao.getOrders(orderIds);

			Map<String, RentOffersDBBean> rentMap = CommonUtility.getRentMap(rentOffersDBBeans);
			Map<String, OrdersDBBean> orderMap = CommonUtility.getOrdersMap(ordersDBBeans);
			Map<String, ImagesDBBean> productImageMap = imagesDao.getPrimaryImageOfProduct(productsDBBeans);

			CurrentHoldingsProjectorIB currentHoldingsProjectorIB = new CurrentHoldingsProjectorIB();
			currentHoldingsProjectorIB.setCurrentHoldingsDBBeans(currentHoldingsDBBeans);
			currentHoldingsProjectorIB.setOrdersMap(orderMap);
			currentHoldingsProjectorIB.setProductsMap(productsMap);
			currentHoldingsProjectorIB.setRentMap(rentMap);
			currentHoldingsProjectorIB.setProductImagesMap(productImageMap);

			currentHoldingsProjectorOBs= currentHoldingsProjector.getMyCurrentHoldingsProjector(currentHoldingsProjectorIB);
		}
		return currentHoldingsProjectorOBs;
	}

	public void enableAutorenewal(String holdingId)
	{
		CurrentHoldingsDBBean currentHoldingsDBBean = currentHoldingsDao.getCurrentHolding(holdingId);
		if(null!=userProfile && userProfile.getUserName().equals(currentHoldingsDBBean.getUsername()))
		{
			currentHoldingsDBBean.setAutorenew(true);
			currentHoldingsDao.setAutoRenewStatus(currentHoldingsDBBean);
		}
	}

	public void disableAutorenewal(String holdingId)
	{
		CurrentHoldingsDBBean currentHoldingsDBBean = currentHoldingsDao.getCurrentHolding(holdingId);
		if(null!=userProfile && userProfile.getUserName().equals(currentHoldingsDBBean.getUsername()))
		{
			currentHoldingsDBBean.setAutorenew(false);
			currentHoldingsDao.setAutoRenewStatus(currentHoldingsDBBean);
		}
	}

	private void sendPaymentNotifications(final List<PaymentsDBBean> paymentsDBBeans)
	{
		Runnable myrunnable = new Runnable() {
			public void run() {
				List<String> usernames = new ArrayList<String>();
				for(PaymentsDBBean paymentsDBBean: paymentsDBBeans)
				{
					if(!usernames.contains(paymentsDBBean.getFromusername()))
					{
						usernames.add(paymentsDBBean.getFromusername());
					}
					if(!usernames.contains(paymentsDBBean.getTousername()))
					{
						usernames.add(paymentsDBBean.getTousername());
					}
				}

				if(usernames.size()>0)
				{
					List<UsersDBBean> usersDBBeans = usersDao.getMultipleUserDetails(usernames);
					Map<String, UsersDBBean> usermap = CommonUtility.getUsersmap(usersDBBeans);

					for(PaymentsDBBean paymentsDBBean: paymentsDBBeans)
					{
						MailHandler.paymentAddedMail(paymentsDBBean, usermap.get(paymentsDBBean.getTousername()));
						MailHandler.paymentAddedMail(paymentsDBBean, usermap.get(paymentsDBBean.getFromusername()));
					}
				}
			}
		};
		new Thread(myrunnable).start();
	}
	
	public RentOffersDao getRentOffersDao() {
		return rentOffersDao;
	}

	public void setRentOffersDao(RentOffersDao rentOffersDao) {
		this.rentOffersDao = rentOffersDao;
	}

	public CurrentHoldingsDao getCurrentHoldingsDao() {
		return currentHoldingsDao;
	}

	public void setCurrentHoldingsDao(CurrentHoldingsDao currentHoldingsDao) {
		this.currentHoldingsDao = currentHoldingsDao;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public PaymentsDao getPaymentsDao() {
		return paymentsDao;
	}

	public void setPaymentsDao(PaymentsDao paymentsDao) {
		this.paymentsDao = paymentsDao;
	}

	public OrdersDao getOrdersDao() {
		return ordersDao;
	}

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	public ProductsDao getProductsDao() {
		return productsDao;
	}

	public void setProductsDao(ProductsDao productsDao) {
		this.productsDao = productsDao;
	}

	public CurrentHoldingsProjector getCurrentHoldingsProjector() {
		return currentHoldingsProjector;
	}

	public void setCurrentHoldingsProjector(
			CurrentHoldingsProjector currentHoldingsProjector) {
		this.currentHoldingsProjector = currentHoldingsProjector;
	}

	public ImagesDao getImagesDao() {
		return imagesDao;
	}

	public void setImagesDao(ImagesDao imagesDao) {
		this.imagesDao = imagesDao;
	}

	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
}
