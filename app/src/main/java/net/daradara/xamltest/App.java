package net.daradara.xamltest;

import net.daradara.xpf.controls.Page;
import net.daradara.xpf.media.Brush;
import net.daradara.xpf.media.Color;
import net.daradara.xpf.media.SolidColorBrush;
import net.daradara.xpf.shapes.Rectangle;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class App {

    public static Page getMainPage()
    {
        Page page = new Page();
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(new SolidColorBrush(Color.fromRgb(250, 250, 250)));
        rectangle.setStroke(new SolidColorBrush(Color.fromRgb(192, 192, 192)));
        rectangle.setStrokeThickness(1.0);
        rectangle.setWidth(100.0);
        rectangle.setHeight(80.0);
        page.setContent(rectangle);

        return page;
    }
}
