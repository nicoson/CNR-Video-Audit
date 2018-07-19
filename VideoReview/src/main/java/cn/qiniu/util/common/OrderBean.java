package cn.qiniu.util.common;

import java.io.Serializable;


/**
 * 
 * 〈排序对象〉
 * 〈功能详细描述〉
 * @author    FELIX
 * @version   V1.00 2015-10-10 [版本号, YYYY-MM-DD]
 * @see       [相关类/方法]
 * @since     kindMed V1.0R001 [金域文控管理系统/通用模块]
 */
public class OrderBean implements Serializable
{
    private static final long serialVersionUID = 647958405335027261L;

    private String propertyName;

    private String ascending;

    /**
     * @return  
     */
    public OrderBean()
    {
    }

    /**
     *@param propertyName value
     *@param ascending value
     * @return 
     */
    protected OrderBean(String propertyName, String ascending)
    {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    /**
     *@param propertyName value
     * @return OrderBean
     */
    public static OrderBean asc(String propertyName)
    {
        return new OrderBean(propertyName, "asc");
    }

    /**
     *@param propertyName value
     * @return OrderBean 
     */
    public static OrderBean desc(String propertyName)
    {
        return new OrderBean(propertyName, "desc");
    }

    public String getPropertyName()
    {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public String getAscending()
    {
        return ascending;
    }

    public void setAscending(String ascending)
    {
        this.ascending = ascending;
    }

    /**
     *@param other value
     * @return boolean
     */
    public boolean equals(Object other)
    {
    	if (this.propertyName == null || this.ascending == null) {
    		return false;
    	}
    	
        if ( ! (other instanceof OrderBean))
        {
            return false;
        }

        OrderBean order = (OrderBean)other;
        if (this.propertyName.equals(order.getPropertyName())
            && (this.ascending.equals(order.getAscending())))
        {
            return true;
        }
        return false;
    }

    /**
     * hashCode
     * @return int hashcode
     */
    public int hashCode()
    {
    	if (this.propertyName == null) {
    		return super.hashCode();
    	}
        return this.propertyName.hashCode();
    }

    /**
     * @return String
     */
    public String toString()
    {
        return "";
    }
}
