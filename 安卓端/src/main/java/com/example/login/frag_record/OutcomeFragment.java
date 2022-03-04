package com.example.login.frag_record;

import androidx.fragment.app.Fragment;

import com.example.login.MainActivity;
import com.example.login.R;
import com.example.login.db.DBManager;
import com.example.login.db.TypeBean;
import com.example.login.downloadActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutcomeFragment extends BaseRecordFragment {


    // 重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("else");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        MainActivity.getLogname(accountBean);
        DBManager.insertItemToAccounttb(accountBean);
        DBManager.postForm(accountBean);


    }
}
