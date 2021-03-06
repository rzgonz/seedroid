package id.codigo.seedroid_core.delegate;

import android.databinding.ViewDataBinding;

import id.codigo.seedroid_core.presenter.BasePresenterBinding;
import id.codigo.seedroid_core.view.BaseView;

/**
 * Created by papahnakal on 25/10/17.
 */

public interface RootDelegateBinding<B extends ViewDataBinding, V extends BaseView, P extends BasePresenterBinding> {
    /**
     * Function that return id of layout resource
     */
    int attachLayout();
    /**
     * Function that return new class of mvpPresenter
     */
    P createPresenter();
    /**
     * Getter mvpView variable
     */
    V getMvpView();
    /**
     * Getter mvpPresenter variable
     */
    P getMvpPresenter();
    /**
     * Getter ViewDataBinding variable
     */
    B getViewBinding();
}
