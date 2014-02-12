package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.name.Named;
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
 * 班级成员
 */
public class ClassroomBabayActivity extends BaseActivity{

    private GridView gridView;
    private ClassRoomAdapter classRoomAdapter;
    private ClassRoomBabay.Model model;
    private TextView bottom_num;

    @Inject
    @Named("babayIndexActivity")
    private Class babayIndexActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroombabay);
        initView();
        loadData();
    }
    public void updateUI(ClassRoomBabay.Model model){
        if(model==null){
            bottom_num.setText("暂无信息");
        }else {
            this.model=model;
            String[] resKeys=new String[]{"getLogopath","getRenname"};
            int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
            classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_gridview_image3,resKeys,reses);
            gridView.setAdapter(classRoomAdapter);
            bottom_num.setText("共 "+model.getData().size()+" 人");
        }

    }
    private void initView(){
        bottom_num=(TextView)findViewById(R.id.bottom_num);
        gridView =(GridView)findViewById(R.id.cnp_gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(ClassroomBabayActivity.this,babayIndexActivity);
                intent.putExtra("vo",model.getData().get(i));
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        ClassroomBabayRequestListener sendwordRequestListener = new ClassroomBabayRequestListener(this);
        ClassroomBabayRequest schoolRecipeRequest=new ClassroomBabayRequest(Photo.Model.class,this);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
