package cn.qiniu.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by roderickWang on 6/24/16.
 */
public class PagerForm {
    private int current;
    private int rowCount;

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
    public void validateForm() {
        if (StringUtils.isEmpty(String.valueOf(rowCount))||rowCount==0) {
            rowCount = 12;
        }

        if (StringUtils.isEmpty(String.valueOf(current))||current==0) {
            current = 1;
        }
    }
}
