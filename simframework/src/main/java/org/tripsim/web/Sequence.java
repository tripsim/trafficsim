/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.web;

import java.io.Serializable;

public class Sequence implements Serializable {

	public static final long INIT_USER_SEQ = 10000;

	private static final long serialVersionUID = 1L;

	private long seq;

	public Sequence() {
		this(INIT_USER_SEQ);
	}

	public Sequence(long initSeq) {
		reset(initSeq);
	}

	public void reset(long initSeq) {
		seq = initSeq > INIT_USER_SEQ ? initSeq : INIT_USER_SEQ;
	}

	public final Long seq() {
		return seq;
	}

	public final Long nextId() {
		return seq++;
	}

	public final Long[] nextIds(int num) {
		Long[] seqs = new Long[num];
		for (int i = 0; i < num; i++)
			seqs[i] = seq++;
		return seqs;
	}
}
