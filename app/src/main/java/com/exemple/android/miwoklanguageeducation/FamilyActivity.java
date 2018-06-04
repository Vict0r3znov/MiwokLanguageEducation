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

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioPlayer;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                if(mMediaPlayer != null){mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);}
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (mMediaPlayer != null) {
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

        numbers_word.add(new Word("Father", "әpә", R.drawable.family_father, R.raw.family_father));
        numbers_word.add(new Word("Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        numbers_word.add(new Word("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        numbers_word.add(new Word("Daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        numbers_word.add(new Word("Older Brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        numbers_word.add(new Word("Younger Brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        numbers_word.add(new Word("Older Sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        numbers_word.add(new Word("Younger Sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        numbers_word.add(new Word("GrandMother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        numbers_word.add(new Word("GrandFather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter items = new WordAdapter(this, numbers_word, R.color.family_members_category);

        ListView list = findViewById(R.id.list);

        list.setAdapter(items);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = numbers_word.get(position);
                releaseMediaPlayer();

                int result = mAudioPlayer.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getSoundToPlay());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
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
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            releaseMediaPlayer();
        }

    }
}
