package com.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.beanutils.PropertyUtils;

public class CommonUtility {

	public static void copyBean(Object sourceBean, Object destinationBean)
	{
		Map<String, Object> elementsMap = null;
		try {
			elementsMap = PropertyUtils.describe(destinationBean);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null!= elementsMap )
		{
			Set<String> elementSet = elementsMap.keySet();
			for(String element : elementSet )
			{
				Object value = null;
				try {
					
					value = PropertyUtils.getProperty(sourceBean, element);

					if(null!= value && !element.equals("class"))
					{

						PropertyUtils.setProperty(destinationBean, element, value);
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
	}



}
