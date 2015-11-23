package com.lm.testtransition;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(19)
public class MainActivity extends Activity implements Transition.TransitionListener{
    private Scene mBScene,mCScene;
    private ViewGroup mSceneRoot;
    private Transition mFadeTransition,mTransitionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>19) {
            tansition();
        }
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.scenes_from_res:
                if (Build.VERSION.SDK_INT > 19) {
                    go(mBScene,mFadeTransition);
                }
                break;
            case R.id.scenes_from_code:
                if (Build.VERSION.SDK_INT > 19) {
                    go(mCScene,mFadeTransition);
                }
                break;
            case R.id.add_target:
                if(Build.VERSION.SDK_INT>19) {
                    //只使view樹的一部分Transition有效
                    mFadeTransition.addTarget(R.id.c);
                    //用Target(View view)這個函數button沒有動畫效果
                    //mFadeTransition.addTarget(button.getId());
                    go(mBScene, mFadeTransition);
                }
                break;
            case R.id.mutiple_transition:
                if(Build.VERSION.SDK_INT>19) {
                    go(mBScene, mTransitionSet);
                }
                break;
            case R.id.without_scene:
                if(Build.VERSION.SDK_INT>19){
                    //不適用Scene
                    TextView txtView=new TextView(this);
                    txtView.setText("This is a new TextView");
                    txtView.setId(View.generateViewId());
                    Fade fade=new Fade(Fade.IN);
                    TransitionManager.beginDelayedTransition(mSceneRoot,fade);
                    mSceneRoot.addView(txtView);
                }
                break;
            case R.id.transition_listener:
                if(Build.VERSION.SDK_INT>19) {
                    //設置transition監聽器
                    mFadeTransition.addListener(this);
                    go(mBScene, mFadeTransition);
                }
                break;
            case R.id.custom_transition:
                if(Build.VERSION.SDK_INT>19){
                    Transition customTransition=new CustomTransition();
                    go(mBScene,customTransition);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    public void tansition(){

        mSceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        mBScene = Scene.getSceneForLayout(mSceneRoot, R.layout.b_scene, this);
        //在代碼里創建scene
        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout ll=(LinearLayout)layoutInflater.inflate(R.layout.c_scene, null);
        Button button=new Button(this);
        button.setId(View.generateViewId());
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "CScene", Toast.LENGTH_LONG).show();
            }
        });
        ll.addView(button);
		//!注意，Scene里view的id一樣，代表start和end
        mCScene=new Scene(mSceneRoot,ll);

        mFadeTransition= TransitionInflater.from(this).inflateTransition(R.transition.fade_transition);

        mTransitionSet=TransitionInflater.from(this).inflateTransition(R.transition.transitions);

    }

    @TargetApi(19)
    public void go(Scene scene,Transition transition){
        TransitionManager.go(scene,transition);
    }

    @Override
    public void onTransitionStart(Transition transition) {
        Toast.makeText(this,"Transition start",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        Toast.makeText(this,"Transition end",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        Toast.makeText(this,"Transition cancel",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransitionPause(Transition transition) {
        Toast.makeText(this,"Transition pause",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransitionResume(Transition transition) {
        Toast.makeText(this,"Transition resume",Toast.LENGTH_LONG).show();
    }
}
