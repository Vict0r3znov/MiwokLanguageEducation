package com.exemple.android.miwoklanguageeducation;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioPlayer;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange  == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                if(mMediaPlayer != null){mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);}
            }else if (focusChange  ==  AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            } else if (focusChange ==  AudioManager.AUDIOFOCUS_LOSS){
                if (mMediaPlayer != null){
                    mMediaPlayer.stop();
                }
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);
        mAudioPlayer = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create the Table which content the numbers from 1 - 10
        final ArrayList<Word> numbers_word = new ArrayList<>();
        //filling the numbers table with numbers from 1 - 10 (words)

        numbers_word.add(new Word("Red", "weṭeṭṭi", R.drawable.color_red,R.raw.color_red));
        numbers_word.add(new Word("Green", "chokokki", R.drawable.color_green,R.raw.color_green));
        numbers_word.add(new Word("Brown", "ṭakaakki", R.drawable.color_brown,R.raw.color_brown));
        numbers_word.add(new Word("Gray", "ṭopoppi", R.drawable.color_gray,R.raw.color_gray));
        numbers_word.add(new Word("Black", "kululli", R.drawable.color_black,R.raw.color_black));
        numbers_word.add(new Word("White", "kelelli", R.drawable.color_white,R.raw.color_white));
        numbers_word.add(new Word("Dusty Yellow", "ṭopiisә", R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        numbers_word.add(new Word("Mustard Yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter items = new WordAdapter(this, numbers_word, R.color.color_category);

        ListView list = findViewById(R.id.list);

        list.setAdapter(items);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = numbers_word.get(position);
                releaseMediaPlayer();

                int result = mAudioPlayer.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getSoundToPlay());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }}
        });
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mAudioPlayer.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            releaseMediaPlayer();
        }

    }
}
