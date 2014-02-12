package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hyrt.cnp.account.model.Album;
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
    private Photo.Model model;
    private String  Category;
    private TextView bottom_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotolist);
        initView();
        loadData();
    }

    /**
     * 更新ui界面
     * */
    public void updateUI(Photo.Model model){
        if(model==null){
            bottom_num.setText("暂无信息");
        }else{
            this.model=model;
            String[] resKeys=new String[]{"getImagethpath","getTitle"};
            int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
            classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_gridview_image1,resKeys,reses);
            gridView.setAdapter(classRoomAdapter);
            bottom_num.setText("共 "+model.getData().size()+" 张");
        }
    }
    private void initView(){
        bottom_num=(TextView)findViewById(R.id.bottom_num);
        gridView =(GridView)findViewById(R.id.cnp_gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(ClassroomphotolistActivity.this,ClassroomphotoinfoActivity.class);
                intent.putExtra("vo",model.getData().get(i));
                intent.putExtra("Category",Category);
                startActivity(intent);
//                        ShowPop(gridView,model.getData().get(i).getImagepics());
            }
        });
    }

    private void loadData(){
        Intent intent = getIntent();
        Album album = (Album)intent.getSerializableExtra("vo");
        Category=intent.getStringExtra("Category");
        if(Category.equals("ClassroomIndexActivity")){
            titletext.setText("班级相册");
        }else if(Category.equals("BabayIndexActivity")){
            titletext.setText("动感相册");
        }
        ClassroomPhotoListRequestListener sendwordRequestListener = new ClassroomPhotoListRequestListener(this);
        ClassroomPhotoListRequest schoolRecipeRequest=new ClassroomPhotoListRequest(Photo.Model.class,this,album.getPaId());
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model=null;
        classRoomAdapter=null;
    }
}
