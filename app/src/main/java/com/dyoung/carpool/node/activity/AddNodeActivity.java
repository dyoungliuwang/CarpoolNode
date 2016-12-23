package com.dyoung.carpool.node.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

import com.dyoung.carpool.node.R;
import com.dyoung.carpool.node.greendao.model.Node;
import com.dyoung.carpool.node.mvpview.IAddNodeView;
import com.dyoung.carpool.node.presenter.AddNodePresenter;
import com.dyoung.carpool.node.util.LogUtil;
import com.dyoung.carpool.node.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNodeActivity extends BaseActivity<AddNodePresenter> implements IAddNodeView {

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.content)
    EditText content;
    private Node editNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);
        ButterKnife.bind(this);

        toolbar.setTitle("记事本");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_back_selector);

        editNode=getIntent().getParcelableExtra("node");
        mPresenter=new AddNodePresenter(this,this);

        if(editNode!=null){
            title.setText(editNode.getTitle());
            content.setText(editNode.getContent());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.save:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /***
     * 添加记事本
     */
    private void save(){
        //进行修改
        Node node=createNode();
        if(node==null){
            LogUtil.d(TAG,"save note ==null");
            return;
        }
        if(editNode!=null){
            LogUtil.d(TAG,"edidNode!=null  update");
            node.setId(editNode.getId());
            mPresenter.update(node);
            return;
        }
        LogUtil.d(TAG,"save insert ");
        mPresenter.insert(node);
    }

    /**
     * 创建对象
     * @return
     */
    private  Node createNode(){
        String titleStr=title.getText().toString().trim();
        String contentStr=content.getText().toString().trim();
        if(TextUtils.isEmpty(titleStr)) {
            ToastUtil.show("标题不能为空");
            return null;
        }else if(TextUtils.isEmpty(contentStr)){
            ToastUtil.show("内容不能为空");
            return null;
        }
        return  new Node(null, titleStr, contentStr, System.currentTimeMillis());
    }
    @Override
    public void success() {
//        ToastUtil.show("保存成功");
        finish();

    }

    @Override
    public void failed() {
        ToastUtil.show("保存失败");
    }



}
