package com.hyrt.cnp.classroom.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hyrt.cnp.account.model.Photo;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomPhotoListRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomPhotoListRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-17.
 */
public class ClassroomphotolistActivity extends BaseActivity{

    private GridView gridView;
    private ClassRoomAdapter classRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotolist);
        initView();
        loadData();
    }

    public void updateUI(final Photo.Model model){
        String[] resKeys=new String[]{"getImagethpath","getTitle"};
        int[] reses=new int[]{R.id.gridview_image,R.id.item_album_title};
        classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_gridview_image1,resKeys,reses);
        gridView.setAdapter(classRoomAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ShowPop(gridView,model.getData().get(i).getImagepics());
            }
        });
    }
    private void initView(){
        gridView =(GridView)findViewById(R.id.cnp_gridview);
    }

    private void loadData(){
        ClassroomPhotoListRequestListener sendwordRequestListener = new ClassroomPhotoListRequestListener(this);
        ClassroomPhotoListRequest schoolRecipeRequest=new ClassroomPhotoListRequest(Photo.Model.class,this);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
