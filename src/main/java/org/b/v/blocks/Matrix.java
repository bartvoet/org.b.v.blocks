package org.b.v.blocks;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Matrix<T> extends AbstractSet<Map.Entry<Position,T>>{
	
	private LinkedList<LinkedList<T>> elements = new LinkedList<LinkedList<T>>();
	private int columns;
	private int size=0;
	
	public Matrix() {
		columns=0;
	}
	
	public boolean existsElementAt(int x,int y) {
		return false;
	}
	
	public T getElementAt(int x,int y) {
		if(x >= this.columns || y >= this.elements.size() ) {
			return null;
		}
		LinkedList<T> row = getRow(y);
		if(x >= row.size()) {
			return null;
		}
		return row.get(x);
	}
	
	private LinkedList<T> getRow(int y) {
		if(y + 1 > elements.size()) {
			for(int i=0;i < (y + 1 - elements.size());i++) {
				this.elements.addLast(new LinkedList<T>());
			}
		}
		return this.elements.get(y);
	}
	
	private void setElementAtColumn(int x,T t,LinkedList<T> row) {
		if(t == null) {
			return;
		}
		
		if(x >= this.columns) {
			this.columns=x+1;
		}
		if(this.columns > row.size()) {
			adaptSize(row);
		}
		
		T currenValue = row.get(x);
		
		if(currenValue==null && t != null) {
			this.size++;
		}
		if(currenValue!=null && t==null) {
			this.size--;
		}
		
		row.set(x,t);
	}

	private void adaptSize(LinkedList<T> row) {
		int gap = this.columns - row.size();
		for(int i=0;i < gap;i++) {
			row.addLast(null);
		}
	}
	
	public Matrix<T> setElementAt(T t,int x,int y) {
		if(x<0) {
			insertColumnsAtStart(Math.abs(x));
		}
		
		if(y<0) {
			insertRowsAtStart(Math.abs(y));
		}
		setElementAtColumn((x < 0 ? 0 : x), t, getRow(y < 0 ? 0 : y));	
		return this;
	}


	public Matrix<T> setElementAt(T t,Position position) {
		setElementAt(t,position.getX(),position.getY());
		return this;
	}

	
	public void insertColumAtStart() {
		this.columns++;
		for(LinkedList<T> row : this.elements) {
			row.addFirst(null);
		}
	}
	
	public void insertColumnsAtStart(int n) {
		this.columns++;
		for(LinkedList<T> row : this.elements) {
			for(int i=0;i<n;i++) {
				row.addFirst(null);
			}
		}
	}

	private void insertRowsAtStart(int n) {
		for(int i=0;i<n;i++) {
			elements.addFirst(new LinkedList<T>());
		}
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
		//TODO make sure to clean up rows withouth elements
	}

	public Position lookForFirstOccurence(T value) {
		if(value==null) {
			return null;
		}
		
		int y=0;
		
		for(LinkedList<T> row : this.elements) {
			int x=0;
			for(T t:row) {
				if(value.equals(t)) {
					return new 	Position(x,y);
				}
				x++;
			}
			y++;
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(LinkedList<T> row : elements) {
			builder.append(row.toString()).append("\n");
		}
		return builder.toString();
	}

	@Override
	public Iterator<Entry<Position, T>> iterator() {
		return new Iterator<Entry<Position,T>>() {

			private int row=0;
			private int column=0;
			
			@Override
			public boolean hasNext() {
				
				return false;
			}

			@Override
			public Entry<Position, T> next() {
				
				
				return null;
			}
			
		};
	}

	@Override
	public int size() {
		return this.size;
	}
}
