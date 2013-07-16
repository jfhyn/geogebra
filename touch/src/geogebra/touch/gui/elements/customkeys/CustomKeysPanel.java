package geogebra.touch.gui.elements.customkeys;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class CustomKeysPanel extends PopupPanel
{
	public enum CustomKey
	{
		plus("+"), minus("\u2212"), times("\u2217", "*"), divide("/"), squared("\u00B2"), power("^"), degree("\u00B0"), pi("\u03C0"), leftpar("("), rightpar(
		    ")"), leftbracket("["), rightbracket("]"), leftbrace("{"), rightbrace("}"), equals("=");

		String s;
		private String replace;

		CustomKey(String s, String replace)
		{
			this.s = s;
			this.replace = replace;
		}

		CustomKey(String s)
		{
			this.s = s;
			this.replace = "";
		}

		@Override
		public String toString()
		{
			return this.s;
		}

		public String getReplace()
		{
			return this.replace;
		}
	}

	private HorizontalPanel buttonContainer = new HorizontalPanel();

	private List<CustomKeyListener> listeners;

	public CustomKeysPanel()
	{
		super(false, false);

		this.listeners = new ArrayList<CustomKeyListener>();
		this.setStyleName("customKeyPanel");

		for (final CustomKey k : CustomKey.values())
		{
			Button b = new Button();
			b.setText(k.toString());

			b.addDomHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					fireClickEvent(k);
				}
			}, ClickEvent.getType());

			// Specific styles for last button
			CustomKey lastKey = CustomKey.values()[CustomKey.values().length - 1];
			if (k == lastKey)
			{
				b.setStyleName("last");
			}

			this.buttonContainer.add(b);
		}

		this.add(this.buttonContainer);
	}

	public void addCustomKeyListener(CustomKeyListener l)
	{
		if (!this.listeners.contains(l))
		{
			this.listeners.add(l);
		}
	}

	public void removeCustomKeyListener(CustomKeyListener l)
	{
		this.listeners.remove(l);
	}

	protected void fireClickEvent(CustomKey key)
	{
		for (CustomKeyListener c : this.listeners)
		{
			c.onCustomKeyPressed(key);
		}
	}
}