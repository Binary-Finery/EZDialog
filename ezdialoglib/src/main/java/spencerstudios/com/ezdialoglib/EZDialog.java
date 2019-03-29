package spencerstudios.com.ezdialoglib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EZDialog {

    public static class Builder {

        private String
                title,
                message,
                positiveBtnText,
                negativeBtnText,
                neutralBtnText;

        private int
                backgroundColor,
                headerColor,
                titleDividerColor,
                buttonTextColor,
                titleTextColor,
                messageTextColor;

        private boolean
                cancelOnTouchOutside = true,
                showTitleDivider = true;

        private Context context;

        private EZDialogListener
                positiveListener,
                negativeListener,
                neutralListener;

        private Animation animation;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAnimation(Animation animation) {
            this.animation = animation;
            return this;
        }

        public Builder setCancelableOnTouchOutside(boolean cancelOnTouchOutside) {
            this.cancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder showTitleDivider(boolean showTitleDivider) {
            this.showTitleDivider = showTitleDivider;
            return this;
        }

        public Builder setTitleDividerLineColor(int titleDividerColor) {
            this.titleDividerColor = titleDividerColor;
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setMessageTextColor(int messageTextColor) {
            this.messageTextColor = messageTextColor;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setHeaderColor(int headerColor) {
            this.headerColor = headerColor;
            return this;
        }

        public Builder setButtonTextColor(int buttonTextColor) {
            this.buttonTextColor = buttonTextColor;
            return this;
        }

        public Builder setPositiveBtnText(String positiveBtnText) {
            this.positiveBtnText = positiveBtnText;
            return this;
        }

        public Builder setNegativeBtnText(String negativeBtnText) {
            this.negativeBtnText = negativeBtnText;
            return this;
        }

        public Builder setNeutralBtnText(String neutralBtnText) {
            this.neutralBtnText = neutralBtnText;
            return this;
        }

        public Builder OnPositiveClicked(EZDialogListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder OnNegativeClicked(EZDialogListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder OnNeutralClicked(EZDialogListener neutralListener) {
            this.neutralListener = neutralListener;
            return this;
        }

        public Builder isCancellable(boolean cancel) {
            this.cancelOnTouchOutside = cancel;
            return this;
        }

        public void build() {

            int style;

            if (animation == Animation.UP) style = R.style.UpTheme;
            else if (animation == Animation.DOWN) style = R.style.DownTheme;
            else style = 0;

            final AlertDialog dialog = style != 0 ?
                    new AlertDialog.Builder(context, style).create() :
                    new AlertDialog.Builder(context).create();

            dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);

            @SuppressLint("InflateParams")
            View v = LayoutInflater.from(context).inflate(R.layout.ez_dialog_layout, null);
            dialog.setView(v);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            TextView tvTitle = v.findViewById(R.id.title),
                    tvMessage = v.findViewById(R.id.message);

            Button btnNegative = v.findViewById(R.id.cancel),
                    btnPositive = v.findViewById(R.id.confirm),
                    btnNeutral = v.findViewById(R.id.neutral);

            LinearLayout llRoot = v.findViewById(R.id.ll_dialog_sub_view);

            View titleDivider = v.findViewById(R.id.title_divider);

            titleDivider.setVisibility(showTitleDivider ? View.VISIBLE : View.INVISIBLE);

            tvTitle.setText(title);
            tvMessage.setText(message);

            if (backgroundColor != 0) {
                llRoot.setBackgroundColor(backgroundColor);
            }

            if (headerColor != 0) {
                tvTitle.setBackgroundColor(headerColor);
            }

            if (titleTextColor != 0) {
                tvTitle.setTextColor(titleTextColor);
            }

            if (messageTextColor != 0) {
                tvMessage.setTextColor(messageTextColor);
            }

            if (titleDividerColor != 0) {
                titleDivider.setBackgroundColor(titleDividerColor);
            }

            if (buttonTextColor != 0) {
                btnPositive.setTextColor(buttonTextColor);
                btnNegative.setTextColor(buttonTextColor);
                btnNeutral.setTextColor(buttonTextColor);
            }

            if (positiveBtnText != null) {
                btnPositive.setText(positiveBtnText);
            }

            if (negativeBtnText != null) {
                btnNegative.setText(negativeBtnText);
            }

            if (neutralBtnText != null) {
                btnNeutral.setText(neutralBtnText);
            }

            if (positiveListener != null) {
                btnPositive.setVisibility(View.VISIBLE);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        positiveListener.OnClick();
                        dialog.dismiss();
                    }
                });

            } else {
                btnPositive.setVisibility(View.GONE);
            }

            if (negativeListener != null) {
                btnNegative.setVisibility(View.VISIBLE);
                btnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        negativeListener.OnClick();
                        dialog.dismiss();
                    }
                });
            } else {
                btnNegative.setVisibility(View.GONE);
            }

            if (neutralListener != null) {
                btnNeutral.setVisibility(View.VISIBLE);
                btnNeutral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        neutralListener.OnClick();
                        dialog.dismiss();
                    }
                });
            } else {
                btnNeutral.setVisibility(View.GONE);
            }

            dialog.show();
        }
    }
}
