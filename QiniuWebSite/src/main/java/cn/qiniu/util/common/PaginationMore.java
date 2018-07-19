package cn.qiniu.util.common;

/**
 * 
 * 〈分页对象〉 〈改对象自动封装分页所有的操作和属性〉
 * 
 * @author FELIX
 * @version V1.00 2015-10-10 [版本号, YYYY-MM-DD]
 * @see [相关类/方法]
 * @since kindMed V1.0R001 [金域文控管理系统/通用模块]
 */
public class PaginationMore
{
    public static final int PAGESIZE = 10;
    
    private int totalPages = 0;// 总页数
    
    private int currentPage = 1;// 当前页
    
    private int pageSize = 0;// 每页记录数
    
    private int totalRows = 0;// 总记录数
    
    private int startNum = 0;// 开始记录数
    
    private int nextPage = 0;// 下一页
    
    private int nextPage2 = 0;// 下2页
    
    private int nextPage3 = 0;// 下3页
    
    private int nextPage4 = 0;// 下4页
    
    private int nextPage5 = 0;// 下5页
    
    private int previousPage = 0;// 上一页
    
    private int previousPage2 = 0;// 上2页
    
    private int previousPage3 = 0;// 上3页
    
    private int previousPage4 = 0;// 上4页
    
    private int previousPage5 = 0;// 上5页
    
    private int previousPage6 = 0;// 上6页
    
    private int queryRecordSize = 10;
    
    private boolean hasNextPage = false;// 是否有下一页
    
    private boolean hasNextPage2 = false;// 是否有下2页
    
    private boolean hasNextPage3 = false;// 是否有下3页
    
    private boolean hasNextPage4 = false;// 是否有下4页
    
    private boolean hasNextPage5 = false;// 是否有下5页
    
    private boolean hasPreviousPage = false;// 是否有上一页
    
    private boolean hasPreviousPage2 = false;// 是否有上2页
    
    private boolean hasPreviousPage3 = false;// 是否有上3页
    
    private boolean hasPreviousPage4 = false;// 是否有上4页
    
    private boolean hasPreviousPage5 = false;// 是否有上5页
    
    private boolean hasPreviousPage6 = false;// 是否有上6页
    
    private OrderBean order;// 排序对象
    
    /**
     * <默认构造函数>
     */
    public PaginationMore()
    {
        this(0, 1, PAGESIZE);
    }
    
    /**
     * @param totalRows value
     * @return
     */
    public PaginationMore(int totalRows)
    {
        this(totalRows, 1, PAGESIZE);
    }
    
    /**
     * 〈〉
     * 
     * @param totalRows value
     * @param currentPage value
     * @return
     */
    public PaginationMore(int totalRows, int currentPage)
    {
        this(totalRows, currentPage, PAGESIZE);
    }
    
    /**
     * @param totalRows value
     * @param currentPage value
     * @param pageSize value
     * @return
     */
    public PaginationMore(int totalRows, int currentPage, int pageSize)
    {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRows = totalRows;
        
        this.repaginate();
    }
    
    /**
     * @return String
     */
    public String toString()
    {
        return "currentPage:" + currentPage + " totalPages:" + totalPages + " nextPage:" + nextPage + " nextPage2:"
            + nextPage2 + " hasNextPage4:" + hasNextPage4;
            
    }
    
    /*************** repaginate(),isHasNextPage(),isHasPreviousPage方法 ************************/
    public void repaginate()
    {
        // 重计算总记录数
        if ((totalRows % pageSize) == 0)
        {
            totalPages = totalRows / pageSize;
        }
        else
        {
            totalPages = totalRows / pageSize + 1;
        }
        
        // 判断当前页是否大于总页数
        if (currentPage >= totalPages)
        {
            hasNextPage = false;
            // currentPage = totalPages;
        }
        else
        {
            hasNextPage = true;
        }
        // 判断当前页+1是否大于总页数
        if (currentPage + 1 >= totalPages)
        {
            hasNextPage2 = false;
        }
        else
        {
            hasNextPage2 = true;
        }
        // 判断当前页+2是否大于总页数
        if (currentPage + 2 >= totalPages)
        {
            hasNextPage3 = false;
        }
        else
        {
            hasNextPage3 = true;
        }
        // 判断当前页+3是否大于总页数
        if (currentPage + 3 >= totalPages)
        {
            hasNextPage4 = false;
        }
        else
        {
            hasNextPage4 = true;
        }
        // 判断当前页+4是否大于总页数
        if (currentPage + 4 >= totalPages)
        {
            hasNextPage5 = false;
        }
        else
        {
            hasNextPage5 = true;
        }
        
        // 判断当前页小于1的情况
        if (currentPage <= 1)
        {
            hasPreviousPage = false;
            // currentPage = 1;
        }
        else
        {
            hasPreviousPage = true;
        }
        // 判断当前页-1小于1的情况
        if (currentPage - 1 <= 1)
        {
            hasPreviousPage2 = false;
        }
        else
        {
            hasPreviousPage2 = true;
        }
        // 判断当前页-2小于1的情况
        if (currentPage - 2 <= 1)
        {
            hasPreviousPage3 = false;
        }
        else
        {
            hasPreviousPage3 = true;
        }
        // 判断当前页-3小于1的情况
        if (currentPage - 3 <= 1)
        {
            hasPreviousPage4 = false;
        }
        else
        {
            hasPreviousPage4 = true;
        }
        // 判断当前页-4小于1的情况
        if (currentPage - 4 <= 1)
        {
            hasPreviousPage5 = false;
        }
        else
        {
            hasPreviousPage5 = true;
        }
        // 判断当前页-5小于1的情况
        if (currentPage - 5 <= 1)
        {
            hasPreviousPage6 = false;
        }
        else
        {
            hasPreviousPage6 = true;
        }
        
        startNum = (currentPage - 1) * pageSize;
        nextPage = currentPage + 1;
        nextPage2 = currentPage + 2;
        nextPage3 = currentPage + 3;
        nextPage4 = currentPage + 4;
        nextPage5 = currentPage + 5;
        
        if (nextPage >= totalPages)
        {
            nextPage = totalPages;
        }
        
        if (nextPage2 >= totalPages)
        {
            nextPage2 = totalPages;
        }
        if (nextPage3 >= totalPages)
        {
            nextPage3 = totalPages;
        }
        if (nextPage4 >= totalPages)
        {
            nextPage4 = totalPages;
        }
        if (nextPage5 >= totalPages)
        {
            nextPage5 = totalPages;
        }
        
        previousPage = currentPage - 1;
        previousPage2 = currentPage - 2;
        previousPage3 = currentPage - 3;
        previousPage4 = currentPage - 4;
        previousPage5 = currentPage - 5;
        previousPage6 = currentPage - 6;
        
        if (previousPage <= 1)
        {
            previousPage = 1;
        }
        if (previousPage2 <= 1)
        {
            previousPage2 = 1;
        }
        if (previousPage3 <= 1)
        {
            previousPage3 = 1;
        }
        if (previousPage4 <= 1)
        {
            previousPage4 = 1;
        }
        if (previousPage5 <= 1)
        {
            previousPage5 = 1;
        }
        if (previousPage6 <= 1)
        {
            previousPage6 = 1;
        }
        
        if (this.queryRecordSize == 0)
        {
            queryRecordSize = pageSize;
        }
        
    }
    
    // 是否有下一页
    public boolean isHasNextPage()
    {
        return hasNextPage;
    }
    
    // 是否有上一页
    public boolean isHasPreviousPage()
    {
        return hasPreviousPage;
    }
    
    /**************************** set/get方法 *****************************************/
    /**
     * @return int
     */
    public int getNextPage()
    {
        return nextPage;
    }
    
    public void setNextPage(int nextPage)
    {
        this.nextPage = nextPage;
    }
    
    public int getPreviousPage()
    {
        return previousPage;
    }
    
    public void setPreviousPage(int previousPage)
    {
        this.previousPage = previousPage;
    }
    
    public int getCurrentPage()
    {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTotalPages()
    {
        return totalPages;
    }
    
    public int getTotalRows()
    {
        return totalRows;
    }
    
    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }
    
    public void setHasPreviousPage(boolean hasPreviousPage)
    {
        this.hasPreviousPage = hasPreviousPage;
    }
    
    public int getStartNum()
    {
        return startNum;
    }
    
    public void setStartNum(int startNum)
    {
        this.startNum = startNum;
    }
    
    public int getQueryRecordSize()
    {
        return queryRecordSize;
    }
    
    public OrderBean getOrder()
    {
        return order;
    }
    
    public void setOrder(OrderBean order)
    {
        this.order = order;
    }
    
    public void setQueryRecordSize(int queryRecordSize)
    {
        this.queryRecordSize = queryRecordSize;
    }
    
    public void setHasNextPage(boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
    }
    
    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }
    
    public int getNextPage2()
    {
        return nextPage2;
    }
    
    public void setNextPage2(int nextPage2)
    {
        this.nextPage2 = nextPage2;
    }
    
    public boolean isHasNextPage2()
    {
        return hasNextPage2;
    }
    
    public void setHasNextPage2(boolean hasNextPage2)
    {
        this.hasNextPage2 = hasNextPage2;
    }
    
    public boolean isHasNextPage4()
    {
        return hasNextPage4;
    }
    
    public void setHasNextPage4(boolean hasNextPage4)
    {
        this.hasNextPage4 = hasNextPage4;
    }
    
    public boolean isHasNextPage3()
    {
        return hasNextPage3;
    }
    
    public void setHasNextPage3(boolean hasNextPage3)
    {
        this.hasNextPage3 = hasNextPage3;
    }
    
    public int getNextPage3()
    {
        return nextPage3;
    }
    
    public void setNextPage3(int nextPage3)
    {
        this.nextPage3 = nextPage3;
    }
    
    public int getNextPage4()
    {
        return nextPage4;
    }
    
    public void setNextPage4(int nextPage4)
    {
        this.nextPage4 = nextPage4;
    }
    
    public boolean isHasPreviousPage2()
    {
        return hasPreviousPage2;
    }
    
    public void setHasPreviousPage2(boolean hasPreviousPage2)
    {
        this.hasPreviousPage2 = hasPreviousPage2;
    }
    
    public int getPreviousPage2()
    {
        return previousPage2;
    }
    
    public void setPreviousPage2(int previousPage2)
    {
        this.previousPage2 = previousPage2;
    }
    
    public boolean isHasPreviousPage3()
    {
        return hasPreviousPage3;
    }
    
    public void setHasPreviousPage3(boolean hasPreviousPage3)
    {
        this.hasPreviousPage3 = hasPreviousPage3;
    }
    
    public boolean isHasPreviousPage4()
    {
        return hasPreviousPage4;
    }
    
    public void setHasPreviousPage4(boolean hasPreviousPage4)
    {
        this.hasPreviousPage4 = hasPreviousPage4;
    }
    
    public boolean isHasPreviousPage5()
    {
        return hasPreviousPage5;
    }
    
    public void setHasPreviousPage5(boolean hasPreviousPage5)
    {
        this.hasPreviousPage5 = hasPreviousPage5;
    }
    
    public int getPreviousPage3()
    {
        return previousPage3;
    }
    
    public void setPreviousPage3(int previousPage3)
    {
        this.previousPage3 = previousPage3;
    }
    
    public int getPreviousPage4()
    {
        return previousPage4;
    }
    
    public void setPreviousPage4(int previousPage4)
    {
        this.previousPage4 = previousPage4;
    }
    
    public int getPreviousPage5()
    {
        return previousPage5;
    }
    
    public void setPreviousPage5(int previousPage5)
    {
        this.previousPage5 = previousPage5;
    }
    
    public int getNextPage5()
    {
        return nextPage5;
    }
    
    public void setNextPage5(int nextPage5)
    {
        this.nextPage5 = nextPage5;
    }
    
    public boolean isHasNextPage5()
    {
        return hasNextPage5;
    }
    
    public void setHasNextPage5(boolean hasNextPage5)
    {
        this.hasNextPage5 = hasNextPage5;
    }
    
    public int getPreviousPage6()
    {
        return previousPage6;
    }
    
    public void setPreviousPage6(int previousPage6)
    {
        this.previousPage6 = previousPage6;
    }
    
    public boolean isHasPreviousPage6()
    {
        return hasPreviousPage6;
    }
    
    public void setHasPreviousPage6(boolean hasPreviousPage6)
    {
        this.hasPreviousPage6 = hasPreviousPage6;
    }
}
