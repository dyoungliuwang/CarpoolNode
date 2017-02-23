package com.dyoung.carpool.node.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.adapter.MainFragmentPagerAdapter;
import com.dyoung.carpool.node.fragment.CarPoolNodeListFragment;
import com.dyoung.carpool.node.fragment.MineFragment;
import com.dyoung.carpool.node.fragment.NodeListFragment;
import com.dyoung.carpool.node.util.ToastUtil;
import com.dyoung.carpool.node.view.CommonPopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    /**
     * 主页
     * @param savedInstanceState
     */
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList=new ArrayList<Fragment>();
    private  long lastBackTime;

    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.navigation_carpool_icon) ImageView carPoolImg;
    @BindView(R.id.navigation_carpool_txt) TextView carPoolTxt;
    @BindView(R.id.navigation_carpool_layout)   LinearLayout carPoolLayout;

    @BindView(R.id.navigation_node_icon) ImageView nodeImg;
    @BindView(R.id.navigation_node_txt) TextView nodeTxt;
    @BindView(R.id.navigation_node_layout)   LinearLayout nodeLayout;

    @BindView(R.id.navigation_mine_icon) ImageView mineImg;
    @BindView(R.id.navigation_mine_txt) TextView mineTxt;
    @BindView(R.id.navigation_mine_layout)   LinearLayout mineLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CommonPopupWindow commonPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initView();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     *
     * 初始化数据
     */
    private  void initView(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
//                    case R.id.search:
//                        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
//                        ToastUtil.show("load success");
//                        break;
                    case R.id.add:
                        showPopupWindow(toolbar);
                        break;
                }
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        recoverTxtColor(carPoolImg,carPoolTxt);
                        break;
                    case 1:
                        recoverTxtColor(nodeImg,nodeTxt);
                        break;
                    case 2:
                        recoverTxtColor(mineImg,mineTxt);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        recoverTxtColor(carPoolImg,carPoolTxt);
        mViewPager.setOffscreenPageLimit(2);
        fragmentList.add(new CarPoolNodeListFragment());
        fragmentList.add(new NodeListFragment());
        fragmentList.add(new MineFragment());

        fragmentPagerAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(fragmentPagerAdapter);

    }

    private void showPopupWindow(View view){
        commonPopupWindow=new CommonPopupWindow(this,R.layout.home_add_popupwindow_layout);
        commonPopupWindow.setOnClickListener(R.id.home_add_carpool, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, AddCarNodeActivity.class));
//                startActivity(new Intent(MainActivity.this, HealthyActivity.class));
            }
        });
        commonPopupWindow.setOnClickListener(R.id.home_add_node, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this,AddNodeActivity.class));
            }
        });
        commonPopupWindow.showPopupWindow(view);
    }


    /**
     * 设置选中时的状态
     * @param currentImg
     * @param currentTxt
     */
    private  void recoverTxtColor(ImageView currentImg,TextView currentTxt){
        carPoolImg.setSelected(false);
        mineImg.setSelected(false);
        nodeImg.setSelected(false);

        carPoolTxt.setSelected(false);
        nodeTxt.setSelected(false);
        mineTxt.setSelected(false);

        currentImg.setSelected(true);
        currentTxt.setSelected(true);
    }
    @OnClick({R.id.navigation_carpool_layout,R.id.navigation_node_layout,R.id.navigation_mine_layout})
    public void onViewClick(View view) {
        if(view==carPoolLayout){
            mViewPager.setCurrentItem(0,false);
            recoverTxtColor(carPoolImg,carPoolTxt);
        }else if(view==nodeLayout){
            mViewPager.setCurrentItem(1,false);
            recoverTxtColor(nodeImg,nodeTxt);
        }else if(view==mineLayout){
            mViewPager.setCurrentItem(2,false);
            recoverTxtColor(mineImg,mineTxt);
        }/*else if(view== addCarNode){

        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long currentTime=System.currentTimeMillis();
        if((currentTime-lastBackTime)>2000){
            lastBackTime=System.currentTimeMillis();
            ToastUtil.show("再按一下退出拼车行");
        }else{
            finish();
        }
    }
}
