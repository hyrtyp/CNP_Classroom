package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.account.model.Comment;
import com.hyrt.cnp.account.model.Photo;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomaddcommentRequest;
import com.hyrt.cnp.classroom.request.ClassroomcommentRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomaddcommentRequestListener;
import com.hyrt.cnp.classroom.requestListener.ClassroomcommentRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-22.
 */
public class ClassroomphotoinfoActivity extends BaseActivity{

    private ImageView imgphoto;
    private TextView albumname;
    private TextView photoname;
    private EditText editcommit;
    private Button btnset;
    private ListView listView;
    private Photo photo;
    private ClassRoomAdapter classRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotoinfo);
        initView();
        initData();
        LoadData();
    }

    private void initView(){
        imgphoto=(ImageView)findViewById(R.id.img_photo);
        albumname=(TextView)findViewById(R.id.text_albumname);
        photoname=(TextView)findViewById(R.id.text_photoname);
        editcommit=(EditText)findViewById(R.id.edit_commit);
        btnset=(Button)findViewById(R.id.btn_set);
        listView = (ListView)findViewById(R.id.commit_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editcommit.getText().toString().equals("")){
                    addcomment();
                }else{
                    Toast.makeText(ClassroomphotoinfoActivity.this,"评论不能为空！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initData(){
        Intent intent = getIntent();
        photo=(Photo)intent.getSerializableExtra("vo");
        photoname.setText("照片名称："+photo.getTitle());
        showDetailImage(photo.getImagepics(),imgphoto,false);
    }

    private void LoadData(){
        ClassroomcommentRequestListener sendwordRequestListener = new ClassroomcommentRequestListener(this);
        ClassroomcommentRequest schoolRecipeRequest=
                new ClassroomcommentRequest(Comment.Model.class,this,photo.getPhotoID()+"","15");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void updateUI(Comment.Model model){
        String[] resKeys=new String[]{"getphotoImage","getUsername","getRedate","getContent"};
        int[] reses=new int[]{R.id.comment_photo,R.id.text_name,R.id.text_time,R.id.text_con};
        classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_comment,resKeys,reses);
        listView.setAdapter(classRoomAdapter);
    }

    public void ShowSuccess(){
        Toast.makeText(ClassroomphotoinfoActivity.this,"添加评论成功",Toast.LENGTH_SHORT).show();
    }
    private void addcomment(){
        Comment comment=new Comment();
        comment.setInfoID(photo.getPhotoID());
        comment.setInfoTitle(photo.getTitle());
        comment.setInfoUserId(photo.getUserID());
        comment.setInfoNurseryId(photo.getNurseryID());
        comment.setInfoClassroomId(photo.getClassroomID());
        comment.setSiteid("15");
        comment.setUrl(photo.getImagepics());
        comment.setLstatus("N");
        comment.setContent(editcommit.getText().toString());
        comment.setReply("0");
        comment.setRecontent("");
        comment.setReuserId("");
        comment.setReusername("");
        comment.setRedate("");
        ClassroomaddcommentRequestListener sendwordRequestListener = new ClassroomaddcommentRequestListener(this);
        ClassroomaddcommentRequest schoolRecipeRequest=
                new ClassroomaddcommentRequest(Comment.Model3.class,this,comment);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
