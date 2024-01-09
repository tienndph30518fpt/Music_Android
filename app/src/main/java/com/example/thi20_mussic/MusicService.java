package com.example.thi20_mussic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer = null;

    private Handler seekBarHandler = new Handler();
    private int seekBarMax;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle bundle = intent.getExtras();
        String action = bundle.getString("action");
        Song song = (Song) bundle.getSerializable("song");
        switch (action) {
            case "UPDATE_MEDIA":
                if (mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResouce());
                mediaPlayer.start();
                updateSeekBar();
                break;


            case "PLAY":
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResouce());
                }
                seekBarMax = mediaPlayer.getDuration();
                if (mediaPlayer.getCurrentPosition() == seekBarMax) {
                    mediaPlayer.seekTo(0);
                }
                mediaPlayer.start();
                updateSeekBar();
                break;

            case "PAUSE":
                if (mediaPlayer != null) {
                    mediaPlayer.pause();

                    int currentPosition = mediaPlayer.getCurrentPosition();
                    // Lưu vị trí hiện tại vào biến curentPosition
                    // Tiếp theo, khi bạn chạy lại nhạc, sử dụng phương thức seekTo() để đặt vị trí của nhạc
                    mediaPlayer.seekTo(currentPosition);
                }
                break;
            case "SEEK_TIME":
                int percent = bundle.getInt("PERCENT_SEEK");
//                int toTime = (percent * mediaPlayer.getDuration())/100;
               mediaPlayer.seekTo(percent);
                break;
        }


        return START_NOT_STICKY;

    }


    private void updateSeekBar() {
        if (mediaPlayer != null ) {
            seekBarHandler.removeCallbacks(this::updateSeekBar);
            int currentPosition = mediaPlayer.getCurrentPosition();
            int aa = mediaPlayer.getDuration();
            // Gửi giá trị tiến trình của SeekBar thông qua Intent
            Intent intent = new Intent("SEEK_PROGRESS");
            intent.putExtra("progress", currentPosition);
            intent.putExtra("getduration", aa);
            sendBroadcast(intent);

            // Lập lịch cho việc cập nhật lại SeekBar sau 1 giây
            seekBarHandler.postDelayed(this::updateSeekBar, 100);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}







