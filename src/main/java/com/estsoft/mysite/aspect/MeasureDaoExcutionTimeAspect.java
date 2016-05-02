package com.estsoft.mysite.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureDaoExcutionTimeAspect {
	// before+after
	@Around("execution(* *..mysite.dao.*.*(..) )||execution(* *..mysite.service.*.*(..) )||execution(* *..mysite.controller.*.*(..) )")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {

		//before
		System.out.println("call [around.before Advice]");
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		

		//method가 실행되는 객체의 class이름 : pjp.getTarget().getClass()
		//실행될 method 이름
		String taskName = pjp.getTarget().getClass()+"."+pjp.getSignature().getName();
		
		//App.java에서 service call을 하게 된 code부분 실행함
		Object vo = (Object) pjp.proceed();
		
//		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList");


		stopWatch.stop();
		System.out.println("[Execution Time][dao"+taskName+"]:" + stopWatch.getTotalTimeMillis() + "millis");
		
		// after
		System.out.println("call [around.after Advice]");
	
		return vo;
	}
}
