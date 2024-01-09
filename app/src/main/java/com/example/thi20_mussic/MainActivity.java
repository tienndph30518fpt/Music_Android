package com.example.thi20_mussic;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Adapter.ICallBack {

    public ImageButton btnBack, btnPlayPause, btnNext;
    private String ACTION = null;
    private boolean isPlay = false;
    private ArrayList<Song> songArrayList;
    private int curentMusic = 0;
    private int seebarDuration;

    private SeekBarBroadcastReceiver seekBarReceiver;
    private IntentFilter seekBarIntentFilter;

    private ImageView circleImageView;
    public SeekBar seekBar;
    private TextView tvtimefirst, tvtimecover;

    private ObjectAnimator circleAnimator;
    private float currentRotation = 0f;
    private RecyclerView recyclerView;
    Boolean isUserSeeking = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        circleImageView = findViewById(R.id.imgbgr);
        recyclerView = findViewById(R.id.recycview);


        btnPlayPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        seekBar = findViewById(R.id.idseebar);
        tvtimefirst = findViewById(R.id.tvtimefirst);
        tvtimecover = findViewById(R.id.tvtimeover);

        seekBarReceiver = new SeekBarBroadcastReceiver();
        seekBarIntentFilter = new IntentFilter("SEEK_PROGRESS");



        circleAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        circleAnimator.setDuration(20000);
        circleAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        circleAnimator.setInterpolator(new LinearInterpolator());
        circleAnimator.start();


        songArrayList = new ArrayList<>();
        Song song = new Song("Set fire to the rain", "Singer1",
                R.raw.set_fire_to_the_rain);
        songArrayList.add(song);
        Song song1 = new Song("Lonly dance", "Singer2",
                R.raw.lonly_dance);
        songArrayList.add(song1);
        Song song2 = new Song("waiting for you", "Singer3",
                R.raw.waiting_for_you);
        songArrayList.add(song2);
        loadData();


    }

    @Override
    public void itemClick(int index) {

        btnPlayPause.setImageResource(R.drawable.ic_pause);
        ACTION = "UPDATE_MEDIA";
        Intent mIntent = new Intent(MainActivity.this, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", songArrayList.get(index));
        bundle.putString("action", ACTION);
        mIntent.putExtras(bundle);
        startService(mIntent);

        isPlay = true;

        circleAnimator.setFloatValues(0f, 360f);
        circleAnimator.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnBack) {
            curentMusic--;
            if (curentMusic < 0)
                curentMusic = songArrayList.size() - 1;
            btnPlayPause.setImageResource(R.drawable.ic_pause);
            ACTION = "UPDATE_MEDIA";
            Intent mIntent = new Intent(MainActivity.this, MusicService.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", songArrayList.get(curentMusic));
            bundle.putString("action", ACTION);
            mIntent.putExtras(bundle);
            startService(mIntent);

            isPlay = true;

            circleAnimator.setFloatValues(0f, 360f);
            circleAnimator.start();

        } else if (id == R.id.btnPlayPause) {
            if (isPlay) {
                circleAnimator.pause();
                currentRotation = circleImageView.getRotation();

                btnPlayPause.setImageResource(R.drawable.ic_play);
                ACTION = "PAUSE";
                Intent mIntent = new Intent(MainActivity.this, MusicService.class);
                Bundle bundle = new Bundle();
                bundle.putString("action", ACTION);
                mIntent.putExtras(bundle);
                startService(mIntent);
                isPlay = false;

            } else {
                circleAnimator.setFloatValues(currentRotation, currentRotation + 360f);
                circleAnimator.start();
                btnPlayPause.setImageResource(R.drawable.ic_pause);
                ACTION = "PLAY";
                Intent mIntent = new Intent(MainActivity.this, MusicService.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("song", songArrayList.get(curentMusic));
                bundle.putString("action", ACTION);
                mIntent.putExtras(bundle);
                startService(mIntent);

                isPlay = true;

            }


        } else if (id == R.id.btnNext) {
            curentMusic++;
            if (curentMusic == songArrayList.size())
                curentMusic = 0;
            btnPlayPause.setImageResource(R.drawable.ic_pause);
            ACTION = "UPDATE_MEDIA";
            Intent mIntent = new Intent(MainActivity.this, MusicService.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", songArrayList.get(curentMusic));
            bundle.putString("action", ACTION);
            mIntent.putExtras(bundle);
            startService(mIntent);
            isPlay = true;

            circleAnimator.setFloatValues(0f, 360f);
            circleAnimator.start();

        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì trong phương thức này
                isUserSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progresss = seekBar.getProgress();
                int duration = seebarDuration;
                int newPos = (duration * progresss) / 100;

                ACTION = "SEEK_TIME";
                Intent mIntent = new Intent(MainActivity.this, MusicService.class);
                Bundle bundle = new Bundle();
                bundle.putString("action", ACTION);
                bundle.putInt("PERCENT_SEEK",newPos );
                mIntent.putExtras(bundle);
                startService(mIntent);

                isUserSeeking = false;

            }
        });

    }




    private String formatTime(int millis) {//nhận đầu vào là một giá trị thời gian
        // tính bằng mili giây và trả về một chuỗi định dạng
        int seconds = (millis / 1000) % 60;
        int minutes = (millis / (1000 * 60)) % 60;// được chuyển đổi thành phút
        return String.format("%02d:%02d", minutes, seconds);
    }

    private class SeekBarBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // lấy giá trị nhận được từ bên servie
            if (intent.getAction().equals("SEEK_PROGRESS") && !isUserSeeking) {
                int progress = intent.getIntExtra("progress", 0);
                int getdura = intent.getIntExtra("getduration", 0);
                seekBar.setProgress(progress * 100 / getdura);
                seebarDuration = getdura;

                //sử dụng chúng để cập nhật trạng thái của một SeekBar (seekBar) và hai TextView (tvtimefirst và tvtimecover).
                // progressbar và getduration
                String timeFirst = formatTime(progress);// chuyển đổi giây thành chuỗi thời gian kết quả nhận được chuỗi thời gian hiện tại
                String timeCover = formatTime(getdura);// tính toán thời gian hiện tại xong chuyển thành chuỗi

                tvtimefirst.setText(timeFirst);// sau đó set lên textView
                tvtimecover.setText(timeCover);

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(seekBarReceiver, seekBarIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(seekBarReceiver);
    }

    public void loadData() {
        Adapter adapter = new Adapter(songArrayList, MainActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.list = songArrayList;
        recyclerView.setAdapter(adapter);
    }





}