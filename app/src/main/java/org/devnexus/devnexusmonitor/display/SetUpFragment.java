package org.devnexus.devnexusmonitor.display;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.devnexus.devnexusmonitor.R;

/**
 * Created by summers on 1/1/17.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class SetUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.setup, null);
    }

    public static SetUpFragment newInstance() {
        return new SetUpFragment();
    }
}
