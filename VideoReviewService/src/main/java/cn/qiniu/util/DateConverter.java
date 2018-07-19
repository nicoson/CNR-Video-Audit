package cn.qiniu.util;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import cn.qiniu.vo.VoConverter;

/**
 * Created by roderickWang on 6/25/16.
 */
@Component
public class DateConverter implements VoConverter {
    @Override
    public String convert(Object oldValue) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(oldValue);
    }
}
