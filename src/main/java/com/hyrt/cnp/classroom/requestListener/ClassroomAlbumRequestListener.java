package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Album;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.ui.ClassroomAlbumActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomAlbumRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public ClassroomAlbumRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            ClassroomAlbumActivity activity = (ClassroomAlbumActivity)context.get();
            Album.Model result= (Album.Model)data;
            activity.updateUI(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public ClassroomAlbumRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
