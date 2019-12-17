package com.example.weatherapplication.base;

import android.support.v4.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/*xutils加载网络数据的步骤
*1.整体模块的声明，重新写了一个Application文件，然后在AndroidManifest.xml中声明一下name
*2.执行网络请求操作
**/
public class BaseFragment extends Fragment implements Callback.CommonCallback<String> {
    public void loadData(String path){
        RequestParams params = new RequestParams(path);
        x.http().get(params, this);
    }

//  获取数据成功时会回调的接口
    @Override
    public void onSuccess(String result) {

    }
//  获取数据失败的时候，会回调的接口
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }
//  取消请求时，会回调的接口
    @Override
    public void onCancelled(CancelledException cex) {

    }
//  请求完成时，会回调的接口
    @Override
    public void onFinished() {

    }
}
