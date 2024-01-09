package com.example.thi20_mussic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Adapter extends RecyclerView.Adapter<Adapter.MyHover> {
    ArrayList<Song> list;

    private int selectedPosition = RecyclerView.NO_POSITION;

  ;

    private boolean[] isPlayingArray;
    private ICallBack callBack;
    private Context context;

    public Adapter(ArrayList<Song> list, Context context, ICallBack callBack) {
        this.list = list;
        this.callBack = callBack;
        this.context = context;

    }


    @NonNull
    @Override
    public MyHover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        isPlayingArray = new boolean[list.size()];
        Arrays.fill(isPlayingArray, false);
        return new MyHover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHover holder, @SuppressLint("RecyclerView") int position) {


        holder.tvTenCaSi.setText("Tên Bài Hát: " + list.get(position).getName());
        holder.tvTenBaiHat.setText("Tên ca Sĩ: " + list.get(position).getSinger());
        holder.imgNhac.setImageResource(R.drawable.ic_music);
        if (selectedPosition==position){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorXanh));
        }else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener((v) -> {
            selectedPosition = position;
            notifyDataSetChanged();
            callBack.itemClick(position);
        });

    }

    interface ICallBack {

        void itemClick(int index);

    }


    @Override
    public int getItemCount() {

        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class MyHover extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTenCaSi, tvTenBaiHat;
        ImageView imgNhac;
        public MyHover(@NonNull View itemView) {
            super(itemView);

            tvTenCaSi = itemView.findViewById(R.id.tenCaSi);
            tvTenBaiHat = itemView.findViewById(R.id.tenBaihat);
            imgNhac = itemView.findViewById(R.id.imgNhac);
            cardView  =itemView.findViewById(R.id.idManin);
        }

    }

}




//package tienndph30518.thi_mussic;
//
//import android.annotation.SuppressLint;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageButton;
//import android.widget.ImageSwitcher;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Adapter extends RecyclerView.Adapter<Adapter.MyHover> {
//    ArrayList<Song> list;
//    Context context;
//    private MediaPlayer mediaPlayer;
//
//    private int selectedPosition = RecyclerView.NO_POSITION;
//
//
//    public Adapter(ArrayList<Song> list, Context context) {
//        this.list = list;
//        this.context = context;
//
//    }
//
//
//
//    @NonNull
//    @Override
//    public MyHover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
//        return new MyHover(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyHover holder,  int position) {
//        holder.tvTenCaSi.setText("Tên Bài Hát: " + list.get(position).getName());
//        holder.tvTenBaiHat.setText("Tên ca Sĩ: " + list.get(position).getSinger());
//
//        if (selectedPosition == position) {
//            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.black));
//        } else {
//            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
//        }
//
//
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (list != null) {
//            return list.size();
//        }
//        return 0;
//    }
//
//    class MyHover extends RecyclerView.ViewHolder {
//
//
//
//        TextView tvTenCaSi, tvTenBaiHat;
//
//        public MyHover(@NonNull View itemView) {
//            super(itemView);
//            tvTenCaSi = itemView.findViewById(R.id.tenCaSi);
//            tvTenBaiHat = itemView.findViewById(R.id.tenBaihat);
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        if (mediaPlayer != null) {
//                            mediaPlayer.stop();
//                            mediaPlayer.release();
//                            mediaPlayer = null;
//                        }
//                        Song song = list.get(position);
//                        mediaPlayer = MediaPlayer.create(context, song.getResouce());
//                        mediaPlayer.start();
//
//                        // Cập nhật màu sắc của item được click và item trước đó
//                        notifyItemChanged(selectedPosition);
//                        selectedPosition = position;
//                        notifyItemChanged(selectedPosition);
//                    }
//                }
//            });
//        }
//    }
//}
//

