package com.best.subtasks.C_PVE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;

import com.best.R;
import com.best.loadProfile.ProfileInfoActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PVE2Test extends AppCompatActivity {

    private Timer timer;
    private SoundPool sound;
    private Animator mCurrentAnimator;
    private View mDisplayView;
    private ImageButton redo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pve_test);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        long pveTarget = (Long.parseLong(sh.getString("pvePref2", "53")) * 1000) + 2000;

        mDisplayView = findViewById(R.id.pveLay);
        redo = findViewById(R.id.redoPVE);
        ImageButton cancel = findViewById(R.id.cancelPVE);

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator == null) {
                    redo();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sound = new SoundPool.Builder().build();
        final int toneId = sound.load(this, R.raw.tone2, 1);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sound.play(toneId, 1, 1, 1, 0, 1);
            }
        }, 2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sound.play(toneId, 1, 1, 1, 0, 1);
                pveTestDone();
            }
        }, pveTarget);
    }

    private void redo() {
        reveal(mDisplayView, R.color.colorPrimary, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                timer.cancel();
                sound.release();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                redo2();
            }
        });

        //this.recreate();
    }

    private void redo2() {
        this.recreate();
    }

    private void reveal(View sourceView, int colorRes, Animator.AnimatorListener listener) {
        final ViewGroupOverlay groupOverlay = (ViewGroupOverlay) getWindow().getDecorView().getOverlay();

        final Rect displayRect = new Rect();
        sourceView.getGlobalVisibleRect(displayRect);

        // Make reveal cover the display and status bar.
        final View revealView = new View(this);
        revealView.setBottom(displayRect.bottom);
        revealView.setLeft(displayRect.left);
        revealView.setRight(displayRect.right);
        revealView.setBackgroundColor(getResources().getColor(colorRes));
        groupOverlay.add(revealView);

        int revealCenterX = redo.getRight();
        int revealCenterY = redo.getBottom();

        float revealRadius = (float) Math.hypot(sourceView.getWidth(), sourceView.getHeight());

        final Animator revealAnimator = ViewAnimationUtils.createCircularReveal(revealView, revealCenterX, revealCenterY, 0.0f, revealRadius);
        revealAnimator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

        final Animator alphaAnimator = ObjectAnimator.ofFloat(revealView, View.ALPHA, 0.0f);
        alphaAnimator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        alphaAnimator.addListener(listener);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(revealAnimator).before(alphaAnimator);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                groupOverlay.remove(revealView);
                mCurrentAnimator = null;
            }
        });

        mCurrentAnimator = animatorSet;
        animatorSet.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // If there's an animation in progress, end it immediately to ensure the state is
        // up-to-date before it is serialized.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.end();
        }
        super.onSaveInstanceState(outState);
    }

    // cancel running timers if activity is destroyed
    @Override
    protected void onDestroy() {
        timer.cancel();
        sound.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop BEST");
        builder.setMessage("Are you sure you want to stop administering this BEST?\n\nResults will not be saved!");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopBEST();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void stopBEST() {
        Bundle bundle = getIntent().getExtras();
        String id = "";

        if (bundle != null) {
            id = (String) bundle.get("id");
        }

        Intent intent = new Intent(this, ProfileInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("profileId", id);
        startActivity(intent);
    }

    private void pveTestDone() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";
        String pve1Result = "";
        String pve2Result = "";
        String ppe1Result = "";
        String ppe2Result = "";
        String rbeResult = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
            pre2Result = (String) bundle.get("pre2Result");
            pve1Result = (String) bundle.get("pve1Result");
            pve2Result = (String) bundle.get("pve2Result");
            ppe1Result = (String) bundle.get("ppe1Result");
            ppe2Result = (String) bundle.get("ppe2Result");
            rbeResult = (String) bundle.get("rbeResult");
        }

        Intent intent = new Intent(this, PVE2Done.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        intent.putExtra("pve1Result", pve1Result);
        intent.putExtra("pve2Result", pve2Result);
        intent.putExtra("ppe1Result", ppe1Result);
        intent.putExtra("ppe2Result", ppe2Result);
        intent.putExtra("rbeResult", rbeResult);
        startActivity(intent);
    }
}
