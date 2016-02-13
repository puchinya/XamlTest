package net.daradara.xpf.media;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public class FormattedText {

    public FormattedText(String text, Typeface typeface,
                         double emSize, Brush foreground)
    {
        m_text = text;
    }

    private @NonNull String getText() {
        return m_text;
    }

    private String m_text;
}

