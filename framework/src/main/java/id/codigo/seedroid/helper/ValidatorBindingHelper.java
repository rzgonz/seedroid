package id.codigo.seedroid.helper;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import id.codigo.seedroid.R;
import id.codigo.seedroid.validation.rules.Rule;
import id.codigo.seedroid.validation.utils.ViewTagHelper;

/**
 * Created by papahnakal on 07/07/17.
 */

public class ValidatorBindingHelper {
    private static final int FIELD_VALIDATION_MODE = 0;
    private static final int FORM_VALIDATION_MODE = 1;

    private ViewDataBinding target;
    private ValidationListener validationListener;

    private int mode = FIELD_VALIDATION_MODE;
    private final Set<View> disabledViews;

    private String failMessage;
    private String successMessage;

    public ValidatorBindingHelper(ViewDataBinding target) {
        this.target = target;
        this.disabledViews = new HashSet<>();
    }
    public ValidatorBindingHelper setFailMessage(String message){
        this.failMessage = message;
        return this;
    }
    public ValidatorBindingHelper setSuccessMessage(String message){
        this.successMessage = message;
        return this;
    }

    public void setValidationListener(ValidationListener validationListener) {
        this.validationListener = validationListener;
    }

    public void validatesCallback() {
        if (validationListener == null) throw new IllegalArgumentException("Validation listener should not be null.");

        if (validates()) {
            if (successMessage!=null){
                validationListener.onValidationSuccess(successMessage);
            }else {
                validationListener.onValidationSuccess("");
            }
        } else {
            if(failMessage!=null) {
                validationListener.onValidationError(failMessage);
            }else{
                validationListener.onValidationError("");
            }
        }
    }
    public void validateCallback(View v) {
        if (validationListener == null) throw new IllegalArgumentException("Validation listener should not be null.");

        if (validate(v)) {
            if (successMessage!=null){
                validationListener.onValidationSuccess(successMessage);
            }else {
                validationListener.onValidationSuccess("");
            }
        } else {
            if(failMessage!=null) {
                validationListener.onValidationError(failMessage);
            }else{
                validationListener.onValidationError("");
            }
        }
    }
    public <ViewType extends View> void validateListCallback(List<ViewType> v) {
        if (validationListener == null) throw new IllegalArgumentException("Validation listener should not be null.");

        if (validateList(v)) {
            if (successMessage!=null){
                validationListener.onValidationSuccess(successMessage);
            }else {
                validationListener.onValidationSuccess("");
            }
        } else {
            if(failMessage!=null) {
                validationListener.onValidationError(failMessage);
            }else{
                validationListener.onValidationError("");
            }
        }
    }

    public boolean validates() {
        List<View> viewWithValidations = getViewsWithValidation();
        return isAllViewsValid(viewWithValidations);
    }

    public boolean validate(View view) {
        List<View> viewWithValidations = getViewsWithValidation(view);
        return isAllViewsValid(viewWithValidations);
    }

    public <ViewType extends View> boolean validateList(List<ViewType> views) {
        List<View> viewWithValidations = getViewsWithValidation(views);
        return isAllViewsValid(viewWithValidations);
    }

    private boolean isAllViewsValid(List<View> viewWithValidations) {
        boolean allViewsValid = true;
        for (View viewWithValidation : viewWithValidations) {
            boolean viewValid = true;
            List<Rule> rules = (List) viewWithValidation.getTag(R.id.validator_rule);
            for (Rule rule : rules) {
                viewValid = viewValid && isRuleValid(rule);
                allViewsValid = allViewsValid && viewValid;
            }

            if(mode == FIELD_VALIDATION_MODE && !viewValid) {
                break;
            }
        }
        return allViewsValid;
    }

    private boolean isRuleValid(Rule rule) {
        return disabledViews.contains(rule.getView()) || rule.validate();
    }

    public void disableValidation(View view) {
        disabledViews.add(view);
    }

    public void enableValidation(View view) {
        disabledViews.remove(view);
    }

    public void enableFormValidationMode() {
        this.mode = FORM_VALIDATION_MODE;
    }

    public void enableFieldValidationMode() {
        this.mode = FIELD_VALIDATION_MODE;
    }

    private List<View> getViewsWithValidation() {
        if(target.getRoot() instanceof ViewGroup) {
            return ViewTagHelper.getViewsByTag((ViewGroup) target.getRoot(), R.id.validator_rule);
        }
        return Collections.singletonList(target.getRoot());
    }

    private <ViewType extends View> List<View> getViewsWithValidation(List<ViewType> views) {
        return ViewTagHelper.filterViewsWithTag(R.id.validator_rule, views);
    }

    private List<View> getViewsWithValidation(View view) {
        return ViewTagHelper.filterViewWithTag(R.id.validator_rule, view);
    }

    public interface ValidationListener {

        void onValidationSuccess(String message);

        void onValidationError(String message);
    }
}