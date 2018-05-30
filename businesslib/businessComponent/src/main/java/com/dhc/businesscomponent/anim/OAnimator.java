package com.dhc.businesscomponent.anim;

import android.os.Parcel;
import android.os.Parcelable;


import com.dhc.library.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * Created by YoKeyword on 16/2/5.
 */
public class OAnimator extends FragmentAnimator implements Parcelable{

    public OAnimator() {
        enter = R.anim.h_fragment_enter;
        exit = R.anim.h_fragment_exit;
        popEnter = R.anim.v_fragment_pop_enter;
        popExit = R.anim.v_fragment_pop_exit;
    }

    protected OAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OAnimator> CREATOR = new Creator<OAnimator>() {
        @Override
        public OAnimator createFromParcel(Parcel in) {
            return new OAnimator(in);
        }

        @Override
        public OAnimator[] newArray(int size) {
            return new OAnimator[size];
        }
    };
}
