package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.ClassroomIndexAdapter;
import com.jingdong.common.frame.BaseActivity;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-16.
 */
public class ClassroomIndexActivity extends BaseActivity{
    private GridView gridView;
    private int[] imageResId;
    private String[] text={"班级公告","每日食谱","班级相册","班级成员"};
    private int[] bg;
    private Intent intent;
    @Inject
    @Named("schoolNoticeActivity")
    private Class schoolNoticeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomindex);
        RoboGuice.getInjector(this.getApplicationContext()).injectMembers(this);
        initView();
    }

    private void initView(){
        imageResId = new int[] { R.drawable.classroom_notice, R.drawable.classroom_recipe,
                R.drawable.classroom_photo, R.drawable.classroom_member};
        bg = new int[]{R.color.classroomindex_notice,R.color.classroomindex_recipe,
                R.color.classroomindex_photo,R.color.classroomindex_member};
        gridView = (GridView) findViewById(R.id.schoolindexlist);
        ClassroomIndexAdapter schoolIndexAdapter=new ClassroomIndexAdapter(text,imageResId,bg,this);
        gridView.setAdapter(schoolIndexAdapter);
        gridView.setOnItemClickListener(new ItemClickListener());
    }

    class  ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3) {
            int i = arg2;
            switch (i){
                case 0:
                    intent =new Intent();
                    intent.setClass(ClassroomIndexActivity.this,schoolNoticeActivity);
                    intent.putExtra("data","classroom");
                    startActivity(intent);
                    break;
                case 1:
                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,ClassroomRecipeInfoActivity.class));
                    break;
                case 2:
                    intent =new Intent();
                    intent.setClass(ClassroomIndexActivity.this,ClassroomAlbumActivity.class);
                    intent.putExtra("Category","ClassroomIndexActivity");
                    startActivity(intent);
                    break;
                case 3:
                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,ClassroomBabayActivity.class));
                    break;
//                case 4:
//                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,StarBabayActivity.class));
//                    break;
//                case 5:
//                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,SchoolInfoActivity.class));
//                    break;
//                case 6:
//                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,ClassRoomListActivity.class));
//                    break;
//                case 7:
//                    startActivity(new Intent().setClass(ClassroomIndexActivity.this,SchoolRecipeActivity.class));
//                    break;

            }
        }
    }
}
