package com.lxb.jyb.tool;

import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

public class Soft {
	
	public static void setEditTextCursorLocation(EditText editText) {
		CharSequence text = editText.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}
}
