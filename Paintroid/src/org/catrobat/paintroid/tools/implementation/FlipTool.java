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
package org.catrobat.paintroid.tools.implementation;

import org.catrobat.paintroid.PaintroidApplication;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.implementation.FlipCommand;
import org.catrobat.paintroid.command.implementation.FlipCommand.FlipDirection;
import org.catrobat.paintroid.ui.button.ToolbarButton.ToolButtonIDs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class FlipTool extends BaseTool {

	public FlipTool(Context context, ToolType toolType) {
		super(context, toolType);
	}

	@Override
	public boolean handleDown(PointF coordinate) {
		if (coordinate == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean handleMove(PointF coordinate) {
		if (coordinate == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean handleUp(PointF coordinate) {
		if (coordinate == null) {
			return false;
		}
		return true;
	}

	@Override
	public void resetInternalState() {
	}

	@Override
	public int getAttributeButtonResource(ToolButtonIDs toolButtonID) {
		switch (toolButtonID) {
		case BUTTON_ID_PARAMETER_BOTTOM_1:
			return R.drawable.icon_menu_flip_vertical;
		case BUTTON_ID_PARAMETER_BOTTOM_2:
			return R.drawable.icon_menu_flip_horizontal;
		default:
			return super.getAttributeButtonResource(toolButtonID);
		}
	}

	@Override
	public void attributeButtonClick(ToolButtonIDs toolButtonID) {
		FlipDirection flipDirection = null;
		switch (toolButtonID) {
		case BUTTON_ID_PARAMETER_BOTTOM_1:
			flipDirection = FlipDirection.FLIP_HORIZONTAL;
			break;
		case BUTTON_ID_PARAMETER_BOTTOM_2:
			flipDirection = FlipDirection.FLIP_VERTICAL;
			break;
		default:
			return;
		}

		Command command = new FlipCommand(flipDirection);
		mProgressDialog.show();
		((FlipCommand) command).addObserver(this);
		PaintroidApplication.COMMAND_MANAGER.commitCommand(command);
	}

	@Override
	public int getAttributeButtonColor(ToolButtonIDs buttonNumber) {
		switch (buttonNumber) {
		case BUTTON_ID_PARAMETER_TOP:
			return Color.TRANSPARENT;
		default:
			return super.getAttributeButtonColor(buttonNumber);
		}
	}

	@Override
	public void draw(Canvas canvas) {
	}

}
