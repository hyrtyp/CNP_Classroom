package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.ClassRoomService;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomInfoRequest extends BaseRequest{

    @Inject
    private ClassRoomService schoolListService;


    public ClassroomInfoRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
        return schoolListService.getClassRoomInfoData1(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomInfo1";
    }
}
