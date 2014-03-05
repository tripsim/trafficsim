package edu.trafficsim.engine.factory;

import java.io.Serializable;

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
