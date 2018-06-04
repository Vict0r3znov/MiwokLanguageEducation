package com.exemple.android.miwoklanguageeducation;

public class Word {
    /**
     * Defining Variables
     * for the Default Translation and the Miwok Translation
     */
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mPicturesId = HAS_NO_IMAGE;
    private int mSoundToPlay;
    private static final int HAS_NO_IMAGE = -1;

    /**
     * Constructor with 4 inputs
     * */

    public Word(String DefaultTranslation, String MiwokTranslation,int picturesId,int soundToPlay){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mPicturesId = picturesId;
        mSoundToPlay = soundToPlay;
    }

    /**Constructor with 3 inputs*/

    public Word(String DefaultTranslation, String MiwokTranslation,int soundToPlay){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mSoundToPlay = soundToPlay;
    }

    /**
     * Create The Get (Default/Miwok) Translation Methods
     */

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getPicturesId(){
        return mPicturesId;
    }

    public boolean getHasImage(){ return mPicturesId != HAS_NO_IMAGE;}

    public int getSoundToPlay(){ return mSoundToPlay;}
}
