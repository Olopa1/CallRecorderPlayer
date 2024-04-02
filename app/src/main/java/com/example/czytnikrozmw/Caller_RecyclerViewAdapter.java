package com.example.czytnikrozmw;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Caller_RecyclerViewAdapter extends RecyclerView.Adapter<Caller_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Caller_model> callerModels;

    public Caller_RecyclerViewAdapter(Context context, ArrayList<Caller_model> callerModels) {
        this.context = context;
        this.callerModels = callerModels;
    }


    @NonNull
    @Override
    public Caller_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.res_rec_row,parent,false);
        return new Caller_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Caller_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.callerName.setText(callerModels.get(position).name);
        String textDate = callerModels.get(position).day + "." + callerModels.get(position).month + "." + callerModels.get(position).year;
        holder.callDate.setText(textDate);
        String textTime = callerModels.get(position).hour + ":" + callerModels.get(position).minute + ":" + callerModels.get(position).second;
        holder.callTime.setText(textTime);
        if(holder.playMedia == null){
            holder.playMedia = MediaPlayer.create(context,callerModels.get(position).call_uri);
        }
        holder.mediaDuration.setMax(holder.playMedia.getDuration());

        holder.mediaDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    holder.playMedia.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.playButton.isChecked()){
                    if(holder.playMedia == null){
                        holder.playMedia = MediaPlayer.create(context,callerModels.get(holder.getAdapterPosition()).call_uri);
                    }
                    holder.playMedia.start();
                    UpdateSeekBar updateSeekBar = new UpdateSeekBar(holder.playMedia, holder.mediaDuration, holder.handler);
                    holder.handler.post(updateSeekBar);
                }
                else{
                    if(holder.playMedia != null) {
                        holder.playMedia.pause();
                    }
                }
            }
        });

    }
    public static class UpdateSeekBar implements Runnable{
        MediaPlayer media;
        SeekBar seekBar;
        Handler handler;
        public UpdateSeekBar(MediaPlayer media,SeekBar seekBar,Handler handler) {
            this.media = media;
            this.seekBar = seekBar;
            this.handler = handler;
        }

        @Override
        public void run() {
            if(media != null) {
                seekBar.setProgress(media.getCurrentPosition());
            }
            handler.postDelayed(this,100);
        }
    }
    @Override
    public int getItemCount() {
        return callerModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView callerName;
        TextView callDate;
        TextView callTime;
        ToggleButton playButton;
        MediaPlayer playMedia;
        SeekBar mediaDuration;
        Handler handler;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.callerName = itemView.findViewById(R.id.caller_name);
            this.callDate = itemView.findViewById(R.id.call_date);
            this.callTime = itemView.findViewById(R.id.call_time);
            this.playButton = itemView.findViewById(R.id.call_play);
            this.playMedia = null;
            this.mediaDuration = itemView.findViewById(R.id.player_duration);
            this.handler = new Handler();
        }
    }

}
