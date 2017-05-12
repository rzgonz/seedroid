package id.codigo.seedroid.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.codigo.seedroid.configs.ThirdPartyConfigs;
import id.codigo.seedroid.helper.GaHelper;
import id.codigo.seedroid.helper.GtmHelper;
import id.codigo.seedroid.presenter.BasePresenter;
import id.codigo.seedroid.view.BaseView;
import id.codigo.seedroid.view.callback.BaseCallback;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements BaseCallback<B, V, P>, BaseView {
    private B viewBinding;
    private P mvpPresenter;

    protected GtmHelper gtmHelper = ThirdPartyConfigs.isUsingGtm ? new GtmHelper() : null;
    protected GaHelper gaHelper = ThirdPartyConfigs.isUsingGtm ? new GaHelper() : null;

    public BaseActivity() {
        if (gtmHelper != null) {
            gtmHelper.setActivityClassName(getClass().getSimpleName());
        }
        if (gaHelper != null) {
            gaHelper.setActivityClassName(getClass().getSimpleName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, attachLayout());
        getMvpPresenter().onStartUI();

        if (gtmHelper != null) {
            gtmHelper.init(this);
        }
        if (gaHelper != null) {
            gaHelper.init(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gtmHelper != null) {
            gtmHelper.captureScreen();
        }
        if (gaHelper != null) {
            gaHelper.captureScreen();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBack();
        return true;
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    public void onBack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count >= 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public P getMvpPresenter() {
        if (mvpPresenter == null) {
            mvpPresenter = createPresenter();
            mvpPresenter.setMvpView(getMvpView());
        }

        return this.mvpPresenter;
    }

    @Override
    public B getViewBinding() {
        return this.viewBinding;
    }
}
