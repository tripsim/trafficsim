package edu.trafficsim.web;

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
