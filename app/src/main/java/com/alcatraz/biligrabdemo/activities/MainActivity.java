package com.alcatraz.biligrabdemo.activities;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import java.util.*;
import com.alcatraz.biligrabdemo.search.*;
import android.widget.AdapterView.*;
import com.alcatraz.biligrabdemo.bean.Season;
import com.alcatraz.biligrabdemo.bean.Episodes;
import com.alcatraz.biligrabdemo.*;
import android.content.*;
import java.io.*;
import android.net.*;

public class MainActivity extends Activity 
{
	//Views
	View actionbar;
	EditText search;
	LinearLayout empty;
	LinearLayout detail;
	ListView search_view;
	ListView episode;
	TextView title;
	TextView coin;
	TextView danmaku;
	TextView status;
	//Data
	List<Result> data=new LinkedList<Result>();
	List<Episodes> data_1=new LinkedList<Episodes>();
	SearchAdapter sa;
	SearchAdapter sa_1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		findViews();
		setupActionBar();
		initViews();
		if (getIntent().getAction() == Intent.ACTION_SEND)
		{
			final String extra=getIntent().getStringExtra(Intent.EXTRA_TEXT);
			if ((extra != null && !extra.equals("")))
			{
				Utils.NetWork.connectToBili(Utils.getCallbackAddressByBangumiId(Utils.StringCutting.getBangumiIdFromIntentExtra(extra)), new Utils.NetWorkCallBack(){

						@Override
						public void onReceiveRawContent(final String raw)
						{
							runOnUiThread(new Runnable(){

									@Override
									public void run()
									{
										
										final Season s=Utils.getSeasonObjFromCallback(Utils.StringCutting.unWrapBiliCallBack(raw));
										data_1.clear();
										data_1.addAll(s.getResult().getEpisodes());
										runOnUiThread(new Runnable(){

												@Override
												public void run()
												{
													stage_3(s);
													// TODO: Implement this method
												}
											});
										// TODO: Implement this method
									}
								});
							// TODO: Implement this method
						}
					});
			}
		}
    }
	public void findViews()
	{
		actionbar = getLayoutInflater().inflate(R.layout.m_query_action, null);
		search = (EditText) actionbar.findViewById(R.id.mqueryactionEditText1);
		empty = (LinearLayout) findViewById(R.id.mainLinearLayout1);
		detail = (LinearLayout) findViewById(R.id.mainLinearLayout2);
		search_view = (ListView) findViewById(R.id.mainListView2);
		episode = (ListView) findViewById(R.id.mainListView1);
		title = (TextView) findViewById(R.id.mainTextView1);
		status = (TextView) findViewById(R.id.mainTextView2);
		coin = (TextView) findViewById(R.id.mainTextView3);
		danmaku = (TextView) findViewById(R.id.mainTextView4);
	}
	public void initViews()
	{
		search.setOnEditorActionListener(new EditText.OnEditorActionListener(){

				@Override
				public boolean onEditorAction(TextView p1, int p2, KeyEvent p3)
				{
					if (p2 == EditorInfo.IME_ACTION_DONE)
					{
						String key=search.getText().toString().trim();
						flushState(key);
					}
					// TODO: Implement this method
					return true;
				}
			});
		sa = new SearchAdapter(this, data);
		sa_1 = new SearchAdapter(this, data_1, true);
		search_view.setAdapter(sa);
		episode.setAdapter(sa_1);
		search_view.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					Result cl=(Result) p1.getItemAtPosition(p3);
					String getU=Utils.getCallbackAddressByBangumiId(cl.getSeason_id() + "");
					Utils.NetWork.connectToBili(getU, new Utils.NetWorkCallBack(){

							@Override
							public void onReceiveRawContent(String raw)
							{
								final Season s=Utils.getSeasonObjFromCallback(Utils.StringCutting.unWrapBiliCallBack(raw));
								data_1.clear();
								data_1.addAll(s.getResult().getEpisodes());
								runOnUiThread(new Runnable(){

										@Override
										public void run()
										{
											stage_3(s);
											// TODO: Implement this method
										}
									});
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			});
		episode.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					Episodes ep=(Episodes) p1.getItemAtPosition(p3);
					download(ep.getCover(), new downloadCallBack(){

							@Override
							public void onComplete(File down)
							{
								toast("下载完成," + down.getAbsolutePath());
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			});
	}
	public void stage_3(Season s)
	{
		empty.setVisibility(View.GONE);
		search_view.setVisibility(View.GONE);
		detail.setVisibility(View.VISIBLE);
		title.setText(s.getResult().getTitle());
		status.setText("番剧ID:" + s.getResult().getBangumi_id());
		coin.setText("硬币:" + s.getResult().getCoins());
		danmaku.setText("弹幕数:" + s.getResult().getDanmaku_count());
		sa_1.notifyDataSetChanged();
	}
	public void download(final String url1, final downloadCallBack call)
	{
		final DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		Uri uri = Uri.parse(url1);
		DownloadManager.Request request = new DownloadManager.Request(uri);
		File dire=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/");
		dire.mkdirs();
		final File f=new File(dire.getAbsolutePath() + "/" + url1.split("/")[url1.split("/").length - 1]);
		request.setDestinationUri(Uri.fromFile(f));
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.allowScanningByMediaScanner();
		request.setVisibleInDownloadsUi(true);
		final long refernece = dManager.enqueue(request);
		IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		BroadcastReceiver receiver = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent)
			{
				long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				if (refernece == myDwonloadID)
				{
					call.onComplete(f);
				}
			}
		};
		registerReceiver(receiver, filter);
	}
	interface downloadCallBack
	{
		public void onComplete(File down);
	}
	public void flushState(String key)
	{
		Utils.NetWork.runSearch(key, new Utils.NetWorkCallBack(){

				@Override
				public void onReceiveRawContent(final String raw)
				{
					runOnUiThread(new Runnable(){

							@Override
							public void run()
							{
								SearchJson sj=Utils.getSeachFromCallback(raw);
								data.clear();
								data.addAll(sj.getResult());
							}
						});
					// TODO: Implement this method
				}
			});
		sa.notifyDataSetChanged();
		empty.setVisibility(View.GONE);
		detail.setVisibility(View.GONE);
		search_view.setVisibility(View.VISIBLE);
	}
	public void setupActionBar()
	{
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setTitle(null);
		getActionBar().setCustomView(actionbar);
	}
	public void toast(String con)
	{
		Toast.makeText(this, con, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		new MenuInflater(this).inflate(R.menu.main, menu);
		// TODO: Implement this method
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		String key=search.getText().toString().trim();
		flushState(key);
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}

}
