package ir.pepotec.app.awesomeapp.view.uses;

import android.content.Context;
import android.widget.ImageView;
import cn.lightsky.infiniteindicator.ImageLoader;
import com.bumptech.glide.Glide;

/**
 * Created by usb-web on 12/12/2017.
 */

public class GlideLoader implements ImageLoader {

    public void initLoader(Context context) {

    }

    @Override
    public void load(Context context, ImageView targetView, Object res) {

        if (res instanceof String){
            Glide.with(context)
                    .load( res)
                    .centerCrop()
                    .into(targetView);
        }
    }
}