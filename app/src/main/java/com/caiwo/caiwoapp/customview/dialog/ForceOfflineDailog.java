package com.caiwo.caiwoapp.customview.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.caiwo.caiwoapp.R;


/**
 * Created by bb on 2016/12/12.
 * 账号被顶掉
 */
public class ForceOfflineDailog extends AbsDialogCreator{

    private Context mContext;

    public ForceOfflineDailog(Context context) {
        super(context, false);
        mContext = context;
    }

    @Override
    public int getRootViewId() {
        return R.layout.dialog_forceoffline;
    }

    @Override
    public void findView(View rootView) {
        TextView forceoffline_ok_Tx = (TextView) rootView.findViewById(R.id.forceoffline_ok_Tx);
        forceoffline_ok_Tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LiveApplication.backhome = "yes";
                ForceOfflineDailog.this.dismiss();
//                Intent intent = new Intent(mContext, HomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
            }
        });

    }
}
