package search.projector;

import java.util.ArrayList;
import java.util.List;

import search.dao.outputBeans.SearchProductDaoOB;
import search.projector.outputBeans.SearchProductProjectorOB;

import com.databaseBeans.ProductsDBBean;
import com.structures.status.ProductStatus;


public class SearchProductProjector {

	public List<SearchProductProjectorOB> getSearchList(SearchProductDaoOB searchProductDaoOB)
	{
		List<SearchProductProjectorOB> searchProductProjectorOBs = new ArrayList<SearchProductProjectorOB>();
		for(ProductsDBBean productsDBBean : searchProductDaoOB.getProductsDBBeans())
		{
			if(!productsDBBean.getApprovalStatus().equals(ProductStatus.PENDING.toString()) || searchProductDaoOB.isVendor())
			{
				SearchProductProjectorOB searchProductProjectorOB = new SearchProductProjectorOB();
				searchProductProjectorOB.setProductId(productsDBBean.getProductid());
				searchProductProjectorOB.setProductName(productsDBBean.getProductname());
				if(null!=searchProductDaoOB.getRentMap().get(String.valueOf(productsDBBean.getProductid())))
				{
					searchProductProjectorOB.setRentAmount(searchProductDaoOB.getRentMap().get(String.valueOf(productsDBBean.getProductid())).getAmount());
					searchProductProjectorOB.setPeriodValue(searchProductDaoOB.getRentMap().get(String.valueOf(productsDBBean.getProductid())).getPeriodvalue());
					searchProductProjectorOB.setPeriodUnit(searchProductDaoOB.getRentMap().get(String.valueOf(productsDBBean.getProductid())).getPeriodunit());
				}
				if(null!= searchProductDaoOB.getImageMap() && null != searchProductDaoOB.getImageMap().get(String.valueOf(productsDBBean.getProductid())))
				{
					searchProductProjectorOB.setImageUrl(searchProductDaoOB.getImageMap().get(String.valueOf(productsDBBean.getProductid())).getImagepath());
				}

				searchProductProjectorOB.setProductCity(productsDBBean.getProductcity());
				searchProductProjectorOB.setProductState(productsDBBean.getProductstate());
				searchProductProjectorOB.setProductPin(productsDBBean.getProductpin());
				searchProductProjectorOB.setProductType(productsDBBean.getProducttype());
				searchProductProjectorOB.setProductSubtype(productsDBBean.getSubproducttype());
				searchProductProjectorOB.setUserName(productsDBBean.getUsername());
				searchProductProjectorOBs.add(searchProductProjectorOB);
			}
		}
		return searchProductProjectorOBs;
	}
	
	public List<SearchProductProjectorOB> getFeaturedProduct(SearchProductDaoOB searchProductDaoOB)
	{
		List<SearchProductProjectorOB> searchProductProjectorOBs = new ArrayList<SearchProductProjectorOB>();
		for(ProductsDBBean productsDBBean : searchProductDaoOB.getProductsDBBeans())
		{
			if(!productsDBBean.getApprovalStatus().equals(ProductStatus.PENDING.toString()) || searchProductDaoOB.isVendor())
			{
				SearchProductProjectorOB searchProductProjectorOB = new SearchProductProjectorOB();
				searchProductProjectorOB.setProductId(productsDBBean.getProductid());
				searchProductProjectorOB.setProductName(productsDBBean.getProductname());
				
				if(null!= searchProductDaoOB.getImageMap() && null != searchProductDaoOB.getImageMap().get(String.valueOf(productsDBBean.getProductid())))
				{
					searchProductProjectorOB.setImageUrl(searchProductDaoOB.getImageMap().get(String.valueOf(productsDBBean.getProductid())).getImagepath());
				}

				searchProductProjectorOB.setProductCity(productsDBBean.getProductcity());
				searchProductProjectorOB.setProductState(productsDBBean.getProductstate());
				searchProductProjectorOB.setProductPin(productsDBBean.getProductpin());
				searchProductProjectorOB.setProductType(productsDBBean.getProducttype());
				searchProductProjectorOB.setProductSubtype(productsDBBean.getSubproducttype());
				searchProductProjectorOB.setUserName(productsDBBean.getUsername());
				searchProductProjectorOBs.add(searchProductProjectorOB);
			}
		}
		return searchProductProjectorOBs;
	}
	
	
	
}
