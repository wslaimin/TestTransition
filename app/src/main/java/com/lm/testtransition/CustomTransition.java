package com.lm.testtransition;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 10528 on 2015/11/23.
 */
@TargetApi(19)
public class CustomTransition extends Transition {
    //key命名方式package_name:transition_name:property_name
    private static final String VALUE_BACKGROUND="com.lm.testtransition:customtransition:background";
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        System.out.println("startValues:"+startValues+" endValues:"+endValues);
        if(null==startValues||null==endValues){
            return null;
        }

        final View view=endValues.view;

        Drawable startBackground=(Drawable)startValues.values.get(VALUE_BACKGROUND);
        Drawable endBackground=(Drawable)endValues.values.get(VALUE_BACKGROUND);
        if(startBackground instanceof ColorDrawable&&endBackground instanceof ColorDrawable){
            ColorDrawable startColor=(ColorDrawable)startBackground;
            ColorDrawable endColor=(ColorDrawable)endBackground;

            ValueAnimator animator=ValueAnimator.ofObject(new ArgbEvaluator(),startColor.getColor(),endColor.getColor());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object value=animation.getAnimatedValue();
                    if(null!=value){
                        view.setBackgroundColor((Integer)value);
                    }
                }
            });
            return animator;
        }
        return null;
    }

    public void captureValues(TransitionValues transitionValues){
        transitionValues.values.put(VALUE_BACKGROUND,transitionValues.view.getBackground());
    }
}
