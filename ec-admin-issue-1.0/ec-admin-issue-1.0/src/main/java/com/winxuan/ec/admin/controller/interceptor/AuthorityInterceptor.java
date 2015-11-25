package com.winxuan.ec.admin.controller.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.authority.Resource;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.authority.resource.ResourceService;
import com.winxuan.framework.validator.IdentityValidator;


/**
 * 权限控制拦截器
 * @author sunflower
 *
 */
@Component
@Aspect
public class AuthorityInterceptor {
	
	private static final Log LOG = LogFactory.getLog(AuthorityInterceptor.class);
	
	private static final String FORBIDDEN = "/forbidden";//请求错误-禁止
		
	@Autowired
	private IdentityValidator identityValidator;
	
	@Autowired
	private ResourceService resourceService;
	
	   //@Pointcut("execution(* com.winxuan.ec.admin.controller..*.*(..))")  
		@Pointcut("execution(public org.springframework.web.servlet.ModelAndView com.winxuan.ec.admin.controller..*Controller.*(..))")  
	    public void pointCut() {}  
	      
	    @Around("pointCut()")  
	    public Object authorityVerify(ProceedingJoinPoint pjp)  {

	    	List<Resource> allRresources = resourceService.queryResources();
	    	if(allRresources == null || allRresources.size() == 0){//不做权限判断
	    		
	    		return process(pjp);
	    	}
	    	String targetName = pjp.getTarget().getClass().getName();
	    	String methodName = pjp.getSignature().getName();

	    	String target = targetName + "." + methodName;
	    	
	    	if(!hasResource(target,allRresources)){//如果当前需要访问的资源不在资源列表中，不做权限判断
	    		
	    		return process(pjp);
	    	}
	    	Employee employee = (Employee) identityValidator.currentPrincipal();
	    	if(employee == null ){
	    		return forbiddenMovelAndView("您不是登录用户，无法访问资源信息");
	    	}
	    	Set<Resource> resources = employee.getResources();
	    	Iterator<Resource> resourceIt = resources.iterator();
	    	while(resourceIt.hasNext()){
	    		Resource resource = resourceIt.next();
	    		if(resource.getValue().equalsIgnoreCase(target)){
	    			
	    			return process(pjp);
	    		}
	    	}
	    	ModelAndView modelAndView = new ModelAndView(FORBIDDEN);
	    	modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "您没有操作“"+getResourceDescription(target,allRresources)+"”的权限，请联系管理员");
	    	modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_INTERNAL_ERROR);
	    	return modelAndView;
	    }

		private boolean hasResource(String target, List<Resource> allRresources) {
			
			for(Resource resource : allRresources){
				if(resource.getValue().equalsIgnoreCase(target)){
					return true;
				}
			}
			return false;
		}
		
		private String getResourceDescription(String target, List<Resource> allRresources){
			for(Resource resource : allRresources){
				if(resource.getValue().equalsIgnoreCase(target)){
					return resource.getDescription(); 
				}
			}
			return null;
		}

		private Object process(ProceedingJoinPoint pjp) {
			try {
				return pjp.proceed();
			} catch (Throwable e) {
				LOG.error(e.getMessage(),e);
				return forbiddenMovelAndView(e.getMessage());
			}
		}

		private Object forbiddenMovelAndView(String message) {
			
	    	ModelAndView modelAndView = new ModelAndView(FORBIDDEN);
	    	modelAndView.addObject(ControllerConstant.MESSAGE_KEY, message);
	    	modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_INTERNAL_ERROR);
	    	return modelAndView;
		}
}
