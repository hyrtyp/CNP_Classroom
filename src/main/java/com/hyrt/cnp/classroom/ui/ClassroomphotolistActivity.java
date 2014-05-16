package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomPhotoListRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomPhotoListRequestListener;
import com.hyrt.cnp.dynamic.adapter.DynamicPhotoInfoAdapter;
import com.hyrt.cnp.dynamic.request.DynamicPhotoListRequest;
import com.hyrt.cnp.dynamic.requestListener.DynamicPhotoListRequestListener;
import com.hyrt.cnp.dynamic.ui.DynamicPhotoInfoActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-1-17.
 */
public class ClassroomphotolistActivity extends BaseActivity{

    private GridView gridView;
    private ClassRoomAdapter classRoomAdapter;
    private Photo.Model model;
    private DynamicPhoto.Model DynamicModel;
    private String  Category;
    private TextView bottom_num;
    private ArrayList<String> imageurls = new ArrayList<String>();
    private ArrayList<String> commentNums = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotolist);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
            imageurls.clear();
            commentNums.clear();
            for(int i=0,j=model.getData().size(); i<j; i++){
                imageurls.add(model.getData().get(i).getImagepics());
                commentNums.add(model.getData().get(i).getCommentNum()+"");
            }
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
                /*Intent intent = new Intent();
                intent.setClass(ClassroomphotolistActivity.this,ClassroomphotoinfoActivity.class);
                intent.putExtra("vo",model.getData().get(i));
                intent.putExtra("Category",Category);
                startActivity(intent);*/
                showPop3(gridView, imageurls, commentNums, i, ClassroomphotolistActivity.this, mShowPop3Listener, false);
//                        ShowPop(gridView,model.getData().get(i).getImagepics());
            }
        });
    }

    showPop3Listener mShowPop3Listener = new showPop3Listener() {
        @Override
        public void onClick(int type, int position) {
            if(type == 1 || type == 2){
                Intent intent = new Intent();
                if(Category.equals("BabayIndexActivity")){
                    intent.setClass(ClassroomphotolistActivity.this,DynamicPhotoInfoActivity.class);
                    intent.putExtra("dynamicPhoto", DynamicModel.getData().get(position));
                    intent.putExtra("album", getIntent().getSerializableExtra("album"));
                }else{
                    intent.setClass(ClassroomphotolistActivity.this,ClassroomphotoinfoActivity.class);
                    intent.putExtra("vo",model.getData().get(position));
                    intent.putExtra("Category",Category);
                }
                if(type == 1){
                    intent.putExtra("etFocus", true);
                }
                startActivity(intent);
                popWin.dismiss();
            }
        }
    };

    private void loadData(){
        Intent intent = getIntent();
        Album album = (Album)intent.getSerializableExtra("vo");
        Category=intent.getStringExtra("Category");
        if(Category.equals("ClassroomIndexActivity")){
            titletext.setText("班级相册");
            ClassroomPhotoListRequestListener sendwordRequestListener = new ClassroomPhotoListRequestListener(this);
            ClassroomPhotoListRequest schoolRecipeRequest=new ClassroomPhotoListRequest(Photo.Model.class,this,album.getPaId());
            spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    sendwordRequestListener.start());
        }else if(Category.equals("BabayIndexActivity")){
            titletext.setText("动感相册");
            DynamicPhotoListRequestListener requestListener = new DynamicPhotoListRequestListener(this);
            requestListener.setListener(mDynamicPhotoRequestListener);
            DynamicPhotoListRequest request = new DynamicPhotoListRequest(DynamicPhoto.Model.class,this,album.getPaId());
            spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    requestListener.start());
        }



    }

    private DynamicPhotoListRequestListener.RequestListener mDynamicPhotoRequestListener
            = new DynamicPhotoListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(DynamicPhoto.Model data) {
            DynamicModel = data;
            imageurls.clear();
            commentNums.clear();
            for(int i=0,j=data.getData().size(); i<j; i++){
                imageurls.add(data.getData().get(i).getImagepics());
                commentNums.add(data.getData().get(i).getCommentNum()+"");
            }
            String[] resKeys=new String[]{"getImagethpath","getTitle"};
            int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
            classRoomAdapter = new ClassRoomAdapter(
                    ClassroomphotolistActivity.this,data.getData(),
                    R.layout.layout_item_gridview_image1,resKeys,reses);
            gridView.setAdapter(classRoomAdapter);
            bottom_num.setText("共 "+data.getData().size()+" 张");
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model=null;
        classRoomAdapter=null;
    }
}
