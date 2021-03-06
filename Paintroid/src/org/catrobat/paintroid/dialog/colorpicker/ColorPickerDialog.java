/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2012 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *    This file incorporates work covered by the following copyright and  
 *    permission notice: 
 *    
 *        Copyright (C) 2011 Devmil (Michael Lamers) 
 *        Mail: develmil@googlemail.com
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */

package org.catrobat.paintroid.dialog.colorpicker;

import java.util.ArrayList;

import org.catrobat.paintroid.R;
import org.catrobat.paintroid.dialog.BaseDialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public final class ColorPickerDialog extends BaseDialog {

	private static final String NOT_INITIALIZED_ERROR_MESSAGE = "ColorPickerDialog has not been initialized. Call init() first!";

	private ColorPickerView mColorPickerView;
	private ArrayList<OnColorPickedListener> mOnColorPickedListener;
	private int mNewColor;
	private Button mButtonNewColor;

	private static ColorPickerDialog instance;

	public interface OnColorPickedListener {
		public void colorChanged(int color);
	}

	private ColorPickerDialog(Context context) {
		super(context);
		mOnColorPickedListener = new ArrayList<ColorPickerDialog.OnColorPickedListener>();
	}

	public static ColorPickerDialog getInstance() {
		if (instance == null) {
			throw new IllegalStateException(NOT_INITIALIZED_ERROR_MESSAGE);
		}
		return instance;
	}

	public static void init(Context context) {
		instance = new ColorPickerDialog(context);
	}

	public void addOnColorPickedListener(OnColorPickedListener listener) {
		mOnColorPickedListener.add(listener);
	}

	public void removeOnColorPickedListener(OnColorPickedListener listener) {
		mOnColorPickedListener.remove(listener);
	}

	private void updateColorChange(int color) {
		for (OnColorPickedListener listener : mOnColorPickedListener) {
			listener.colorChanged(color);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.colorpicker_dialog);

		mButtonNewColor = (Button) findViewById(R.id.btn_colorchooser_ok);
		mButtonNewColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateColorChange(mNewColor);
				dismiss();
			}
		});

		mColorPickerView = (ColorPickerView) findViewById(R.id.view_colorpicker);
		mColorPickerView
				.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
					@Override
					public void colorChanged(int color) {
						changeNewColor(color);
					}
				});

	}

	public void setInitialColor(int color) {
		changeNewColor(color);
		mColorPickerView.setSelectedColor(color);
	}

	private void changeNewColor(int color) {
		mButtonNewColor.setBackgroundColor(color);

		int referenceColor = (Color.red(color) + Color.blue(color) + Color
				.green(color)) / 3;
		if (referenceColor <= 128) {
			mButtonNewColor.setTextColor(Color.WHITE);
		} else {
			mButtonNewColor.setTextColor(Color.BLACK);
		}

		mNewColor = color;
		if (mOnColorPickedListener != null) {
			updateColorChange(mNewColor);
		}
	}
}
