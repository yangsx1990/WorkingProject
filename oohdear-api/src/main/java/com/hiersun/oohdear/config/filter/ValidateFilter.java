package com.hiersun.oohdear.config.filter;/**
 * Created by liubaocheng on 2017/3/1.
 */

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-01 14:57
 **/
@WebFilter
public class ValidateFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
