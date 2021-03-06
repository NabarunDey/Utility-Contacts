package com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import addProduct.appService.inputBeans.AddRentOffersAppServiceIB;
import addProduct.dao.outputBeans.AddRentOfferDaoOB;

import com.databaseBeans.ProductsDBBean;
import com.databaseBeans.RentOffersDBBean;

@Transactional
public class RentOffersDao {

	HibernateTemplate template;  


	public void setTemplate(HibernateTemplate template) {  
		this.template = template;  
	}  



	public AddRentOfferDaoOB addRentOffer(
			AddRentOffersAppServiceIB addRentOfferAppServiceIB) {

		String[] periodUnit = addRentOfferAppServiceIB.getPeriodunit().split(",");
		String[] periodValue = addRentOfferAppServiceIB.getPeriodvalue().split(",");
		String[] rentAmount = addRentOfferAppServiceIB.getAmount().split(",");
		boolean success = true;
		int count =0;
		try{
			while(count<periodUnit.length)
			{
				if(StringUtils.isNotEmpty(periodValue[count].trim()) && StringUtils.isNotEmpty(rentAmount[count].trim()))
				{
					RentOffersDBBean rentOffersDBBean  = new  RentOffersDBBean();
					rentOffersDBBean.setProductid(addRentOfferAppServiceIB.getProductid());
					rentOffersDBBean.setPeriodunit(periodUnit[count].trim());
					rentOffersDBBean.setPeriodvalue(periodValue[count].trim());
					rentOffersDBBean.setAmount(rentAmount[count].trim());
					template.getSessionFactory().getCurrentSession().save(rentOffersDBBean);
				}
				count++;
			}
		}catch (Exception exception)
		{
			success= false;
		}
		AddRentOfferDaoOB addRentOfferDaoOB =new AddRentOfferDaoOB();
		addRentOfferDaoOB.setSuccess(success);
		return addRentOfferDaoOB;
	}

	public List<RentOffersDBBean> getAllRentOffersForProduct(int productId){  
		List<RentOffersDBBean> list; 
		RentOffersDBBean rentOffersDBBean = new RentOffersDBBean();
		rentOffersDBBean.setProductid(productId);

		list =(List<RentOffersDBBean>) template.getSessionFactory().getCurrentSession().createCriteria(RentOffersDBBean.class)
				.add(Example.create(rentOffersDBBean)).list();
		return list;  
	}  

	public Map<String,RentOffersDBBean> getMinimumRents(List<ProductsDBBean> productsDBBeans)
	{
		Map<String,RentOffersDBBean> rentMap = new HashMap<String, RentOffersDBBean>();
		List<Integer> productIds = new ArrayList<Integer>();
		for(ProductsDBBean productsDBBean : productsDBBeans)
		{
			productIds.add(productsDBBean.getProductid());
		}
		Criteria criteria = template.getSessionFactory().getCurrentSession().createCriteria(RentOffersDBBean.class)
				.add(Restrictions.in("productid", productIds));
		List<RentOffersDBBean> rentOffersDBBeans = criteria.list();

		for(RentOffersDBBean rentOffersDBBean : rentOffersDBBeans)
		{
			try{
				if(rentMap.containsKey(String.valueOf(rentOffersDBBean.getProductid()))
						&& Integer.parseInt(rentMap.get(String.valueOf(rentOffersDBBean.getProductid())).getAmount().trim()) 
						< Integer.parseInt(rentOffersDBBean.getAmount().trim()))
				{
					continue;
				}
				rentMap.put(String.valueOf(rentOffersDBBean.getProductid()), rentOffersDBBean);
			}catch(Exception e)
			{
				System.out.println("Error in RentOffersDao.getMinimumRents rent id :"+rentOffersDBBean.getRentid());
			}
		}

		return rentMap;
	}
	public void deleteRentOffer(String productId)
	{
		String hql = "delete from com.databaseBeans.RentOffersDBBean where productid= :productid";
		template.getSessionFactory().getCurrentSession().createQuery(hql).setInteger("productid", Integer.parseInt(productId)).executeUpdate();
	}

	public List<RentOffersDBBean> getRentOffersByIdsString(List<String> rentIds)
	{
		List<RentOffersDBBean> rentOffersDBBean = null;
		List<Integer> integers = new ArrayList<Integer>();
		for(String rentId : rentIds)
		{
			integers.add(Integer.parseInt(rentId));
		}
		Criteria criteria = template.getSessionFactory().getCurrentSession().createCriteria(RentOffersDBBean.class)
				.add(Restrictions.in("rentid", integers));
		rentOffersDBBean=criteria.list();
		return rentOffersDBBean;
	}

	public List<RentOffersDBBean> getRentOffersByIdsInteger(List<Integer> rentIds)
	{
		List<RentOffersDBBean> rentOffersDBBean = null;
		if(null!= rentIds && rentIds.size()>0)
		{
			Criteria criteria = template.getSessionFactory().getCurrentSession().createCriteria(RentOffersDBBean.class)
					.add(Restrictions.in("rentid", rentIds));
			rentOffersDBBean=criteria.list();
		}
		return rentOffersDBBean;
	}

	public RentOffersDBBean getRentOffersById(int rentId)
	{
		RentOffersDBBean rentOffersDBBean = null;
		rentOffersDBBean = template.get(RentOffersDBBean.class, rentId);
		return rentOffersDBBean;
	}

}
