package com.dyoung.carpool.node.mvpview;

import com.dyoung.carpool.node.greendao.model.Node;

import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
public interface INodeListView {
    void setNodeList(List<Node> list);
    void  appendNode(Node node);
    void deleteSuccess(Node node);
    void deleteFailed();
}
