package cn.qiniu.util.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * 〈转码〉
 * 〈功能详细描述〉
 * @author    FELIX
 * @version   V1.00 2015-10-10 [版本号, YYYY-MM-DD]
 * @see       [相关类/方法]
 * @since     kindMed V1.0R001 [金域文控管理系统/模块版本]
 */
public class SetCharacterEncodingFilter implements Filter
{
    private String encoding = null;
    private FilterConfig filterConfig = null;
    private boolean ignore = true;

    public SetCharacterEncodingFilter()
    {}

    public void destroy()
    {
        this.encoding = null;
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException
    {
        if (ignore || request.getCharacterEncoding() == null)
        {
            encoding = selectEncoding(request);
            if (encoding != null)
            {
                request.setCharacterEncoding(encoding);
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filteConfig)
        throws ServletException
    {
        this.filterConfig = filteConfig;
        this.encoding = this.filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null)
        {
            ignore = true;
        }
        else if (value.equalsIgnoreCase("true"))
        {
            ignore = true;
        }
        else if (value.equalsIgnoreCase("yes"))
        {
            ignore = true;
        }
        else
        {
            ignore = false;
        }
    }

    private String selectEncoding(ServletRequest request)
    {
        return this.encoding;
    }
}