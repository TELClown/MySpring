package com.chr.spring.framework.aop.aspectj;

import com.chr.spring.framework.aop.intf.PointCut;
import com.chr.spring.framework.aop.intf.PointcutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    private Advice advice;

    private AspectJExpressionPointCut aspectJExpressionPointCut;
    private String expression;


    public void setAdvice(Advice advice) {
        this.advice = advice;
    }


    public void setExpression(String expression) {
        this.expression = expression;
        aspectJExpressionPointCut = new AspectJExpressionPointCut(expression);
    }

    @Override
    public PointCut getPointcut() {
        return aspectJExpressionPointCut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
