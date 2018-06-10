package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.provatsoft.apps.flag71.models.FlagItem;
import java.util.ArrayList;

public class FlagListFragment extends Fragment {
    GridView bdFlagGridView;
    ArrayList<FlagItem> flagItems;

    /* renamed from: com.provatsoft.apps.flag71.FlagListFragment.1 */
    class C04631 implements OnItemClickListener {
        C04631() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            FlagListFragment.this.showToast(v);
            FlagItem flagItem = (FlagItem) FlagListFragment.this.flagItems.get(position);
            Intent output = new Intent();
            output.putExtra(AppConfigs.kDRAWABLE_IMAGE_ID, flagItem.getFrameFlagId());
            Activity activity = FlagListFragment.this.getActivity();
            FlagListFragment.this.getActivity();
            activity.setResult(-1, output);
            FlagListFragment.this.getActivity().finish();
        }
    }

    public FlagListFragment() {
        this.flagItems = new ArrayList();
        this.flagItems.add(new FlagItem("Bangladesh", C0467R.drawable.bdflag1_512_moveable_circle, C0467R.drawable.bdflag1_512_moveable_circle));
        this.flagItems.add(new FlagItem("Bangladesh", C0467R.drawable.bijoy_71_1, C0467R.drawable.bijoy_71_1));
        this.flagItems.add(new FlagItem("Bangladesh", C0467R.drawable.bijoy_71_2, C0467R.drawable.bijoy_71_2));
        this.flagItems.add(new FlagItem("Bangladesh", C0467R.drawable.bdflag2_512, C0467R.drawable.bdflag2_512));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(C0467R.layout.fragment_flag_list, container, false);
        this.bdFlagGridView = (GridView) rootView.findViewById(C0467R.id.bdFlagGridView);
        loadBdFlags();
        return rootView;
    }

    private void showToast(View v) {
    }

    private void loadBdFlags() {
        this.bdFlagGridView.setAdapter(new FlagGridAdapter(getActivity(), this.flagItems));
        this.bdFlagGridView.setOnItemClickListener(new C04631());
    }
}
