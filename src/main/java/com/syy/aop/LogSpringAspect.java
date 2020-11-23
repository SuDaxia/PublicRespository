package com.syy.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

/**
 * 切面类
 */
@Aspect //告诉容器标记此类为切面类
public class LogSpringAspect {

    //抽取公共的切入点表达式
    // 1 本类引用 @Pintcut()注解本类想切入的引用方法，然后 @Before @After @AfterReturning @AfterThrowing 括号放该引用方法名参数即可 pointCutExample()
    // 2 其它切面引入，要写全类名
    @Pointcut("execution(public int com.syy.aop.MathCalculator.*(..))")
    public void pointCut(){ } //System.out.println("just to println pointCutExample ");//return 8888;

    //@Before("public int com.syy.aop.MathCalculator.div(int i ,int j)")
    //@Before("public int com.syy.aop.MathCalculator.*(..)") //任意方法，两个参数
    @Before("com.syy.aop.LogSpringAspect.pointCut().pointCut()") //外部方法就算是@Pointcut方式也要全类名，除非在当前类中
    public void logBefore(JoinPoint joinPoint){
        System.out.println("spring aspect before "+joinPoint.getSignature().getName()+" 运行目标参数："+ Arrays.asList(joinPoint.getArgs()));

    }

    //@After("public int com.syy.aop.MathCalculator.*(..)") //任意方法，两个参数，无论切入方法是否正常返回，还是异常返回都会执行的方法
    @After("pointCut()")
    public void logAfter(JoinPoint joinPoint){
        System.out.println("spring aspect after "+joinPoint.getSignature().getName()+" 运行目标参数："+ Arrays.asList(joinPoint.getArgs()));
    }

    //@AfterReturning(value="public int com.syy.aop.MathCalculator.*(..)",returning="result") //任意方法，两个参数
    @AfterReturning(value="pointCut()",returning = "result")
    public void logBReturn(JoinPoint joinPoint,Object result){//JoinPoint必须放在其他参数前面，不然获取不到
        System.out.println("spring aspect return "+joinPoint.getSignature().getName()+" 运行目标参数已经获取不到了， 运行结果："+result);
    }

    //@AfterThrowing("public int com.syy.aop.MathCalculator.*(..)") //任意方法，两个参数
    @AfterThrowing(value="pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        System.out.println("spring aspect Exception "+joinPoint.getSignature().getName()+" 运行目标参数："+ Arrays.asList(joinPoint.getArgs())+" 运行异常信息："+exception.getMessage());
    }

    @Around("pointCut()") // @Aroud 环绕通知，使用joinPoint.procced()推动方法执行
    public Object logJoin(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args= joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法数组类型,然后课处理参数修改
        Class[] paramTypeArray=methodSignature.getParameterTypes();
        System.out.println("spring aspect logJoin "+joinPoint.getSignature().getName()+" 原先运行目标参数："+ Arrays.asList(joinPoint.getArgs()));
        //修改参数
        for(int i = 0;i< args.length;i++){
            args[i]=(((Integer)args[i])*3);
        }
        System.out.println("spring aspect logJoin "+joinPoint.getSignature().getName()+" 修改后运行目标参数："+ Arrays.asList(joinPoint.getArgs()));
        //只有ProceedingJoinPoint可以，joinPoint.proceed无参的，不不能让修改参数生效，必须joinPoint.proceed(Object[] args)才能让修改的参数生效
        Object result = joinPoint.proceed(args);
        System.out.println("spring aspect logJoin "+joinPoint.getSignature().getName()+" 修改后运行目标参数 执行完毕："+ Arrays.asList(joinPoint.getArgs()));
        //而别必须返回result，不然就是变了
        return result;
    }
}
