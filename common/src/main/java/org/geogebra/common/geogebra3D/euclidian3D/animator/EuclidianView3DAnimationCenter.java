package org.geogebra.common.geogebra3D.euclidian3D.animator;

import org.geogebra.common.geogebra3D.euclidian3D.EuclidianView3D;
import org.geogebra.common.geogebra3D.euclidian3D.animator.EuclidianView3DAnimator.AnimationType;
import org.geogebra.common.kernel.Matrix.Coords;

/**
 * animation for centering view
 *
 */
public class EuclidianView3DAnimationCenter extends EuclidianView3DAnimation {

	private double xEnd, yEnd, zEnd;

	/**
	 * 
	 * @param view3D
	 * @param animator
	 * @param p
	 *            point to center about
	 */
	public EuclidianView3DAnimationCenter(EuclidianView3D view3D, EuclidianView3DAnimator animator, Coords p) {

		super(view3D, animator);
		xEnd = -p.getX();
		yEnd = -p.getY();
		zEnd = -p.getZ();
	}

	public void setupForStart() {
		// nothing to do
	}

	public AnimationType getType() {
		return AnimationType.TRANSLATION;
	}

	public void animate() {
		view3D.setXZero(xEnd);
		view3D.setYZero(yEnd);
		view3D.setZZero(zEnd);
		view3D.getSettings().updateOriginFromView(xEnd, yEnd, zEnd);

		// update the view
		view3D.updateTranslationMatrix();
		view3D.updateUndoTranslationMatrix();
		view3D.setGlobalMatrices();

		view3D.setViewChangedByTranslate();

		end();
	}


}