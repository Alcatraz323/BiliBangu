package com.alcatraz.biligrabdemo;
import android.content.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.alcatraz.biligrabdemo.search.*;
import java.util.*;
import android.graphics.*;
import android.app.*;
import com.alcatraz.biligrabdemo.bean.Episodes;


public class SearchAdapter extends BaseAdapter
{
	List<Result> data;
	List<Episodes> data_1;
	Activity ctx;
	LayoutInflater lf;
	boolean isdetail;
	public SearchAdapter(Activity ctx,List<Result> data){
		this.ctx=ctx;
		lf=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
		this.data=data;
		isdetail=false;
	}
	public SearchAdapter(Activity ctx,List<Episodes> data,boolean differ){
		this.ctx=ctx;
		lf=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
		this.data_1=data;
		isdetail=true;
	}
	@Override
	public int getCount()
	{
		if(isdetail){
		// TODO: Implement this method
			return data_1.size();
		}else{
			return data.size();
		}
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		if(isdetail){
			// TODO: Implement this method
			return data_1.get(p1);
		}else{
			return data.get(p1);
		}
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		if(p2==null){
			p2=lf.inflate(R.layout.searchitem,null);
		}
		final ImageView imgv=(ImageView) p2.findViewById(R.id.searchitemImageView1);
		TextView txv1=(TextView) p2.findViewById(R.id.searchitemTextView1);
		TextView txv2=(TextView) p2.findViewById(R.id.searchitemTextView2);
		if(isdetail){
			Episodes ep=data_1.get(p1);
			Utils.NetWork.returnBitMap(ep.getCover(), new Utils.BitmapReceive(){

					@Override
					public void onReceiveBitmap(final Bitmap bm)
					{
						ctx.runOnUiThread(new Runnable(){

								@Override
								public void run()
								{
									imgv.setImageBitmap(bm);
									// TODO: Implement this method
								}
							});
						// TODO: Implement this method
					}
				});

			txv1.setText("第"+ep.getIndex()+"集  av"+ep.getAv_id());
			txv2.setText(ep.getIndex_title());
		}else{
			Result cur=data.get(p1);
			Utils.NetWork.returnBitMap("http:" + cur.getCover(), new Utils.BitmapReceive(){

					@Override
					public void onReceiveBitmap(final Bitmap bm)
					{
						ctx.runOnUiThread(new Runnable(){

								@Override
								public void run()
								{
									imgv.setImageBitmap(bm);
									// TODO: Implement this method
								}
							});
						// TODO: Implement this method
					}
				});

			txv1.setText(cur.getTitle());
			txv2.setText("更新至"+cur.getTotal_count()+"集");
		}
		// TODO: Implement this method
		return p2;
	}
	
}
