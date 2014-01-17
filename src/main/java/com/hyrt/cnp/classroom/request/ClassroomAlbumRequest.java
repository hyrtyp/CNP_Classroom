package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.AlbumService;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomAlbumRequest extends BaseRequest{

    @Inject
    private AlbumService schoolListService;


    public ClassroomAlbumRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
        return schoolListService.getAlbumData(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomalbum";
    }
}
