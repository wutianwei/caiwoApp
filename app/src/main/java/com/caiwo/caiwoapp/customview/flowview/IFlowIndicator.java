package com.caiwo.caiwoapp.customview.flowview;

public interface IFlowIndicator extends FlowView.ViewSwitchListener {
    
    public void setFlowView(FlowView view);
    
    public void onScrolled(int h, int v, int oldh, int oldv);
}
