package org.fansuregrin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.entity.Menu;
import org.fansuregrin.entity.User;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.mapper.MenuMapper;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAspect {

    private final MenuMapper menuMapper;

    private static final ThreadLocal<Short> scopeHolder = new ThreadLocal<>();

    public PermissionAspect(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Around("@annotation(org.fansuregrin.annotation.MenuPermissionCheck)")
    public Object doMenuPermissionCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = pjp.getTarget();
        Class<?> clazz = obj.getClass();
        String methodName = pjp.getSignature().getName();
        Class<?>[] parameterTypes = ((MethodSignature)pjp.getSignature())
            .getParameterTypes();
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        MenuPermissionCheck annotation;
        if ((annotation = method.getAnnotation(MenuPermissionCheck.class)) != null) {
            String code = annotation.value();
            User loginUser = UserUtil.getLoginUser();
            Menu menu = menuMapper.selectByCodeAndUserId(code, loginUser.getId());
            if (menu == null) {
                throw new PermissionException("没有权限");
            } else {
                scopeHolder.set(menu.getScope());
            }
        }

        try {
            return pjp.proceed();
        } finally {
            scopeHolder.remove();
        }
    }

    public static Short getScope() {
        return scopeHolder.get();
    }

}
