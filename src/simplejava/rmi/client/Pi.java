package simplejava.rmi.client;

import java.io.Serializable;

import simplejava.rmi.Task;

public class Pi implements Task<Double>, Serializable{


	private static final long serialVersionUID = 6717009150246159071L;

	@Override
	public Double execute() {
		return Math.PI;
	}

}
