package com.exemple.android.miwoklanguageeducation;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioPlayer;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange){
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                if (mMediaPlayer != null){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);}
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                }
            } else if (focusChange ==  AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
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
        mAudioPlayer  = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //Create the Table which content the numbers from 1 - 10
        final ArrayList<Word> numbers_word = new ArrayList<>();
        //filling the numbers table with numbers from 1 - 10 (words)

        numbers_word.add(new Word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        numbers_word.add(new Word("Two", "Otiiko", R.drawable.number_two, R.raw.number_two));
        numbers_word.add(new Word("Three", "Tolookosu", R.drawable.number_three, R.raw.number_three));
        numbers_word.add(new Word("Four", "Oyyisa", R.drawable.number_four, R.raw.number_four));
        numbers_word.add(new Word("Five", "Massokka", R.drawable.number_five, R.raw.number_five));
        numbers_word.add(new Word("Six", "Temmokka", R.drawable.number_six, R.raw.number_six));
        numbers_word.add(new Word("Seven", "Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbers_word.add(new Word("Eight", "Kwinta", R.drawable.number_eight, R.raw.number_eight));
        numbers_word.add(new Word("Nine", "Wo'e", R.drawable.number_nine, R.raw.number_nine));
        numbers_word.add(new Word("Ten", "Na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter items = new WordAdapter(this, numbers_word, R.color.numbers_category);

        ListView list = findViewById(R.id.list);

        list.setAdapter(items);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = numbers_word.get(position);
                releaseMediaPlayer();

                int result  = mAudioPlayer.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getSoundToPlay());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            releaseMediaPlayer();
        }

    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mAudioPlayer.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
