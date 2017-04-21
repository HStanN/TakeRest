package com.hug.takerest.util;

import com.beltaief.flowlayout.FlowLayout;
import com.hug.takerest.R;

/**
 * Created by HStan on 2017/4/1.
 */

public class FlowLayoutManager {
    private FlowLayout flowLayout;

    public FlowLayoutManager(FlowLayout flowLayout){
        this.flowLayout = flowLayout;
        flowLayout.setConnectedText(R.string.connected_text);
        flowLayout.setDisconnectedText(R.string.disconnected_text);
        flowLayout.setConnectivityAware(true);
    }

    public void setEmpty(){
        flowLayout.setMode(FlowLayout.EMPTY);
    }

    public void showProgress(){
        flowLayout.setMode(FlowLayout.PROGRESS);
    }

    public void showContent(){
        flowLayout.setMode(FlowLayout.CONTENT);
    }

    public void showError(){
        flowLayout.setMode(FlowLayout.ERROR);
    }

}
