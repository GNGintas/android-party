package lt.gintas.testio.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import lt.gintas.testio.App;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.R;
import lt.gintas.testio.di.component.AppComponent;
import lt.gintas.testio.di.module.ActivityModule;
import lt.gintas.testio.ui.dialog.LoadingDialog;

/**
 * Created by Gintautas on 2016-12-11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected AppPreferences appPrefs;
    LoadingDialog mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        getAppComponent().inject(this);
        ButterKnife.bind(this);
        appPrefs = new AppPreferences(this);

        mProgress = new LoadingDialog.Builder(this).build();
    }

    protected abstract int getLayoutResId();

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void shortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Observable<Boolean> dialog(final String titleRes, final String messageRes, final String confirmRes, final String cancelRes) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final @NonNull ObservableEmitter<Boolean> e) throws Exception {
                try {
                    final Dialog dialog = new Dialog(BaseActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_alert_2_buttons);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);

                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText(titleRes);
                    TextView message = (TextView) dialog.findViewById(R.id.message);
                    message.setText(messageRes);
                    TextView confirm = (TextView) dialog.findViewById(R.id.confirm);
                    if (confirmRes.length() == 0) {
                        confirm.setVisibility(View.GONE);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.setCancelable(true);
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                e.onNext(true);
                                e.onComplete();
                            }
                        });
                    } else confirm.setText(confirmRes);
                    TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
                    cancel.setText(cancelRes);

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            e.onNext(true);
                            e.onComplete();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            e.onNext(false);
                            e.onComplete();
                        }
                    });
                    dialog.show();
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    Dialog transfer;
    public void closeTransferMoney() {
        if (transfer != null)
            transfer.dismiss();
    }

}
