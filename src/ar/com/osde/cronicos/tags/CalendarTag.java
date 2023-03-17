package ar.com.osde.cronicos.tags;

import java.net.MalformedURLException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.RequestUtils;

/**
 * Taglib para mostrar calendario
 *
 * @author Javier Licanic y Adrian Metinez
 * 
 * @version $Id: CalendarTag.java,v 1.2 2005/09/23 18:12:55 adrian Exp $
 */

public class CalendarTag extends TextTag {
	private static final String 
		CALENDARIO_IMG = "/jsp/cronicos/images/date.gif";
	
    private String styleButton;
    private String imgLink;

    /**
     * @exception JspTagException exception
     * @return int
     */
	public int doStartTag() throws JspTagException {

		try {
			StringBuffer buffer = new StringBuffer("");
			buffer.append("<table>");
		    buffer.append("<tr>");			
		    buffer.append("<td>");			
		    if (this.getSize() == null || "".equals(this.getSize())) {
		        this.setSize("10");
		    }
		    if (this.getMaxlength() == null || "".equals(this.getMaxlength())) {
			    this.setMaxlength("10");
		    }
		    this.setStyleId(this.getProperty());
		    pageContext.getOut().println(buffer.toString());
		    super.doStartTag();
		    
		    buffer = new StringBuffer("");
		    buffer.append("</td>");
		    buffer.append("<td>");
			buffer.append("<img src=\"");
			buffer.append(convertPath(pageContext, CALENDARIO_IMG));
			buffer.append("\"");
			if (!"actualiza".equals(this.getImgLink())){
				buffer.append(" onclick=\"return showCalendar('");
			} 
			buffer.append(this.getProperty());
			buffer.append("');\"");
			if (!"".equals(this.getStyleButton())) {
			    buffer.append(" style=\"" + getStyleButton() + "\" ");
			}
			buffer.append("/>");
			buffer.append("</td></tr></table>");
			
			
			pageContext.getOut().println(buffer.toString());

		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		
		//CalendarTag.EVAL_BODY_AGAIN
		return (2);
	}
	
	private String convertPath(PageContext pageContext, String path) {
		String url = "";
		try {
			url =
				RequestUtils.computeURL(
					pageContext,
					null,
					null,
					path,
					null,
					null,
					null,
					false);
		} catch (MalformedURLException e) {
		    url = path;
		}
		return url;
	}	
	
    /**
     * @return Returns the styleButton.
     */
    public String getStyleButton() {
        return this.styleButton;
    }
    
    /**
     * @param pStyleButton The styleButton to set.
     */
    public void setStyleButton(String pStyleButton) {
        this.styleButton = pStyleButton;
    }
	/**
	 * @return Returns the imgLink.
	 */
	public String getImgLink() {
		return this.imgLink;
	}
	/**
	 * @param imgLink The imgLink to set.
	 */
	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
}
