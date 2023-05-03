// Generated by view binder compiler. Do not edit!
package com.example.ehealth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.ehealth.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AppbarLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton ibAppBarBack;

  @NonNull
  public final ImageButton ibAppBarFilter;

  @NonNull
  public final TextView tvAppBar;

  private AppbarLayoutBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton ibAppBarBack,
      @NonNull ImageButton ibAppBarFilter, @NonNull TextView tvAppBar) {
    this.rootView = rootView;
    this.ibAppBarBack = ibAppBarBack;
    this.ibAppBarFilter = ibAppBarFilter;
    this.tvAppBar = tvAppBar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AppbarLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AppbarLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.appbar_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AppbarLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ibAppBarBack;
      ImageButton ibAppBarBack = ViewBindings.findChildViewById(rootView, id);
      if (ibAppBarBack == null) {
        break missingId;
      }

      id = R.id.ibAppBarFilter;
      ImageButton ibAppBarFilter = ViewBindings.findChildViewById(rootView, id);
      if (ibAppBarFilter == null) {
        break missingId;
      }

      id = R.id.tvAppBar;
      TextView tvAppBar = ViewBindings.findChildViewById(rootView, id);
      if (tvAppBar == null) {
        break missingId;
      }

      return new AppbarLayoutBinding((ConstraintLayout) rootView, ibAppBarBack, ibAppBarFilter,
          tvAppBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
