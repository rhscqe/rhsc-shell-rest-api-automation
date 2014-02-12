/**
 * 
 */
package com.redhat.qe.helpers;

import java.util.Iterator;

import com.google.common.base.Function;

/**
 * @author dustin 
 * Jan 14, 2013
 */
public class Times implements Iterable<Integer> {

	
	private class Itr implements Iterator<Integer>{

		int size;
		int cursor = -1;
		
		public Itr(int size){
			this.size = size;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return cursor < size -1;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Integer next() {
			cursor = cursor + 1;
			return cursor;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			// TODO do nothing
			
		}
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */

	private int numTimes;
	
	public Times(int numTimes){
		this.numTimes = numTimes;
	}

	public Iterator<Integer> iterator() {
		return new Itr(numTimes);
	}

	
	

}
