package lt.gintas.testio.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.pnikosis.materialishprogress.ProgressWheel;

import lt.gintas.testio.R;
import timber.log.Timber;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class LoadingDialog {

    private final View mView;
    private final Activity mActivity;
    private Dialog dialog = null;
    private ProgressWheel mProgress;

    private LoadingDialog(Builder builder) {
        mProgress = builder.mProgress;
        mView = builder.mView;
        mActivity = builder.mActivity;
    }

    public void show() {
        // Create dialog
        if (dialog == null) {
            dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(mView);

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setDimAmount(0);
            }
        }

        try {
            mProgress.spin();
            dialog.show();
        } catch (Exception e) {
            Timber.e(e, "error showing loading dialog");
            e.printStackTrace();
        }
    }

    public void hide() {
        if (dialog != null && !mActivity.isFinishing()) {
            mProgress.stopSpinning();
            dialog.dismiss();
        }
    }

    public static class Builder {

        private final ProgressWheel mProgress;
        private final View mView;
        private final Activity mActivity;

        public Builder(Activity activity) {
            mActivity = activity;
            mView = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null);
            mProgress = mView.findViewById(R.id.progress_wheel);
        }

        public LoadingDialog build() {
            return new LoadingDialog(this);
        }
    }
}
