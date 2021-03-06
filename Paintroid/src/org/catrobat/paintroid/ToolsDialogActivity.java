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
package org.catrobat.paintroid;

import org.catrobat.paintroid.dialog.DialogHelp;
import org.catrobat.paintroid.dialog.DialogTools;
import org.catrobat.paintroid.ui.button.ToolButton;
import org.catrobat.paintroid.ui.button.ToolButtonAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ToolsDialogActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {
	public static final String EXTRA_SELECTED_TOOL = "EXTRA_SELECTED_TOOL";
	protected ToolButtonAdapter mToolButtonAdapter;
	private DialogTools mDialogTools;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean openedFromCatrobat;
		Intent intent = getIntent();
		openedFromCatrobat = intent.getExtras().getBoolean(
				MainActivity.EXTRA_INSTANCE_FROM_CATROBAT);

		mToolButtonAdapter = new ToolButtonAdapter(this, openedFromCatrobat);

		int actionBarHeight = intent.getExtras().getInt(
				MainActivity.EXTRA_ACTION_BAR_HEIGHT);
		mDialogTools = new DialogTools(this, this, mToolButtonAdapter,
				actionBarHeight);
		mDialogTools.show();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View button,
			int position, long id) {
		ToolButton toolButton = mToolButtonAdapter.getToolButton(position);
		Intent resultIntent = new Intent();
		resultIntent.putExtra(EXTRA_SELECTED_TOOL,
				toolButton.buttonId.ordinal());
		setResult(Activity.RESULT_OK, resultIntent);
		mDialogTools.cancel();
		if (isFinishing() == false) {
			finish();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View button,
			int position, long id) {
		ToolButton toolButton = mToolButtonAdapter.getToolButton(position);
		new DialogHelp(this, toolButton.stringId).show();
		return true;
	}

}
