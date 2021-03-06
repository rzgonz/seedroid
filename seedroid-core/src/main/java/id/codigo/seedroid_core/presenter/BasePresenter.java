package id.codigo.seedroid_core.presenter;

import id.codigo.seedroid_core.view.BaseView;

/**
 * Created by papahnakal on 12/12/17.
 */

public abstract class BasePresenter<V extends BaseView> {

    private V mvpView;

    /**
     * Getter mvpView variable
     */
    public V getMvpView() {
        return mvpView;
    }

    /**
     * Setter mvpView variable
     */
    public void setMvpView(V mvpView) {
        this.mvpView = mvpView;
    }

}
