package jp.co.melco.mxcomponentsample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements OnClickListener {

//	private static final String TAG = "MainFragment";

	private MainFragmentCallback callback;

	public static interface MainFragmentCallback {
		//[MainFragment Callback Declare]

		public void onButtonClick(View v);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		//[Check Activity Support Callback Method]
		if (activity instanceof MainFragmentCallback == false) {
			throw new ClassCastException("non support menu callbacl");
		}
		callback = (MainFragmentCallback) activity;
	}

	public MainFragment() {


	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		
		//[Set Button's Listener]
		Button bt = (Button) v.findViewById(R.id.open_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.close_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.readdeviceblock_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.writedeviceblock_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.readdevicerandom_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.writedevicerandom_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.readarraylabel_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.writearraylabel_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.readlabelrandom_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.writelabelrandom_button);
		bt.setOnClickListener(this);

		bt = (Button) v.findViewById(R.id.clear_button);
		bt.setOnClickListener(this);

		return v;
	}

    @Override
    public void onClick(View v) {


    	//[On Click Buttons Call Activity Callback Method]
		if (null != callback) {

			callback.onButtonClick(v);
		}

	}

}
