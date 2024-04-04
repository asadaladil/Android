package com.example.playall;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private TextView Title, running, fixed;
    private ImageView image;
    private SeekBar seek;
    private Button prv, stop, play, next;
    private ArrayList<MediaPlayer>mp=new ArrayList<>();
    private int time=0,i=0,x;
    private ArrayList<String>music=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Title = (TextView) findViewById(R.id.name);
        running = (TextView) findViewById(R.id.running);
        fixed = (TextView) findViewById(R.id.fixed);
        prv = (Button) findViewById(R.id.previous);
        stop = (Button) findViewById(R.id.stop);
        play = (Button) findViewById(R.id.play);
        next = (Button) findViewById(R.id.next);
        seek=(SeekBar)findViewById(R.id.seekbar);
        mp.add(MediaPlayer.create(this,R.raw.ki_tomar_naam_minar));
        mp.add(MediaPlayer.create(this,R.raw.ei_bristi_veja_raate_artcell));
        music.add("Ki Tomar Name");
        music.add("Ei Bristi Veja Rate");
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        prv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play&&!mp.get(i).isPlaying()) {
            if (mp != null) {
                setMusic();
                mp.get(i).start();
                mp.get(i).seekTo(seek.getProgress()*1000);
                play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause,0,0,0);
                final Handler handler = new Handler();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time= mp.get(i).getCurrentPosition()/1000;
                        seek.setProgress(time);
                        handler.postDelayed(this, 1000);
                    }
                });


            }
        } else if (v.getId() == R.id.play&&mp.get(i).isPlaying()) {
            if (mp != null) {
                mp.get(i).pause();
                play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play,0,0,0);
            }
        }
         else if (v.getId() == R.id.stop) {
            if (mp != null) {
                mp.get(i).seekTo(0);
                seek.setProgress(mp.get(i).getCurrentPosition()/1000);
                updateTime(0);
                play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play,0,0,0);
                mp.get(i).pause();
            }
        } else if (v.getId()==R.id.next) {
             if (i!=mp.size()-1) {
                 mp.get(i).seekTo(0);
                 seek.setProgress(mp.get(i).getCurrentPosition()/1000);
                 updateTime(0);
                 play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause,0,0,0);
                 mp.get(i).pause();
                 i++;
                 updateTime(0);
                 seek.setProgress(0);
                 setMusic();
                 mp.get(i).start();
             }

        }
        else if (v.getId()==R.id.previous) {
            if (i!=0) {
                mp.get(i).seekTo(0);
                seek.setProgress(mp.get(i).getCurrentPosition()/1000);
                updateTime(0);
                play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause,0,0,0);
                mp.get(i).pause();
                i--;
                updateTime(0);
                seek.setProgress(0);
                setMusic();
                mp.get(i).start();
            }

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp.get(i)!=null&&mp.get(i).isPlaying())
        {
            mp.get(i).stop();
            mp.get(i).release();
            mp=null;
            time=0;
        }
    }

    public void updateTime(int Time)
    {
        if (Time%60<10)
        {
            running.setText('0'+String.valueOf(Time/60)+":0"+String.valueOf(Time%60));
        }
        else
        {
            running.setText('0'+String.valueOf(Time/60)+':'+String.valueOf(Time%60));
        }
    }
    public void setMusic()
    {
        Title.setText("Music Name: "+music.get(i));
        x = mp.get(i).getDuration()/1000;
        fixed.setText('0'+String.valueOf(x / 60) + ':' + String.valueOf(x % 60));
        seek.setMax(x);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int get=seekBar.getProgress();
                updateTime(get);
                mp.get(i).seekTo(get*1000);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int get=seekBar.getProgress();
                updateTime(get);
                mp.get(i).seekTo(get*1000);
            }
        });
        mp.get(i).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Code to execute after the music has finished playing
                play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play,0,0,0);
                mp.seekTo(0);
                updateTime(0);
            }
        });
    }
}
