package com.lzh.administrator.androidframe.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzh.administrator.androidframe.Adapter.adapter.AutoCompleteTextViewAdapter;
import com.lzh.administrator.androidframe.Adapter.adapter.StationMultiItemTypeAdapter;
import com.lzh.administrator.androidframe.Adapter.connector.FlowContentItemDelegate;
import com.lzh.administrator.androidframe.Adapter.connector.StationContentItemDelegate;
import com.lzh.administrator.androidframe.Adapter.connector.StationLetterAndContentDelegate;
import com.lzh.administrator.androidframe.Adapter.connector.StationTitleDelegate;
import com.lzh.administrator.androidframe.Api.HttpManager;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Common.PinyinComparator;
import com.lzh.administrator.androidframe.GreenDAo.StationBean;
import com.lzh.administrator.androidframe.Model.StationAndTitleBean;
import com.lzh.administrator.androidframe.Model.TitleBean;
import com.lzh.administrator.androidframe.Model.TrainStationBean;
import com.lzh.administrator.androidframe.MyApplication;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;
import com.lzh.administrator.androidframe.Utils.SDCardUtil;
import com.lzh.administrator.androidframe.constant.Keys;
import com.lzh.administrator.androidframe.widget.SideBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lzh27651 on 2016/9/19.
 */

public class StationSelectionFragment extends BaseFragment {

    private TextView mTextViewDialog;
    private SideBar mSideBar;
    private ListView listView;
    private Call<ResponseBody> call;
    private Callback<ResponseBody> callback;
    private PinyinComparator pinyinComparator;
    private StationMultiItemTypeAdapter adapter;
    private RelativeLayout mQueryLayout;
    private AutoCompleteTextViewAdapter mCommonAdapter;
    private List<StationBean> mAutoCompleteDats;
    private AutoCompleteTextView autoCompleteTextView;
    private InputMethodManager manager;
    private FlowContentItemDelegate flowContentItemDelegate;

    private TrainStationBean initTrainStation;
    private int initPosition = -1;

    /**
     * 列表数据源
     */
    private List<StationAndTitleBean> mStationAndTitleBeen;

    /**
     * 存储数据库的对象
     */
    private List<StationBean> mStationBeen;

    private String mHotNames[] = {
            "北京","上海","杭州",
            "广州","南京","武汉",
            "郑州","长沙","深圳",
            "成都","西安","合肥",
            "重庆","汉口","济南"
    };

    private String mHotNamesPinYin[] = {
            "beijing","shanghai","hangzhou",
            "guangzhou","nanjing","wuhan",
            "zhengzhou","changsha","shenzhen",
            "chendu","xian","hefei",
            "chongqing","hankou","jinan"
    };

    private String mHotNamesPinYins[] = {
            "bj","sh","hz",
            "gz","nj","wh",
            "zz","cs","sz",
            "cd","xa","hf",
            "cq","hk","jn"
    };

    private String mHotNamesFirstPinYinS[] = {
            "B","S","H",
            "G","N","W",
            "Z","C","S",
            "C","X","F",
            "C","H","J"
    };

    private String mHotStationCode[] = {
            "BJP","SHH","HZH",
            "GZQ","NJH","WHN",
            "ZZF","CSQ","SZQ",
            "CDW","XAY","HFH",
            "CQW","HKN","JNK"
    };

    public static StationSelectionFragment newInstance(){
        return new StationSelectionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_station_selection;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        (mBaseActivity).setTitle(R.string.query_selection_from);

        mQueryLayout = (RelativeLayout) view.findViewById(R.id.query_layout);
        autoCompleteTextView = (AutoCompleteTextView) mQueryLayout.findViewById(R.id.query_auto_complete_textview);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null && s.length()>0){
                    autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(getContext(),R.drawable.icon_search),null,
                            ContextCompat.getDrawable(getContext(),R.drawable.icon_cancle),null);

                }else{
                    autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(getContext(),R.drawable.icon_search),null,null,null);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(autoCompleteTextView.isFocused()){
                    manager.showSoftInput(autoCompleteTextView,InputMethodManager.SHOW_FORCED);
                }
                Drawable rightDrawable = autoCompleteTextView.getCompoundDrawables()[2];
                if(rightDrawable!=null && event.getAction()==MotionEvent.ACTION_UP){
                    //检查点击的位置是否是右侧的删除图标
                    //注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
                    int leftEdgeOfRightDrawable = autoCompleteTextView.getRight()
                            - autoCompleteTextView.getPaddingRight()
                            - rightDrawable.getBounds().width();
                    if(event.getRawX() >= leftEdgeOfRightDrawable){
                        autoCompleteTextView.setText("");
                    }
                }
                return true;
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.setText("");
                hideInputMethed();
                TrainStationBean bean = new TrainStationBean();
                StationBean stationBean = mCommonAdapter.getItem(position);
                bean.setmName(stationBean.getName());
                bean.setmPinYin(stationBean.getPinyin());
                bean.setmPinYinS(stationBean.getPinyins());
                bean.setmStationCode(stationBean.getStationcode());
                bean.setmFirstPYS(stationBean.getFirstpys());
                backToMainWithData(bean);
            }
        });
        mTextViewDialog = (TextView) view.findViewById(R.id.query_city_alpha);
        mSideBar = (SideBar) view.findViewById(R.id.query_sidebar);
        mSideBar.setmTextView(mTextViewDialog);
        mSideBar.setOnLetterTouchedChangeListener(new SideBar.onLetterTouchedChangeListener() {
            @Override
            public void onTouchedLetterChange(String letterTouched) {
                LogUtil.d("sidebar :"+letterTouched);
                if(getString(R.string.query_location).equals(letterTouched)){
                    listView.setSelection(0);
                }else if(getString(R.string.query_hot).equals(letterTouched)){
                    listView.setSelection(2);
                }else if(getString(R.string.query_history).equals(letterTouched)){

                }else{
                    int position = adapter.getPositionForSection(letterTouched.charAt(0));
                    if(position!=-1){
                        listView.setSelection(position);
                    }
                }
            }
        });

        listView = (ListView) view.findViewById(R.id.query_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainStationBean bean = null;
                StationAndTitleBean item = adapter.getItem(position);
                if("content".equals(item.getmStyle())){
                    bean = item.getTrainStationBean();
                }else if("flow".equals(item.getmStyle())){
                   //适配器里面处理了
                }else if("letter_content".equals(item.getmStyle())){
                    bean = item.getTrainStationBean();
                }else{
                    return;
                }
                backToMainWithData(bean);
            }
        });

    }

    private void backToMainWithData(TrainStationBean bean) {
        Intent intent = new Intent();
        intent.putExtra(Keys.FROM_SELECTED_STATION,bean);
        intent.putExtra(Keys.TO_SELECTED_STATION,bean);
        getActivity().setResult(RESULT_OK,intent);
        getActivity().finish();
    }

    @Override
    public void initData() {
        initTrainStation = getActivity().getIntent().getParcelableExtra(Keys.FROM_SELECTED_STATION);
        if(initTrainStation==null){
            initTrainStation = getActivity().getIntent().getParcelableExtra(Keys.TO_SELECTED_STATION);
        }
        manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mStationBeen = new ArrayList<>();
        mStationAndTitleBeen = new ArrayList<>();
        pinyinComparator = new PinyinComparator();

        /**
         * 当前
         */
        StationAndTitleBean title = new StationAndTitleBean();
        title.setmStyle("title");
        title.setTitleBean(new TitleBean(getString(R.string.query_location)));
        /**
         * 当前城市
         */
        StationAndTitleBean location = new StationAndTitleBean();
        location.setmStyle("content");
        TrainStationBean localStation = new TrainStationBean();
        localStation.setmName("苏州");
        localStation.setmPinYin("suzhou");
        localStation.setmFirstPYS("S");
        localStation.setmPinYinS("sz");
        localStation.setmStationCode("SZH");
        location.setTrainStationBean(localStation);

        /**
         * 热门
         */
        StationAndTitleBean hot = new StationAndTitleBean();
        hot.setmStyle("title");
        hot.setTitleBean(new TitleBean(getString(R.string.query_hot)));

        /**
         * 热门城市
         */
        StationAndTitleBean flowStation = new StationAndTitleBean();
        flowStation.setmStyle("flow");
        List<TrainStationBean> list = new ArrayList<>();
        for(int i = 0; i< mHotNames.length; i++){
            TrainStationBean flowStationItem = new TrainStationBean();
            if(initTrainStation!=null){
                if(initTrainStation.getmName().equals(mHotNames[i])){
                    initPosition = i;
                }
            }
            flowStationItem.setmName(mHotNames[i]);
            flowStationItem.setmPinYin(mHotNamesPinYin[i]);
            flowStationItem.setmFirstPYS(mHotNamesFirstPinYinS[i]);
            flowStationItem.setmPinYinS(mHotNamesPinYins[i]);
            flowStationItem.setmStationCode(mHotStationCode[i]);
            list.add(flowStationItem);
        }
        flowStation.setTrainStationBeanList(list);
        mStationAndTitleBeen.add(title);
        mStationAndTitleBeen.add(location);
        mStationAndTitleBeen.add(hot);
        mStationAndTitleBeen.add(flowStation);



        List<StationBean> stationBeanList = MyApplication.getDaoSession(getContext()).loadAll(StationBean.class);
        if(stationBeanList==null || stationBeanList.size()<=0){
            LogUtil.d("数据库空的读取文件");
            final String path;
            path = getPath();
            File file = new File(path+ File.separator + "station_name");
            if(file.exists() && file.length()>0){
                LogUtil.d("文件存在读取");
                readAndPackagingDatas(file);
            }else{
                LogUtil.d("文件不存在下载");
                downloadStationInfo(path);
            }
        }else{
            LogUtil.d("数据库不为空");
            Collections.sort(stationBeanList,pinyinComparator);
            mAutoCompleteDats = stationBeanList;
            for(int i=0;i<stationBeanList.size();i++){
                StationBean stationBean = stationBeanList.get(i);
                TrainStationBean trainStationBean = new TrainStationBean();
                trainStationBean.setmName(stationBean.getName());
                trainStationBean.setmStationCode(stationBean.getStationcode());
                trainStationBean.setmPinYin(stationBean.getPinyin());
                trainStationBean.setmPinYinS(stationBean.getPinyins());
                trainStationBean.setmFirstPYS(stationBean.getFirstpys());
                StationAndTitleBean content = new StationAndTitleBean();
                content.setmStyle("letter_content");
                content.setTrainStationBean(trainStationBean);
                mStationAndTitleBeen.add(content);
            }
        }

        flowContentItemDelegate = new FlowContentItemDelegate(new FlowContentItemDelegate.onSelectedListenter() {
            @Override
            public void onItemSelected(StationAndTitleBean bean,int position) {
                TrainStationBean trainStationBean = bean.getTrainStationBeanList().get(position);
                backToMainWithData(trainStationBean);
            }
        },initPosition);

        mCommonAdapter = new AutoCompleteTextViewAdapter(getContext(),mAutoCompleteDats,R.layout.adapter_station_content);
        autoCompleteTextView.setAdapter(mCommonAdapter);

        adapter = new StationMultiItemTypeAdapter(getContext(),mStationAndTitleBeen);
        adapter.addItemViewDelegate(new StationTitleDelegate());
        adapter.addItemViewDelegate(new StationContentItemDelegate());
        adapter.addItemViewDelegate(flowContentItemDelegate);
        adapter.addItemViewDelegate(new StationLetterAndContentDelegate(mStationAndTitleBeen));
        listView.setAdapter(adapter);

    }

    /**
     * 下载站点信息到文件里面和封装存入数据库
     * @param path
     */
    private void downloadStationInfo(final String path) {
        call = HttpManager.getInstance().getHttpService().downloadFileWithFixedUrl();
        callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(path);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    File file1 = new File(path+ File.separator + "station_name");
                    if(file1.exists()){
                        return;
                    }
                    file1.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file1);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    LogUtil.d("下载站点信息成功");
                    if(file1.exists()){
                        readAndPackagingDatas(file1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.d("下载站点信息失败");
            }
        };
        HttpManager.getInstance().doHttpDeal(call,callback);
    }

    /**
     * 读取文件和封装数据存入数据库
     * @param file
     */
    private void readAndPackagingDatas(File file) {
        List<StationBean> beforeComparator = new ArrayList<>();

        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            inputStream.close();
            /* 将字节数组转换成字符创， 并转换编码的格式 */
            String res = new String(b,"utf-8");
            LogUtil.d("读取文件成功 :"+res);

            String[] buffer = res.split("@");
            for(int i=1;i<buffer.length;i++){
                String[] ss = buffer[i].split("\\|");
                StationBean stationBean = new StationBean((long)i-1,ss[1],ss[3],ss[4],ss[2],ss[4].substring(0,1).toUpperCase());
                beforeComparator.add(stationBean);
                mStationBeen.add(stationBean);
            }

            Collections.sort(beforeComparator,pinyinComparator);
            mAutoCompleteDats = beforeComparator;

            for(int j=0;j<beforeComparator.size();j++){
                StationBean stationBean = beforeComparator.get(j);
                TrainStationBean trainStationBean = new TrainStationBean();
                trainStationBean.setmName(stationBean.getName());
                trainStationBean.setmStationCode(stationBean.getStationcode());
                trainStationBean.setmPinYin(stationBean.getPinyin());
                trainStationBean.setmPinYinS(stationBean.getFirstpys());
                trainStationBean.setmFirstPYS(stationBean.getFirstpys());
                StationAndTitleBean content = new StationAndTitleBean();
                content.setmStyle("letter_content");
                content.setTrainStationBean(trainStationBean);
                mStationAndTitleBeen.add(content);
            }

            MyApplication.getStationBeanDao(getContext()).insertOrReplaceInTx(mStationBeen);

        } catch (Exception e) {
            LogUtil.d("读取文件失败");
        }
    }

    /**
     * 获取站点文件地址
     * @return
     */
    @NonNull
    private String getPath() {
        String path;
        if(SDCardUtil.isSDCardEnable()){
            path = SDCardUtil.getSDCardPath()+"XGTGJ";
        }else{
            path = SDCardUtil.getRootDirectoryPath() + "/XGTGJ";
        }
        return path;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideInputMethed();
    }

    private void hideInputMethed() {
        if(manager!=null && autoCompleteTextView!=null){
            LogUtil.d("隐藏输入法键盘");
            manager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(),0);
        }
    }
}
