package com.dhc.businesscomponent.anim;

import android.os.Parcel;
import android.os.Parcelable;


import com.dhc.businesscomponent.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * Created by YoKeyword on 16/2/5.
 */
public class BelowAnimator extends FragmentAnimator implements Parcelable{

    public BelowAnimator() {
        enter = R.anim.login_in_below;
        exit = R.anim.login_out_below;
        popEnter = R.anim.login_in_below;
        popExit = R.anim.login_out_below;
    }

    protected BelowAnimator(Parcel in) {
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

    public static final Creator<BelowAnimator> CREATOR = new Creator<BelowAnimator>() {
        @Override
        public BelowAnimator createFromParcel(Parcel in) {
            return new BelowAnimator(in);
        }

        @Override
        public BelowAnimator[] newArray(int size) {
            return new BelowAnimator[size];
        }
    };
}
