package com.exemple.android.miwoklanguageeducation;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> wordsList, int colorResourceId) {
        super(context, 0, wordsList);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);
        TextView defaultTranslation = listItemView.findViewById(R.id.englishWord);
        assert currentWord != null;
        defaultTranslation.setText(currentWord.getDefaultTranslation());

        TextView miwokTranslation = listItemView.findViewById(R.id.miwokWord);
        miwokTranslation.setText(currentWord.getMiwokTranslation());

        ImageView picturesId = listItemView.findViewById(R.id.picture);
        if (currentWord.getHasImage()) {
            picturesId.setImageResource(currentWord.getPicturesId());
            picturesId.setVisibility(View.VISIBLE);
        } else {
            picturesId.setVisibility(View.GONE);
        }

        View containerView = listItemView.findViewById(R.id.elementsText);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        containerView.setBackgroundColor(color);

//        View playPronunciation = listItemView.findViewById(R.id.element);
//        int soundId = currentWord.getSoundToPlay();
//
//        MediaPlayer play = MediaPlayer.create(getContext(),soundId);
//
//        playPronunciation.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                play.start();
//                play.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer){
//                        play.release();
//                        private void releaseMediaPlayer() {
//                            // If the media player is not null, then it may be currently playing a sound.
//                            if (play != null) {
//                                // Regardless of the current state of the media player, release its resources
//                                // because we no longer need it.
//                                play.release();
//
//                                // Set the media player back to null. For our code, we've decided that
//                                // setting the media player to null is an easy way to tell that the media player
//                                // is not configured to play an audio file at the moment.
//                                play = null;
//                            }
//                        }
//                    }
//                });
//            }
//        });

        return listItemView;
    }
}
