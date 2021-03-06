package org.catrobat.paintroid.test.integration.tools;

import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.PaintroidApplication;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.test.integration.BaseIntegrationTestClass;
import org.catrobat.paintroid.test.utils.PrivateAccess;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.Tool.ToolType;
import org.catrobat.paintroid.ui.DrawingSurface;
import org.catrobat.paintroid.ui.Toolbar;
import org.catrobat.paintroid.ui.implementation.DrawingSurfaceImplementation;
import org.junit.Before;

import android.graphics.PointF;

public class FillToolIntegrationTest extends BaseIntegrationTestClass {

	protected Toolbar mToolbar;

	public FillToolIntegrationTest() throws Exception {
		super();
	}

	@Override
	@Before
	protected void setUp() {
		super.setUp();
		resetBrush();
		try {
			mToolbar = (Toolbar) PrivateAccess.getMemberValue(MainActivity.class, getActivity(), "mToolbar");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void testBitmapIsFilled() throws InterruptedException {
		assertTrue("Waiting for DrawingSurface", mSolo.waitForView(DrawingSurfaceImplementation.class, 1, TIMEOUT));

		selectTool(ToolType.FILL);
		Tool mFillTool = mToolbar.getCurrentTool();

		int colorToFill = mToolbar.getCurrentTool().getDrawPaint().getColor();
		DrawingSurface drawingSurface = (DrawingSurfaceImplementation) getActivity().findViewById(
				R.id.drawingSurfaceView);
		int xCoord = 100;
		int yCoord = 200;
		PointF pointOnBitmap = new PointF(xCoord, yCoord);
		int colorBeforeFill = drawingSurface.getBitmapColor(pointOnBitmap);

		PointF pointOnScreen = new PointF(pointOnBitmap.x, pointOnBitmap.y);
		PaintroidApplication.CURRENT_PERSPECTIVE.convertFromScreenToCanvas(pointOnScreen);

		mSolo.clickOnScreen(pointOnScreen.x, pointOnScreen.y); // to fill the bitmap
		mSolo.sleep(4000);
		int colorAfterFill = drawingSurface.getBitmapColor(pointOnBitmap);
		assertEquals("Pixel color should be the same", colorToFill, colorAfterFill);
	}
}
