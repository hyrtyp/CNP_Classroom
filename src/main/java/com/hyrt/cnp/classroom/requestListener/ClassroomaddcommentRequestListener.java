package com.hyrt.cnp.classroom.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.ui.ClassroomphotoinfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomaddcommentRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public ClassroomaddcommentRequestListener(Activity context) {
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
            ClassroomphotoinfoActivity activity = (ClassroomphotoinfoActivity)context.get();
            Comment.Model3 result= (Comment.Model3)data;
            if(result.getCode().equals("200")){
                activity.ShowSuccess();
            }else{
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
        }

    }

    @Override
    public ClassroomaddcommentRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
