package geogebra.web.gui.util;

import geogebra.common.main.AbstractApplication;
import geogebra.web.awt.Dimension;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class Slider extends FocusWidget implements HasChangeHandlers, HasValue<Integer> {
	
	private static Element range;
	private boolean valueChangeHandlerInitialized;
	 
	public Slider() {
		this(0,100);
	}

	public Slider(int min, int max) {
		this(range = Document.get().createElement("input"),min,max);
	  
    }

	public Slider(Element slider, int min, int max) {
	   super(slider);
	   range.setAttribute("type", "range");
	   range.setAttribute("min", String.valueOf(min));
	   range.setAttribute("max", String.valueOf(max));   
	   range.setAttribute("value", String.valueOf(min));
    }

	public void removeChangeListener(PopupMenuButton popupMenuButton) {
	    // TODO Auto-generated method stub
	    
    }

	public void setValue(String value) {
		  DOM.setElementProperty(getElement(), "value", value != null ? value : "0");
    }

	public void addChangeListener(PopupMenuButton popupMenuButton) {
		addChangeHandler(popupMenuButton);
    }

	public Integer getValue() {
	   return Integer.valueOf(range.getAttribute("value"));
    }

	public void setMinimum(int min) {
	    range.setAttribute("min", String.valueOf(min));
    }

	public void setMaximum(int max) {
		range.setAttribute("max", String.valueOf(max));
    };

	public void setMajorTickSpacing(int step) {
		range.setAttribute("step", String.valueOf(step));
    }

	public void setMinorTickSpacing(int step) {
		range.setAttribute("step", String.valueOf(step));
    }

	public void setPaintTicks(boolean b) {
		AbstractApplication.debug("not applicable for range");
    }

	public void setPaintLabels(boolean b) {
		AbstractApplication.debug("not applicable for range");
    }

	public Dimension getPreferredSize() {
	   return new Dimension(100,10);
    }

	public HandlerRegistration addValueChangeHandler(
            ValueChangeHandler<Integer> handler) {
		 if (!valueChangeHandlerInitialized) {
		      valueChangeHandlerInitialized = true;
		      addChangeHandler(new ChangeHandler() {
		        public void onChange(ChangeEvent event) {
		          ValueChangeEvent.fire(Slider.this, getValue());
		        }
		      });
		    }
		    return addHandler(handler, ValueChangeEvent.getType());
    }

	public void setValue(Integer value) {
		setValue(String.valueOf(value));
    }

	public void setValue(Integer value, boolean fireEvents) {
		Integer oldValue = getValue();
	    setValue(String.valueOf(value));
	    if (fireEvents) {
	      ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
	    }
    }

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
    }

}
