package com.heroan.operation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.heroan.operation.R;

import java.util.List;

public class SimpleSpinnerAdapter extends ArrayAdapter<String>
{

	private int selectedItem = 0;
	private Context mContext;

	public SimpleSpinnerAdapter(Context context, int resource, List<String> objects)
	{
		super(context, resource, objects);
		this.mContext = context;
	}

	public SimpleSpinnerAdapter(Context context, int resource, String[] objects)
	{
		super(context, resource, objects);
		this.mContext = context;
	}

	public void setSelectedItem(int selectedItem)
	{
		this.selectedItem = selectedItem;
		this.notifyDataSetChanged();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		View view = super.getView(position, convertView, parent);
		if (view != null)
		{
			if (selectedItem == position)
			{
				((TextView) view).setTextColor(mContext.getResources().getColor(R.color.white));
				view.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
			}
			else
			{
				((TextView) view).setTextColor(mContext.getResources().getColor(R.color.black));
				view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			}
		}
		if (view != null)
		{
			view.setPadding(5, 10, 5, 10);
		}
		return view;
	}
}
