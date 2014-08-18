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
package edu.trafficsim.engine.factory;

import java.io.Serializable;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class Sequence implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final long INIT_USER_SEQ = 100;
	private long seq;

	public static final Sequence create() {
		return create(INIT_USER_SEQ);
	}

	public static final Sequence create(long seq) {
		return new Sequence(seq);
	}

	Sequence(long initSeq) {
		this.seq = initSeq > INIT_USER_SEQ ? initSeq : INIT_USER_SEQ;
	}

	public final Long seq() {
		return seq;
	}

	final Long nextId() {
		return seq++;
	}

	final Long[] nextIds(int num) {
		Long[] seqs = new Long[num];
		for (int i = 0; i < num; i++)
			seqs[i] = seq++;
		return seqs;
	}

}
