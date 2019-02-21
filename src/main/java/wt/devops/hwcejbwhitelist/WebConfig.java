package wt.devops.hwcejbwhitelist;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import wt.devops.hwcejbwhitelist.intercepter.AuditInterceptor;

/**
 * @ClassName: WebConfig
 * @Description:
 * @author
 * @date
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Resource
	private AuditInterceptor auditInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(auditInterceptor).addPathPatterns("/huaweicloud/ejb/whitelist/**");
	}
}