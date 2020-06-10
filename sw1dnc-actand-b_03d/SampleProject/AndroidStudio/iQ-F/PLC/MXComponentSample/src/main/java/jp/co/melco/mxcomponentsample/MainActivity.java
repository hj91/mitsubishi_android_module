//
//  MainActivity.java
//  MXComponentSample
//
//  Copyright (c) 2015 Mitsubishi Electric Corporation. All rights reserved.
//


package jp.co.melco.mxcomponentsample;

import java.util.ArrayList;

import jp.co.melco.mxcomponentsample.MXComponentManager.MXComponentManagerCallback;
import jp.co.melco.mxcomponentsample.MainFragment.MainFragmentCallback;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity implements MainFragmentCallback,MXComponentManagerCallback{

	/** Current API Sequence Number */
	private int current_sequence=0;
	/** [Recourd] Sequence number] */
	private ArrayList<Integer> record_sequence;
	/** [Recourd] MXAPI MODE] */
	private ArrayList<Integer> record_mode;
	/** [Recourd] MXAPI ResultValue] */
	private ArrayList<Integer> record_result;
	/** [Recourd] MXAPI ResultDetailString] */
	private ArrayList<Long> record_processtime;
	/** [Recourd] MXAPI ResultDetailString] */
	private ArrayList<String>  record_detail;
	/** [Recourd] MXAPI inProgress Status] */
	private ArrayList<Boolean> record_inprogress;
	/** MX Component Manager Class */
	private MXComponentManager mxmanager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.layout_main);

	    //[initial Record data Array]
		record_sequence    = new ArrayList<Integer>();
	    record_mode        = new ArrayList<Integer>();
	    record_result      = new ArrayList<Integer>();
	    record_processtime = new ArrayList<Long>();
	    record_detail      = new ArrayList<String>();
	    record_inprogress  = new ArrayList<Boolean>();

	    //[Initalize MXComponent Management Class]
	    mxmanager=new MXComponentManager();
	    mxmanager.setCallback(this);
	}

	/**
	 *
	 * Name        : onButton Click function (override)
	 * Description : Button Event
	 *
	 */
	@Override
	public synchronized void onButtonClick(View v){


		if(mxmanager.commandexecute==true)return;


		if(v.getId()==R.id.open_button){
			//[Execute MX Componennt "OPEN" API]
			addRecord(MXMODE.OPEN,mxmanager.getOpenString());
			addTableRow();
			mxmanager.execOpen(current_sequence);
			current_sequence++;
		}
		else if(v.getId()==R.id.close_button){
			//[Execute MX Componennt "CLOSE" API]
			addRecord(MXMODE.CLOSE,"");
			addTableRow();
			mxmanager.execClose(current_sequence);
			current_sequence++;

		}
		else if(v.getId()==R.id.clear_button){
			//[Execute MX Componennt "READDEVICEBLOCK" API]

			//[ClearTableRecord]
			record_sequence.clear();
	        record_mode.clear();
	        record_result.clear();
	        record_processtime.clear();
	        record_detail.clear();
	        record_inprogress.clear();

	        clearTableRow();
		}
		else if(v.getId()==R.id.readdeviceblock_button){
			//[Execute MX Componennt "READDEVICERANDOM" API]
			addRecord(MXMODE.READ_DEVICE_BLOCK,"");
			addTableRow();
			mxmanager.execReadDeviceBlock(current_sequence);
			current_sequence++;

		}
		else if(v.getId()==R.id.writedeviceblock_button){
			//[Execute MX Componennt "WRITEDEVICEBLOCK" API]
			addRecord(MXMODE.WRITE_DEVICE_BLOCK,"");
			addTableRow();
			mxmanager.execWriteDeviceBlock(current_sequence);
			current_sequence++;

		}
		else if(v.getId()==R.id.readdevicerandom_button){
			//[Execute MX Componennt "READDEVICERANDOM" API]
			addRecord(MXMODE.READ_DEVICE_RANDOM,"");
			addTableRow();
			mxmanager.execReadDeviceRandom(current_sequence);
			current_sequence++;

		}
		else if(v.getId()==R.id.writedevicerandom_button){
			//[Execute MX Componennt "WRITEDEVICERANDOM" API]
			addRecord(MXMODE.WRITE_DEVICE_RANDOM,"");
			addTableRow();
			mxmanager.execWriteDeviceRandom(current_sequence);
			current_sequence++;
		}
		else if(v.getId()==R.id.readarraylabel_button){
			//[Execute MX Componennt "READARRAYLABEL" API]
			addRecord(MXMODE.READ_ARRAY_LABEL,"");
			addTableRow();
			mxmanager.execReadArrayLabel(current_sequence);
			current_sequence++;
		}
		else if(v.getId()==R.id.writearraylabel_button){
			//[Execute MX Componennt "WRITEARRAYLABEL" API]
			addRecord(MXMODE.WRITE_ARRAY_LABEL,"");
			addTableRow();
			mxmanager.execWriteArrayLabel(current_sequence);
			current_sequence++;

		}
		else if(v.getId()==R.id.readlabelrandom_button){
			//[Execute MX Componennt "READLABELRANDOM" API]
			addRecord(MXMODE.READ_LABEL_RANDOM,"");
			addTableRow();
			mxmanager.execReadLabelRandom(current_sequence);
			current_sequence++;
		}
		else if(v.getId()==R.id.writelabelrandom_button){
			//[Execute MX Componennt "WRITELABELRANDOM" API]
			addRecord(MXMODE.WRITE_LABEL_RANDOM,"");
			addTableRow();
			mxmanager.execWriteLabelRandom(current_sequence);
			current_sequence++;
		}

	}

	/**
	 *
	 * Name        : addTableRow function
	 * Description : Add TableView Row
	 *
	 */
	private void addTableRow(){

		int row=record_sequence.size()-1;

		if(row<0)return;

        ViewGroup vg = (ViewGroup)findViewById(R.id.tableview_record);
	    getLayoutInflater().inflate(R.layout.table_row, vg);
    	int count=vg.getChildCount();

		//[Make TableView Row]
        TableRow tablerow = (TableRow)vg.getChildAt(count-1);
        final float scale =getResources().getDisplayMetrics().density;
        int pixels = (int) (80 * scale + 0.5f);
        tablerow.setMinimumHeight(pixels);

        //[SetState]
        ImageView ivstate= (ImageView)tablerow.getChildAt(0);
        ivstate.setBackgroundColor(Color.parseColor("#FF4500"));

        //[SetMode]
        String sResult="";
        Integer mode= record_mode.get(row);
        ImageView ivmode= (ImageView)tablerow.getChildAt(1);
        if( mode.equals( MXMODE.OPEN.ordinal())){
        	ivmode.setImageResource(R.drawable.open);
        	ivmode.setBackgroundColor(Color.parseColor("#0033FF"));
        	sResult="Open";
        }else if(mode.equals( MXMODE.CLOSE.ordinal())){
        	ivmode.setImageResource(R.drawable.close);
        	ivmode.setBackgroundColor(Color.parseColor("#778899"));
        	sResult="Close";
        }else if(mode.equals( MXMODE.READ_DEVICE_BLOCK.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
        	ivmode.setImageResource(R.drawable.dev);
        	sResult="ReadDeviceBlock";
        }else if(mode.equals( MXMODE.WRITE_DEVICE_BLOCK.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
        	ivmode.setImageResource(R.drawable.dev);
        	sResult="WriteDeviceBlock";
        }else if(mode.equals( MXMODE.READ_DEVICE_RANDOM.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
        	ivmode.setImageResource(R.drawable.dev);
        	sResult="ReadDeviceRandom";
        }else if(mode.equals( MXMODE.WRITE_DEVICE_RANDOM.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
        	ivmode.setImageResource(R.drawable.dev);
        	sResult="WriteDeviceRandom";
        }else if(mode.equals( MXMODE.READ_ARRAY_LABEL.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
        	ivmode.setImageResource(R.drawable.label);
        	sResult="ReadArrayLabel";
        }else if(mode.equals( MXMODE.WRITE_ARRAY_LABEL.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
        	ivmode.setImageResource(R.drawable.label);
        	sResult="WriteArrayLabel";
        }else if(mode.equals( MXMODE.READ_LABEL_RANDOM.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
        	ivmode.setImageResource(R.drawable.label);
        	sResult="ReadLabelRandom";
        }else if(mode.equals( MXMODE.WRITE_LABEL_RANDOM.ordinal())){
        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
        	ivmode.setImageResource(R.drawable.label);
        	sResult="WriteLabelRandom";
        }

        LinearLayout layoutLv1= (LinearLayout)tablerow.getChildAt(2);
        LinearLayout layoutLv2= (LinearLayout)layoutLv1.getChildAt(0);
        TextView tvResult= (TextView)layoutLv2.getChildAt(0);
        TextView tvDetail= (TextView)layoutLv1.getChildAt(1);

        //[SetResult]
        tvResult.setText(sResult);
        tvResult.setTextColor(Color.parseColor("#FF4500"));

        //[SetDetail]
        String  sDetail=(String)record_detail.get(row);
        tvDetail.setText(sDetail);

        //[Scroll to Bottom]
        scrollbottomTableRow();

	}

	/**
	 *
	 * Name        : updateTableRow function
	 * Description : Update TableView Row
	 *
	 */
	private void updateTableRow(int seqno){

	    //[Check Sequence Number]
	    int row=getCollectionRowFromSequaceNo(seqno);

	    //[Update Match Recorad]

        if(row!=-1){

        	ViewGroup vg = (ViewGroup)findViewById(R.id.tableview_record);
	        TableRow tablerow = (TableRow)vg.getChildAt(row);

	        //[SetModeString]
	        Integer mode= record_mode.get(row);
	        ImageView ivmode= (ImageView)tablerow.getChildAt(1);
	        String sResult="";
	        if( mode.equals( MXMODE.OPEN.ordinal())){
	        	ivmode.setImageResource(R.drawable.open);
	        	ivmode.setBackgroundColor(Color.parseColor("#0033FF"));
	        	sResult="Open";
	        }else if(mode.equals( MXMODE.CLOSE.ordinal())){
	        	ivmode.setImageResource(R.drawable.close);
	        	ivmode.setBackgroundColor(Color.parseColor("#778899"));
	        	sResult="Close";
	        }else if(mode.equals( MXMODE.READ_DEVICE_BLOCK.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
	        	ivmode.setImageResource(R.drawable.dev);
	        	sResult="ReadDeviceBlock";
	        }else if(mode.equals( MXMODE.WRITE_DEVICE_BLOCK.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
	        	ivmode.setImageResource(R.drawable.dev);
	        	sResult="WriteDeviceBlock";
	        }else if(mode.equals( MXMODE.READ_DEVICE_RANDOM.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
	        	ivmode.setImageResource(R.drawable.dev);
	        	sResult="ReadDeviceRandom";
	        }else if(mode.equals( MXMODE.WRITE_DEVICE_RANDOM.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
	        	ivmode.setImageResource(R.drawable.dev);
	        	sResult="WriteDeviceRandom";
	        }else if(mode.equals( MXMODE.READ_ARRAY_LABEL.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
	        	ivmode.setImageResource(R.drawable.label);
	        	sResult="ReadArrayLabel";
	        }else if(mode.equals( MXMODE.WRITE_ARRAY_LABEL.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
	        	ivmode.setImageResource(R.drawable.label);
	        	sResult="WriteArrayLabel";
	        }else if(mode.equals( MXMODE.READ_LABEL_RANDOM.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#008080"));
	        	ivmode.setImageResource(R.drawable.label);
	        	sResult="ReadLabelRandom";
	        }else if(mode.equals( MXMODE.WRITE_LABEL_RANDOM.ordinal())){
	        	ivmode.setBackgroundColor(Color.parseColor("#892222"));
	        	ivmode.setImageResource(R.drawable.label);
	        	sResult="WriteLabelRandom";
	        }


	        LinearLayout layoutLv1= (LinearLayout)tablerow.getChildAt(2);
	        LinearLayout layoutLv2= (LinearLayout)layoutLv1.getChildAt(0);
	        TextView tvResult= (TextView)layoutLv2.getChildAt(0);
	        TextView tvTime= (TextView)layoutLv2.getChildAt(1);
	        TextView tvDetail= (TextView)layoutLv1.getChildAt(1);


	        //[SetState & ResultString]
	        Integer result= record_result.get(row);
	        ImageView ivstate= (ImageView)tablerow.getChildAt(0);
	        if(result.equals(0)){
	        	ivstate.setBackgroundColor(Color.parseColor("#32CD32"));
	        	sResult+="          SUCCESS";
	            tvResult.setText(sResult);
	            tvResult.setTextColor(Color.parseColor("#228B22"));

	        }else{
	        	ivstate.setBackgroundColor(Color.parseColor("#FF0000"));
	        	sResult+="         "+String.format("0x%08X",result);
	            tvResult.setText(sResult);
	            tvResult.setTextColor(Color.parseColor("#FF0000"));
	        }

	        //[SetDetail]
	        String  sDetail=(String)record_detail.get(row);
	        tvDetail.setText(sDetail);

	        //[SetTime]
	        String sTime=String.valueOf(record_processtime.get(row))+"msec";
	        tvTime.setText(sTime);
	    }
	}
	/**
	 *
	 * Name        : clearTableRow function
	 * Description : Clear TableView Row
	 *
	 */
	private void clearTableRow(){


        ViewGroup vg = (ViewGroup)findViewById(R.id.tableview_record);
        vg.removeAllViews();

	}

	/**
	 *
	 * Name        : scrollbottomTableRow function
	 * Description : Scroll To TableRow Bottom
	 *
	 */
	private void scrollbottomTableRow(){

		final ScrollView sv = ((ScrollView) findViewById(R.id.scrollview_record));
		sv.post(new Runnable() {
	        public void run() {
	        	sv.scrollTo(0, sv.getBottom());
	        	//
	        	sv.fullScroll(ScrollView.FOCUS_DOWN);
	        }
		});
	}


	/**
	 *
	 * Name          : addRecord
	 * Description   : Add Record Array to Data
	 * @param mode   : MXAPI mode
	 * @param detail : ResultDetailString
	 *
	 */
	private void addRecord(MXMODE mode, String detail){

	    //[Add PreExecute Recode]
	    record_sequence.add(Integer.valueOf(current_sequence));
	    record_mode.add(Integer.valueOf(mode.ordinal()));
	    record_result.add(Integer.valueOf(-1));
	    record_processtime.add(Long.valueOf(-1L));
	    record_detail.add(detail);
	    record_inprogress.add(Boolean.valueOf(true));

	}
	/**
	 *
	 * Name           : Update Record
	 * Description    : Update Record Array to Data
	 * @param seqno   : Update Record Sequence Number (if no much Record , not update)
	 * @param result  : API Result Code
	 * @param time    : API Process Time (msec)
	 * @param detail  : ResultDetailString
	 *
	 */
	private void updateRecord(int seqno, int result, long time, String detail){

	    //[Update Result Recode]

	    //[Check Sequence Number]
	    int row=getCollectionRowFromSequaceNo(seqno);

	    //[Update Match Recorad]
	    if(row!=-1){

	        record_result.set(     row, Integer.valueOf(result));
	        record_processtime.set(row, Long.valueOf(time));
	        record_detail.set(     row, new String(detail));
	        record_inprogress.set( row, Boolean.valueOf(false));
	    }

	}

	/**
	 *
	 * Name           : Get Row Sequence Number
	 * Description    : Search Record Row From Sequence Number
	 * @param seqno   : Update Record Sequence Number (if no much Record , not update)
	 * @return        : Match Row (no mach return -1)
	 *
	 */
	private int getCollectionRowFromSequaceNo(int sequence){

	    //[SearchCorrectionVirwRow by CommandSequenceNo (no match return -1)]
	    for(int index=0;index<record_sequence.size();index++){
	        if( ((Integer)record_sequence.get(index)).equals(sequence)){
	            return index;
	        }
	    }
	    return -1;
	}

	/**
	 *
	 * Name           : resultMXComponent (Delegate)
	 * Description    : Callback from MX Manager Class on API Relust
	 * @param seqno   : Update Record Sequence Number
	 * @param result  : API Result Code
	 * @param time    : API Process Time (msec)
	 * @param detail  : ResultDetailString
	 *
	 */
	public void resultMXComponent(int seqno,int result, long time ,String detail){

		//[Update Record Data]
		updateRecord(seqno, result, time, detail);
		//[Update TableView Row]
		updateTableRow(seqno);

	}

}
