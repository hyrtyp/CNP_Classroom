package com.hyrt.cnp.classroom.ui;

import android.os.Bundle;
import android.widget.GridView;

import com.hyrt.cnp.account.model.ClassRoomBabay;
import com.hyrt.cnp.account.model.Photo;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomBabayRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomBabayRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-16.
 * 班级专辑
 */
public class ClassroomBabayActivity extends BaseActivity{

    private GridView gridView;
    private ClassRoomAdapter classRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotolist);
        initView();
        loadData();
    }

    public void updateUI(ClassRoomBabay.Model model){
        String[] resKeys=new String[]{"getLogopath","getRenname"};
        int[] reses=new int[]{R.id.gridview_image,R.id.item_album_title};
        classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_gridview_image1,resKeys,reses);
        gridView.setAdapter(classRoomAdapter);

    }
    private void initView(){
        gridView =(GridView)findViewById(R.id.cnp_gridview);
    }

    private void loadData(){
        ClassroomBabayRequestListener sendwordRequestListener = new ClassroomBabayRequestListener(this);
        ClassroomBabayRequest schoolRecipeRequest=new ClassroomBabayRequest(Photo.Model.class,this);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
