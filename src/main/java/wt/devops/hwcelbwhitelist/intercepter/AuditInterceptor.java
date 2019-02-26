package wt.devops.hwcelbwhitelist.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wt.devops.hwcelbwhitelist.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
@Component
public class AuditInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = IPUtils.getRealIP(request);

        //do interceptor, if need
        logger.info("USER IP ADDRESS IS => {}", ipAddress);
        logger.info("getContextPath:{}",  request.getContextPath());
        logger.info("getServletPath:{}",  request.getServletPath());
        logger.info("getRequestURI:{}" , request.getRequestURI());
        logger.info("getRequestURL:{}" , request.getRequestURL());

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}