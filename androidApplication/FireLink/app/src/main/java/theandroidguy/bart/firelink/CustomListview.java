package theandroidguy.bart.firelink;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.ContentHandler;

public class CustomListview extends ArrayAdapter<String>{

    private String[] devicetype;
    private Integer[] imgid;
    private Activity context;

    public CustomListview(Activity context, String[] devicetype, Integer[] imgid){
        super(context, R.layout.listviewlayout, devicetype);

        this.context = context;
        this.devicetype = devicetype;
        this.imgid = imgid;

    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listviewlayout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.txt1.setText(devicetype[position]);
        viewHolder.img.setImageResource(imgid[position]);
        return r;
    }
    class ViewHolder
    {
        TextView txt1;
        ImageView img;
        ViewHolder(View v)
        {
            txt1 = v.findViewById(R.id.devicename);
            img = v.findViewById(R.id.devicetype);
        }
    }
}
