// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package org.radf.plat.taglib;

import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

// Referenced classes of package com.lbs.cp.taglib:
//            TagUtil

public class StringInputTag extends TextTag
{

    protected String required;
    protected String disable;
    protected String label;

    public int doStartTag()
        throws JspException
    {
        ResponseUtils.write(pageContext, renderStringInput());
        return 1;
    }

    protected String renderStringInput()
        throws JspException
    {
        if("true".equalsIgnoreCase(disable))
        {
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append("<input type = 'hidden' name='");
            stringbuffer.append(property);
            stringbuffer.append("' value=\"");
            stringbuffer.append(getStringInputValue());
            stringbuffer.append("\" />");
            stringbuffer.append("<label>");
            stringbuffer.append(getStringInputValue());
            stringbuffer.append("</label>");
            return stringbuffer.toString();
        }
        StringBuffer stringbuffer1 = new StringBuffer("<input type=\"");
        stringbuffer1.append(type);
        stringbuffer1.append("\" name=\"");
        if(indexed)
            prepareIndex(stringbuffer1, name);
        stringbuffer1.append(property);
        stringbuffer1.append("\"");
        if(accesskey != null)
        {
            stringbuffer1.append(" accesskey=\"");
            stringbuffer1.append(accesskey);
            stringbuffer1.append("\"");
        }
        if(accept != null)
        {
            stringbuffer1.append(" accept=\"");
            stringbuffer1.append(accept);
            stringbuffer1.append("\"");
        }
        if(required != null)
        {
            stringbuffer1.append(" required=\"");
            stringbuffer1.append(required);
            stringbuffer1.append("\" label=\"");
            stringbuffer1.append(label).append("\" ");
        }
        if(disable != null)
        {
            stringbuffer1.append(" disable=\"");
            stringbuffer1.append(disable);
            stringbuffer1.append("\"");
        }
        if(maxlength != null)
        {
            stringbuffer1.append(" maxlength=\"");
			/*if("text".equals(type))
            {
				stringbuffer1.append(Integer.parseInt(maxlength)/2);
            }
			else
			{*/
				stringbuffer1.append(maxlength);
			//}
            stringbuffer1.append("\"");
        }
        if(cols != null)
        {
            stringbuffer1.append(" size=\"");
            stringbuffer1.append(cols);
            stringbuffer1.append("\"");
        }
        if(tabindex != null)
        {
            stringbuffer1.append(" tabindex=\"");
            stringbuffer1.append(tabindex);
            stringbuffer1.append("\"");
        }
        stringbuffer1.append(" value=\"");
        stringbuffer1.append(getStringInputValue());
        stringbuffer1.append("\"");
        stringbuffer1.append(prepareEventHandlers());
        stringbuffer1.append(prepareStyles());
        stringbuffer1.append(" class=\"text\"");
        stringbuffer1.append(getElementClose());
        return stringbuffer1.toString();
    }

    public String getStringInputValue()
    {
        if(value != null)
            return ResponseUtils.filter(value);
        if(redisplay || !"password".equals(type))
            return TagUtil.getPropertyValue(pageContext, property);
        else
            return "";
    }

    public StringInputTag()
    {
        disable = "true";
    }

    public String getRequired()
    {
        return required;
    }

    public String getDisable()
    {
        return disable;
    }

    public String getLabel()
    {
        return label;
    }

    public void setRequired(String s)
    {
        required = s;
    }

    public void setDisable(String s)
    {
        disable = s;
    }

    public void setLabel(String s)
    {
        label = s;
    }

    public void release()
    {
        super.release();
    }
}
