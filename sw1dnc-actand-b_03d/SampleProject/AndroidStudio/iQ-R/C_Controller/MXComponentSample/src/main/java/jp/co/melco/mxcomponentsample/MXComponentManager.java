package jp.co.melco.mxcomponentsample;
//
//  MXComponentManager.Java
//  MXComponentSample
//
//  Copyright (c) 2015 Mitsubishi Electric Corporation. All rights reserved.
//

import java.util.Random;

import jp.co.melco.mxcomponent.MELMxCommunication;
import jp.co.melco.mxcomponent.MELMxLabel;
import jp.co.melco.mxcomponent.MELMxOpenSettings;
import android.os.AsyncTask;

public class MXComponentManager {

	 //[Interface MXComponent result Callback]
	public static interface MXComponentManagerCallback {
		public void resultMXComponent(int seqno,int result, long time ,String detail);
	}

	//[Load Library]
	static{
		try {
			System.loadLibrary("MXComponent");
	    } catch (UnsatisfiedLinkError e) {
	        System.out.println("loadLibrary Errer");
	    }
	}

	//[MX Component Communication Class]
	private MELMxCommunication mxcomm;
	//[MX Component OpenSetting Class]
	private MELMxOpenSettings mxopen;
	//[MX Component "Open"API Password argumet]
	private String password = "";
	//[MXComponent result Callback Delegate Pointer]
	private MXComponentManagerCallback callback = null;

	//[Command Execute Status Flag]
	public Boolean commandexecute=false;

	String detailstring;


	public void setCallback(MXComponentManagerCallback callbackclass) {

		callback = callbackclass;
	}

	/**
	 *
	 * Name        : Class Constructor
	 * Description : Initialize Class Member variable and Status
	 *
	 */
	public MXComponentManager() {

        //[initialize MX Component]
		mxcomm = new MELMxCommunication();
		mxopen = new MELMxOpenSettings();

        //[Call OpenSettingClass Initalize function]
		setOpenSetting();


	}
	/**
	 *
	 * Name        : Open Setting Initalize
	 * Description : Initialize MELMxOpenSetting Class Members
	 *
	 */
	private void setOpenSetting(){

	    //[NOTE:Change below Value for User's Environment]

	    //[Set MELSEC HostAddress (Hostname or IPv4String)]
	    mxopen.hostAddress="192.168.2.100";
	    //[Set MELSEC Port]
	    mxopen.destinationPortNumber=5007;
	    //[Set MELSEC UnitTypeNumber (see User's Manual)]
	    mxopen.unitType=0x1002; //[ex. iQ-R-Ether Direct]
	    //[Set MELSEC CPUTypeNumber (see User's Manual)]
	    mxopen.cpuType=0x1021;  //[ex. R12CCPU-V]
	    //[Set MELSEC RemotePassword (if needed)]
	    password="";
	    //[Set MELSEC I/ONumber (see User's Manual)]
	    mxopen.ioNumber=0x03E1;

	}

	/**
	 *
	 * Name        : Open MELSEC CONNECTION
	 * Description : Execute MX Component "Open" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execOpen(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Open" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Open" API]
				return  mxcomm.open(mxopen, password);
			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {

	                //[set DetailResultString Host/port String]
					detailstring=getOpenString();


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}


	/**
	 *
	 * Name        : Close MELSEC CONNECTION
	 * Description : Execute MX Component "Close" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execClose(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Close" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Close" API]
				return  mxcomm.close();
			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {

	                //[set DetailResultString blank]
					detailstring="";

					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	/**
	 *
	 * Name        : Read Device Block from MELSEC
	 * Description : Execute MX Component "Read Device Block" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execReadDeviceBlock(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Read Device Block" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Read Device Block" API]
		        //[ex. Device:"D100" size:"1"]
		        int readdata[]=new int[1];
		        int result = mxcomm.readDeviceBlock("D100",1,readdata);

		        //[if API Success Set DetailResultString Readed data("fault" set blank)]
		        if(result==0)
		        	detailstring="D100=【" + String.valueOf(readdata[0]) +"】";
		        else
		        	detailstring="";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	/**
	 *
	 * Name        : Write Device Block to MELSEC
	 * Description : Execute MX Component "Write Device Block" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execWriteDeviceBlock(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Write Device Block" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {


		        int writedata[]=new int[1];

		        Random rnd = new Random();
		        writedata[0]= (short)rnd.nextInt(100);


		        //[call "Write Device Block" API]
		        //[ex. Device:"D100" size:"1"]
		        int result = mxcomm.writeDeviceBlock("D100",1,writedata);

		        //[Set DetailResultString Write data]
	        	detailstring="D100=【" + String.valueOf(writedata[0]) +"】";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}




	/**
	 *
	 * Name        : Read Device Random from MELSEC
	 * Description : Execute MX Component "Read Device Random" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execReadDeviceRandom(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Read Device Random" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Read Device Random" API]
		        //[ex. Device:"D100"/"M0"/"LZ0"]
				String devicenames[] = { "D100","M0","LZ0" };
		        int readdata[]=new int[3];

		        int result = mxcomm.readDeviceRandom(devicenames,readdata);

		        //[if API Success Set DetailResultString Readed data("fault" set blank)]
		        if(result==0)
		        	detailstring="D100=【" + String.valueOf(readdata[0]) +"】 M0=【"+ String.valueOf(readdata[1]==0?"OFF":"ON") +"】 LZ0=【"+ String.valueOf(readdata[2]) +"】";
		        else
		        	detailstring="";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}


	/**
	 *
	 * Name        : Write Device Random to MELSEC
	 * Description : Execute MX Component "Write Device Random" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execWriteDeviceRandom(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Write Device Random" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Read Device Random" API]
		        //[ex. Device:"D100"/"M0"/"LZ0"]
				String devicenames[] = { "D100","M0","LZ0" };
		        int writedata[]=new int[3];
		        //[Make Write Data 0〜99 pseudorandom]
		        Random rnd = new Random();
		        writedata[0]= (short)rnd.nextInt(100);
		        //[Make Write Data 0〜1 pseudorandom]
		        rnd = new Random();
		        writedata[1]= (short)rnd.nextInt(2);
		        //[Make Write Data 0〜9999999 pseudorandom]
		        rnd = new Random();
		        writedata[2]= (short)rnd.nextInt(10000000);

		        int result = mxcomm.writeDeviceRandom(devicenames,writedata);

		        //[Set DetailResultString Write data]
	        	detailstring="D100=【" + String.valueOf(writedata[0]) +"】 M0=【"+ (writedata[1]==0?"OFF":"ON") +"】 LZ0=【"+ String.valueOf(writedata[2]) +"】";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	/**
	 *
	 * Name        : Read Array Label from MELSEC
	 * Description : Execute MX Component "Read Array Label" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execReadArrayLabel(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Read Array Label" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Read Array Label" API]
		        //[ex. Label:"wLabel(WordType) Size:1]
		        MELMxLabel label= new MELMxLabel();
		        try{
		        	label.setWordLabel("wLabel[0]",null,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        int result =mxcomm.readArrayLabel(label);


		        //[if API Success Set DetailResultString Readed data("fault" set blank)]
		        if(result==0)
		        	detailstring="wLabel=【" + String.valueOf( label.getValues()[0] ) +"】";
		        else
		        	detailstring="";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	/**
	 *
	 * Name        : Write Array Label to MELSEC
	 * Description : Execute MX Component "ReadWriteArray Label" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execWriteArrayLabel(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Write Array Label" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Write Array Label" API]
		        //[ex. Label:"wLabel(WordType) Size:1]
		        MELMxLabel label= new MELMxLabel();

		        //[Make Write Data 0〜99 pseudorandom]
		        Integer writedata[]=new Integer[1];
		        Random rnd = new Random();
		        writedata[0]= Integer.valueOf((int)rnd.nextInt(100));
		        try{
		        	label.setWordLabel("wLabel[0]",writedata,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        int result =mxcomm.writeArrayLabel(label);


		        //[if API Success Set DetailResultString Readed data("fault" set blank)]
		        detailstring="wLabel=【" + String.valueOf( writedata[0] ) +"】";


		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	/**
	 *
	 * Name        : Read Label Random from MELSEC
	 * Description : Execute MX Component "Read Label Random" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execReadLabelRandom(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Read Label Random" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Read Label Random" API]
		        //[ex. Label:"wLabel(WordType)"/"bLabel(BitType)"/"sLabel(UnicodeString:32bytes)" Size:1]
				MELMxLabel[] labels = new MELMxLabel[3];

				//[Word Label Class Setting]
				MELMxLabel wlabel= new MELMxLabel();
		        try{
		        	wlabel.setWordLabel("wLabel[0]",null,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        labels[0]=wlabel;

		        //[Bit Label Class Setting]

		        MELMxLabel blabel= new MELMxLabel();
		        try{
		        	blabel.setBitLabel("bLabel[0]",null,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        labels[1]=blabel;

		        //[UnicodeString Label Class Setting]
				MELMxLabel slabel= new MELMxLabel();
		        try{
		        	slabel.setUnicodeStringLabel("sLabel[0]",32L,null,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        labels[2]=slabel;


		        int result =mxcomm.readLabelRandom(labels);


		        //[if API Success Set DetailResultString Readed data("fault" set blank)]
		        if(result==0){
		            Boolean bdata=(Boolean)labels[1].getValues()[0];

		        	detailstring="wLabel[0]=【" + String.valueOf( labels[0].getValues()[0] ) +"】 bLabel[0]=【"
		        	                            +  (bdata==false?"OFF":"ON")   +"】 sLabel[0]=【"
		        	                            + String.valueOf( labels[2].getValues()[0] ) +"】";
		        }else
		        	detailstring="";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}


	/**
	 *
	 * Name        : Write Label Random to MELSEC
	 * Description : Execute MX Component "Write Label Random" Function
	 * @param seqno: Execute sequence number (result callback with this number)
	 *
	 */
	public void execWriteLabelRandom(int seqno) {

	    //[check command exetcute state ]
	    if(commandexecute)return;

	    //[set command exetcute state]
	    commandexecute=true;

	    final int seq=seqno;

	    //[Call MX "Write Label Random" by Background Thread]
		new AsyncTask<Void, Void, Integer>() {

	        //[Set Process Start Time]
			long start = System.currentTimeMillis();

			@Override
			protected Integer doInBackground(Void... arg0) {

		        //[call "Write Label Random" API]
		        //[ex. Label:"wLabel(WordType)"/"bLabel(BitType)"/"sLabel(UnicodeString:32bytes)" Size:1]
				MELMxLabel[] labels = new MELMxLabel[3];

				//[Word Label Class Setting]
				MELMxLabel wlabel= new MELMxLabel();
				Integer wdata[]=new Integer[1];
				try{
			        Random rnd = new Random();
			        wdata[0]= Integer.valueOf(rnd.nextInt(100));
		        	wlabel.setWordLabel("wLabel[0]",wdata,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        labels[0]=wlabel;

		        //[Bit Label Class Setting]

		        MELMxLabel blabel= new MELMxLabel();
				Boolean bdata[]=new Boolean[1];
		        try{
			        Random rnd = new Random();
			        bdata[0]= rnd.nextInt(2)%2==0?Boolean.valueOf(false):Boolean.valueOf(true);

		        	blabel.setBitLabel("bLabel[0]",bdata,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        labels[1]=blabel;

		        //[UnicodeString Label Class Setting]
				MELMxLabel slabel= new MELMxLabel();
				String sdata[]=new String[1];
		        try{
		        	sdata[0]=getRandomString();
		        	slabel.setUnicodeStringLabel("sLabel[0]",32L,sdata,1);
		        }catch(Exception e){
		        	detailstring="";
		        	return 0xFFFFFFFF;
		        }
		        labels[2]=slabel;

		        int result =mxcomm.writeLabelRandom(labels);

		        //[if API Success Set DetailResultString Readed data("fault" set blank)]
		         detailstring="wLabel[0]=【" + String.valueOf( wdata[0] ) +"】 bLabel[0]=【"
		        	                            +  (bdata[0]==false?"OFF":"ON")   +"】 sLabel[0]=【"
		        	                            + String.valueOf( sdata[0] ) +"】";

		        return result;

			}
			@Override
			protected void onPostExecute(Integer result){

	            //[Check Pointer(not nul)]
				if (null != callback) {


					//[Set Process Duration]
					long interval = System.currentTimeMillis()-start;

	                //[Call result callback]
					callback.resultMXComponent(seq,(int)result,interval,detailstring);

				}
	            //[set command exetcute state]
			    commandexecute=false;

				return ;

			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}


	/**
	 *
	 * Name        : Make Random String for String Label Data
	 * Description : Make Random String (10charctor uppercase Alphabet)
	 * @return     : Make String
	 *
	 */
	private String getRandomString(){

	    byte sdata[]=new byte[10];
	    for(int index=0;index<10;index++){
	        //[MakeNumber 0-25 ,add offset ASCII Uppercase A(65)]
		    Random rnd = new Random();
		    sdata[index]=(byte)((byte)(rnd.nextInt(26)) + (byte)(65));
	    }

	    //[Convert Byte to String]
	    String sRandom="";
	    try{
	    	sRandom=new String(sdata,"US-ASCII");
	    }catch(Exception e){

	    }
	    return sRandom;
	}

	/**
	 *
	 * Name        : Get Connect Stringh from OpenSettings
	 * Description : Make Conect Host and Port Description String
	 * @return     : Make String
	 *
	 */
	public String getOpenString(){

		return "Host=【"+String.valueOf(mxopen.getHostAddress())+"】Port=【"+String.valueOf(mxopen.getDestinationPortNumber())+"】IoNumber=【"+(Integer.toHexString( mxopen.getIoNumber())).toUpperCase()+"】";
	}


}
