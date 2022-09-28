package com.impsfundtransfer.Entity;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomGenId implements IdentifierGenerator {
	private static final long LIMIT = 1000000000000000L;
	private static long last = 0;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// 15 digits.
		long id = System.currentTimeMillis() % LIMIT;
		if (id <= last) {
			id = (last + 1) % LIMIT;
		}
		return last = id;
	}

}
