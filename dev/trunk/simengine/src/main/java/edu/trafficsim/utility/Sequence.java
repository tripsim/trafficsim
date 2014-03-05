package edu.trafficsim.utility;

import java.io.Serializable;

public class Sequence implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final long INIT_USER_SEQ = 100;
	private long seq;

	public Sequence() {
		seq = INIT_USER_SEQ;
	}

	public Sequence(long initSeq) {
		this.seq = initSeq > INIT_USER_SEQ ? initSeq : INIT_USER_SEQ;
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
