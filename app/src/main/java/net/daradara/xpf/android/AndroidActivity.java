package net.daradara.xpf.android;

import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import net.daradara.xpf.controls.Page;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class AndroidActivity extends AppCompatActivity {
    public void setPage(Page page)
    {
        AndroidView view = new AndroidView(getBaseContext());
        view.setContent(page);
        setContentView(view);
    }
}
