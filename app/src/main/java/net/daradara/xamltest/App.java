package net.daradara.xamltest;

import net.daradara.xpf.Thickness;
import net.daradara.xpf.controls.Orientation;
import net.daradara.xpf.controls.Page;
import net.daradara.xpf.controls.StackPanel;
import net.daradara.xpf.controls.TextBlock;
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
        StackPanel panel = new StackPanel();

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setFill(new SolidColorBrush(Color.fromRgb(250, 250, 250)));
        rectangle1.setStroke(new SolidColorBrush(Color.fromRgb(192, 192, 192)));
        rectangle1.setStrokeThickness(1.0);
        rectangle1.setWidth(100.0);
        rectangle1.setHeight(80.0);
        rectangle1.setMargin(new Thickness(10.0));
        panel.getChildren().add(rectangle1);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setFill(new SolidColorBrush(Color.fromRgb(250, 250, 250)));
        rectangle2.setStroke(new SolidColorBrush(Color.fromRgb(192, 192, 192)));
        rectangle2.setStrokeThickness(1.0);
        rectangle2.setWidth(100.0);
        rectangle2.setHeight(80.0);
        rectangle2.setMargin(new Thickness(10.0));
        panel.getChildren().add(rectangle2);

        TextBlock tb = new TextBlock();
        tb.setText("Sample");
        tb.setForeground(new SolidColorBrush(Color.fromRgb(192, 192, 192)));
        panel.getChildren().add(tb);

        page.setContent(panel);

        return page;
    }
}
