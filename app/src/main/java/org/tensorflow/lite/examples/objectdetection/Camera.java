package org.tensorflow.lite.examples.objectdetection;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
        mv = {1, 6, 0},
        k = 1,
        d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\n"},
        d2 = {"Lorg/tensorflow/lite/examples/objectdetection/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "activityMainBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/ActivityMainBinding;", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class Camera extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding var10001 = ActivityMainBinding.inflate(this.getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(var10001, "ActivityMainBinding.inflate(layoutInflater)");
        this.activityMainBinding = var10001;
        var10001 = this.activityMainBinding;
        if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activityMainBinding");
        }

        this.setContentView((View)var10001.getRoot());
    }

    public void onBackPressed() {
        if (VERSION.SDK_INT == 29) {
            this.finishAfterTransition();
        } else {
            super.onBackPressed();
        }

    }
}