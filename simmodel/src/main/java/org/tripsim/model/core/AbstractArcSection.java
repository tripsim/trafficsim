/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.tripsim.model.core;

import java.util.Collections;
import java.util.List;

import org.tripsim.api.model.Arc;
import org.tripsim.api.model.ArcSection;
import org.tripsim.model.util.Coordinates;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractArcSection extends AbstractArc implements
		ArcSection {

	private static final long serialVersionUID = 1L;

	// the offset distance for the section within the arc
	// positive number towards downstream, and negative number towards upstream
	protected double startOffset;
	protected double endOffset;

	// in meter
	protected double width;
	// shift from the center line of link
	protected double shift;

	protected final Arc parent;

	public AbstractArcSection(long id, Arc parent, double startOffset,
			double endOffset, double width, double shift) {
		super(id);
		if (parent == null) {
			throw new IllegalArgumentException(
					"Parent Arc cannot be null for ArcSection!");
		}
		this.parent = parent;

		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.width = width;
		this.shift = shift;
		onGeomUpdated();
	}

	private void fixkStartEnd(double start, double end) {
		try {
			checkStartEnd(start, end);
		} catch (RuntimeException e) {
			this.startOffset = this.endOffset = 0;
		}
	}

	private void checkStartEnd(double start, double end) {
		if (start - end > parent.getLength()) {
			throw new IllegalArgumentException(
					"Section element has negative length with the start and end settings!");
		}
	}

	@Override
	public final Arc getParent() {
		return parent;
	}

	@Override
	public final List<ArcSection> getSections() {
		return Collections.emptyList();
	}

	@Override
	public final double getStart() {
		return startOffset;
	}

	@Override
	public final void setStartOffset(double startOffset) {
		if (this.startOffset == startOffset) {
			return;
		}
		checkStartEnd(startOffset, endOffset);
		this.startOffset = startOffset;
		onGeomUpdated();
	}

	@Override
	public final double getEnd() {
		return endOffset;
	}

	@Override
	public final void setEndOffset(double endOffset) {
		if (this.endOffset == endOffset)
			return;

		checkStartEnd(startOffset, endOffset);
		this.endOffset = endOffset;
		onGeomUpdated();
	}

	@Override
	public final double getShift() {
		return shift;
	}

	@Override
	public final void setShift(double shift, boolean update) {
		if (update) {
			onShiftUpdate(shift - this.shift);
		}
		if (this.shift == shift)
			return;
		this.shift = shift;
		onGeomUpdated();
	}

	@Override
	public final double getWidth() {
		return width;
	}

	@Override
	public final void setWidth(double width, boolean update) {
		if (update) {
			onWidthUpdate(width - this.width);
		}
		if (this.width == width)
			return;
		this.width = width;
		onGeomUpdated();
	}

	private void updateLinearGeom() {
		try {
			linearGeom = Coordinates.getOffSetLineString(getCrs(),
					parent.getLinearGeom(), shift);
			fixkStartEnd(startOffset, endOffset);
			linearGeom = Coordinates.trimLinearGeom(getCrs(), linearGeom,
					startOffset, -endOffset);
		} catch (Exception e) {
			throw new RuntimeException("Geometry update failed on subsegment!",
					e);
		}
	}

	@Override
	public void onGeomUpdated() {
		updateLinearGeom();
		calculateLength();
	}

	abstract protected void onShiftUpdate(double offset);

	abstract protected void onWidthUpdate(double offset);

}
