package net.daradara.xpf.data;

/**
 * Created by masatakanabeshima on 2016/01/21.
 */
public class Binding {

    public Binding()
    {

    }

    public Binding(String path)
    {
        m_path = path;
    }

    public Object getSource()
    {
        return m_source;
    }

    public void setSource(Object value)
    {
        m_source = value;
    }

    public String getPath()
    {
        return m_path;
    }

    public void setPath(String value)
    {
        m_path = value;
    }

    public BindingMode getMode()
    {
        return m_mode;
    }

    public void setMode(BindingMode value)
    {
        m_mode = value;
    }

    public Object getTargetNullValue()
    {
        return m_targetNullValue;
    }

    public void setTargetNullValue(Object value)
    {
        m_targetNullValue = value;
    }

    private Object m_source = null;
    private String m_path = null;
    private BindingMode m_mode = BindingMode.DEFAULT;
    private Object m_targetNullValue = null;
}
