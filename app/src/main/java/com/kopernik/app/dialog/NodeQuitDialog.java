//package com.kopernik.app.dialog;
//
//import android.app.Dialog;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.TextWatcher;
//import android.text.style.ForegroundColorSpan;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.fragment.app.DialogFragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
//
//
//import java.util.List;
//
//import skin.support.content.res.SkinCompatResources;
//
//public class NodeQuitDialog extends DialogFragment implements OnStatusChildClickListener {
//
//    private RecyclerView recyclerView;
//    private TextView doneTv,contentTv;
//    private EditText inputEt;
//    private SmartRefreshLayout smartRefreshLayout;
//
//    private String quitNodeName;
//    private NodeQuitChooseAdapter adapter;
//    private StatusLayoutManager statusLayoutManager;
//    private int pageNumber = 1;
//    private int pageSize = 10;
//
//
//    public static NodeQuitDialog newInstance(String quitNodeName) {
//        NodeQuitDialog fragment = new NodeQuitDialog();
//        Bundle bundle = new Bundle();
//        bundle.putString("quitNodeName",quitNodeName);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_node_quit);
//        dialog.setCanceledOnTouchOutside(true);
//        final Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.AnimBottom);
//        window.setBackgroundDrawableResource(android.R.color.transparent);
//        final WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.BOTTOM;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height =  getActivity().getWindowManager().getDefaultDisplay().getHeight() * 4 / 5;
//        window.setAttributes(lp);
//        quitNodeName = getArguments().getString("quitNodeName");
//        initView(dialog);
//        return dialog;
//    }
//
//    private void initView(Dialog dialog) {
//        smartRefreshLayout = dialog.findViewById(R.id.root_view);
//        recyclerView = dialog.findViewById(R.id.recycler_view);
//        doneTv = dialog.findViewById(R.id.done_tv);
//        contentTv = dialog.findViewById(R.id.txt_msg);
//        inputEt = dialog.findViewById(R.id.input_et);
//        KeyboardUtils.hideSoftKeyboard(inputEt);
//        inputEt.setHint(getActivity().getString(R.string.input_node_hash_or_name));
//        statusLayoutManager = StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout,getActivity(),this);
//        String contentStr = String.format(getActivity().getString(R.string.node_quit_content),quitNodeName);
//
//        int start = 0;
//        switch (UserConfig.getSingleton().getLanguageTag()) {
//            case 1:
//                start = 6;
//                break;
//            case 2:
//                start = 24;
//                break;
//            case 3:
//                start = 5;
//                break;
//        }
//        SpannableString msp = new SpannableString(contentStr);
//        msp.setSpan(new ForegroundColorSpan(Color.RED), start, start + quitNodeName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        contentTv.setText(msp);
//
//        inputEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (StringUtils.isEmpty(s.toString())) {
//                    doneTv.setEnabled(false);
//                    doneTv.setTextColor(getActivity().getResources().getColor(R.color.text_gray));
//                } else {
//                    adapter.setNodeNameOrHash(s.toString());
//                    doneTv.setEnabled(true);
//                    doneTv.setTextColor(SkinCompatResources.getColor(getActivity(),R.color.quite_node_done));
//                }
//            }
//        });
//
//        adapter = new NodeQuitChooseAdapter(R.layout.item_quit_node_choose_list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
//
//        inputEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    String keytag = inputEt.getText().toString().trim();
//                    if (StringUtils.isEmpty(keytag)) {
//                        KeyboardUtils.hideSoftKeyboard(inputEt);
//                        pageNumber = 1;
//                        loadData();
//                        return true;
//                    }
//                    // 搜索功能主体
//                    KeyboardUtils.hideSoftKeyboard(inputEt);
//                    pageNumber = 1;
//                    search();
//                    return true;
//                }
//                return false;
//            }
//        });
//        smartRefreshLayout.autoRefresh();
//        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if (StringUtils.isEmpty(inputEt.getText().toString())) {
//                    loadData();
//                }
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                pageNumber = 1;
//                if (StringUtils.isEmpty(inputEt.getText().toString())) {
//                    loadData();
//                }
//            }
//        });
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter2, View view, int position) {
//                NodeQuitListBean.DatasBean item = adapter.getItem(position);
//                if (inputEt.getText().toString().equals(item.getName())) {
//                    inputEt.setText("");
//                    adapter.setNodeNameOrHash("");
//                } else {
//                    inputEt.setText(item.getName());
//                    adapter.setNodeNameOrHash(item.getName());
//                }
//            }
//        });
//        doneTv.setOnClickListener(v -> {
//            KeyboardUtils.hideSoftKeyboard(inputEt);
//            updateAccountRecommendNode();
//        });
//    }
//
//    private void loadData() {
//        OkGo.<BaseBean<NodeQuitListBean>>get(ServiceUrl.nodeQuitData)
//                .tag(this)
//                .params("pageNumber",pageNumber)
//                .params("pageSize",pageSize)
//                .execute(new DialogCallback<BaseBean<NodeQuitListBean>>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBean<NodeQuitListBean>> response) {
//                        List<NodeQuitListBean.DatasBean> datas = response.body().data.getDatas();
//                        if (pageNumber == 1) {
//                            smartRefreshLayout.finishRefresh(300);
//                            if (datas == null || datas.size() == 0) {
//                                statusLayoutManager.showEmptyLayout();
//                                smartRefreshLayout.setNoMoreData(true);
//                                return;
//                            }
//                            if (datas.size() == 10) {
//                                pageNumber++;
//                            } else {
//                                smartRefreshLayout.finishRefreshWithNoMoreData();
//                            }
//                            statusLayoutManager.showSuccessLayout();
//                            adapter.setNewData(datas);
//                        } else {
//                            if (datas.size() < 10) {
//                                smartRefreshLayout.finishLoadMoreWithNoMoreData();
//                            } else {
//                                pageNumber++;
//                                smartRefreshLayout.finishLoadMore(true);
//                            }
//                            statusLayoutManager.showSuccessLayout();
//                            adapter.addData(datas);
//                        }
//                    }
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        ErrorMsg.showErrorMsg(getActivity(),code);
//                        statusLayoutManager.showErrorLayout();
//                    }
//                });
//    }
//
//    private void search() {
//        OkGo.<BaseBean<NodeQuitListBean>>get(ServiceUrl.nodeQuitSerach)
//                .tag(this)
//                .params("pageNumber",pageNumber)
//                .params("pageSize",pageSize)
//                .params("name",inputEt.getText().toString())
//                .execute(new DialogCallback<BaseBean<NodeQuitListBean>>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<BaseBean<NodeQuitListBean>> response) {
//                        List<NodeQuitListBean.DatasBean> datas = response.body().data.getDatas();
//                        if (pageNumber == 1) {
//                            smartRefreshLayout.finishRefresh(300);
//                            if (datas == null || datas.size() == 0) {
//                                statusLayoutManager.showEmptyLayout();
//                                smartRefreshLayout.setNoMoreData(true);
//                                return;
//                            }
//                            if (datas.size() == 10) {
//                                pageNumber++;
//                            } else {
//                                smartRefreshLayout.finishRefreshWithNoMoreData();
//                            }
//                            statusLayoutManager.showSuccessLayout();
//                            adapter.setNewData(datas);
//                        } else {
//                            if (datas.size() < 10) {
//                                smartRefreshLayout.finishLoadMoreWithNoMoreData();
//                            } else {
//                                pageNumber++;
//                                smartRefreshLayout.finishLoadMore(true);
//                            }
//                            statusLayoutManager.showSuccessLayout();
//                            adapter.addData(datas);
//                        }
//                    }
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        ErrorMsg.showErrorMsg(getActivity(),code);
//                        statusLayoutManager.showErrorLayout();
//                    }
//                });
//    }
//
//    private void updateAccountRecommendNode() {
//        OkGo.<BaseBean<String>>post(ServiceUrl.updateAccountNode)
//                .tag(this)
//                .params("nodeHash",inputEt.getText().toString())
//                .execute(new JsonCallback<BaseBean<String>>() {
//                    @Override
//                    public void onSuccess(Response<BaseBean<String>> response) {
//                        ToastUtils.showShort(getActivity(),getActivity().getString(R.string.modify_account_node_success));
//                        dismiss();
//                    }
//
//                    @Override
//                    public void onShowErrorMsg(int code) {
//                        ErrorMsg.showErrorMsg(getActivity(),code);
//                    }
//                });
//    }
//
//
//    @Override
//    public void onEmptyChildClick(View view) {
//
//    }
//
//    @Override
//    public void onErrorChildClick(View view) {
//        KeyboardUtils.hideSoftKeyboard(inputEt);
//        String keytag = inputEt.getText().toString().trim();
//        if (StringUtils.isEmpty(keytag)) {
//            pageNumber = 1;
//            loadData();
//        } else {
//            pageNumber = 1;
//            search();
//        }
//    }
//
//    @Override
//    public void onCustomerChildClick(View view) {
//
//    }
//}
