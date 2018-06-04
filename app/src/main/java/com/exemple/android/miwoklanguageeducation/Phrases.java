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

public class Phrases extends AppCompatActivity {

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
        mAudioPlayer = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create the Table which content the numbers from 1 - 10
        final ArrayList<Word> numbers_word = new ArrayList<>();
        //filling the numbers table with numbers from 1 - 10 (words)

        numbers_word.add(new Word("Where Are You Going ?","minto wuksus",R.raw.phrase_where_are_you_going));
        numbers_word.add(new Word("What Is Your Name ?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        numbers_word.add(new Word("My Name Is ...","oyaaset...",R.raw.phrase_my_name_is));
        numbers_word.add(new Word("How Are You Feeling ?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        numbers_word.add(new Word("I'm Feeling Good.","kuchi achit",R.raw.phrase_im_feeling_good));
        numbers_word.add(new Word("Are You Coming ?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        numbers_word.add(new Word("Yes I'm Coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        numbers_word.add(new Word("I'm Coming.","әәnәm",R.raw.phrase_im_coming));
        numbers_word.add(new Word("Let's Go","yoowutis",R.raw.phrase_lets_go));
        numbers_word.add(new Word("Come Here","әnni",R.raw.phrase_come_here));

        WordAdapter items = new WordAdapter(this, numbers_word,R.color.phrases_category);

        ListView list = findViewById(R.id.list);

        list.setAdapter(items);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
              Word word = numbers_word.get(position);
              releaseMediaPlayer();

                int result  = mAudioPlayer.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(Phrases.this, word.getSoundToPlay());
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

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;

        }
        mAudioPlayer.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
