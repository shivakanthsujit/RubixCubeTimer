package io.github.shivakanthsujit.rubixcubetimer;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends Activity {

    private boolean isInp = false;
    private int clickNumber =0;
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    MediaPlayer mp;
    TextView r;
    Stopwatch timer = new Stopwatch();
    ConstraintLayout layout;
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    timer.start(); //start timer
                    mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    if(timer.getElapsedTimeSecs() < 60)
                        r.setText("" + String.format("%02d", timer.getElapsedTimeSecs()%60) + ":" + String.format("%03d", timer.getElapsedTime() % 1000));

                    else
                        r.setText("" + String.format("%02d", timer.getElapsedTimeSecs() / 60) +":"+ String.format("%02d", timer.getElapsedTimeSecs()%60) + ":" + String.format("%03d", timer.getElapsedTime() % 1000));
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,10);
                    break;
                case MSG_STOP_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER);
                    timer.stop();
                    if(timer.getElapsedTimeSecs() < 60)
                        r.setText("" + String.format("%02d", timer.getElapsedTimeSecs()%60) + ":" + String.format("%03d", timer.getElapsedTime() % 1000));

                    else
                        r.setText("" + String.format("%02d", timer.getElapsedTimeSecs() / 60) +":"+ String.format("%02d", timer.getElapsedTimeSecs()%60) + ":" + String.format("%03d", timer.getElapsedTime() % 1000));

                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = (TextView)findViewById(R.id.rtimer);
        r.setText("15:000");
        layout = (ConstraintLayout)findViewById(R.id.lay);
        layout.setBackgroundColor(Color.parseColor("#81D4FA"));
        mp = MediaPlayer.create(this, R.raw.sound1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventaction = event.getAction();
        if(eventaction == MotionEvent.ACTION_DOWN )
        {
            if ((clickNumber % 3) == 0 )
            {
                    if(!isInp)
                    {
                        isInp = true;

                    clickNumber++;
                    layout.setBackgroundColor(Color.parseColor("#81C784"));
                    new CountDownTimer(5000, 10) {

                        public void onTick(long millisUntilFinished) {
                            r.setText("" + String.format("%02d", millisUntilFinished / 1000) + ":" + String.format("%03d", millisUntilFinished % 1000));


                           if(millisUntilFinished == 3000)
                            {
                               mp.start();
                            }
                        }

                        public void onFinish() {
                            r.setText("done!");
                            startWatch();
                            isInp = false;
                        }
                    }.start();
                    }
            }
            else if ((clickNumber % 3) == 1 && !isInp)
            {
                    mHandler.sendEmptyMessage(MSG_STOP_TIMER);
                    clickNumber++;
                    isInp = false;
            }
            else if((clickNumber % 3) == 2 && !isInp )
            {
                r.setText("15:000");
                layout.setBackgroundColor(Color.parseColor("#81D4FA"));
                clickNumber++;
                isInp = false;
            }
            }

        return true;
    }

    private void startWatch()
    {
        r.setText("0:000");
        mHandler.sendEmptyMessage(MSG_START_TIMER);
        layout.setBackgroundColor(Color.parseColor("#ef9a9a"));
    }
}


