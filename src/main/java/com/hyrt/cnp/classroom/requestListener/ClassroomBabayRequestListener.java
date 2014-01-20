package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.ClassRoomBabay;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.ui.ClassroomBabayActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomBabayRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public ClassroomBabayRequestListener(Activity context) {
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
            ClassroomBabayActivity activity = (ClassroomBabayActivity)context.get();
            ClassRoomBabay.Model result= (ClassRoomBabay.Model)data;
            activity.updateUI(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public ClassroomBabayRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
