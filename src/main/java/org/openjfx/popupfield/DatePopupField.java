package org.openjfx.popupfield;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * TextPopupField
 */
public class DatePopupField extends PopupField {

	public DatePopupField(String name) {
		super(name);
		setField(new DatePicker());
	}

	@Override
	public LocalDate getValue() {
		return ((DatePicker)getField()).getValue();
	}

	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}
}
