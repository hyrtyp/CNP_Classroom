package com.hyrt.cnp.classroom.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.account.model.Album;
import com.hyrt.cnp.account.model.RecipeInfo;
import com.hyrt.cnp.classroom.R;
import com.hyrt.cnp.classroom.adapter.RepiceInfoAdapter;
import com.hyrt.cnp.classroom.request.ClassroomRecipeInfoRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomRecipeInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.utils.FormatUtils;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GYH on 14-1-16.
 * 班级专辑
 */
public class ClassroomRecipeInfoActivity extends BaseActivity {

    private Date date = new java.util.Date();
    private ListView listview;
    private RepiceInfoAdapter repiceInfoAdapter;
    private String[] footstime = new String[]{"早餐", "早餐配料", "加餐", "午餐", "午餐配料", "午点", "晚餐", "晚餐配料", "日营养量", "负责人"};
    private ArrayList<String> foot;
    private TextView foottimetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomrepiceinfo);
        initView();
        loadData(FormatUtils.formatDate(date));
    }

    public void updateUI(RecipeInfo.Model models) {
        foot = new ArrayList<String>();
        if (models != null) {
            RecipeInfo model = models.getData();
            if (repiceInfoAdapter == null) {
                foot.add(model.getBreakfast());
                foot.add(model.getB_ingredients());
                foot.add(model.getAddfood());
                foot.add(model.getLunch());
                foot.add(model.getL_ingredients());
                foot.add(model.getLunchsnacks());
                foot.add(model.getDinner());
                foot.add(model.getD_ingredients());
                foot.add(model.getLevel());
                foot.add(model.getFooder());
                repiceInfoAdapter = new RepiceInfoAdapter(this, footstime, foot);
                listview.setAdapter(repiceInfoAdapter);
            } else {
                foot.add(model.getBreakfast());
                foot.add(model.getB_ingredients());
                foot.add(model.getAddfood());
                foot.add(model.getLunch());
                foot.add(model.getL_ingredients());
                foot.add(model.getLunchsnacks());
                foot.add(model.getDinner());
                foot.add(model.getD_ingredients());
                foot.add(model.getLevel());
                foot.add(model.getFooder());
                repiceInfoAdapter.notifyDataSetChanged();
            }
        } else {
            if (repiceInfoAdapter != null) {
                foot.clear();
                repiceInfoAdapter.notifyDataSetChanged();
            }
        }


    }

    private void initView() {
        foottimetext = (TextView) findViewById(R.id.foottimetext);
        listview = (ListView) findViewById(R.id.recipeinfo_listview);
        Button recipelast = (Button) findViewById(R.id.recipe_last);
        recipelast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(FormatUtils.preDay(date));
            }
        });
        Button recipenext = (Button) findViewById(R.id.recipe_next);
        recipenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(FormatUtils.nextDay(date));
            }
        });


    }

    private void loadData(String time) {
        foottimetext.setText(time);
        ClassroomRecipeInfoRequestListener sendwordRequestListener = new ClassroomRecipeInfoRequestListener(this);
        ClassroomRecipeInfoRequest schoolRecipeRequest = new ClassroomRecipeInfoRequest(Album.Model.class, this, time);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
