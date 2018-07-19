package cn.qiniu.vo;

import java.util.List;

import cn.qiniu.util.PagerForm;

/**
 * Created by roderickWang on 6/24/16.
 */
public class PagerData {
    private int totalPageCount;
    private int current;
    private int rowCount;
    private int totalRowCount;
    private List data;
    
    public PagerData(List data,PagerForm pageform, int totalRow) {
        setData(data);
        setCurrent(pageform.getCurrent());
        int rowCount = pageform.getRowCount();
        setRowCount(rowCount);
        setTotalRowCount(totalRow);

        int totalPageCount = totalRow / rowCount;
        if (totalRow % rowCount == 0) {
            setTotalPageCount(totalPageCount);
        } else {
            setTotalPageCount(totalPageCount + 1);
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
