package geogebra.common.plugin.script;

import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.main.App;
import geogebra.common.plugin.Event;
import geogebra.common.plugin.EventType;
import geogebra.common.plugin.ScriptError;
import geogebra.common.plugin.ScriptType;
import geogebra.common.util.StringUtil;

import java.util.ArrayList;

/**
 * @author arno
 * Parent class for script objects.  There is one script class for each
 * type of script.  They must be added to the enum ScriptType.
 */
public abstract class Script {
	
	/**
	 * Application the script belongs to
	 */
	protected final App app;
	/**
	 * source code for the script
	 */
	protected String text;
	
	/**
	 * @param app the script's application
	 * @param text the script's source code
	 */
	public Script(App app, String text) {
		super();
		this.app = app;
		this.text = text;
	}
	
	/**
	 * Get the script's source code
	 * @return the source code as a string
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Get the script's internal text (which could be different from the 
	 * localized text)
	 * @return the internal text
	 */
	public String getInternalText () {
		return text;
	}
	
	/**
	 * Perform actions necessary (if any) to bind the script to
	 * a GeoElement via a given EventType
	 * @param geo the geo
	 * @param evtType the event type
	 */
	public void bind(GeoElement geo, EventType evtType) {
		// Do nothing by default here
	}
	
	/**
	 * Perform actions necessary (if any) to unbind the script from
	 * a GeoElement via a given EventType
	 * @param geo the geo
	 * @param evtType the event type
	 */
	public void unbind(GeoElement geo, EventType evtType) {
		// Do nothing by default here
	}
	
	/**
	 * Run the script
	 * @param evt the event that triggered the script
	 * @throws ScriptError error thrown if the script cannot be run
	 */
	public abstract void run(Event evt) throws ScriptError;
	
	/**
	 * Get the script's type
	 * @return the script's type
	 */
	public abstract ScriptType getType();
	
	/**
	 * Get the language name of the script (convenience function)
	 * @return the language name
	 */
	public String getLanguageName() {
		return this.getType().getName();
	}

	/**
	 * @return the XML attribute name for serialization to file
	 */
	public Object getXMLName() {
		return this.getType().getXMLName();
	}

	/**
	 * @return a new copy of this object
	 */
	public abstract Script copy();

	/**
	 * The text of this script is modified by changing every
	 * whole word oldLabel to newLabel.
	 * 
	 * @return whether any renaming happened
	 */
	public boolean renameGeo(String oldLabel, String newLabel) {
		if (oldLabel == null || "".equals(oldLabel) ||
			newLabel == null || "".equals(newLabel)) {
			return false;
		}
		ArrayList<String> work = StringUtil.wholeWordTokenize(text);
		boolean ret = false;
		for (int i = 1; i < work.size(); i += 2) {
			if (oldLabel.equals(work.get(i))) {
				// now it's still possible that oldLabel
				// is used as a command name here,
				// so we have to rule out that possibility first.
				// Luckily, command names are always followed
				// by a [, as far as we know, so it is easy.
				if (i+1 < work.size() && work.get(i+1) != null &&
					work.get(i+1).length() > 0 &&
					"[".equals(work.get(i+1).charAt(0))) {
					// this is a command name, so false positive
					// do nothing
				} else {
					// this is really something to be renamed!
					// ...or not? (pi, e, i, JavaScript, etc) TODO: check
					work.set(i, newLabel);
					ret = true;
				}
			}
		}
		text = StringUtil.joinTokens(work, null);
		return ret;
	}
}
