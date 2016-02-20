package net.daradara.xpf.controls;

import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.ResourceDictionary;
import net.daradara.xpf.TriggerCollection;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class ControlTemplate extends FrameworkElement {
    public ControlTemplate()
    {

    }

    public ControlTemplate(Class targetType)
    {
        m_targetType = targetType;
    }

    private boolean m_hasContent = false;
    private Class m_targetType = null;
    private ResourceDictionary m_resources = new ResourceDictionary();
    private TriggerCollection m_triggers = new TriggerCollection();
}
