package order.appService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import order.appService.inputBeans.Address;
import order.appService.inputBeans.OrderAppServiceIB;
import order.dao.outputBeans.OrderDaoOB;
import order.projector.OrderProjector;
import order.projector.outputBeans.OrderProjectorOB;
import payment.appService.inputBeans.PaymentAppServiceIB;
import cart.appService.CartAppService;
import cart.projector.outputBeans.CartItem;
import cart.projector.outputBeans.CartProjectorOB;

import com.dao.AddressDao;
import com.dao.OrdersDao;
import com.dao.PaymentsDao;
import com.dao.ProductsDao;
import com.dao.RentOffersDao;
import com.dao.UsersDao;
import com.databaseBeans.AddressDBBean;
import com.databaseBeans.OrdersDBBean;
import com.databaseBeans.PaymentsDBBean;
import com.databaseBeans.ProductsDBBean;
import com.databaseBeans.RentOffersDBBean;
import com.databaseBeans.UsersDBBean;
import com.sessionBeans.UserProfile;
import com.structures.status.OrderStatus;
import com.structures.userTypes.UserType;
import com.util.CommonUtility;
import com.util.MailHandler;
import com.util.SMSHandler;

import currentHoldings.appService.CurrentHoldingsAppService;

/**
 * @author nd29794
 *
 */
public class OrderAppService {

	CartAppService cartAppService;
	OrderProjector orderProjector;
	UserProfile userProfile;
	OrdersDao ordersDao;
	ProductsDao productsDao;
	RentOffersDao rentOffersDao;
	PaymentsDao paymentsDao;
	AddressDao addressDao;
	UsersDao usersDao;
	CurrentHoldingsAppService currentHoldingsAppService;

	public OrderProjectorOB getCartOrderInput() {
		CartProjectorOB cartProjectorOB = cartAppService.viewCart();
		OrderDaoOB orderDaoOB = new OrderDaoOB();
		orderDaoOB.setCartItems(cartProjectorOB.getCartItems());
		OrderProjectorOB orderProjectorOB = orderProjector
				.getOrderInput(orderDaoOB);
		AddressDBBean addressDBBean = addressDao
				.getAddressForUser(userProfile.getUserName());
		orderProjectorOB.setAddressDBBean(addressDBBean);
		return orderProjectorOB;
	}

	public void checkIfDeliveryAvailable(Address address,
			OrderProjectorOB orderProjectorOB) {
		for (CartItem cartItem : orderProjectorOB.getCartItems()) {
			boolean deliveryAvailable = false;
			if (cartItem.getProductState().equals(address.getState())
					&& cartItem.getProductCity().equals(address.getCity())) {
				if (cartItem.getProductPin().equals("All")) {
					deliveryAvailable = true;
				} else {
					String[] pins = cartItem.getProductPin().split("\\|");
					List<String> pinList = Arrays.asList(pins);
					if (pinList.contains(address.getPin())) {
						deliveryAvailable = true;
					}
				}
			}
			cartItem.setDeliveryAvailable(deliveryAvailable);
		}
	}

	public OrderProjectorOB placeCartOrder(List<CartItem> cartItems,
			Address address) {
		List<OrderAppServiceIB> orderAppServiceIBs = new ArrayList<OrderAppServiceIB>();
		for (CartItem cartItem : cartItems) {
			OrderAppServiceIB orderAppServiceIB = new OrderAppServiceIB();
			orderAppServiceIB.setProductid(cartItem.getProductId());
			orderAppServiceIB.setRentid(cartItem.getRentId());
			orderAppServiceIB.setUsername(userProfile.getUserName());
			orderAppServiceIBs.add(orderAppServiceIB);
		}
		List<OrdersDBBean> ordersDBBeans = ordersDao.addOrder(
				orderAppServiceIBs, userProfile.getUserName(), address);
		if (StringUtils.isEmpty(address.getAddressId()) || "0".equals(address.getAddressId()) ) {
			addressDao.addAddress(address, userProfile.getUserName());
		}
		else {
			addressDao.updateAddress(address, userProfile.getUserName());
		}

		Iterator<CartItem> cartIterator = cartItems.iterator();
		Iterator<OrdersDBBean> orderIterator = ordersDBBeans.iterator();
		List<PaymentAppServiceIB> paymentAppServiceIBs = new ArrayList<PaymentAppServiceIB>();
		while (cartIterator.hasNext() && orderIterator.hasNext()) {

			try{
				CartItem cartItem = cartIterator.next();
				OrdersDBBean ordersDBBean = orderIterator.next();
				PaymentAppServiceIB paymentAppServiceIB = new PaymentAppServiceIB();
				paymentAppServiceIB.setFromusername(userProfile.getUserName());
				paymentAppServiceIB.setOrderid(ordersDBBean.getOrderid());
				paymentAppServiceIB.setRentamount(cartItem.getRentAmount());
				paymentAppServiceIB.setDeliveryCharge(cartItem.getDeliveryCharge());
				paymentAppServiceIB.setSecuritymoney(cartItem.getSecurityMoney());
				ProductsDBBean productsDBBean =productsDao.getProductDetails(ordersDBBean.getProductid());
				UsersDBBean usersDBBean = usersDao.getUserDetails(productsDBBean.getUsername());

				int total = 0;
				if(StringUtils.isNotEmpty(cartItem.getRentAmount()))
					total=total+Integer.parseInt(cartItem.getRentAmount());
				if(StringUtils.isNotEmpty(cartItem.getDeliveryCharge()))
					total=total+Integer.parseInt(cartItem.getDeliveryCharge());
				if(StringUtils.isNotEmpty(cartItem.getSecurityMoney()))
					total=total+Integer.parseInt(cartItem.getSecurityMoney());

				sendOrderNotifications(usersDBBean, productsDBBean, cartItem, ordersDBBean, total);

				paymentAppServiceIB.setTousername(productsDBBean.getUsername());
				paymentAppServiceIBs.add(paymentAppServiceIB);
			}catch(Exception e){

			}

		}
		List<PaymentsDBBean> paymentsDBBeans = paymentsDao.addPayment(paymentAppServiceIBs);
		sendPaymentNotifications(paymentsDBBeans);
		cartAppService.emptyCart();
		OrderProjectorOB orderProjectorOB = orderProjector
				.confirmOrder(ordersDBBeans);
		return orderProjectorOB;
	}

	public OrderProjectorOB viewOrders(boolean isAdmin) {
		OrderProjectorOB orderProjectorOB = null;
		List<OrdersDBBean> ordersDBBeans = null;
		if (isAdmin) {
			ordersDBBeans = ordersDao.getOrdersForAdmin();
		} else {
			ordersDBBeans = ordersDao.getOrdersForUser(userProfile
					.getUserName());
		}
		if (null != ordersDBBeans && ordersDBBeans.size() >= 1) {
			List<Integer> productIds = new ArrayList<Integer>();
			List<Integer> rentOfferIds = new ArrayList<Integer>();
			for (OrdersDBBean ordersDBBean : ordersDBBeans) {
				productIds.add(ordersDBBean.getProductid());
				rentOfferIds.add(ordersDBBean.getRentid());
			}
			List<ProductsDBBean> productsDBBeans = productsDao
					.getProductListByIdsInteger(productIds);
			List<RentOffersDBBean> rentOffersDBBeans = rentOffersDao
					.getRentOffersByIdsInteger(rentOfferIds);

			orderProjectorOB = orderProjector.viewOrders(ordersDBBeans,
					productsDBBeans, rentOffersDBBeans);
		}
		return orderProjectorOB;
	}

	public OrderProjectorOB getOrdersForVendor() {
		OrderProjectorOB orderProjectorOB = null;
		List<OrdersDBBean> ordersDBBeans = null;

		if (userProfile.getUserType().equals(UserType.VENDOR)) {
			List<ProductsDBBean> productsDBBeans = productsDao
					.searchByVendor(userProfile.getUserName());
			ArrayList<Integer> productIds = new ArrayList<Integer>();
			for (ProductsDBBean productsDBBean : productsDBBeans) {
				productIds.add(productsDBBean.getProductid());
			}
			if (productIds.size() >= 1) {
				ordersDBBeans = ordersDao.getOrdersForVendor(productIds);
				if (null != ordersDBBeans && ordersDBBeans.size() >= 1) {
					List<Integer> rentOfferIds = new ArrayList<Integer>();
					for (OrdersDBBean ordersDBBean : ordersDBBeans) {
						productIds.add(ordersDBBean.getProductid());
						rentOfferIds.add(ordersDBBean.getRentid());
					}
					List<RentOffersDBBean> rentOffersDBBeans = rentOffersDao
							.getRentOffersByIdsInteger(rentOfferIds);

					orderProjectorOB = orderProjector.viewOrders(ordersDBBeans,
							productsDBBeans, rentOffersDBBeans);
				}
			}
		}
		return orderProjectorOB;
	}

	public OrderProjectorOB getOrdersForAdmin() {
		OrderProjectorOB orderProjectorOB = null;
		if (userProfile.getUserType().equals(UserType.ADMIN)) {
			orderProjectorOB = viewOrders(true);
		}
		return orderProjectorOB;
	}

	public void changeOrderStatus(OrderAppServiceIB orderAppServiceIB) {
		if(null!=userProfile && userProfile.getUserType().equals(UserType.ADMIN))
		{
			OrdersDBBean ordersDBBean =ordersDao.changeOrderStatus(orderAppServiceIB.getOrderId(),
					OrderStatus.valueOf(orderAppServiceIB.getOrderStatus()));
			if(ordersDBBean.getOrderstatus().equals(OrderStatus.COMPLETE.toString()))
			{
				currentHoldingsAppService.addCurrentHolding(ordersDBBean);
			}
		}
	}


	private void sendOrderNotifications(final UsersDBBean usersDBBean,final ProductsDBBean productsDBBean, final CartItem cartItem,
			final OrdersDBBean ordersDBBean, final int total)
	{
		Runnable myrunnable = new Runnable() {
			public void run() {
				SMSHandler.sendOrderConfirmationSmsVendor(usersDBBean.getMobileno1(), productsDBBean, ordersDBBean,cartItem, total);
				SMSHandler.sendOrderConfirmationSmsCustomer(userProfile.getMobile(), productsDBBean, ordersDBBean, total);
				MailHandler.orderConfirmationMailVendor(productsDBBean,ordersDBBean,usersDBBean.getEmail());
				MailHandler.orderConfirmationMailCustomer(productsDBBean,ordersDBBean,userProfile,total);
			}
		};
		new Thread(myrunnable).start();
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

	public CartAppService getCartAppService() {
		return cartAppService;
	}

	public void setCartAppService(CartAppService cartAppService) {
		this.cartAppService = cartAppService;
	}

	public OrderProjector getOrderProjector() {
		return orderProjector;
	}

	public void setOrderProjector(OrderProjector orderProjector) {
		this.orderProjector = orderProjector;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

	public RentOffersDao getRentOffersDao() {
		return rentOffersDao;
	}

	public void setRentOffersDao(RentOffersDao rentOffersDao) {
		this.rentOffersDao = rentOffersDao;
	}

	public PaymentsDao getPaymentsDao() {
		return paymentsDao;
	}

	public void setPaymentsDao(PaymentsDao paymentsDao) {
		this.paymentsDao = paymentsDao;
	}

	public AddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public CurrentHoldingsAppService getCurrentHoldingsAppService() {
		return currentHoldingsAppService;
	}

	public void setCurrentHoldingsAppService(
			CurrentHoldingsAppService currentHoldingsAppService) {
		this.currentHoldingsAppService = currentHoldingsAppService;
	}

}
