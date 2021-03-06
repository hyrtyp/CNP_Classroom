package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyrt.cnp.account.model.Album;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomAlbumRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomAlbumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-16.
 * 班级专辑
 */
public class ClassroomAlbumActivity extends BaseActivity{

    private ListView listview;
    private ClassRoomAdapter classRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomalbum);
        initView();
        loadData();
    }

    public void updateUI(Album.Model model){
        String[] resKeys=new String[]{"getImagepath","getAlbumName","getAlbumDesc","getPosttime"};
        int[] reses=new int[]{R.id.item_album_image,R.id.item_album_title,R.id.item_album_con,R.id.item_album_time};
        classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_album,resKeys,reses);
        listview.setAdapter(classRoomAdapter);
    }

    private void initView(){
        listview=(ListView)findViewById(R.id.cnp_listview_album);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent().setClass(ClassroomAlbumActivity.this,ClassroomphotolistActivity.class));
            }
        });
    }

    private void loadData(){
        ClassroomAlbumRequestListener sendwordRequestListener = new ClassroomAlbumRequestListener(this);
        ClassroomAlbumRequest schoolRecipeRequest=new ClassroomAlbumRequest(Album.Model.class,this);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
