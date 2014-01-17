package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.PhotoService;

/**
 * Created by GYH on 14-1-17.
 */
public class ClassroomPhotoListRequest extends BaseRequest {

    @Inject
    private PhotoService schoolListService;


    public ClassroomPhotoListRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
        return schoolListService.getClassroomAlbumphotolistData(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomPhotoList";
    }
}
