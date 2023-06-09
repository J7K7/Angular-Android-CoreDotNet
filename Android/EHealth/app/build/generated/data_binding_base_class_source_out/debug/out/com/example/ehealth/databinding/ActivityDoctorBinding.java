// Generated by view binder compiler. Do not edit!
package com.example.ehealth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.ehealth.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDoctorBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppbarLayoutBinding appbar;

  @NonNull
  public final RecyclerView rvDoctorActivity;

  private ActivityDoctorBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppbarLayoutBinding appbar, @NonNull RecyclerView rvDoctorActivity) {
    this.rootView = rootView;
    this.appbar = appbar;
    this.rvDoctorActivity = rvDoctorActivity;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDoctorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDoctorBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_doctor, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDoctorBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appbar;
      View appbar = ViewBindings.findChildViewById(rootView, id);
      if (appbar == null) {
        break missingId;
      }
      AppbarLayoutBinding binding_appbar = AppbarLayoutBinding.bind(appbar);

      id = R.id.rvDoctorActivity;
      RecyclerView rvDoctorActivity = ViewBindings.findChildViewById(rootView, id);
      if (rvDoctorActivity == null) {
        break missingId;
      }

      return new ActivityDoctorBinding((ConstraintLayout) rootView, binding_appbar,
          rvDoctorActivity);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
