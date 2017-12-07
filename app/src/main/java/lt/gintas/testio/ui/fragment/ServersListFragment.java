package lt.gintas.testio.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lt.gintas.testio.R;
import lt.gintas.testio.core.model.ServerItem;
import lt.gintas.testio.core.model.User;
import lt.gintas.testio.di.component.UserComponent;
import lt.gintas.testio.ui.adapter.ServersAdapter;
import lt.gintas.testio.ui.mvp.presenter.ServersListPresenter;
import lt.gintas.testio.ui.mvp.view.ServersListView;

/**
 * Created by gintautas on 07/12/2017.
 */

public class ServersListFragment extends BaseFragment implements ServersListView {

    private Unbinder mUnbinder;

    @Inject
    ServersListPresenter mPresenter;

    @Inject
    User mUser;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.serversList)
    RecyclerView serversList;

    List<ServerItem> serverItems = new ArrayList<>();
    ServersAdapter serversAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle savedInstanceState) {
        View view = inf.inflate(R.layout.fragment_servers_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initialize() {
        this.getComponent(UserComponent.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);

        serversList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        serversAdapter = new ServersAdapter(serverItems);
        serversList.setAdapter(serversAdapter);

        if (isAdded() && !getActivity().isFinishing())
            loadServerList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadServerList();
            }
        });
    }

    private void loadServerList() {
        mPresenter.getServerItems(getResources().getString(R.string.token_secret) + " "
                + mUser.getToken());
    }

    @Override
    public void onDestroyView() {
        mPresenter.destroy();
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onShowLoading() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onServerListLoaded(List<ServerItem> serverItems) {
        this.serverItems.clear();
        this.serverItems.addAll(serverItems);
        serversAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        shortToast(getResources().getString(R.string.error));
    }
}
