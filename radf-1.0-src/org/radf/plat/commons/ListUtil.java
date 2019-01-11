package org.radf.plat.commons;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import org.radf.plat.util.exception.AppException;



/**
 * <p>
 * Title: 劳动力市场信息系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: tzsw
 * </p>
 * 
 * @author llc
 * @version 1.0
 */

public class ListUtil {
	public ListUtil() {
	}

	/**
	 * 将List中实体中某个属性的值读取出来并组成一个指定分隔符分隔的字符串
	 * 
	 * @param list
	 *            List 读取值的List对象
	 * @param prop
	 *            String 属性名
	 * @param separator
	 *            String 分隔符
	 * @throws ApplicationException
	 * @return String
	 */
	public static String listToString(List list, String prop, String separator)
			throws AppException {
		StringBuffer ret = new StringBuffer("");
		String property = "";

		if (list == null) {
			throw new AppException("输入的列表为空！");
		}
		for (int i = 0; i < list.size(); i++) {
			Object entity = list.get(i);
			try {
				property = BeanUtils.getSimpleProperty(entity, prop);
			} catch (NoSuchMethodException ex) {
				throw new AppException("没有指定的" + prop + "方法！", ex);
			} catch (InvocationTargetException ex) {
				throw new AppException("没有指定的" + prop + "目标！", ex);
			} catch (IllegalAccessException ex) {
				throw new AppException("访问" + prop + "属性时出错！", ex);
			}

			if ((property == null) || "".equals(property))
				throw new AppException("输入的列表有项目的值为空！");
			ret.append(separator).append(property);
		}

		return ret.substring(separator.length());
	}

	/**
	 * 将List中查询结果中某个属性的值读取出来并组成一个指定分隔符分隔的字符
	 * 
	 * @param list
	 *            List 读取值的List对象
	 * @param prop
	 *            String 属性名
	 * @param separator
	 *            String 分隔符
	 * @throws AppException
	 * @return String
	 */
	public static String listToString(List list, String prop, String separator,
			QueryInfo qi) throws AppException {
		StringBuffer ret = new StringBuffer("");
		String property = "";

		if (list == null) {
			throw new AppException("输入的列表为空！");
		}
		for (int i = 0; i < list.size(); i++) {
			Object entity = list.get(i);
			try {
				if (entity instanceof Object[]) {
					property = TypeCast
							.objToString(((Object[]) entity)[ActionUtil
									.getIndex(qi, prop)]);
				} else {
					property = BeanUtils.getSimpleProperty(entity, prop);
				}

			} catch (NoSuchMethodException ex) {
				throw new AppException("没有指定的" + prop + "方法！", ex);
			} catch (InvocationTargetException ex) {
				throw new AppException("没有指定的" + prop + "目标！", ex);
			} catch (IllegalAccessException ex) {
				throw new AppException("访问" + prop + "属性时出错！", ex);
			}

			if ((property == null) || "".equals(property))
				property = "0";
			ret.append(separator).append(property);
		}

		return ret.substring(separator.length());
	}

	

	/**
	 * 在List列表中找到指定的属性中，参数值为Value的索引。
	 * 
	 * @param list
	 *            List 查找对象List
	 * @param prop[]
	 *            String 查找的属性
	 * @param value
	 *            String 查找属性的值
	 * @return @throws
	 *         AppException
	 */
	public static int getIndex(List list, String prop, String value)
			throws AppException {
		int index = -1;
		Object ent = null;
		if (list == null)
			return index;
		if (isNull(prop))
			throw new AppException("属性参数为空！");
		if (isNull(value))
			throw new AppException("参数值为空！");

		try {
			for (int i = 0; i < list.size(); i++) {
				ent = list.get(i);
				if (value.equals(BeanUtils.getSimpleProperty(ent, prop))) {
					index = i;
					i = list.size();
				}
			}
		} catch (NoSuchMethodException ex) {
			throw new AppException("没有指定的" + prop + "方法！", ex);
		} catch (InvocationTargetException ex) {
			throw new AppException("没有指定的" + prop + "目标！", ex);
		} catch (IllegalAccessException ex) {
			throw new AppException("访问" + prop + "属性时出错！", ex);
		}
		return index;
	}

	/**
	 * 在List列表中找到指定的属性中，参数值为Value的索引。
	 * 
	 * @param list
	 *            List 查找对象List
	 * @param prop[]
	 *            String 查找的属性
	 * @param value[]
	 *            String 查找属性的值
	 * @return @throws
	 *         AppException
	 */
	public static int getIndex(List list, String prop[], String value[])
			throws AppException {
		int index = -1;
		Object ent = null;
		if (list == null)
			return index;
		if (NullFlag.isObjNull(prop))
			throw new AppException("属性参数为空！");
		if (NullFlag.isObjNull(value))
			throw new AppException("参数值为空！");

		if (prop.length != value.length) {
			throw new AppException("输入的属性与值不匹配！");
		}

		try {
			for (int i = 0; i < list.size(); i++) {
				boolean flag = true;
				ent = list.get(i);
				for (int j = 0; j < value.length; j++) {
					if (!value[j].equals(BeanUtils.getSimpleProperty(ent,
							prop[j]))) {
						j = value.length;
						flag = false;
					}
				}
				if (flag) {
					index = i;
					i = list.size();
				}
			}
		} catch (NoSuchMethodException ex) {
			throw new AppException("没有指定的" + prop + "方法！", ex);
		} catch (InvocationTargetException ex) {
			throw new AppException("没有指定的" + prop + "目标！", ex);
		} catch (IllegalAccessException ex) {
			throw new AppException("访问" + prop + "属性时出错！", ex);
		}
		return index;
	}

	/**
	 * 取得一个列表里元素中某个字段最大的那个元素
	 * 
	 * @param list
	 *            要使用的列表
	 * @param prop
	 *            比较的字段名
	 * @return int 值最大 的那个列表元素索引
	 * @throws AppException
	 */
	public static int getPropertyMaxValue(List list, String prop)
			throws AppException {
		Object obj = null, t;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		Iterator it = list.iterator();
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            t	=	PropertyUtils.getSimpleProperty(obj,prop);
	            if (t instanceof Comparable) {
	                value = (Comparable) t;
				} else {
					throw new AppException("指定的属性不能比较大小！");
				}
	            
	            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
					oldvalue = value;
					rtn	=	i;
				}
	        }
		} catch (Exception e) {
			throw new AppException(e);
		}
		
		if (NullFlag.isObjNull(oldvalue)) {
		    rtn	=	-1;
		}
		return rtn;
	}
	
	/**
	 * 取得一个列表里元素中某个字段最大的那个元素
	 * 
	 * @param list
	 *            要使用的列表
	 * @param prop
	 *            比较的字段名
	 * @return int 值最小的那个列表元素索引
	 * @throws AppException
	 */
	public static int getPropertyMinValue(List list, String prop)
			throws AppException {
		Object obj = null, t;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		Iterator it = list.iterator();
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            t	=	PropertyUtils.getSimpleProperty(obj,prop);
	            if (t instanceof Comparable) {
	                value = (Comparable) t;
				} else {
					throw new AppException("指定的属性不能比较大小！");
				}
	            
	            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
					oldvalue = value;
					rtn	=	i;
				}
	        }
		} catch (Exception e) {
			throw new AppException(e);
		}
		
		if (NullFlag.isObjNull(oldvalue)) {
		    rtn	=	-1;
		}
		return rtn;
	}
	

	/**
	 * 取得一个列表里元素中某个字段最大的那个元素
	 * 
	 * @param list
	 *            要使用的列表
	 * @param prop
	 *            比较的字段名
	 * @return int 值最大 的那个列表元素索引
	 * @throws AppException
	 */
	public static int getPropertyMaxValue(List list, int	index)
			throws AppException {
		Object	obj	=	null;
		Object	t	=	null;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            if (obj instanceof Object[]) {
		            t	=	((Object[])obj)[index];
		            
		            if (t instanceof Comparable) {
		                value = (Comparable) t;
					} else {
						throw new AppException("指定的属性不能比较大小！");
					}
		            
		            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
						oldvalue = value;
						rtn	=	i;
					}
		        }
			}
        } catch (Exception e) {
            throw new AppException(e);
        }

        if (NullFlag.isObjNull(oldvalue)) {
            rtn = -1;
        }
        return rtn;
	}
	
	/**
	 * 取得一个列表里元素中某个字段最大的那个元素
	 * 
	 * @param list
	 *            要使用的列表
	 * @param prop
	 *            比较的字段名
	 * @return int 值最小的那个列表元素索引
	 * @throws AppException
	 */
	public static int getPropertyMinValue(List list, int	index)
			throws AppException {
		Object obj = null, t;
		Comparable value, oldvalue = null;
		int rtn	=	-1;
		Iterator it = list.iterator();
		try {
			for (int i = 0; i < list.size(); i++) {
	            obj =	list.get(i);
	            if (obj instanceof Object[]) {
		            t	=	((Object[])obj)[index];
		            
		            if (t instanceof Comparable) {
		                value = (Comparable) t;
					} else {
						throw new AppException("指定的属性不能比较大小！");
					}
		            
		            if (NullFlag.isObjNull(oldvalue)||!NullFlag.isObjNull(value) && value.compareTo(oldvalue) > 0) {
						oldvalue = value;
						rtn	=	i;
					}
		        }
			}
        } catch (Exception e) {
            throw new AppException(e);
        }

        if (NullFlag.isObjNull(oldvalue)) {
            rtn = -1;
        }
		return rtn;
	}
	
	/**
	 * 将数组中的值拷贝到Class相应的属性中，要求数组中值的排列顺序与Class定义顺序一致
	 * 
	 * @param Object[] obj
	 * @param Class obj1
	 * @return 
	 * @throws AppException
	 */
	public static List trimList(List list) throws AppException {
		List result = new Vector();
		for (int i = 0; i < list.size(); i++) {
			Object obj = (Object) list.get(i);
			if (obj instanceof Object[]) {
				Object[] obj1 = (Object[]) obj;
				for (int j = 0; j < obj1.length; j++) {
					if (obj1[j] instanceof String) {
						obj1[j] = TypeCast.objToString(obj1[j]).trim();
					}
				}
			}
			result.add(obj);
		}
		return result;
	}

	/**
	 * 判断输入的对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean isNull(Object obj) {
		boolean ret = false;

		if (obj == null) {
			ret = true;
		} else if ("".equals(obj.toString())) {
			ret = true;
		}

		return ret;
	}

}