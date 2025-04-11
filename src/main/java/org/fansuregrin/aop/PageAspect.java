package org.fansuregrin.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.fansuregrin.dto.PageQuery;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageAspect {

    @Before("@annotation(org.fansuregrin.annotation.PageCheck)")
    public void doPageCheck(JoinPoint joinPoint) {
        PageQuery query = (PageQuery) joinPoint.getArgs()[0];
        Integer page, pageSize;
        if ((page = query.getPage()) == null || page <= 0) {
            query.setPage(page = 1);
        }
        if ((pageSize = query.getPageSize()) == null || pageSize <= 0) {
            query.setPageSize(pageSize = 5);
        }
        query.setStart((page - 1) * pageSize);
    }

}
