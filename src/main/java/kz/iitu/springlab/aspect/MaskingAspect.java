package kz.iitu.springlab.aspect;

import kz.iitu.springlab.account.Sensitive;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;
import java.util.Arrays;

@Aspect
@Component
@Order(4)
public class MaskingAspect {

    private static final Logger log = LoggerFactory.getLogger(MaskingAspect.class);

    @Before("kz.iitu.springlab.aspect.Pointcuts.accountOperation()")
    public void maskAndLog(JoinPoint jp) {
        MethodSignature sig = (MethodSignature) jp.getSignature();
        Annotation[][] paramAnnotations = sig.getMethod().getParameterAnnotations();
        Object[] args = jp.getArgs();
        String[] shown = new String[args.length];

        for (int i = 0; i < args.length; i++) {
            boolean sensitive = false;
            for (Annotation a : paramAnnotations[i]) {
                if (a instanceof Sensitive) sensitive = true;
            }
            shown[i] = sensitive ? "***" : String.valueOf(args[i]);
        }
        log.info("[MASK] {} args={}", sig.toShortString(), Arrays.toString(shown));
    }
}