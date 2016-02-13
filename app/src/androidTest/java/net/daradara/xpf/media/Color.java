package net.daradara.xpf.media;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public class Color {
    public Color()
    {

    }

    public static Color fromArgb(int a, int r, int g, int b)
    {
        Color c = new Color();
        c.a = a;
        c.r = r;
        c.g = g;
        c.b = b;

        return c;
    }

    public static Color fromRgb(int a, int r, int g, int b)
    {
        Color c = new Color();
        c.a = 255;
        c.r = r;
        c.g = g;
        c.b = b;

        return c;
    }

    public int a, r, g, b;
}
