package com.railinc.totoro.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.railinc.totoro.domain.IdentityType;

public class WebFormConstants {
	
	public static final PropertyEditor timestampPropertyEditor() {
		return new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true);	
	}
	public static final PropertyEditor identityTypeEditor() {
		return new PropertyEditorSupport() {
			@Override
			public String getAsText() {
				return String.valueOf(getValue());
			}
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(IdentityType.find(text));
			}
		};
	}

}
